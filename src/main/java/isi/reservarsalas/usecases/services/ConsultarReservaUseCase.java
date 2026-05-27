package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.ReservaRepository;
import isi.reservarsalas.usecases.ports.SalaRepository;

public class ConsultarReservaUseCase {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;

    public ConsultarReservaUseCase(ReservaRepository reservaRepository, SalaRepository salaRepository) {
        this.reservaRepository = reservaRepository;
        this.salaRepository = salaRepository;
    }

    public OperationResult execute(String id) {

        Reserva reserva = reservaRepository.findByReserva(id);

        if (reserva == null) {
            return OperationResult.fail("Reserva no encontrada.");
        }

        Sala sala = salaRepository.findBySala(reserva.getSalaId());

        String texto = "";
        texto = texto + "Información de la reserva\n";
        texto = texto + "-------------------------\n";
        texto = texto + "ID: " + reserva.getId() + "\n";
        texto = texto + "Fecha: " + reserva.getFecha() + "\n";
        texto = texto + "Horario: " + reserva.getHoraInicio() + ":00 - " + reserva.getHoraFin() + ":00\n";
        texto = texto + "Tipo de actividad: " + reserva.getTipoActividad() + "\n";
        texto = texto + "Responsable: " + reserva.getResponsable() + "\n";
        texto = texto + "Asistentes: " + reserva.getCantidadAsistentes() + "\n";

        if (sala != null) {
            texto = texto + "Sala: " + sala.getNombre() + "\n";
            texto = texto + "Tipo de sala: " + sala.getTipo() + "\n";
            texto = texto + "Ubicación: " + sala.getUbicacion() + "\n";
        }

        if (reserva.isCancelada()) {
            texto = texto + "Estado: Cancelada\n";
        } else {
            texto = texto + "Estado: Activa\n";
        }

        return OperationResult.ok(texto);
    }
}
