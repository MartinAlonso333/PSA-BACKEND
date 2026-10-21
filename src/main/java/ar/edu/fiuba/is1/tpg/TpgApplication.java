package ar.edu.fiuba.is1.tpg;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "TPG Squad API",
        version = "0.0.1-SNAPSHOT",
        description = "Starter backend for a TPG squad."
    )
)
@SpringBootApplication
public class TpgApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpgApplication.class, args);
    }
}
