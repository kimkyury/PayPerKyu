// apps/web/src/lib/calculatePlan.ts

export type RiskProfile = 'conservative' | 'neutral' | 'aggressive';

export type IncomeStability = 'stable' | 'variable';

export type AllocationCategory =
    | 'fixed'
    | 'variable'
    | 'emergency'
    | 'invest'
    | 'debt'
    | 'insurance';

export interface DebtItem {
    type?: string;
    balance: number; // KRW
    apr: number;     // e.g. 0.09 for 9%
    minPayment?: number; // KRW (optional)
}

export interface Inputs {
    monthlyNetIncome: number;             // 세후 월 실수령
    riskProfile: RiskProfile;             // 성향
    emergencyFundMonths: number;          // 현재 비상금 (개월)
    targetEmergencyMonths: number;        // 목표 비상금 (개월)
    debts?: DebtItem[];                   // 부채 목록
    incomeStability?: IncomeStability;    // 소득 안정성
    dependents?: number;                  // 부양가족 수
}

export interface Allocation {
    category: AllocationCategory;
    percent: number; // 0~100
    amount: number;  // KRW
}

export interface PlanResult {
    allocations: Allocation[];
    rulesTriggered: string[];
    advice: string[];
    notes?: string[];
}

/** 안전한 수치 보정 */
const clamp = (n: number, min: number, max: number) => Math.max(min, Math.min(max, n));
const round1 = (n: number) => Math.round(n * 10) / 10;

function baseTemplate(risk: RiskProfile) {
    // 퍼센트 총합 = 100
    // 고정35 / 변동15는 고정 축, 나머지 분배
    if (risk === 'conservative')
        return { fixed: 35, variable: 15, emergency: 15, invest: 20, debt: 10, insurance: 5 };
    if (risk === 'aggressive')
        return { fixed: 35, variable: 15, emergency: 5, invest: 35, debt: 5, insurance: 5 };
    return { fixed: 35, variable: 15, emergency: 10, invest: 25, debt: 10, insurance: 5 };
}

/** 특정 키들에서 합을 맞추기 위해 비율을 깎아내는 헬퍼 */
function takeFromBuckets(
    buckets: Record<AllocationCategory, number>,
    need: number,
    order: AllocationCategory[]
) {
    let remain = need;
    for (const key of order) {
        if (remain <= 0) break;
        const canTake = Math.min(remain, Math.max(0, buckets[key] - MIN_FLOOR[key]));
        buckets[key] -= canTake;
        remain -= canTake;
    }
}

const MIN_FLOOR: Record<AllocationCategory, number> = {
    fixed: 30,      // 고정지출 최소 바닥
    variable: 8,    // 변동지출 최소 바닥
    emergency: 0,   // 상황 따라 0도 허용
    invest: 12,     // 장기투자 최소 바닥
    debt: 5,        // 부채 최소 바닥
    insurance: 3,   // 보장 최소 바닥
};

const HIGH_INTEREST_APR = 0.08; // 8% 이상 고금리 기준

