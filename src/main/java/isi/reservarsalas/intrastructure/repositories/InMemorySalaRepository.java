package isi.reservarsalas.intrastructure.repositories;

import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.ports.SalaRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemorySalaRepository implements SalaRepository{
    private List<Sala> salas;

    public InMemorySalaRepository(){
        salas=new ArrayList<Sala>();
    }
    @Override
    public void save(Sala sala){
        int i = 0;
        boolean found = false;

        while (i < salas.size() && !found) {
            if (salas.get(i).getId().equals(sala.getId())) {
                salas.set(i, sala);
                found = true;
            }
            i++;
        }

        if (!found) {
            salas.add(sala);
        }
    }

    @Override
    public Sala findBySala(String IdSala) {
        int i;

        for (i = 0; i < salas.size(); i++) {
            Sala sala = salas.get(i);

            if (sala.getId().equals(IdSala)) {
                return sala;
            }
        }
        return null;
    }
}
