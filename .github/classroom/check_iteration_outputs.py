#!/usr/bin/env python3

from __future__ import annotations

import re
import sys
from pathlib import Path

MODEL_DOMAIN_MARKDOWN_SECTIONS = [
    "## Alcance del modelo",
    "## Inputs usados",
    "## Historias priorizadas tomadas como base",
    "## Clases conceptuales",
    "## Asociaciones",
    "## Atributos",
    "## Diagrama",
    "## Trazabilidad",
    "## Fuera de alcance y exclusiones",
    "## Dudas abiertas",
]
MODEL_DOMAIN_REQUIRED_INPUT_TOKENS = [
    "docs/vision/vision-modulo.md",
    "docs/requisitos/historias-dominio.md",
    "docs/requisitos/historias-usuario.md",
    "docs/requisitos/matriz-trazabilidad.md",
    "src/test/resources/features/",
]
MODEL_DOMAIN_TECHNICAL_PATTERN = r"(?i)(controller|service|repository|dto|api|endpoint|tabla|screen|\bui\b)"
HISTORIAS_DOMINIO_MARKDOWN_SECTIONS = [
    "## Propósito",
    "## Fuente canónica y companions",
    "## Criterio de recorte",
    "## Actores y objetos de trabajo relevantes",
    "## Historias relevadas",
    "## Export visual",
    "## Relación con requisitos posteriores",
    "## Dudas abiertas",
]
HISTORIAS_DOMINIO_REQUIRED_TOKENS = ["Egon", "egon.io", ".egn"]


def require_file(path: str) -> Path:
    target = Path(path)
    if not target.is_file():
        raise SystemExit(f"missing file: {path}")
    return target


def require_markdown_sections(path: str, sections: list[str], *, min_length: int = 120) -> None:
    text = require_file(path).read_text(encoding="utf-8")
    if len(text.strip()) < min_length:
        raise SystemExit(f"file too short: {path}")
    for section in sections:
        if section not in text:
            raise SystemExit(f"missing section {section!r} in {path}")


def require_tokens(path: str, tokens: list[str]) -> None:
    text = require_file(path).read_text(encoding="utf-8")
    for token in tokens:
        if token not in text:
            raise SystemExit(f"missing token {token!r} in {path}")


def require_mermaid_block(path: str, *, tokens: list[str] | None = None) -> None:
    text = require_file(path).read_text(encoding="utf-8")
    if "```mermaid" not in text:
        raise SystemExit(f"missing Mermaid block in {path}")
    for token in tokens or []:
        if token not in text:
            raise SystemExit(f"missing Mermaid token {token!r} in {path}")


def require_companion_files(markdown_path: str, *, editable_suffixes: list[str], image_suffixes: list[str]) -> None:
    markdown = require_file(markdown_path)
    stem = markdown.with_suffix("")
    editable = [stem.with_suffix(suffix) for suffix in editable_suffixes]
    image = [stem.with_suffix(suffix) for suffix in image_suffixes]

    editable_path = next((path for path in editable if path.is_file()), None)
    if editable_path is None:
        raise SystemExit(f"missing editable companion for {markdown_path}")

    image_path = next((path for path in image if path.is_file()), None)
    if image_path is None:
        raise SystemExit(f"missing image companion for {markdown_path}")

    text = markdown.read_text(encoding="utf-8")
    if image_path.name not in text:
        raise SystemExit(f"{markdown_path} must reference {image_path.name}")


def require_modelo_dominio_markdown(path: str) -> None:
    require_markdown_sections(path, MODEL_DOMAIN_MARKDOWN_SECTIONS, min_length=520)
    require_without_placeholder_markers(path)
    require_tokens(path, MODEL_DOMAIN_REQUIRED_INPUT_TOKENS)


def require_conceptual_plantuml_model(path: str) -> None:
    text = require_file(path).read_text(encoding="utf-8")
    for token in ["@startuml", "@enduml"]:
        if token not in text:
            raise SystemExit(f"missing PlantUML token {token!r} in {path}")

    class_count = len(re.findall(r"(?m)^\s*class\s+[A-Za-z_][\w.]*\b", text))
    if class_count < 2:
        raise SystemExit(f"{path} must declare at least two conceptual classes")

    relation_count = sum(1 for line in text.splitlines() if _is_plantuml_relation_line(line))
    if relation_count < 1:
        raise SystemExit(f"{path} must include at least one conceptual relation")

    if re.search(r"(?m)^\s*!include\b", text):
        raise SystemExit(f"{path} must not use !include")
    if re.search(r"(?m)^\s*skinparam\b", text):
        raise SystemExit(f"{path} must not use skinparam")
    if re.search(r"(?m)^\s*(interface|package)\b", text):
        raise SystemExit(f"{path} must stay within the conceptual class-diagram subset")
    if re.search(r"<<[^>]+>>", text):
        raise SystemExit(f"{path} must not use stereotypes")
    if re.search(r"(?m)^\s*[+\-#~]\s*[A-Za-z_]", text):
        raise SystemExit(f"{path} must not use attribute or operation visibilities")
    if any(_is_plantuml_operation_line(line) for line in text.splitlines()):
        raise SystemExit(f"{path} must not declare methods or operations")

    if re.search(MODEL_DOMAIN_TECHNICAL_PATTERN, text):
        raise SystemExit(f"{path} must avoid implementation vocabulary")


