package ar.edu.fiuba.is1.tpg.bdd;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.fiuba.is1.tpg.domain.PingMessage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

public class HealthSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<PingMessage> response;
    private ResponseEntity<String> docsResponse;
    private ResponseEntity<String> uiResponse;

    @Cuando("consulto el endpoint de health")
    public void consulto_el_endpoint_de_health() {
        response = restTemplate.getForEntity("/api/health", PingMessage.class);
    }

    @Entonces("recibo una respuesta ok")
    public void recibo_una_respuesta_ok() {
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo("ok");
    }

    @Cuando("consulto la documentación OpenAPI")
    public void consulto_la_documentacion_openapi() {
        docsResponse = restTemplate.getForEntity("/v3/api-docs", String.class);
    }

    @Entonces("recibo una documentación OpenAPI ok")
    public void recibo_una_documentacion_openapi_ok() {
        assertThat(docsResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(docsResponse.getBody()).isNotNull();
        assertThat(docsResponse.getBody()).contains("\"openapi\"");
    }

    @Cuando("consulto la interfaz Swagger UI")
    public void consulto_la_interfaz_swagger_ui() {
        uiResponse = restTemplate.getForEntity("/docs", String.class);
    }

    @Entonces("recibo una interfaz Swagger UI ok")
    public void recibo_una_interfaz_swagger_ui_ok() {
        assertThat(uiResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(uiResponse.getBody()).isNotNull();
        assertThat(uiResponse.getBody()).contains("SwaggerUIBundle");
    }
}
