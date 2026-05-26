package isi.reservarsalas.entities;

public class Reserva {

    private String id;
    private String salaId;
    private String fecha;
    private int horaInicio;
    private int horaFin;
    private String tipoActividad;
    private String responsable;
    private int cantidadAsistentes;
    private boolean cancelada;

    public Reserva(String id, String salaId, String fecha, int horaInicio, int horaFin,
                   String tipoActividad, String responsable, int cantidadAsistentes) {
        this.id = id;
        this.salaId = salaId;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoActividad = tipoActividad;
        this.responsable = responsable;
        this.cantidadAsistentes = cantidadAsistentes;
        this.cancelada = false;
    }

    public String getId() {
        return id;
    }

    public String getSalaId() {
        return salaId;
    }

    public String getFecha() {
        return fecha;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public String getResponsable() {
        return responsable;
    }

    public int getCantidadAsistentes() {
        return cantidadAsistentes;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }
}
