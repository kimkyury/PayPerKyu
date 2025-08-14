import { useMemo, useState } from "react";
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from "recharts";
import { calculatePlan } from "./lib/calculatePlan";

const COLORS = ["#6366f1", "#22c55e", "#f59e0b", "#ef4444", "#06b6d4", "#a78bfa"] as const;

type Cat =
    | "fixed" | "variable" | "emergency" | "invest" | "debt" | "insurance";

const CAT_LABEL: Record<Cat, string> = {
    fixed: "고정지출",
    variable: "변동지출",
    emergency: "비상금/단기",
    invest: "장기투자",
    debt: "부채상환",
    insurance: "보험/보장",
};

const toKRW = (n: number) => n.toLocaleString("ko-KR");
const toPct = (n: number) => `${n}%`;

export default function App() {
    const [income, setIncome] = useState(4_000_000);
    const [risk, setRisk] = useState<"conservative" | "neutral" | "aggressive">("neutral");
    const [emg, setEmg] = useState(1.5);
    const [target, setTarget] = useState(3);

    const result = useMemo(
        () =>
            calculatePlan({
                monthlyNetIncome: income,
                riskProfile: risk,
                emergencyFundMonths: emg,
                targetEmergencyMonths: target,
                debts: [{ type: "카드론", balance: 3_000_000, apr: 0.09, minPayment: 200_000 }],
                incomeStability: "stable",
                dependents: 1,
            }),
        [income, risk, emg, target]
    );

    const data = result.allocations.map((a) => ({
        name: CAT_LABEL[a.category as Cat] ?? a.category,
        value: a.percent,
    }));

    return (
        <div className="min-h-screen bg-gray-50 text-gray-900">
            <div className="mx-auto max-w-5xl p-6 space-y-8">
                {/* 헤더 */}
                <header className="space-y-2">
                    <h1 className="text-3xl font-bold">paypercent</h1>
                    <p className="text-gray-600">
                        월급을 입력하면, “분석적인 <i>척</i>” 하는 그래프를 그려줄게요 <br />
                        지갑은 가벼워도 기분은 무거워지지 않게. 노력해봅시다.
                    </p>
                </header>

                {/* 입력영역 */}
                <section className="grid md:grid-cols-2 gap-6">
                    <div className="rounded-2xl bg-white p-5 shadow">
                        <h2 className="font-semibold mb-4">입력</h2>

                        <label className="block text-sm mb-1">월 실수령 (₩)</label>
                        <input
                            className="w-full mb-3 border rounded px-3 py-2"
                            type="number"
                            value={income}
                            onChange={(e) => setIncome(+e.target.value)}
                        />

                        <label className="block text-sm mb-1">소비 성향</label>
                        <select
                            className="w-full mb-3 border rounded px-3 py-2"
                            value={risk}
                            onChange={(e) => setRisk(e.target.value as any)}
                        >
                            <option value="conservative">보수적</option>
                            <option value="neutral">중립</option>
                            <option value="aggressive">공격적</option>
                        </select>

                        <div className="grid grid-cols-2 gap-3">
                            <div>
                                <label className="block text-sm mb-1">현재 비상금 (개월)</label>
                                <input
                                    className="w-full border rounded px-3 py-2"
                                    type="number"
                                    step="0.5"
                                    value={emg}
                                    onChange={(e) => setEmg(+e.target.value)}
                                />
                            </div>
                            <div>
                                <label className="block text-sm mb-1">목표 비상금 (개월)</label>
                                <input
                                    className="w-full border rounded px-3 py-2"
                                    type="number"
                                    step="0.5"
                                    value={target}
                                    onChange={(e) => setTarget(+e.target.value)}
                                />
                            </div>
                        </div>

                        <p className="mt-3 text-xs text-gray-500">
                            * 비상금이 모이면 투자 비중을 쪼오금 올려봐요. 모이기 전엔 쪼오금 참구요.
                        </p>
                    </div>

                    {/* 배분 결과 */}
                    <div className="rounded-2xl bg-white p-5 shadow">
                        <h2 className="font-semibold mb-4">배분 결과</h2>

                        <div className="h-72">
                            <ResponsiveContainer width="100%" height="100%">
                                <PieChart>
                                    <Pie data={data} dataKey="value" nameKey="name" outerRadius={110} label>
                                        {data.map((_, i) => (
                                            <Cell key={i} fill={COLORS[i % COLORS.length]} />
                                        ))}
                                    </Pie>
                                    <Tooltip
                                        formatter={(value: any, name: any) => [toPct(value as number), name as string]}
                                    />
                                    <Legend />
                                </PieChart>
                            </ResponsiveContainer>
                        </div>

                        <ul className="mt-4 text-sm space-y-1">
                            {result.allocations.map((a) => (
                                <li key={a.category} className="flex justify-between">
                                    <span>{CAT_LABEL[a.category as Cat]}</span>
                                    <span>
                                        {toPct(a.percent)} · {toKRW(a.amount)}원
                                    </span>
                                </li>
                            ))}
                        </ul>
                    </div>
                </section>

                {/* 피드백 */}
                <section className="rounded-2xl bg-white p-5 shadow">
                    <h2 className="font-semibold mb-3">피드백</h2>
                    {result.advice.length === 0 ? (
                        <p className="text-sm text-gray-600">
                            흠.. 조용한 건 보통 두 가지죠. <b>문제가 없거나, 아직 모르는 거거나.</b>
                        </p>
                    ) : (
                        <ul className="list-disc pl-5 space-y-1">
                            {result.advice.map((m, i) => (
                                <li key={i}>{m}</li>
                            ))}
                        </ul>
                    )}
                </section>
            </div>
        </div>
    );
}
