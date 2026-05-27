package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.intrastructure.repositories.InMemoryReservaRepository;
import isi.reservarsalas.intrastructure.repositories.InMemorySalaRepository;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.ReservaRepository;
import isi.reservarsalas.usecases.ports.SalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsultarReservaUseCaseTest {

    private ReservaRepository reservaRepository;
    private ConsultarReservaUseCase useCase;
    private SalaRepository salaRepository;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reservaRepository = new InMemoryReservaRepository();
        salaRepository = new InMemorySalaRepository();
        useCase = new ConsultarReservaUseCase(reservaRepository, salaRepository);
    }

    @Test
    void execute_ReservaNoENcontrada() {
        OperationResult result = useCase.execute("101");
        assertEquals("Reserva no encontrada.",result.getMessage());
    }
    @Test
    void execute_ReservaExiste() {
        Sala sala = new Sala("GC2", "Sala 101", "AULA", 30, "Edificio A");
        salaRepository.save(sala);

        reserva = new Reserva("101", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);
        reservaRepository.save(reserva);

        OperationResult result = useCase.execute("101");

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("101"));
        assertTrue(result.getMessage().contains("2026-06-01"));
        assertTrue(result.getMessage().contains("Lucia Vega"));
        assertTrue(result.getMessage().contains("Sala 101"));
        assertTrue(result.getMessage().contains("Activa"));
    }

}