# language: es
@marketing
Característica: Registro del canal de origen de un lead

  Para analizar qué canales generan mejores resultados comerciales,
  como responsable de marketing,
  debe registrarse el canal de origen asociado a cada lead.

  Escenario: Atribución exitosa del origen al crear un lead
    Dado que existe una campaña activa llamada "Campaña Primavera 2026"
    Y que existe un lead interesado llamado "Juan Pérez"
    Cuando se registra un nuevo lead para este contacto bajo la campaña "Campaña Primavera 2026"
    Y se define el canal de origen como "Redes sociales"
    Entonces el lead debe quedar vinculado exitosamente a su campaña origen
    Y su procedencia debe quedar clasificada bajo el canal "Redes sociales"

  Escenario: Restricción de registro de leads sin canal definido
    Dado que se intenta dar de alta un lead para la "Campaña Otoño 2026"
    Cuando se omite especificar el canal de origen durante el registro
    Entonces la creación del lead debe ser rechazada por falta de información obligatoria
    Y no debe generarse ningún registro comercial nuevo

  Escenario: Flexibilidad para actualizar el canal de origen
    Dado que el lead de "Tech Solutions S.R.L." fue registrado originalmente vía "Email"
    Cuando se modifica su origen a "Evento presencial"
    Entonces el lead debe reflejar su nueva procedencia correctamente
    Y debe preservarse un registro histórico de este cambio para auditoría

  Escenario: Actualización de datos de un lead existente
    Dado que existe una campaña activa llamada "Campaña Primavera 2026"
    Y que existe un lead interesado llamado "Juan Pérez"
    Y se ha registrado un lead bajo la campaña "Campaña Primavera 2026"
    Cuando se actualiza el lead con los siguientes datos:
      | campo     | valor               |
      | nombre    | Juan Pérez Actualizado |
    Entonces el lead debe reflejar los nuevos datos correctamente
    Y los campos no enviados deben mantener su valor original

  Escenario: Consolidación de leads por canal para análisis de efectividad
    Dado que se han generado los siguientes leads:
      | nombre       | campaña               | canal            |
      | Juan Pérez   | Campaña Primavera     | Redes sociales   |
      | María López  | Campaña Primavera     | Redes sociales   |
      | Pedro García | Campaña Primavera     | Email            |
      | Ana Torres   | Campaña Otoño         | Teléfono         |
    Cuando se solicita el reporte de rendimiento por canal
    Entonces el resumen de resultados debe mostrar:
      | canal            | cantidad |
      | Redes sociales   | 2        |
      | Email            | 1        |
      | Teléfono         | 1        |