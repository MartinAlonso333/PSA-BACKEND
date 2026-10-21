package ar.edu.fiuba.is1.tpg.service;

import ar.edu.fiuba.is1.tpg.domain.PingMessage;
import ar.edu.fiuba.is1.tpg.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service
public class PingService {

    private final PingRepository pingRepository;

    public PingService(PingRepository pingRepository) {
        this.pingRepository = pingRepository;
    }

    public PingMessage health() {
        return pingRepository.defaultMessage();
    }
}
