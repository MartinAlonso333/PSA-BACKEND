# language: es
@proyectos
Característica: Inicio estandarizado de proyectos e implementaciones

  Como líder de proyecto
  Quiero iniciar un proyecto definiendo alcance, roles y riesgos
  Para asegurar que todo proyecto comience con estándares unificados

  Escenario: Alta exitosa de un proyecto con definición mandatoria completa
    Dado que "Ana Torres" ha sido asignada como responsable del nuevo proyecto
    Cuando registra el proyecto "Implementación PSA - Empresa Alfa S.A." con los siguientes datos:
      | campo              | valor                              |
      | alcance            | Módulos Soporte y Proyectos        |
      | etapas             | Inicio, Desarrollo, Cierre         |
      | líder responsable  | Ana Torres                         |
      | riesgo             | Demora en entrega de requerimientos|
    Entonces el proyecto debe quedar registrado en estado "Iniciado"
    Y la información debe estar disponible para el seguimiento de todas las áreas

  Esquema del escenario: Validación de campos mandatorios para la integridad del proyecto
    Dado que "Ana Torres" está configurando el inicio de un nuevo proyecto
    Cuando omite el dato obligatorio <campo_faltante>
    Entonces el sistema debe impedir la creación del proyecto para asegurar el cumplimiento del estándar
    Y se debe informar la falta del requisito mandatorio <requisito>

    Ejemplos:
      | campo_faltante    | requisito                        |
      | riesgo            | al menos un riesgo               |
      | líder responsable | un líder responsable             |
      | etapas            | al menos una etapa de ejecución  |

  Escenario: Consistencia metodológica en implementaciones asociadas
    Dado que existe un proyecto base "Implementación PSA - Empresa Alfa" ya iniciado
    Y dicho proyecto cuenta con sus etapas, roles y riesgos definidos
    Cuando se genera una implementación vinculada a este proyecto base
    Entonces la implementación debe heredar y exigir la misma estructura de secciones obligatorias