def require_without_placeholder_markers(path: str) -> None:
    text = require_file(path).read_text(encoding="utf-8")
    patterns = [
        r"(?m)^\s*\.\.\.\s*$",
        r"(?m)^\s*[-*]\s+\.\.\.\s*$",
        r"(?m)^\s*\d+\.\s+\.\.\.\s*$",
        r"(?m)^\|(?:[^|\n]*\.\.\.[^|\n]*\|){2,}\s*$",
    ]
    if any(re.search(pattern, text) for pattern in patterns):
        raise SystemExit(f"placeholder content detected in {path}")


def require_live_feature(*, min_count: int = 1) -> None:
    features_dir = Path("src/test/resources/features")
    features = [path for path in features_dir.glob("*.feature") if path.name != "feature-template.feature"]
    if len(features) < min_count:
        raise SystemExit(f"missing live feature files in src/test/resources/features (need {min_count})")


def require_minuta() -> None:
    minutas = [
        path
        for path in Path("docs/descubrimiento/minutas").glob("*.md")
        if path.name != "minuta-template.md"
    ]
    if not minutas:
        raise SystemExit("missing live meeting minute in docs/descubrimiento/minutas")
    latest = sorted(minutas)[-1]
    require_markdown_sections(
        str(latest),
        [
            "## Fecha y hora",
            "## Participantes",
            "## Objetivo",
            "## Hallazgos",
            "## Decisiones",
            "## Pendientes",
        ],
        min_length=180,
    )
    require_without_placeholder_markers(str(latest))


def require_java_under(path: str) -> None:
    if not list(Path(path).rglob("*.java")):
        raise SystemExit(f"missing Java source under {path}")


def require_rest_layer_structure() -> None:
    for relative in [
        "src/main/java/ar/edu/fiuba/is1/tpg/controller",
        "src/main/java/ar/edu/fiuba/is1/tpg/service",
        "src/main/java/ar/edu/fiuba/is1/tpg/domain",
        "src/main/java/ar/edu/fiuba/is1/tpg/repository",
        "src/main/java/ar/edu/fiuba/is1/tpg/config",
    ]:
        require_java_under(relative)


def require_schema() -> None:
    schema_text = require_file("docs/db/schema.sql").read_text(encoding="utf-8").lower()
    if "create table" not in schema_text:
        raise SystemExit("schema.sql must contain at least one CREATE TABLE")


def require_delivery_manifest(slug: str, *, sections: list[str], min_length: int = 180) -> None:
    path = f"entregas/{slug}.md"
    require_markdown_sections(path, sections, min_length=min_length)
    require_without_placeholder_markers(path)


def _is_plantuml_relation_line(line: str) -> bool:
    stripped = line.strip()
    if not stripped or stripped.startswith("'"):
        return False
    return bool(re.search(r"(--|\.\.|<\|)", stripped)) and not stripped.startswith("@")


def _is_plantuml_operation_line(line: str) -> bool:
    stripped = line.strip()
    if not stripped or stripped.startswith("'"):
        return False
    return bool(re.match(r"^[A-Za-z_][\w<>]*\s*\([^)\n]*\)(?:\s*:\s*[\w<>\[\]]+)?\s*$", stripped))


def check_iteracion_01() -> None:
    require_markdown_sections(
        "docs/descubrimiento/organigrama.md",
        [
            "## Sistema analizado",
            "## Alcance organizacional",
            "## Roles y áreas relevantes",
            "## Diagrama",
        ],
        min_length=220,
    )
    require_without_placeholder_markers("docs/descubrimiento/organigrama.md")
    require_mermaid_block("docs/descubrimiento/organigrama.md")

    require_markdown_sections(
        "docs/descubrimiento/onion-model.md",
        [
            "## Sistema analizado",
            "## Criterio de capas",
            "## Resumen del modelo",
            "## Registro de interesados",
            "## Cobertura faltante",
            "## Próximas acciones",
        ],
        min_length=280,
    )
    require_without_placeholder_markers("docs/descubrimiento/onion-model.md")
    require_tokens(
        "docs/descubrimiento/onion-model.md",
        ["Our System", "Containing System", "Wider Environment", "Estado", "explícito"],
    )
    require_companion_files(
        "docs/descubrimiento/onion-model.md",
        editable_suffixes=[".drawio", ".puml"],
        image_suffixes=[".svg", ".png"],
    )

    require_minuta()
    require_delivery_manifest(
        "entrega-01",
        sections=[
            "## Entrega y alcance",
            "## Artefactos consolidados",
            "## Cambios principales",
            "## Decisiones tomadas",
            "## Riesgos y pendientes",
            "## Próximos pasos",
        ],
    )


