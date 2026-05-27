package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;
import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.ports.ReservaRepository;
import isi.reservarsalas.usecases.ports.SalaRepository;

import java.util.List;

public class ListarSalasUseCase {

    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;

    public ListarSalasUseCase(SalaRepository salaRepository, ReservaRepository reservaRepository) {
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
    }

    public OperationResult execute() {
        List<Sala> salas = salaRepository.findAll();

        if (salas.isEmpty()) {
            return OperationResult.fail("No hay salas registradas.");
        }

        List<Reserva> reservas = reservaRepository.findAll();

        String texto = "";
        texto = texto + "Listado de salas\n";
        texto = texto + "----------------\n";

        int i;
        for (i = 0; i < salas.size(); i++) {
            Sala sala = salas.get(i);

            boolean tieneReservaActiva = false;
            int j;
            for (j = 0; j < reservas.size(); j++) {
                Reserva reserva = reservas.get(j);
                if (!reserva.isCancelada() && reserva.getSalaId().equals(sala.getId())) {
                    tieneReservaActiva = true;
                }
            }

            String estadoReserva;
            if (tieneReservaActiva) {
                estadoReserva = "Con reserva activa";
            } else {
                estadoReserva = "Disponible";
            }

            texto = texto + sala.getId() + " | "
                    + sala.getNombre() + " | "
                    + sala.getTipo() + " | Capacidad: "
                    + sala.getCapacidad() + " | "
                    + sala.getUbicacion() + " | "
                    + estadoReserva + "\n";
        }

        return OperationResult.ok(texto);
    }
}