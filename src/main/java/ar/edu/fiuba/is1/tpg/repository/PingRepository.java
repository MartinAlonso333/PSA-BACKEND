package ar.edu.fiuba.is1.tpg.repository;

import ar.edu.fiuba.is1.tpg.domain.PingMessage;
import org.springframework.stereotype.Repository;

@Repository
public class PingRepository {

    public PingMessage defaultMessage() {
        return new PingMessage("ok", "TPG squad listo");
    }
}
