"""Utilities to compute metrics and rankings."""
from __future__ import annotations

from dataclasses import dataclass, field
from typing import Dict, List, Tuple


def average_scores(evaluation: Dict) -> float:
    scores = []
    if "questions" in evaluation:
        for q in evaluation["questions"]:
            vals = [q.get("Relevance"), q.get("relevance"),
                    q.get("Clarity_and_Coherence"), q.get("Clarity and Coherence"),
                    q.get("clarity_coherence"), q.get("clarity_and_coherence"),
                    q.get("Conciseness"), q.get("conciseness"),
                    q.get("Coverage"), q.get("coverage")]
            vals = [float(v) for v in vals if v is not None]
            if vals:
                scores.append(sum(vals)/len(vals))
    return sum(scores)/len(scores) if scores else 0.0


@dataclass
class ModelResult:
    model: str
    score: float
    data: Dict = field(default_factory=dict)

def repetition_rate(questions: List[str]) -> float:
    unique = set(q.strip().lower() for q in questions)
    if not questions:
        return 0.0
    return 1 - len(unique) / len(questions)


def aggregate_repetition(model_reps: Dict[str, List[float]]) -> Dict[str, float]:
    """Return average repetition rate per model."""
    return {m: sum(vals) / len(vals) if vals else 0.0 for m, vals in model_reps.items()}


def elo_rating(scores: List[float]) -> float:
    # simplified: average score as rating
    return sum(scores) / len(scores) if scores else 0.0


def build_win_matrix(model_scores: Dict[str, List[float]]) -> Tuple[Dict[str, Dict[str, float]], Dict[str, Dict[str, int]]]:
    """Return win counts and comparison counts between every pair of models."""
    models = list(model_scores.keys())
    wins = {m: {n: 0.0 for n in models} for m in models}
    comps = {m: {n: 0 for n in models} for m in models}
    if not models:
        return wins, comps

    sample_count = max(len(v) for v in model_scores.values())
    for idx in range(sample_count):
        for i, mi in enumerate(models):
            if idx >= len(model_scores[mi]):
                continue
            si = model_scores[mi][idx]
            for j in range(i + 1, len(models)):
                mj = models[j]
                if idx >= len(model_scores[mj]):
                    continue
                sj = model_scores[mj][idx]
                comps[mi][mj] += 1
                comps[mj][mi] += 1
                if si > sj:
                    wins[mi][mj] += 1
                elif sj > si:
                    wins[mj][mi] += 1
                else:
                    wins[mi][mj] += 0.5
                    wins[mj][mi] += 0.5
    return wins, comps


def win_rates(wins: Dict[str, Dict[str, float]], comps: Dict[str, Dict[str, int]]) -> Dict[str, float]:
    rates = {}
    for m in wins:
        total_wins = sum(wins[m].values())
        total_comps = sum(comps[m].values())
        rates[m] = total_wins / total_comps if total_comps else 0.0
    return rates


def elo_ratings(model_scores: Dict[str, List[float]], k: int = 32, score_point: int = 400) -> Dict[str, float]:
    """Compute Elo ratings using formulas described in the paper."""
    ratings = {m: 1000.0 for m in model_scores}
    models = list(model_scores.keys())
    if not models:
        return ratings

    sample_count = max(len(v) for v in model_scores.values())
    for idx in range(sample_count):
        for i, mi in enumerate(models):
            if idx >= len(model_scores[mi]):
                continue
            si = model_scores[mi][idx]
            for j in range(i + 1, len(models)):
                mj = models[j]
                if idx >= len(model_scores[mj]):
                    continue
                sj = model_scores[mj][idx]
                ra, rb = ratings[mi], ratings[mj]
                # Predicted score using Equation (2)
                pa = 1 / (1 + 10 ** ((rb - ra) / score_point))
                pb = 1 / (1 + 10 ** ((ra - rb) / score_point))
                if si > sj:
                    wa, wb = 1.0, 0.0
                elif sj > si:
                    wa, wb = 0.0, 1.0
                else:
                    wa = wb = 0.5
                # New rating using Equation (1)
                ratings[mi] = ra + k * (wa - pa)
                ratings[mj] = rb + k * (wb - pb)
    return ratings