def check_iteracion_02() -> None:
    require_markdown_sections(
        "docs/requisitos/historias-dominio.md",
        HISTORIAS_DOMINIO_MARKDOWN_SECTIONS,
        min_length=260,
    )
    require_without_placeholder_markers("docs/requisitos/historias-dominio.md")
    require_tokens("docs/requisitos/historias-dominio.md", HISTORIAS_DOMINIO_REQUIRED_TOKENS)
    require_companion_files(
        "docs/requisitos/historias-dominio.md",
        editable_suffixes=[".egn"],
        image_suffixes=[".svg", ".png"],
    )

    require_markdown_sections(
        "docs/requisitos/especificacion-requisitos.md",
        [
            "## Objetivo y alcance",
            "## Requisitos funcionales",
            "## Requisitos no funcionales",
            "## Reglas de negocio",
            "## Dudas abiertas",
        ],
        min_length=320,
    )
    require_without_placeholder_markers("docs/requisitos/especificacion-requisitos.md")
    require_tokens("docs/requisitos/especificacion-requisitos.md", ["RF-", "RNF-", "RN-"])

    require_markdown_sections(
        "docs/requisitos/historias-usuario.md",
        ["## Backlog", "## Historias priorizadas", "HU-"],
        min_length=220,
    )
    require_without_placeholder_markers("docs/requisitos/historias-usuario.md")

    require_markdown_sections(
        "docs/requisitos/matriz-trazabilidad.md",
        ["## Criterio de trazabilidad", "## Matriz HDU vs minutas"],
        min_length=180,
    )
    require_without_placeholder_markers("docs/requisitos/matriz-trazabilidad.md")

    require_live_feature(min_count=1)


def check_iteracion_03() -> None:
    check_iteracion_02()

    require_markdown_sections(
        "docs/vision/vision-modulo.md",
        [
            "## Nombre del módulo",
            "## Problema u oportunidad",
            "## Resumen ejecutivo",
            "## Alcance del MVP",
            "## Funcionalidades principales",
        ],
        min_length=260,
    )
    require_without_placeholder_markers("docs/vision/vision-modulo.md")

    require_markdown_sections(
        "docs/analisis/c4-contexto.md",
        ["## Propósito", "## Sistema analizado", "## Actores y sistemas externos", "## Diagrama"],
        min_length=200,
    )
    require_without_placeholder_markers("docs/analisis/c4-contexto.md")
    require_mermaid_block("docs/analisis/c4-contexto.md", tokens=["C4Context"])

    require_modelo_dominio_markdown("docs/analisis/modelo-dominio.md")
    require_companion_files(
        "docs/analisis/modelo-dominio.md",
        editable_suffixes=[".puml"],
        image_suffixes=[".svg", ".png"],
    )
    require_conceptual_plantuml_model("docs/analisis/modelo-dominio.puml")

    require_markdown_sections(
        "docs/analisis/diccionario-datos.md",
        ["## Alcance del diccionario", "## Términos principales"],
        min_length=160,
    )
    require_without_placeholder_markers("docs/analisis/diccionario-datos.md")

    require_markdown_sections(
        "docs/ux/prototipos.md",
        ["## Objetivo del prototipo", "## Link read-only", "## Decisiones de UX visibles"],
        min_length=160,
    )
    require_without_placeholder_markers("docs/ux/prototipos.md")

    require_delivery_manifest(
        "entrega-02",
        sections=[
            "## Entrega y alcance",
            "## Iteraciones cubiertas",
            "## Artefactos consolidados",
            "## Cambios principales",
            "## Decisiones tomadas",
            "## Riesgos y pendientes",
            "## Próximos pasos",
        ],
    )


