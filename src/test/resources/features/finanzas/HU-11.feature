# language: es
@finanzas @reportes
Característica: Reportes de ingresos vs costos por producto

  Como analista
  Quiero recibir reportes de ingresos vs costos por producto
  Para evaluar la rentabilidad de cada proyecto

  Escenario: Generar reporte de rentabilidad para un producto específico
    Dado que el producto "PSA Soporte" tiene los siguientes registros financieros:
      | concepto      | monto  |
      | Ingreso Venta | 500000 |
      | Costo Horas   | 200000 |
      | Gastos MKT    | 50000  |
    Cuando el analista consulta el reporte de rentabilidad para "PSA Soporte"
    Entonces el sistema debe presentar el siguiente balance:
      | ingreso_total | costo_total | rentabilidad |
      | 500000        | 250000      | 250000       |

  Escenario: Visualización de ROI en campañas con métricas definidas
    Dado que la campaña "Black Friday 2025" tiene un costo de 10000 y generó 50000 en ingresos
    Cuando el analista genera el reporte de rentabilidad de dicha campaña
    Entonces el informe debe incluir una columna "ROI" con el valor "400%"

  Escenario: Desglose de costos por tareas en un proyecto
    Dado que el proyecto "Migración Cloud" tiene registradas:
      | tarea           | horas | costo_hora |
      | Configuración   | 10    | 50         |
      | Pruebas         | 5     | 40         |
    Cuando el analista solicita el reporte de costos del proyecto "Migración Cloud"
    Entonces el costo total reportado debe ser 700
    Y se debe mostrar el detalle de 500 para "Configuración" y 200 para "Pruebas"