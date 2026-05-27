package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.ReservaRepository;

public class CancelarReservaUseCase {

    private final ReservaRepository reservaRepository;

    public CancelarReservaUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public OperationResult execute(String id) {

        Reserva reserva = reservaRepository.findByReserva(id);

        if (reserva == null) {
            return OperationResult.fail("No se puede cancelar. La reserva no existe.");
        }

        if (reserva.isCancelada()) {
            return OperationResult.fail("No se puede cancelar. La reserva ya estaba cancelada.");
        }

        reserva.setCancelada(true);

        return OperationResult.ok("Reserva cancelada correctamente.");
    }
}
