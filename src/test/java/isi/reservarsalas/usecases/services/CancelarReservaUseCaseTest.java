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

    @BeforeEach
    void setUp() {
        ReservaRepository reservaRepository = new InMemoryReservaRepository();
        reservaRepository = new InMemoryReservaRepository();
        UseCase = new CancelarReservaUseCase(reservaRepository);
    }

    @Test
    void execute_Reserva_Inexistente() {

        OperationResult result = UseCase.execute(null);
        assertEquals("No se puede cancelar. La reserva no existe.", result.getMessage());
    }
}