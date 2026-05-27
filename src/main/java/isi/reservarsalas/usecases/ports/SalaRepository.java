package isi.reservarsalas.usecases.ports;

import isi.reservarsalas.entities.Sala;

import java.util.List;

public interface SalaRepository {
    void save(Sala sala);
    Sala findBySala(String IdSala);
    List<Sala> findAll();
}
