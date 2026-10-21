# language: es
@marketing
Característica: Registro y métricas de campañas de marketing

  Para medir la efectividad y el retorno de las acciones comerciales realizadas,
  como responsable de marketing,
  debe posibilitarse el registro de campañas de marketing con su información básica,
  presupuesto y productos asociados, permitiendo el seguimiento de métricas derivadas
  de su desempeño comercial.

  Escenario: Registro exitoso de una campaña
    Dado que "Laura Gómez" está preparando el lanzamiento de "Black Friday PSA 2025"
    Cuando se registra la campaña con los siguientes datos:
      | campo        | valor                 |
      | fecha inicio | 01/11/2025            |
      | fecha fin    | 30/11/2025            |
      | presupuesto  | 150000                |
      | costo        | 120000                |
      | estado       | Activa                |
      | producto     | PSA Soporte           |
      | canales      | Email                 |
    Entonces la campaña debe quedar guardada bajo el nombre "Black Friday PSA 2025"
    Y la campaña debe quedar asociada al producto "PSA Soporte"
    Y la campaña debe quedar registrada en estado "Activa"
    Y debe permitirse el seguimiento de métricas de desempeño de la campaña

  Escenario: Restricción de registro sin presupuesto definido
    Dado que se intenta dar de alta la campaña "Black Friday PSA 2025"
    Cuando no se define un presupuesto para la campaña
    Entonces el registro debe ser rechazado por incumplimiento de integridad
    Y no debe crearse la campaña en el repositorio central

  Escenario: Actualización exitosa de una campaña
    Dado que existe una campaña activa denominada "Black Friday PSA 2025"
    Cuando se actualiza la campaña "Black Friday PSA 2025" con los siguientes datos:
      | campo       | valor                     |
      | presupuesto | 200000                    |
      | nombre      | Black Friday PSA 2025 V2 |
    Entonces la campaña debe reflejar los cambios correctamente
    Y los campos no modificados deben mantener su valor original

  Escenario: Eliminación exitosa de una campaña sin leads
    Dado que existe una campaña activa denominada "Campaña a Eliminar"
    Y la campaña no tiene leads asociados
    Cuando se elimina la campaña "Campaña a Eliminar"
    Entonces la campaña debe ser eliminada exitosamente
    Y no debe existir en el repositorio

  Escenario: Restricción de eliminación de campaña con leads asociados
    Dado que existe una campaña activa denominada "Black Friday PSA 2025"
    Y la campaña tiene leads asociados
    Cuando se intenta eliminar la campaña "Black Friday PSA 2025"
    Entonces la eliminación debe ser rechazada por violación de integridad referencial
    Y la campaña debe persistir en el repositorio