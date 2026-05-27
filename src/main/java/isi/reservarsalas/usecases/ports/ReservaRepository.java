package isi.reservarsalas.usecases.ports;

import isi.reservarsalas.entities.Reserva;

import java.util.List;

public interface ReservaRepository {
     void save(Reserva reserva);
     Reserva findByReserva(String IdReserva);
     List<Reserva> findAll();
}
