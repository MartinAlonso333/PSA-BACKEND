#!/usr/bin/env bash

set -euo pipefail

current_branch="${GITHUB_REF_NAME:-$(git branch --show-current)}"

if [[ -z "$current_branch" ]]; then
  echo "No se pudo determinar la rama actual." >&2
  exit 1
fi

case "$current_branch" in
  main)
    bash .github/classroom/check_required_files.sh
    bash .github/classroom/check_forbidden_files.sh
    bash .github/classroom/check_agents_contract.sh
    bash .github/classroom/check_feedback_guidance.sh
    python3 -c 'import ast, pathlib; [ast.parse(pathlib.Path(path).read_text(encoding="utf-8")) for path in (".github/classroom/check_iteration_deadline.py", ".github/classroom/check_iteration_outputs.py")]'
    ;;
  iteracion-01)
    bash scripts/preflight-iteracion-01.sh
    ;;
  iteracion-02)
    bash scripts/preflight-iteracion-02.sh
    ;;
  iteracion-03)
    bash scripts/preflight-iteracion-03.sh
    ;;
  iteracion-04)
    bash scripts/preflight-iteracion-04.sh
    ;;
  iteracion-05)
    bash scripts/preflight-iteracion-05.sh
    ;;
  iteracion-06)
    bash scripts/preflight-iteracion-06.sh
    ;;
  *)
    echo "Rama no soportada por el workflow de classroom: $current_branch" >&2
    exit 1
    ;;
esac
