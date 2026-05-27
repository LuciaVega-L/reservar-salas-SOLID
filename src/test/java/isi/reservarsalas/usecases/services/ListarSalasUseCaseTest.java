package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.intrastructure.repositories.InMemoryReservaRepository;
import isi.reservarsalas.intrastructure.repositories.InMemorySalaRepository;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.ReservaRepository;
import isi.reservarsalas.usecases.ports.SalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListarSalasUseCaseTest {

    private SalaRepository salaRepository;
    private ReservaRepository reservaRepository;
    private ListarSalasUseCase useCase;
    private Sala sala;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        salaRepository = new InMemorySalaRepository();
        reservaRepository = new InMemoryReservaRepository();
        useCase = new ListarSalasUseCase(salaRepository, reservaRepository);
    }

    @Test
    void execute_SinSalas() {
        OperationResult result = useCase.execute();
        assertEquals("No hay salas registradas.", result.getMessage());
    }

    @Test
    void execute_ConSala_Disponible() {
        sala = new Sala("GC2", "Sala 101", "AULA", 30, "Edificio A");
        salaRepository.save(sala);

        OperationResult result = useCase.execute();

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("GC2"));
        assertTrue(result.getMessage().contains("Sala 101"));
        assertTrue(result.getMessage().contains("AULA"));
        assertTrue(result.getMessage().contains("Edificio A"));
        assertTrue(result.getMessage().contains("Disponible"));
    }

    @Test
    void execute_ConSala_ConReservaActiva() {
        sala = new Sala("GC2", "Sala 101", "AULA", 30, "Edificio A");
        salaRepository.save(sala);

        reserva = new Reserva("101", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);
        reservaRepository.save(reserva);

        OperationResult result = useCase.execute();

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("Sala 101"));
        assertTrue(result.getMessage().contains("Con reserva activa"));
    }

    @Test
    void execute_ConSala_ConReservaCancelada() {
        sala = new Sala("GC2", "Sala 101", "AULA", 30, "Edificio A");
        salaRepository.save(sala);

        reserva = new Reserva("101", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);
        reserva.setCancelada(true);
        reservaRepository.save(reserva);

        OperationResult result = useCase.execute();

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("Sala 101"));
        assertTrue(result.getMessage().contains("Disponible"));
    }

}