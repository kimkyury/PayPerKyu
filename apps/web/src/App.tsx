import { useState } from "react";
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from "recharts";
import { calculatePlan } from "./lib/calculatePlan";

const COLORS = ["#6366f1", "#22c55e", "#f59e0b", "#ef4444", "#06b6d4", "#a78bfa"];

export default function App() {
    const [income, setIncome] = useState(4000000);
    const [risk, setRisk] = useState<'conservative' | 'neutral' | 'aggressive'>('neutral');
    const [emg, setEmg] = useState(1.5);
    const [target, setTarget] = useState(3);

    const result = calculatePlan({
        monthlyNetIncome: income,
        riskProfile: risk,
        emergencyFundMonths: emg,
        targetEmergencyMonths: target,
        debts: [{ type: 'card_loan', balance: 3000000, apr: 0.09, minPayment: 200000 }],
        incomeStability: 'stable',
        dependents: 1,
    });

    const data = result.allocations.map((a) => ({ name: a.category, value: a.percent }));

    return (
        <div className="min-h-screen bg-gray-50 text-gray-900">
            <div className="mx-auto max-w-5xl p-6 space-y-8">
                <header className="space-y-2">
                    <h1 className="text-3xl font-bold">paypercent</h1>
                    <p className="text-gray-600">Enter income → get a scientifically-*ish* split.</p>
                </header>

                <section className="grid md:grid-cols-2 gap-6">
                    <div className="rounded-2xl bg-white p-5 shadow">
                        <h2 className="font-semibold mb-4">Inputs</h2>

                        <label className="block text-sm mb-1">Monthly Net Income (KRW)</label>
                        <input className="w-full mb-3 border rounded px-3 py-2"
                            type="number" value={income} onChange={(e) => setIncome(+e.target.value)} />

                        <label className="block text-sm mb-1">Risk Profile</label>
                        <select className="w-full mb-3 border rounded px-3 py-2"
                            value={risk} onChange={(e) => setRisk(e.target.value as any)}>
                            <option value="conservative">conservative</option>
                            <option value="neutral">neutral</option>
                            <option value="aggressive">aggressive</option>
                        </select>

                        <div className="grid grid-cols-2 gap-3">
                            <div>
                                <label className="block text-sm mb-1">Emergency (months)</label>
                                <input className="w-full border rounded px-3 py-2"
                                    type="number" step="0.5" value={emg} onChange={(e) => setEmg(+e.target.value)} />
                            </div>
                            <div>
                                <label className="block text-sm mb-1">Target Emergency</label>
                                <input className="w-full border rounded px-3 py-2"
                                    type="number" step="0.5" value={target} onChange={(e) => setTarget(+e.target.value)} />
                            </div>
                        </div>
                    </div>

                    <div className="rounded-2xl bg-white p-5 shadow">
                        <h2 className="font-semibold mb-4">Allocation</h2>
                        <div className="h-72">
                            <ResponsiveContainer width="100%" height="100%">
                                <PieChart>
                                    <Pie data={data} dataKey="value" nameKey="name" outerRadius={100} label>
                                        {data.map((_, i) => <Cell key={i} fill={COLORS[i % COLORS.length]} />)}
                                    </Pie>
                                    <Tooltip /><Legend />
                                </PieChart>
                            </ResponsiveContainer>
                        </div>

                        <ul className="mt-4 text-sm space-y-1">
                            {result.allocations.map(a => (
                                <li key={a.category} className="flex justify-between">
                                    <span className="capitalize">{a.category}</span>
                                    <span>{a.percent}% · {a.amount.toLocaleString()}원</span>
                                </li>
                            ))}
                        </ul>
                    </div>
                </section>

                <section className="rounded-2xl bg-white p-5 shadow">
                    <h2 className="font-semibold mb-3">Advice</h2>
                    <ul className="list-disc pl-5 space-y-1">
                        {result.advice.map((m, i) => <li key={i}>{m}</li>)}
                    </ul>
                </section>
            </div>
        </div>
    );
}
