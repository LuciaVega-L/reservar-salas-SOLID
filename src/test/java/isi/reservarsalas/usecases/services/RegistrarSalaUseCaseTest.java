package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.intrastructure.repositories.InMemoryReservaRepository;
import isi.reservarsalas.intrastructure.repositories.InMemorySalaRepository;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.ReservaRepository;
import isi.reservarsalas.usecases.ports.SalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrarSalaUseCaseTest {

    private SalaRepository salaRepository;
    private RegistrarSalaUseCase useCase;
    private Sala sala;

    @BeforeEach
    void setUp() {
        salaRepository = new InMemorySalaRepository();
        useCase = new RegistrarSalaUseCase(salaRepository);
        Sala sala = new Sala("GC2", "Sala 100", "AULA", 30, "Edificio A");
        salaRepository.save(sala);
    }
    @Test
    void registrarSala_IdVacio() {
        OperationResult result = useCase.execute(null, "Sala 1", "AUDITORIO", 30, "Edificio E");
        assertEquals("Error: el ID de la sala no puede estar vacío.",  result.getMessage());

    }

    @Test
    void registrarSala_NombreVacio() {
        OperationResult result = useCase.execute("101", "", "AUDITORIO", 30, "Edificio E");
        assertEquals("Error: el nombre de la sala no puede estar vacío.",  result.getMessage());
    }
    @Test
    void registrarSala_TipoVacio() {
        OperationResult result = useCase.execute("101", "sala 1", "", 30, "Edificio E");
        assertEquals("Error: el tipo de sala no puede estar vacío.",  result.getMessage());
    }
    @Test
    void registrarSala_TipoInvalido() {
        OperationResult result = useCase.execute("101", "sala 1", "Sala", 30, "Edificio E");
        assertEquals("Error: tipo de sala no válido. Use AULA, LABORATORIO o AUDITORIO.",  result.getMessage());
    }
    @Test
    void registrarSala_CapacidadInvalida() {
        OperationResult result = useCase.execute("101", "sala 1", "AULA", -30, "Edificio E");
        assertEquals("Error: la capacidad debe ser mayor que cero.",  result.getMessage());
    }
    @Test
    void registrarSala_IdyaExistente() {
        OperationResult result = useCase.execute("GC2", "sala 1", "AULA", 30, "Edificio E");
        assertEquals("Error: ya existe una sala con ese ID.",  result.getMessage());
    }
    @Test
    void registrarSala_Exito() {
        OperationResult result = useCase.execute("GC3", "sala 1", "AULA", 30, "Edificio E");
        assertEquals("Sala registrada correctamente.",  result.getMessage());
    }
}

