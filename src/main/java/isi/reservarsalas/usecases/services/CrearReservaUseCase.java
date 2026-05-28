package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.ReservaRepository;
import isi.reservarsalas.usecases.ports.SalaRepository;
import isi.reservarsalas.usecases.rules.ReglaReserva;

import java.util.List;

public class CrearReservaUseCase {

    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;
    private final List<ReglaReserva> reglas;

    public CrearReservaUseCase(SalaRepository salaRepository,
                               ReservaRepository reservaRepository,
                               List<ReglaReserva> reglas) {
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
        this.reglas = reglas;
    }

    public OperationResult execute(String id, String salaId, String fecha, int horaInicio, int horaFin,
                                   String tipoActividad, String responsable, int cantidadAsistentes) {

        if (id == null || id.trim().isEmpty()) {
            return OperationResult.fail("Error: el ID de la reserva no puede estar vacío.");
        }

        if (reservaRepository.findByReserva(id) != null) {
            return OperationResult.fail("Error: ya existe una reserva con ese ID.");
        }

        Sala sala = salaRepository.findBySala(salaId);

        if (sala == null) {
            return OperationResult.fail("Error: la sala no existe.");
        }

        if (!sala.isActiva()) {
            return OperationResult.fail("Error: la sala no está activa.");
        }

        if (fecha == null || fecha.trim().isEmpty()) {
            return OperationResult.fail("Error: la fecha no puede estar vacía.");
        }

        if (horaInicio < 6 || horaFin > 22 || horaInicio >= horaFin) {
            return OperationResult.fail("Error: horario inválido. Use horas entre 6 y 22.");
        }

        if (tipoActividad == null || tipoActividad.trim().isEmpty()) {
            return OperationResult.fail("Error: el tipo de actividad no puede estar vacío.");
        }

        if (responsable == null || responsable.trim().isEmpty()) {
            return OperationResult.fail("Error: el responsable no puede estar vacío.");
        }


        List<Reserva> todasLasReservas = reservaRepository.findAll();
        for (Reserva reserva : todasLasReservas) {
            if (!reserva.isCancelada()
                    && reserva.getSalaId().equals(salaId)
                    && reserva.getFecha().equals(fecha)) {

                boolean seCruzan = horaInicio < reserva.getHoraFin()
                        && horaFin > reserva.getHoraInicio();

                if (seCruzan) {
                    return OperationResult.fail("Error: la sala ya tiene una reserva en ese horario.");
                }
            }
        }

        Reserva nuevaReserva = new Reserva(id, salaId, fecha, horaInicio, horaFin,
                tipoActividad, responsable, cantidadAsistentes);

        for (ReglaReserva regla : reglas) {
            OperationResult resultado = regla.validar(nuevaReserva, sala);
            if (!resultado.isSuccess()) {
                return resultado;
            }
        }

        reservaRepository.save(nuevaReserva);

        return OperationResult.ok("Reserva creada correctamente.");
    }
}
