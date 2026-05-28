package isi.reservarsalas.usecases.rules;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.dto.OperationResult;

public class ReglaLaboratorio implements ReglaReserva {
    @Override
    public OperationResult validar(Reserva reserva, Sala sala) {
        if (sala.getTipo().equals("LABORATORIO")) {
            if (!reserva.getTipoActividad().equals("PRACTICA")) {
                return OperationResult.fail("El laboratorio solo se reserva para PRACTICA.");
            }
        }
        return OperationResult.ok("Reserva realizada correctamente.");
    }
}