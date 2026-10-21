# language: es
@proyectos @tiempo @cargaHoras
Característica: Carga horaria diaria por tarea

  Como gestor de proyecto
  Quiero cargar horas diarias por tarea
  Para obtener métricas de costos y rentabilidad

  Escenario: Registro exitoso de horas en una tarea de proyecto
    Dado que el proyecto "PRJ-001" tiene asignada la tarea "Análisis de Datos"
    Cuando el gestor registra "8" horas para el día "2026-05-01" en dicha tarea
    Entonces el esfuerzo total acumulado del proyecto debe incrementarse en "8" horas
    Y la carga debe quedar vinculada al historial de la tarea "Análisis de Datos"

  Escenario: Restricción de carga por exceder la jornada laboral máxima
    Dado que la política de la empresa establece un máximo de 8 horas diarias por persona
    Cuando un colaborador intenta registrar "10" horas en una sola tarea para el mismo día
    Entonces el sistema debe impedir el registro para garantizar el cumplimiento de la jornada
    Y el total de horas cargadas para ese día no debe sufrir modificaciones

  Escenario: Colaboración de múltiples usuarios en una misma tarea
    Dado que la tarea "Desarrollo API" está activa en el proyecto "PRJ-001"
    Cuando "Juan" registra "4" horas y "Pedro" registra "4" horas el mismo día
    Entonces el sistema debe consolidar un total de "8" horas trabajadas en la tarea "Desarrollo API"

  Escenario: Bloqueo de carga de horas durante ausencias justificadas
    Dado que un empleado tiene registrada una "Licencia Médica" para el día "2026-05-01"
    Cuando se intenta realizar una carga de horas para ese empleado en dicha fecha
    Entonces la operación debe ser rechazada por tratarse de un día no laborable