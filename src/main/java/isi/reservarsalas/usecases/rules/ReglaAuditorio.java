package isi.reservarsalas.usecases.rules;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.dto.OperationResult;
import javafx.css.Rule;

public class ReglaAuditorio implements ReglaReserva{
    @Override
    public OperationResult validar(Reserva reserva, Sala sala){
        if(sala.getTipo().equals("AUDITORIO")){
            if(reserva.getCantidadAsistentes() < 30){
                return OperationResult.fail("El auditorio requiere mínimo 30 asistentes.");
            }
        }
        return OperationResult.ok("Reserva realizada correctamente.");
    }
}
