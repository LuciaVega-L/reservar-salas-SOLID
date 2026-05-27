package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.intrastructure.repositories.InMemoryReservaRepository;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.ReservaRepository;
import isi.reservarsalas.usecases.ports.SalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CancelarReservaUseCaseTest {

    private ReservaRepository reservaRepository;
    private CancelarReservaUseCase UseCase;
    private Reserva reserva;

    @BeforeEach
    void setUp() {

        reservaRepository = new InMemoryReservaRepository();
        UseCase = new CancelarReservaUseCase(reservaRepository);
    }

    @Test
    void execute_Reserva_Inexistente() {

        OperationResult result = UseCase.execute(null);
        assertEquals("No se puede cancelar. La reserva no existe.", result.getMessage());
    }
    @Test
    void execute_Reserva_Cancelado() {

        reserva = new Reserva("101", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);

        reservaRepository.save(reserva);
        UseCase.execute("101");
        OperationResult result = UseCase.execute("101");

        assertEquals("No se puede cancelar. La reserva ya estaba cancelada.", result.getMessage());
    }
}