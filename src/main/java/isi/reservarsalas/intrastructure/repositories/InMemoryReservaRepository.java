package isi.reservarsalas.intrastructure.repositories;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.usecases.ports.ReservaRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryReservaRepository implements ReservaRepository {
    private List<Reserva> reservas;

    public InMemoryReservaRepository(){ reservas=new ArrayList<Reserva>();}

    @Override
    public void save(Reserva reserva){
        int i = 0;
        boolean found = false;

        while (i < reservas.size() && !found) {
            if (reservas.get(i).getId().equals(reserva.getId())) {
                reservas.set(i, reserva);
                found = true;
            }
            i++;
        }

        if (!found) {
            reservas.add(reserva);
        }
    }

    @Override
    public List<Reserva> findAll() {
        return reservas;
    }

    @Override
    public Reserva findByReserva(String IdReserva){
        int i;

        for(i=0;i< reservas.size();i++){
            Reserva reserva = reservas.get(i);
            if (reserva.getId().equals(IdReserva)){
                return reserva;
            }
        }
        return null;
    }
}
