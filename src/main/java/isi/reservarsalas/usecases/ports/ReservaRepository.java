package isi.reservarsalas.usecases.ports;

import isi.reservarsalas.entities.Reserva;

public interface ReservaRepository {
     void save(Reserva reserva);
     Reserva findByReserva(String IdReserva);
}
