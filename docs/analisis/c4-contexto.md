# R5.1 - Diagrama de Contexto (C4)
## Propósito

- Representar el contexto del módulo de Marketing dentro del sistema PSA.
- Identificar actores principales y sistemas externos con los que interactúa.
- Mostrar cómo el módulo se integra en el flujo de generación de oportunidades de negocio.
- Reflejar la relación funcional entre Marketing, Ventas e Implementación dentro del MVP.
- Mostrar la trazabilidad comercial desde campañas hasta oportunidades concretadas.

## Sistema analizado

- Modulo Marketing del sistema de gestión PSA.

## Actores y sistemas externos

| Elemento | Tipo | Interacción |
| --- | --- | --- |
| Usuario de Marketing | Actor | Crea campañas, registra leads y consulta métricas de efectividad |
| Equipo de Ventas | Actor | Contacta leads, los califica y convierte en oportunidades |
| Equipo de Implementación | Actor | Recibe oportunidades ganadas para iniciar proyectos |
| Sistema CRM | Sistema Externo | Futuras integraciones para sincronización de leads y oportunidades |
| Sistema PSA | Sistema Externo | Centraliza información comercial y resultados |
| Módulo de Ventas | Sistema Interno | Gestiona oportunidades y negociaciones derivadas de leads |
| Módulo de Implementación | Sistema Interno | Gestiona proyectos posteriores a oportunidades ganadas |

## Diagrama

```mermaid
C4Context
    title Contexto del módulo de Marketing - PSA

    Person(marketing_user, "Usuario de Marketing", "Gestiona campañas y leads")
    Person(ventas_user, "Equipo de Ventas", "Gestiona oportunidades y negociaciones")
    Person(implementation_user, "Equipo de Implementación", "Inicia proyectos de implementación")

    System_Boundary(modulo_boundary, "Módulo de Marketing") {
        System(modulo, "Módulo de Marketing", "Gestión de campañas, leads, métricas y trazabilidad comercial")
    }

    System(psa_sales, "Módulo de Ventas", "Gestión de oportunidades y pipeline comercial")
    System(psa_impl, "Módulo de Implementación", "Gestión de proyectos derivados de ventas")

    System_Ext(crm, "Sistema CRM", "Integración futura de campañas, clientes y oportunidades")
    System_Ext(psa_core, "Sistema PSA", "Sistema integral de gestión empresarial")

    Rel(marketing_user, modulo, "Registra campañas y leads")
    Rel(modulo, ventas_user, "Entrega leads generados")
    Rel(ventas_user, modulo, "Actualiza estados y conversiones")

    Rel(modulo, psa_sales, "Comparte leads y campañas asociadas")
    Rel(psa_sales, modulo, "Informa conversiones y resultados comerciales")

    Rel(psa_sales, psa_impl, "Transfiere oportunidades ganadas")
    Rel(psa_impl, implementation_user, "Gestiona proyectos")

    Rel(modulo, crm, "Sincronización futura")
    Rel(modulo, psa_core, "Comparte métricas y resultados")
```