def check_iteracion_04() -> None:
    require_markdown_sections(
        "docs/ux/prototipos.md",
        ["## Objetivo del prototipo", "## Link read-only", "## Decisiones de UX visibles"],
        min_length=160,
    )
    require_without_placeholder_markers("docs/ux/prototipos.md")

    require_markdown_sections(
        "docs/arquitectura/c4-contenedores.md",
        ["## Propósito", "## Contenedores", "## Diagrama"],
        min_length=180,
    )
    require_without_placeholder_markers("docs/arquitectura/c4-contenedores.md")
    require_mermaid_block("docs/arquitectura/c4-contenedores.md", tokens=["C4Container"])

    require_markdown_sections(
        "docs/arquitectura/api-modulo.md",
        ["## Alcance del API", "## Endpoints", "## Contratos principales"],
        min_length=180,
    )
    require_without_placeholder_markers("docs/arquitectura/api-modulo.md")

    require_markdown_sections(
        "docs/db/modelo-er.md",
        ["## Alcance del modelo", "## Entidades y relaciones"],
        min_length=160,
    )
    require_without_placeholder_markers("docs/db/modelo-er.md")
    require_mermaid_block("docs/db/modelo-er.md", tokens=["erDiagram"])
    require_schema()

    require_delivery_manifest(
        "entrega-03",
        sections=[
            "## Entrega y alcance",
            "## Artefactos consolidados",
            "## Cambios principales",
            "## Decisiones tomadas",
            "## Riesgos y pendientes",
            "## Próximos pasos",
        ],
    )


def check_iteracion_05() -> None:
    require_markdown_sections(
        "docs/arquitectura/c4-componentes.md",
        ["## Propósito", "## Componentes principales", "## Diagrama"],
        min_length=180,
    )
    require_without_placeholder_markers("docs/arquitectura/c4-componentes.md")
    require_mermaid_block("docs/arquitectura/c4-componentes.md", tokens=["C4Component"])

    require_rest_layer_structure()
    require_java_under("src/test/java")
    require_live_feature(min_count=1)

    diagrama_codigo = Path("docs/arquitectura/diagrama-codigo.md")
    if diagrama_codigo.exists():
        require_markdown_sections(
            str(diagrama_codigo),
            ["## Motivo del diagrama", "## Recorte aplicado", "## Diagrama"],
            min_length=140,
        )
        require_mermaid_block(str(diagrama_codigo))


def check_iteracion_06() -> None:
    require_markdown_sections(
        "docs/vision/vision-modulo.md",
        [
            "## Nombre del módulo",
            "## Problema u oportunidad",
            "## Resumen ejecutivo",
            "## Alcance del MVP",
            "## Funcionalidades principales",
        ],
        min_length=260,
    )
    require_markdown_sections(
        "docs/requisitos/especificacion-requisitos.md",
        [
            "## Objetivo y alcance",
            "## Requisitos funcionales",
            "## Requisitos no funcionales",
            "## Reglas de negocio",
            "## Dudas abiertas",
        ],
        min_length=320,
    )
    require_markdown_sections(
        "docs/arquitectura/api-modulo.md",
        ["## Alcance del API", "## Endpoints", "## Contratos principales"],
        min_length=180,
    )
    require_markdown_sections(
        "docs/arquitectura/c4-componentes.md",
        ["## Propósito", "## Componentes principales", "## Diagrama"],
        min_length=180,
    )
    require_mermaid_block("docs/arquitectura/c4-componentes.md", tokens=["C4Component"])
    require_markdown_sections(
        "docs/db/modelo-er.md",
        ["## Alcance del modelo", "## Entidades y relaciones"],
        min_length=160,
    )
    require_mermaid_block("docs/db/modelo-er.md", tokens=["erDiagram"])
    require_schema()
    require_rest_layer_structure()
    require_java_under("src/test/java")
    require_live_feature(min_count=2)

    diagrama_codigo = Path("docs/arquitectura/diagrama-codigo.md")
    if diagrama_codigo.exists():
        require_markdown_sections(
            str(diagrama_codigo),
            ["## Motivo del diagrama", "## Recorte aplicado", "## Diagrama"],
            min_length=140,
        )
        require_mermaid_block(str(diagrama_codigo))

    require_delivery_manifest(
        "entrega-04",
        sections=[
            "## Entrega y alcance",
            "## Iteraciones cubiertas",
            "## Artefactos consolidados",
            "## Cambios principales",
            "## Decisiones tomadas",
            "## Evidencia de despliegue o ejecución demostrable",
            "## Riesgos y pendientes",
        ],
        min_length=220,
    )


def main() -> None:
    if len(sys.argv) != 2:
        raise SystemExit("usage: check_iteration_outputs.py <iteracion-XX>")

    checks = {
        "iteracion-01": check_iteracion_01,
        "iteracion-02": check_iteracion_02,
        "iteracion-03": check_iteracion_03,
        "iteracion-04": check_iteracion_04,
        "iteracion-05": check_iteracion_05,
        "iteracion-06": check_iteracion_06,
    }
    slug = sys.argv[1]
    try:
        checks[slug]()
    except KeyError as exc:
        raise SystemExit(f"unsupported iteration slug: {slug}") from exc


if __name__ == "__main__":
    main()
