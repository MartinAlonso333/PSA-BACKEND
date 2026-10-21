#!/usr/bin/env bash

set -euo pipefail

current_branch="${GITHUB_REF_NAME:-$(git branch --show-current)}"

if [[ "$current_branch" != "iteracion-02" ]]; then
  echo "La rama actual debe ser 'iteracion-02'." >&2
  exit 1
fi

bash .github/classroom/check_required_files.sh
bash .github/classroom/check_forbidden_files.sh
bash .github/classroom/check_agents_contract.sh
bash .github/classroom/check_feedback_guidance.sh
python3 .github/classroom/check_iteration_outputs.py iteracion-02

printf 'Preflight de iteracion-02: OK\n'
