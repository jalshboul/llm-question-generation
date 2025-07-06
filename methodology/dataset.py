import pathlib
from typing import Iterator, Dict


def load_code_samples(root: str = "data") -> Iterator[Dict]:
    root_path = pathlib.Path(root)
    for lang_dir in root_path.iterdir():
        if not lang_dir.is_dir():
            continue
        language = lang_dir.name
        for path in lang_dir.glob("**/*"):
            if path.suffix.lower() in {".py", ".cpp", ".java"}:
                code = path.read_text()
                yield {
                    "id": path.stem,
                    "language": language,
                    "path": str(path),
                    "code": code,
                }