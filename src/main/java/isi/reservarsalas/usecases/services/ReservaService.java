package isi.reservarsalas.usecases.services;

import isi.reservarsalas.entities.Reserva;
import isi.reservarsalas.entities.Sala;

import java.util.ArrayList;
import java.util.List;

public class ReservaService {

    private List<Sala> salas;
    private List<Reserva> reservas;

    public ReservaService() {
        this.salas = new ArrayList<Sala>();
        this.reservas = new ArrayList<Reserva>();
    }

    public String registrarSala(String id, String nombre, String tipo, int capacidad, String ubicacion) {
        if (id == null || id.trim().isEmpty()) {
            return "Error: el ID de la sala no puede estar vacío.";
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            return "Error: el nombre de la sala no puede estar vacío.";
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            return "Error: el tipo de sala no puede estar vacío.";
        }

        if (!tipo.equals("AULA") && !tipo.equals("LABORATORIO") && !tipo.equals("AUDITORIO")) {
            return "Error: tipo de sala no válido. Use AULA, LABORATORIO o AUDITORIO.";
        }

        if (capacidad <= 0) {
            return "Error: la capacidad debe ser mayor que cero.";
        }

        if (buscarSalaPorId(id) != null) {
            return "Error: ya existe una sala con ese ID.";
        }

        Sala sala = new Sala(id, nombre, tipo, capacidad, ubicacion);
        salas.add(sala);

        return "Sala registrada correctamente.";
    }

    public String crearReserva(String id, String salaId, String fecha, int horaInicio, int horaFin,
                               String tipoActividad, String responsable, int cantidadAsistentes) {
        if (id == null || id.trim().isEmpty()) {
            return "Error: el ID de la reserva no puede estar vacío.";
        }

        if (buscarReservaPorId(id) != null) {
            return "Error: ya existe una reserva con ese ID.";
        }

        Sala sala = buscarSalaPorId(salaId);

        if (sala == null) {
            return "Error: la sala no existe.";
        }

        if (!sala.isActiva()) {
            return "Error: la sala no está activa.";
        }

        if (fecha == null || fecha.trim().isEmpty()) {
            return "Error: la fecha no puede estar vacía.";
        }

        if (horaInicio < 6 || horaFin > 22 || horaInicio >= horaFin) {
            return "Error: horario inválido. Use horas entre 6 y 22.";
        }

        if (tipoActividad == null || tipoActividad.trim().isEmpty()) {
            return "Error: el tipo de actividad no puede estar vacío.";
        }

        if (responsable == null || responsable.trim().isEmpty()) {
            return "Error: el responsable no puede estar vacío.";
        }

        if (cantidadAsistentes <= 0) {
            return "Error: la cantidad de asistentes debe ser mayor que cero.";
        }

        if (cantidadAsistentes > sala.getCapacidad()) {
            return "Error: la cantidad de asistentes supera la capacidad de la sala.";
        }

        if (sala.getTipo().equals("LABORATORIO")) {
            if (!tipoActividad.equals("PRACTICA")) {
                return "Error: el laboratorio solo se puede reservar para actividades de tipo PRACTICA.";
            }
        }

        if (sala.getTipo().equals("AUDITORIO")) {
            if (cantidadAsistentes < 30) {
                return "Error: el auditorio requiere mínimo 30 asistentes.";
            }
        }

        for (Reserva reserva : reservas) {
            if (!reserva.isCancelada()
                    && reserva.getSalaId().equals(salaId)
                    && reserva.getFecha().equals(fecha)) {

                boolean seCruzan = horaInicio < reserva.getHoraFin()
                        && horaFin > reserva.getHoraInicio();

                if (seCruzan) {
                    return "Error: la sala ya tiene una reserva en ese horario.";
                }
            }
        }

        Reserva reserva = new Reserva(id, salaId, fecha, horaInicio, horaFin,
                tipoActividad, responsable, cantidadAsistentes);

        reservas.add(reserva);

        return "Reserva creada correctamente.";
    }

