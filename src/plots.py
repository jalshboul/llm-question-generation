"""Generate figures from evaluation results."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Dict

import matplotlib.pyplot as plt
import numpy as np


def load_summary(path: str | Path) -> Dict:
    with open(path) as f:
        return json.load(f)


def plot_average_win_rate(summary: Dict, out_path: str | Path = "results/win_rate.png") -> None:
    rates = summary.get("win_rate", {})
    if not rates:
        return
    models = list(rates.keys())
    values = [rates[m] for m in models]
    plt.figure(figsize=(10, 6))
    plt.bar(models, values)
    plt.xticks(rotation=90)
    plt.ylabel("Average Win Rate")
    plt.tight_layout()
    plt.savefig(out_path)
    plt.close()


def plot_win_matrix(summary: Dict, out_path: str | Path = "results/win_matrix.png") -> None:
    wins = summary.get("wins")
    comps = summary.get("comparisons")
    if not wins or not comps:
        return
    models = list(wins.keys())
    size = len(models)
    matrix = np.zeros((size, size))
    for i, mi in enumerate(models):
        for j, mj in enumerate(models):
            total = comps[mi].get(mj, 0)
            if total:
                matrix[i, j] = wins[mi].get(mj, 0) / total
    plt.figure(figsize=(8, 6))
    plt.imshow(matrix, cmap="viridis", vmin=0, vmax=1)
    plt.xticks(range(size), models, rotation=90)
    plt.yticks(range(size), models)
    plt.colorbar(label="Win Rate")
    plt.tight_layout()
    plt.savefig(out_path)
    plt.close()


if __name__ == "__main__":
    import sys

    summary_path = sys.argv[1] if len(sys.argv) > 1 else "results/summary.json"
    summary = load_summary(summary_path)
    out_root = Path(summary_path).resolve().parent
    plot_average_win_rate(summary, out_root / "win_rate.png")
    plot_win_matrix(summary, out_root / "win_matrix.png")