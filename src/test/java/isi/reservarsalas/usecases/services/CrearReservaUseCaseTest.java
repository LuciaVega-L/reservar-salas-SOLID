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

class CrearReservaUseCaseTest {
    private SalaRepository salaRepository;
    private ReservaRepository reservaRepository;
    private CrearReservaUseCase useCase;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        salaRepository = new InMemorySalaRepository();
        reservaRepository = new InMemoryReservaRepository();
        useCase = new CrearReservaUseCase(salaRepository, reservaRepository);
        Sala sala = new Sala("GC2", "Sala 101", "AULA", 30, "Edificio A");
        salaRepository.save(sala);

        reserva = new Reserva("101", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);
        reservaRepository.save(reserva);
    }

    @Test
    void execute_IdVacio() {
        OperationResult result = useCase.execute(null, "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);
        assertEquals("Error: el ID de la reserva no puede estar vacío.",  result.getMessage());
    }
    @Test
    void execute_ReservaConMismoId(){
        OperationResult result = useCase.execute("101", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);

        assertEquals("Error: ya existe una reserva con ese ID.", result.getMessage());
    }
    @Test
    void execute_SalaInexistente(){
        OperationResult result = useCase.execute("102", "GC3", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);
        assertEquals("Error: la sala no existe.", result.getMessage());
    }
    @Test
    void execute_SalaInactiva(){
        salaRepository.findBySala("GC2").isInactiva();
        OperationResult result = useCase.execute("102", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);
        assertEquals("Error: la sala no está activa.", result.getMessage());
    }
    @Test
    void execute_Fechavacia(){
        OperationResult result = useCase.execute("102", "GC2", null, 9, 11,
                "Clase", "Lucia Vega", 30);
        assertEquals("Error: la fecha no puede estar vacía.", result.getMessage());
    }
    @Test
    void execute_HorarioNoValido(){
        OperationResult result = useCase.execute("102", "GC2", "2026-06-01", 1, 2,
                "Clase", "Lucia Vega", 30);
        assertEquals("Error: horario inválido. Use horas entre 6 y 22.", result.getMessage());
    }
    @Test
    void execute_ActividadVacia(){
        OperationResult result = useCase.execute("102", "GC2", "2026-06-01", 9, 11,
                null, "Lucia Vega", 30);
        assertEquals("Error: el tipo de actividad no puede estar vacío.", result.getMessage());
    }
    @Test
    void execute_ResponsableVacio(){
        OperationResult result = useCase.execute("102", "GC2", "2026-06-01", 9, 11,
                "Clase", null, 30);
        assertEquals("Error: el responsable no puede estar vacío.", result.getMessage());
    }
    @Test
    void execute_ceroAsistentes(){
        OperationResult result = useCase.execute("102", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 0);
        assertEquals("Error: la cantidad de asistentes debe ser mayor que cero.", result.getMessage());
    }
    @Test
    void execute_MuchosAsistentes(){
        OperationResult result = useCase.execute("102", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 31);
        assertEquals("Error: la cantidad de asistentes supera la capacidad de la sala.", result.getMessage());
    }
    @Test
    void execute_ReservaNoValia(){
        Sala sala = new Sala("GC3", "Sala 101", "LABORATORIO", 30, "Edificio A");
        salaRepository.save(sala);
        OperationResult result = useCase.execute("102", "GC3", "2026-06-01", 11, 21,
                "Clase", "Lucia Vega", 30);
        assertEquals("Error: el laboratorio solo se puede reservar para actividades de tipo PRACTICA.", result.getMessage());
    }
    @Test
    void execute_PocosAsistentesAuditorio(){
        Sala sala = new Sala("GC3", "Sala 101", "AUDITORIO", 300, "Edificio A");
        salaRepository.save(sala);
        OperationResult result = useCase.execute("102", "GC3", "2026-06-01", 11, 21,
                "Clase", "Lucia Vega", 3);
        assertEquals("Error: el auditorio requiere mínimo 30 asistentes.", result.getMessage());
    }
    @Test
    void execute_MismoHorarioReserva(){
        OperationResult result = useCase.execute("102", "GC2", "2026-06-01", 9, 11,
                "Clase", "Lucia Vega", 30);
        assertEquals("Error: la sala ya tiene una reserva en ese horario.", result.getMessage());
    }
    @Test
    void execute_ReservaExitosa(){
        OperationResult result = useCase.execute("102", "GC2", "2026-06-01", 11, 21,
                "Clase", "Lucia Vega", 30);
        assertEquals("Reserva creada correctamente.", result.getMessage());
    }
}