    public String consultarReserva(String id) {
        Reserva reserva = buscarReservaPorId(id);

        if (reserva == null) {
            return "Reserva no encontrada.";
        }

        Sala sala = buscarSalaPorId(reserva.getSalaId());

        String texto = "";
        texto = texto + "Información de la reserva\n";
        texto = texto + "-------------------------\n";
        texto = texto + "ID: " + reserva.getId() + "\n";
        texto = texto + "Fecha: " + reserva.getFecha() + "\n";
        texto = texto + "Horario: " + reserva.getHoraInicio() + ":00 - " + reserva.getHoraFin() + ":00\n";
        texto = texto + "Tipo de actividad: " + reserva.getTipoActividad() + "\n";
        texto = texto + "Responsable: " + reserva.getResponsable() + "\n";
        texto = texto + "Asistentes: " + reserva.getCantidadAsistentes() + "\n";

        if (sala != null) {
            texto = texto + "Sala: " + sala.getNombre() + "\n";
            texto = texto + "Tipo de sala: " + sala.getTipo() + "\n";
            texto = texto + "Ubicación: " + sala.getUbicacion() + "\n";
        }

        if (reserva.isCancelada()) {
            texto = texto + "Estado: Cancelada\n";
        } else {
            texto = texto + "Estado: Activa\n";
        }

        return texto;
    }

    public String cancelarReserva(String id) {
        Reserva reserva = buscarReservaPorId(id);

        if (reserva == null) {
            return "No se puede cancelar. La reserva no existe.";
        }

        if (reserva.isCancelada()) {
            return "No se puede cancelar. La reserva ya estaba cancelada.";
        }

        reserva.setCancelada(true);

        return "Reserva cancelada correctamente.";
    }

    public String listarSalas() {
        if (salas.isEmpty()) {
            return "No hay salas registradas.";
        }

        String texto = "";
        texto = texto + "Listado de salas\n";
        texto = texto + "----------------\n";

        for (Sala sala : salas) {
            String estado;

            if (sala.isActiva()) {
                estado = "Activa";
            } else {
                estado = "Inactiva";
            }

            texto = texto + sala.getId() + " | "
                    + sala.getNombre() + " | "
                    + sala.getTipo() + " | Capacidad: "
                    + sala.getCapacidad() + " | "
                    + sala.getUbicacion() + " | "
                    + estado + "\n";
        }

        return texto;
    }

    public String listarReservas() {
        if (reservas.isEmpty()) {
            return "No hay reservas registradas.";
        }

        String texto = "";
        texto = texto + "Listado de reservas\n";
        texto = texto + "-------------------\n";

        for (Reserva reserva : reservas) {
            String estado;

            if (reserva.isCancelada()) {
                estado = "Cancelada";
            } else {
                estado = "Activa";
            }

            texto = texto + reserva.getId() + " | Sala: "
                    + reserva.getSalaId() + " | "
                    + reserva.getFecha() + " | "
                    + reserva.getHoraInicio() + ":00 - "
                    + reserva.getHoraFin() + ":00 | "
                    + reserva.getTipoActividad() + " | "
                    + estado + "\n";
        }

        return texto;
    }

    public String generarReporte() {
        int totalAulas = 0;
        int totalLaboratorios = 0;
        int totalAuditorios = 0;
        int reservasActivas = 0;
        int reservasCanceladas = 0;

        for (Sala sala : salas) {
            if (sala.getTipo().equals("AULA")) {
                totalAulas++;
            } else if (sala.getTipo().equals("LABORATORIO")) {
                totalLaboratorios++;
            } else if (sala.getTipo().equals("AUDITORIO")) {
                totalAuditorios++;
            }
        }

        for (Reserva reserva : reservas) {
            if (reserva.isCancelada()) {
                reservasCanceladas++;
            } else {
                reservasActivas++;
            }
        }

        String texto = "";
        texto = texto + "Reporte general\n";
        texto = texto + "---------------\n";
        texto = texto + "Total de salas: " + salas.size() + "\n";
        texto = texto + "Aulas: " + totalAulas + "\n";
        texto = texto + "Laboratorios: " + totalLaboratorios + "\n";
        texto = texto + "Auditorios: " + totalAuditorios + "\n";
        texto = texto + "Total de reservas: " + reservas.size() + "\n";
        texto = texto + "Reservas activas: " + reservasActivas + "\n";
        texto = texto + "Reservas canceladas: " + reservasCanceladas + "\n";

        return texto;
    }

    private Sala buscarSalaPorId(String id) {
        for (Sala sala : salas) {
            if (sala.getId().equals(id)) {
                return sala;
            }
        }

        return null;
    }

    private Reserva buscarReservaPorId(String id) {
        for (Reserva reserva : reservas) {
            if (reserva.getId().equals(id)) {
                return reserva;
            }
        }

        return null;
    }
}
