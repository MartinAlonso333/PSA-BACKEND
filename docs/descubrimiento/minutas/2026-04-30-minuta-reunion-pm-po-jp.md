# Minuta - pm-po-jp.

## Fecha y hora
- 2026/04/30 - 19:30 hs

## Lugar o medio

- Vía Reunión Virtual (Zoom)

## Participantes
- Leonardo Felici.
- Tomas Bruneleschi.
- Maximiliano (Jefe de proyecto)

- Squad 1:
  - Garcia Vazquez Valentino: Desarrollador
  - Beltrame Agustin: Desarrollador
  - Alonso Franco Martin: Desarrollador
  - Van de Velde Tomas: Desarrollador
  - Olmos Gonzalo: Desarrollador

## Objetivo
- Relevar requerimientos para el sistema integral de gestión de proyectos, horas, facturación, incidentes y ventas.  
- Entender problemáticas actuales de la empresa en ingresos, costos, seguimiento de proyectos y oportunidades comerciales.  
- Definir una visión general de los módulos del sistema (proyectos, horas, facturación, incidentes, CRM/ventas).  

## Hallazgos
- No existe claridad sobre la rentabilidad de productos (falta relación ingresos vs costos).  
- Dificultad para calcular costos debido a la falta de información sobre cómo los empleados distribuyen su tiempo.  
- La carga de horas actual es inexistente o poco confiable → se pierde información clave.  
- Problemas en desarrollo:  
  - Incumplimiento de fechas prometidas.  
  - Pérdida de oportunidades de negocio.  
- Facturación desorganizada:  
  - Se realiza con planillas manuales.  
  - No hay corte claro de facturación.  
  - Riesgo de perder horas facturables.  
- Falta visibilidad sobre:  
  - Distribución del trabajo entre proyectos.  
  - Uso de la capacidad de los empleados.  
- Necesidad de controlar SLA e incidentes para evitar multas.  
- Falta de integración entre áreas (ventas, marketing, soporte, desarrollo).  
- Necesidad de métricas comerciales:  
  - Pipeline de ventas  
  - Tasa de cierre  
  - Proyección de ingresos  

## Decisiones
- Se desarrollará un sistema de carga de horas manual (al menos en primera etapa).  
- La carga de horas deberá tener granularidad diaria.  
- Se permitirá carga flexible (diaria o semanal), pero con control semanal obligatorio.  
- Se incluirá funcionalidad de carga masiva de horas desde el inicio.  
- El sistema NO realizará facturación directamente, sino que se integrará con el sistema existente (CRM/facturación).  
- Se implementará un módulo de incidentes con seguimiento de SLA y multas.  
- Se permitirá que múltiples usuarios carguen horas sobre una misma tarea.  
- No se restringirá quién puede crear tareas (por ahora).  
- Se contemplará la carga de licencias (vacaciones, ausencias).  

## Confirmaciones
- La relación jerárquica es:  
  Tarea → Proyecto → Producto  
- Los proyectos pueden ser:  
  - De desarrollo (versiones del producto)  
  - De implementación (por cliente)  
- La unidad mínima de trabajo es la tarea.  
- Cada tarea tiene un único responsable (pero puede cambiar).  
- Las horas cargadas serán base para:  
  - Cálculo de costos  
  - Facturación  
  - Reportes de rentabilidad  
- Se podrán generar alertas por:  
  - Falta de carga de horas  
  - Cercanía a incumplimientos de SLA  
- Las multas por SLA se calculan mensualmente.  
- No todos los costos estarán en el sistema (algunos están incluidos en el costo/hora).  

## Compromisos asumidos
- Diseñar un sistema que:  
  - Permita registrar horas por tarea y por día  
  - Mejore la experiencia de usuario en la carga de horas  
  - Centralice la información de proyectos  
- Incorporar reportes clave:  
  - Costos por proyecto/producto  
  - Ingresos por cliente  
  - Pipeline de ventas  
  - Tasa de cierre  
- Permitir integración futura con CRM y otros sistemas.  
- Considerar alertas automáticas (mail, Slack, etc.).  

## Pendientes
- Definir alcance del MVP (qué módulos entran inicialmente).  
- Definir integración concreta con CRM/facturación.  
- Definir reglas de cálculo para:  
  - Horas extra  
  - Costos reales vs teóricos  
- Diseñar modelo de datos completo (DER).  
- Definir manejo de:  
  - Eliminación de tareas con historial de horas  
  - Versionado de proyectos/productos  
- Especificar UX de carga de horas (clave por sensibilidad del usuario).  
- Evaluar automatización futura de carga de horas.  
- Definir métricas exactas para reportes comerciales y financieros.  