export function calculatePlan(input: Inputs): PlanResult {
    const advice: string[] = [];
    const notes: string[] = [];
    const rulesTriggered: string[] = [];

    if (!isFinite(input.monthlyNetIncome) || input.monthlyNetIncome <= 0) {
        return {
            allocations: [],
            rulesTriggered: [],
            advice: ['월 실수령을 0보다 크게 입력해 주세요.'],
        };
    }

    // 1) 베이스 템플릿
    const t = baseTemplate(input.riskProfile);

    // 2) 규칙 적용
    // 2-1) 비상금 부족 시 비율 가산 (5~15% 범위)
    if (input.emergencyFundMonths < input.targetEmergencyMonths) {
        const gap = input.targetEmergencyMonths - input.emergencyFundMonths;
        const boost = clamp(5 + gap * 3, 5, 15); // 3개월 부족이면 14% 가산
        t.emergency += boost;

        // 출처 순서: invest (절반), variable (나머지), 그래도 부족하면 debt (소폭)
        let need = boost;

        // 투자에서 절반 우선 차감 (단, MIN_FLOOR 유지)
        const fromInvest = Math.min(need * 0.5, Math.max(0, t.invest - MIN_FLOOR.invest));
        t.invest -= fromInvest;
        need -= fromInvest;

        if (need > 0) {
            const fromVar = Math.min(need, Math.max(0, t.variable - MIN_FLOOR.variable));
            t.variable -= fromVar;
            need -= fromVar;
        }
        if (need > 0) {
            const fromDebt = Math.min(need * 0.3, Math.max(0, t.debt - MIN_FLOOR.debt));
            t.debt -= fromDebt;
            need -= fromDebt;
        }

        rulesTriggered.push('LOW_EMERGENCY');
        const monthsShort = round1(gap);
        advice.push(
            `비상금이 목표보다 ${monthsShort}개월 부족합니다. 비상금 비중을 일시적으로 높였습니다. 목표 달성 후 투자 비중을 재상향하세요.`
        );
    }

    // 2-2) 고금리 부채 존재 시 상환 비중 +5% (필요하면 +추가 보정)
    const hasHighInterestDebt = (input.debts ?? []).some((d) => d.apr >= HIGH_INTEREST_APR);
    if (hasHighInterestDebt) {
        const bump = 5;
        t.debt += bump;

        // 차감 순서: variable → invest
        takeFromBuckets(t as any, bump, ['variable', 'invest']);
        rulesTriggered.push('HIGH_INTEREST_DEBT');
        advice.push('연 8% 이상 고금리 부채가 있어 상환 비중을 상향했습니다. 선상환 후 투자 확대를 권장합니다.');
    }

    // 2-3) 변동 소득일 경우 비상금 +5%, 투자 -3%, 변동지출 -2%
    if (input.incomeStability === 'variable') {
        t.emergency += 5;
        // 차감: invest 3, variable 2
        takeFromBuckets(t as any, 3, ['invest']);
        takeFromBuckets(t as any, 2, ['variable']);
        rulesTriggered.push('VARIABLE_INCOME');
        advice.push('소득 변동성이 있어 비상금 비중을 상향했습니다. 최소 6개월분 확보를 권장합니다.');
    }

    // 2-4) 부양가족 많을 때(2명 이상): 고정지출 +2~4% 캡 확장
    if ((input.dependents ?? 0) >= 2) {
        const bump = Math.min(4, (input.dependents! - 1) * 2); // 2명→2%, 3명→4%
        const want = bump;
        t.fixed += want;
        takeFromBuckets(t as any, want, ['variable', 'invest']);
        rulesTriggered.push('HIGH_DEPENDENTS');
        advice.push('부양가족 규모를 반영해 고정지출 여유분을 소폭 확대했습니다.');
    }

    // 3) 총합 100 보정
    const sum =
        t.fixed + t.variable + t.emergency + t.invest + t.debt + t.insurance;
    const diff = round1(sum - 100);
    if (diff !== 0) {
        // 기본적으로 변동지출에서 상쇄, 부족하면 invest→debt 순
        if (diff > 0) {
            takeFromBuckets(t as any, diff, ['variable', 'invest', 'debt']);
        } else {
            // 총합이 100보다 작으면 variable → invest 순으로 보강
            const need = -diff;
            // 균등 보강보단 variable 우선
            t.variable += need;
        }
    }

    // 4) 바닥값 강제
    (Object.keys(t) as AllocationCategory[]).forEach((k) => {
        t[k] = round1(clamp(t[k], MIN_FLOOR[k], 100));
    });

    // 5) 재정규화 (반올림/바닥 적용으로 총합이 살짝 어긋날 수 있음)
    let total = (Object.values(t) as number[]).reduce((a, b) => a + b, 0);
    total = round1(total);
    if (total !== 100) {
        // variable에서 미세 조정
        const fix = round1(total - 100);
        t.variable = round1(clamp(t.variable - fix, MIN_FLOOR.variable, 100));
    }

    // 6) 금액 계산
    const toAmount = (pct: number) =>
        Math.round((pct / 100) * input.monthlyNetIncome);

    const allocations: Allocation[] = [
        { category: 'fixed', percent: t.fixed, amount: toAmount(t.fixed) },
        { category: 'variable', percent: t.variable, amount: toAmount(t.variable) },
        { category: 'emergency', percent: t.emergency, amount: toAmount(t.emergency) },
        { category: 'invest', percent: t.invest, amount: toAmount(t.invest) },
        { category: 'debt', percent: t.debt, amount: toAmount(t.debt) },
        { category: 'insurance', percent: t.insurance, amount: toAmount(t.insurance) },
    ];

    // 7) 추가 참고 노트
    const hi = (input.debts ?? []).filter((d) => d.apr >= HIGH_INTEREST_APR);
    if (hi.length) {
        const names = hi.map((d) => d.type ?? 'debt').join(', ');
        notes.push(`고금리 부채(${names}) 감지됨: APR ≥ ${Math.round(HIGH_INTEREST_APR * 100)}%.`);
    }

    return {
        allocations,
        rulesTriggered,
        advice,
        notes,
    };
}
