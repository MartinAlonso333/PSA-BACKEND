# Minuta - marketing y ventas.

## Fecha y hora

- 07/05/2026 - 19.30hrs.

## Lugar o medio

- Vía Reunión Virtual (Zoom)

## Participantes

- Juan Anvizzio.
- Jose Mercado.

- Squad 1:
  - Garcia Vazquez Valentino: Desarrollador
  - Beltrame Agustin: Desarrollador
  - Alonso Franco Martin: Desarrollador
  - Olmos Gonzalo: Desarrollador

## Objetivo

Definir y aclarar el alcance funcional de los módulos de Marketing y Ventas para el MVP del sistema CRM, incluyendo campañas, leads, oportunidades, métricas de ROI, flujo comercial y comunicación entre módulos.

## Hallazgos

- El nexo principal entre Marketing y Ventas son los leads.
- Las campañas generan leads y Ventas se encarga de convertirlos en oportunidades y eventualmente en ventas.
- El éxito de una campaña se mide principalmente mediante el ROI.
- El ROI se calcula considerando:
  - Costos de campaña.
  - Ingresos provenientes de contratos generados por oportunidades asociadas a la campaña.
- No se consideran horas internas del equipo para el ROI de marketing.
- Los leads deben registrar:
  - Campaña de origen.
  - Empresa asociada.
  - Datos de contacto.
  - Canal de origen.
- El flujo comercial definido es:
  - Campaña → Lead → Oportunidad → Negociación → Ganada/Perdida → Cliente → Proyecto de implementación.
- Marketing registra campañas y leads, mientras que Ventas realiza el seguimiento posterior.
- Las campañas tendrán estados simples:
  - Planificada.
  - Activa.
  - Finalizada.
- Los leads tendrán estados:
  - Nuevo.
  - Contactado.
  - Calificado.
  - Convertido en oportunidad.
- Las oportunidades podrán pasar por etapas de negociación hasta terminar en:
  - Ganada.
  - Perdida.
- Una oportunidad puede incluir múltiples productos o servicios.
- El sistema debe permitir reportes sobre:
  - ROI.
  - Leads por campaña.
  - Oportunidades concretadas.
  - Conversión de leads.
  - Efectividad de campañas.
  - Pipeline de ventas.
- El MVP no incluirá controles avanzados de seguridad ni restricciones de acceso.
- No se espera que el sistema realice análisis automáticos comparativos entre campañas; solo debe centralizar la información.

## Decisiones

- Mantener el MVP lo más simple posible.
- Registrar únicamente información indispensable en clientes, campañas y oportunidades.
- Delegar el seguimiento de leads y oportunidades al equipo de Ventas.
- Implementar estados básicos para campañas, leads y oportunidades.
- Asociar campañas con leads y oportunidades para calcular métricas de efectividad.
- Permitir que una oportunidad incluya múltiples productos vendidos.
- Posponer funcionalidades avanzadas como:
  - Alertas automáticas.
  - Integraciones complejas con CRM.
  - Seguridad y permisos avanzados.

## Confirmaciones

- Marketing es responsable únicamente de generar y registrar leads.
- Ventas es responsable de:
  - Contactar leads.
  - Calificarlos.
  - Convertirlos en oportunidades.
  - Gestionar negociaciones.
- El paso a implementación ocurre únicamente cuando una oportunidad se marca como ganada.
- La información mínima que se transfiere a implementación es:
  - Cliente.
  - Producto.
  - Relación con la oportunidad ganada.
- El sistema debe conservar historial de productos considerados y vendidos en cada oportunidad.

## Compromisos asumidos

- Continuar refinando el modelo funcional en futuras reuniones.
- Evaluar futuras integraciones con CRM y automatizaciones.
- Coordinar posibles reuniones adicionales con:
  - Vendedores.
  - Diseñadores de campañas.
  - Usuarios finales del sistema.

## Pendientes

- Definir políticas de alertas y seguimiento automático.
- Analizar futuras integraciones con CRM.
- Definir permisos y roles de usuario en versiones posteriores.
- Profundizar el flujo entre oportunidades ganadas y proyectos de implementación.
- Validar métricas y reportes adicionales necesarios para Marketing y Ventas.
- Evaluar necesidades de exportación e intercambio de información entre módulos.
