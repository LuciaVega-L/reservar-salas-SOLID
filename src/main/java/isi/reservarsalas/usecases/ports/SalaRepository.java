package isi.reservarsalas.usecases.ports;

import isi.reservarsalas.entities.Sala;

public interface SalaRepository {
    void save(Sala sala);
    Sala findBySala(String IdSala);
}
