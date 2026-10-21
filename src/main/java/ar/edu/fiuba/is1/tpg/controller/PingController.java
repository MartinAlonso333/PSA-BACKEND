package ar.edu.fiuba.is1.tpg.controller;

import ar.edu.fiuba.is1.tpg.domain.PingMessage;
import ar.edu.fiuba.is1.tpg.service.PingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health", description = "Health endpoint for the squad backend")
@RestController
@RequestMapping("/api/health")
public class PingController {

    private final PingService pingService;

    public PingController(PingService pingService) {
        this.pingService = pingService;
    }

    @Operation(summary = "Health check", description = "Returns the minimal health payload.")
    @ApiResponse(responseCode = "200", description = "Backend is healthy")
    @GetMapping
    public PingMessage health() {
        return pingService.health();
    }
}
