package isi.reservarsalas.usecases.rules;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.dto.OperationResult;

public class ReglaCapacidad implements ReglaReserva{
    @Override
    public OperationResult validar(Reserva reserva, Sala sala){
        if(reserva.getCantidadAsistentes() > sala.getCapacidad()){
            return OperationResult.fail("La cantidad de asistentes supera la capacidad.");
        }
        if(sala.getCapacidad() <= 0){
            return OperationResult.fail("La cantidad de asistentes debe ser mayor a 0");
        }
        return OperationResult.ok("Reserva realizada correctamente.");
    }
}
