package isi.reservarsalas.mainView;

import isi.reservarsalas.usecases.dto.OperationResult;
import isi.reservarsalas.usecases.services.ReservaApp;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReservaView {

    private ReservaApp reservaApp;

    private TextField txtSalaId;
    private TextField txtSalaNombre;
    private ComboBox<String> cbSalaTipo;
    private TextField txtSalaCapacidad;
    private TextField txtSalaUbicacion;

    private TextField txtReservaId;
    private TextField txtReservaSalaId;
    private TextField txtFecha;
    private TextField txtHoraInicio;
    private TextField txtHoraFin;
    private ComboBox<String> cbTipoActividad;
    private TextField txtResponsable;
    private TextField txtAsistentes;

    private TextArea txtResultado;

    public ReservaView() {
        reservaApp = new ReservaApp();
    }

    public Parent crearVista() {
        Label titulo = new Label("Sistema de Reserva de Salas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        GridPane formularioSalas = crearFormularioSalas();
        GridPane formularioReservas = crearFormularioReservas();

        HBox botonesSalas = crearBotonesSalas();
        HBox botonesReservas = crearBotonesReservas();

        txtResultado = new TextArea();
        txtResultado.setEditable(false);
        txtResultado.setPrefHeight(230);

        VBox root = new VBox(12);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                titulo,
                new Label("Datos de sala"),
                formularioSalas,
                botonesSalas,
                new Label("Datos de reserva"),
                formularioReservas,
                botonesReservas,
                new Label("Resultado"),
                txtResultado
        );

        return root;
    }

    private GridPane crearFormularioSalas() {
        txtSalaId = new TextField();
        txtSalaNombre = new TextField();

        cbSalaTipo = new ComboBox<>();
        cbSalaTipo.setItems(FXCollections.observableArrayList(
                "AULA",
                "LABORATORIO",
                "AUDITORIO"
        ));
        cbSalaTipo.setPromptText("Seleccione tipo");

        txtSalaCapacidad = new TextField();
        txtSalaUbicacion = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);

        grid.add(new Label("ID sala:"), 0, 0);
        grid.add(txtSalaId, 1, 0);

        grid.add(new Label("Nombre:"), 2, 0);
        grid.add(txtSalaNombre, 3, 0);

        grid.add(new Label("Tipo:"), 0, 1);
        grid.add(cbSalaTipo, 1, 1);

        grid.add(new Label("Capacidad:"), 2, 1);
        grid.add(txtSalaCapacidad, 3, 1);

        grid.add(new Label("Ubicación:"), 0, 2);
        grid.add(txtSalaUbicacion, 1, 2);

        return grid;
    }

    private GridPane crearFormularioReservas() {
        txtReservaId = new TextField();
        txtReservaSalaId = new TextField();
        txtFecha = new TextField();
        txtHoraInicio = new TextField();
        txtHoraFin = new TextField();

        cbTipoActividad = new ComboBox<>();
        cbTipoActividad.setItems(FXCollections.observableArrayList(
                "CLASE",
                "PRACTICA",
                "EVENTO"
        ));
        cbTipoActividad.setPromptText("Seleccione actividad");

        txtResponsable = new TextField();
        txtAsistentes = new TextField();

        txtFecha.setPromptText("2026-05-25");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);

        grid.add(new Label("ID reserva:"), 0, 0);
        grid.add(txtReservaId, 1, 0);

        grid.add(new Label("ID sala:"), 2, 0);
        grid.add(txtReservaSalaId, 3, 0);

        grid.add(new Label("Fecha:"), 0, 1);
        grid.add(txtFecha, 1, 1);

        grid.add(new Label("Hora inicio:"), 2, 1);
        grid.add(txtHoraInicio, 3, 1);

        grid.add(new Label("Hora fin:"), 0, 2);
        grid.add(txtHoraFin, 1, 2);

        grid.add(new Label("Tipo actividad:"), 2, 2);
        grid.add(cbTipoActividad, 3, 2);

        grid.add(new Label("Responsable:"), 0, 3);
        grid.add(txtResponsable, 1, 3);

        grid.add(new Label("Asistentes:"), 2, 3);
        grid.add(txtAsistentes, 3, 3);

        return grid;
    }

    private HBox crearBotonesSalas() {

        Button btnRegistrarSala = new Button("Registrar sala");
        Button btnListarSalas = new Button("Listar salas");

        btnRegistrarSala.setOnAction(e -> registrarSala());
        btnListarSalas.setOnAction(e -> listarSalas());

        HBox hbox = new HBox(10);

        hbox.getChildren().addAll(
                btnRegistrarSala,
                btnListarSalas
        );

        return hbox;
    }

    private HBox crearBotonesReservas() {
        Button btnCrearReserva = new Button("Crear reserva");
        Button btnConsultarReserva = new Button("Consultar reserva");
        Button btnCancelarReserva = new Button("Cancelar reserva");
        Button btnLimpiar = new Button("Limpiar");

        btnCrearReserva.setOnAction(e -> crearReserva());
        btnConsultarReserva.setOnAction(e -> consultarReserva());
        btnCancelarReserva.setOnAction(e -> cancelarReserva());
        btnLimpiar.setOnAction(e -> limpiarCampos());

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(
                btnCrearReserva,
                btnConsultarReserva,
                btnCancelarReserva,
                btnLimpiar
        );

        return hbox;
    }

    private void registrarSala() {
        try {
            String id = txtSalaId.getText();
            String nombre = txtSalaNombre.getText();
            String tipo = cbSalaTipo.getValue();
            int capacidad = Integer.parseInt(txtSalaCapacidad.getText());
            String ubicacion = txtSalaUbicacion.getText();

            OperationResult resultado = reservaApp.registrarSala(
                    id,
                    nombre,
                    tipo,
                    capacidad,
                    ubicacion
            );

            txtResultado.setText(resultado.getMessage());

        } catch (NumberFormatException e) {
            txtResultado.setText(
                    "Error: la capacidad debe ser un número entero."
            );
        }
    }

    private void listarSalas() {

        OperationResult resultado = reservaApp.listarSalas();

        txtResultado.setText(resultado.getMessage());
    }

    private void crearReserva() {
        try {
            String id = txtReservaId.getText();
            String salaId = txtReservaSalaId.getText();
            String fecha = txtFecha.getText();
            int horaInicio = Integer.parseInt(txtHoraInicio.getText());
            int horaFin = Integer.parseInt(txtHoraFin.getText());
            String tipoActividad = cbTipoActividad.getValue();
            String responsable = txtResponsable.getText();
            int asistentes = Integer.parseInt(txtAsistentes.getText());

            OperationResult resultado = reservaApp.crearReserva(
                    id,
                    salaId,
                    fecha,
                    horaInicio,
                    horaFin,
                    tipoActividad,
                    responsable,
                    asistentes
            );

            txtResultado.setText(resultado.getMessage());

        } catch (NumberFormatException e) {
            txtResultado.setText(
                    "Error: las horas y la cantidad de asistentes deben ser números enteros."
            );
        }
    }

    private void consultarReserva() {
        OperationResult resultado =
                reservaApp.consultarReserva(txtReservaId.getText());

        txtResultado.setText(resultado.getMessage());
    }

    private void cancelarReserva() {
        OperationResult resultado =
                reservaApp.cancelarReserva(txtReservaId.getText());

        txtResultado.setText(resultado.getMessage());
    }

    private void limpiarCampos() {
        txtSalaId.clear();
        txtSalaNombre.clear();
        cbSalaTipo.setValue(null);
        txtSalaCapacidad.clear();
        txtSalaUbicacion.clear();

        txtReservaId.clear();
        txtReservaSalaId.clear();
        txtFecha.clear();
        txtHoraInicio.clear();
        txtHoraFin.clear();
        cbTipoActividad.setValue(null);
        txtResponsable.clear();
        txtAsistentes.clear();

        txtResultado.clear();
    }
}