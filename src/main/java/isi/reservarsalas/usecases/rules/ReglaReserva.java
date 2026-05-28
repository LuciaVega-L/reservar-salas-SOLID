package isi.reservarsalas.usecases.rules;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.dto.OperationResult;

public interface ReglaReserva   {
    OperationResult validar(Reserva reserva, Sala sala);
}
