package isi.reservarsalas.usecases.services;

import isi.reservarsalas.intrastructure.repositories.InMemoryReservaRepository;
import isi.reservarsalas.intrastructure.repositories.InMemorySalaRepository;
import isi.reservarsalas.usecases.rules.ReglaReserva;
import isi.reservarsalas.usecases.services.ConsultarReservaUseCase;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.ReservaRepository;
import isi.reservarsalas.usecases.ports.SalaRepository;
import isi.reservarsalas.usecases.ports.ReservaRepository;

import java.util.List;

public class ReservaApp {

    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;


    private final RegistrarSalaUseCase registrarSalaUseCase;
    private final CrearReservaUseCase crearReservaUseCase;
    private final ConsultarReservaUseCase consultarReservaUseCase;
    private final CancelarReservaUseCase cancelarReservaUseCase;
    private final ListarSalasUseCase listarSalasUseCase;
    private final List<ReglaReserva> reglas;

    public ReservaApp(List<ReglaReserva> reglas) {
        this.reglas = reglas;
        salaRepository = new InMemorySalaRepository();
        reservaRepository = new InMemoryReservaRepository();


        registrarSalaUseCase = new RegistrarSalaUseCase(salaRepository);
        crearReservaUseCase = new CrearReservaUseCase(salaRepository, reservaRepository,  reglas);
        consultarReservaUseCase = new ConsultarReservaUseCase(reservaRepository, salaRepository);
        cancelarReservaUseCase = new CancelarReservaUseCase(reservaRepository);
        listarSalasUseCase = new ListarSalasUseCase(salaRepository, reservaRepository);

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        registrarSalaUseCase.execute("S001", "Aula 101", "AULA", 35, "Bloque A");
        registrarSalaUseCase.execute("S002", "Laboratorio de Sistemas", "LABORATORIO", 25, "Bloque B");
        registrarSalaUseCase.execute("S003", "Auditorio Principal", "AUDITORIO", 120, "Bloque Central");

        crearReservaUseCase.execute("R001", "S001", "2026-05-25", 8, 10, "CLASE", "Ing. Pérez", 30);
        crearReservaUseCase.execute("R002", "S002", "2026-05-25", 10, 12, "PRACTICA", "Ing. Gómez", 20);
    }

    public OperationResult registrarSala(String id, String nombre, String tipo, int capacidad, String ubicacion) {
        return registrarSalaUseCase.execute(id, nombre, tipo, capacidad, ubicacion);
    }

    public OperationResult crearReserva(String id, String salaId, String fecha, int horaInicio, int horaFin,
                                        String tipoActividad, String responsable, int cantidadAsistentes) {
        return crearReservaUseCase.execute(id, salaId, fecha, horaInicio, horaFin, tipoActividad, responsable, cantidadAsistentes);
    }

    public OperationResult consultarReserva(String id) {
        return consultarReservaUseCase.execute(id);
    }

    public OperationResult cancelarReserva(String id) {
        return cancelarReservaUseCase.execute(id);
    }

    public OperationResult listarSalas() {
        return listarSalasUseCase.execute();
    }
}