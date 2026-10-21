# Minuta - Usuarios PSA

## Fecha y hora
- 2026/04/23 - 19:30 hs

## Lugar o medio
- Vía Reunión Virtual (Zoom)

## Participantes
- Fernando Zoluzzia
- Jose Mercado
- Squad 1:
  - Garcia Vazquez Valentino: Desarrollador
  - Beltrame Agustin: Desarrollador
  - Alonso Franco Martin: Desarrollador
  - Van de Velde Tomas: Desarrollador
  - Olmos Gonzalo: Desarrollador

## Objetivo
- Relevar los requerimientos funcionales del sistema PSA, abarcando los módulos de Proyectos, Implementaciones, Soporte, Infraestructura y Marketing, a fin de definir el alcance del desarrollo y estandarizar los procesos internos de la organización.
- Los resultados esperados del sistema son: (1) aumentar la satisfacción de los clientes, (2) tener visibilidad clara sobre la rentabilidad de los proyectos, y (3) incrementar los ingresos a través de la venta de productos.

## Hallazgos

### Módulo de Proyectos y Desarrollo
- Cada líder de proyecto lleva adelante los proyectos de manera diferente, sin un estándar común ni reporte de riesgos hacia la dirección.
- No existe un proceso unificado para el manejo de riesgos en proyectos de desarrollo.
- Se requiere estandarizar la forma en que se llevan adelante los proyectos, incluyendo alcance, roles (desarrolladores, líderes) y gestión de riesgos.

### Módulo de Implementaciones
- Los proyectos de implementación involucran perfiles orientados a servicios, que en ciertos casos realizan desarrollos específicos para cada cliente.
- El producto base (ej. SIU Guaraní) varía según la facultad/cliente; el sistema debe contemplar estas diferencias.
- Implementaciones enfrenta problemáticas similares a las del área de Proyectos: falta de estandarización en la gestión.
- Se determinó que Proyectos e Implementaciones deben trabajar de la misma manera metodológicamente.

### Módulo de Soporte
- Los clientes se comunican por múltiples canales (mail, teléfono, sistema de tickets, etc.), lo que dificulta el seguimiento.
- No hay un estándar para el ingreso y gestión de tickets por parte de los ingenieros de soporte.
- Se requiere derivación de tickets hacia los equipos de Desarrollo e Implementaciones desde el módulo de Soporte.
- El mayor problema identificado es la gestión de escalaciones: situaciones en las que un cliente escala directamente a dirección sin que el área de soporte haya podido actuar a tiempo.
- Se requiere visibilidad completa sobre tiempos de resolución de tickets y métricas de calidad del servicio.

### Módulo de Infraestructura
- Módulo de menor prioridad en esta etapa.
- Gestiona los sistemas cloud que opera la organización.

### Comunicación entre Áreas
- La comunicación entre las áreas no está estandarizada: se utilizan canales como reuniones presenciales, mail, teléfono y planillas de cálculo de forma indistinta.
- La derivación de tickets de Soporte hacia otras áreas también carece de un proceso definido.

### Módulo de Marketing
- Se gestionan campañas de distintos tipos: presentación de productos, mantenimiento de marca y capacitación.
- Las campañas implican costos en múltiples canales (radio, TV, web, streamers, ferias/expos, etc.) y generan prospects/leads.
- El flujo de seguimiento de un prospect incluye: contacto inicial, entendimiento de necesidad, consultoría, demostración, presupuestación y cierre.
- No existe un sistema que relacione campañas con prospects ni que permita calcular el retorno de inversión por campaña.
- Las campañas de mayor movimiento identificadas son las relacionadas con ferias, stands y exposiciones.
- Se requiere seguimiento granular del estado de los prospects: a quién se le debe presupuesto, cuántos presupuestos están hechos y cuántos fueron firmados.
- Se prevé focalización por producto ante la aparición de competencia o lanzamientos propios; el sistema debe permitir priorizar productos en las campañas.
- Una necesidad creciente del mercado es la interoperabilidad entre productos y servicios; esto impacta en cómo se posicionan y comunican los productos en las campañas.
- No hay stakeholders negativos internos identificados, pero sí existe competencia externa. Se señala la necesidad de identificar fortalezas propias e innovar para no intentar igualar a la competencia en todo simultáneamente.

## Decisiones
- Se desarrollará un sistema modular compuesto por: Proyectos, Implementaciones, Soporte, Infraestructura y Marketing.
- Proyectos e Implementaciones compartirán la misma metodología de gestión y flujo de trabajo.
- Se implementará un sistema de celeridades para la atención de tickets de soporte:
  - **Celeridad 1:** Resolución en el día. Si impacta al usuario, respuesta al cliente en menos de 1 hora.
  - **Celeridad 2:** Resolución en 7 días.
  - **Celeridad 3:** Resolución en 30 días.
- El módulo de Soporte centralizará todos los canales de comunicación con clientes y estandarizará el ingreso de tickets.
- Se habilitará la derivación de tickets desde Soporte hacia Desarrollo e Implementaciones dentro del mismo sistema.
- El módulo de Marketing registrará campañas, vinculará prospects con su origen y permitirá medir el retorno de inversión.

## Confirmaciones
- Se confirma que el módulo de Infraestructura es de menor prioridad y no formará parte de la primera versión del sistema.
- Se confirma que toda consulta o problema de un cliente debe generar un ticket en el módulo de Soporte, independientemente del canal de entrada.
- Se confirma la necesidad de medir la satisfacción del cliente: se realizará una encuesta antes de la implementación del sistema y otra a los 3 meses de su puesta en funcionamiento.

## Compromisos asumidos
- El Squad 1 se compromete a iniciar el análisis y diseño de los módulos de Proyectos e Implementaciones como primera fase del desarrollo.
- Fernando Zoluzzia y Jose Mercado se comprometen a proveer información adicional sobre los procesos actuales de cada área para profundizar el relevamiento.
- El equipo acordó documentar el flujo actual de comunicación entre áreas para utilizarlo como base del diseño del módulo de Soporte.

## Pendientes
- Definir el alcance detallado de la primera versión del módulo de Proyectos.
- Diseñar el flujo completo de estados y derivación de tickets para el módulo de Soporte.
- Establecer las etapas formales del ciclo de vida de un prospect para el módulo de Marketing.
- Realizar la encuesta de satisfacción inicial a clientes previo al inicio del desarrollo.
- Definir qué información se registrará de las campañas y qué métricas de ROI se calcularán.
- Coordinar próxima reunión para revisión del diseño funcional del módulo de Proyectos.
- Identificar y documentar las fortalezas diferenciales de los productos PSA frente a la competencia como insumo para la estrategia de campañas.
- Evaluar cómo el sistema puede contemplar el atributo de interoperabilidad entre productos como eje de posicionamiento en el mercado