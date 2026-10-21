package ar.edu.fiuba.is1.tpg.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Minimal health payload returned by the squad backend")
public record PingMessage(String status, String message) {
}
