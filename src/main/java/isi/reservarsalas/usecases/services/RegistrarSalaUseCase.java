package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.SalaRepository;

public class RegistrarSalaUseCase {

    private final SalaRepository salaRepository;

    public RegistrarSalaUseCase(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public OperationResult execute(String id, String nombre, String tipo, int capacidad, String ubicacion) {

        if (id == null || id.trim().isEmpty()) {
            return OperationResult.fail("Error: el ID de la sala no puede estar vacío.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            return OperationResult.fail("Error: el nombre de la sala no puede estar vacío.");
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            return OperationResult.fail("Error: el tipo de sala no puede estar vacío.");
        }

        if (!tipo.equals("AULA") && !tipo.equals("LABORATORIO") && !tipo.equals("AUDITORIO")) {
            return OperationResult.fail("Error: tipo de sala no válido. Use AULA, LABORATORIO o AUDITORIO.");
        }

        if (capacidad <= 0) {
            return OperationResult.fail("Error: la capacidad debe ser mayor que cero.");
        }

        if (salaRepository.findBySala(id) != null) {
            return OperationResult.fail("Error: ya existe una sala con ese ID.");
        }

        Sala sala = new Sala(id, nombre, tipo, capacidad, ubicacion);
        salaRepository.save(sala);

        return OperationResult.ok("Sala registrada correctamente.");
    }
}
