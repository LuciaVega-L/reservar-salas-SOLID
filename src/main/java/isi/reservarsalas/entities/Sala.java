package isi.reservarsalas.entities;

public class Sala {

    private String id;
    private String nombre;
    private String tipo;
    private int capacidad;
    private String ubicacion;
    private boolean activa;

    public Sala(String id, String nombre, String tipo, int capacidad, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.activa = true;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public boolean isActiva() {
        return activa;
    }

    public boolean isInactiva() {
        return activa = false;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
