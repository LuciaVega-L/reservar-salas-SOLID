package isi.reservarsalas.mainView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        ReservaService reservaService = new ReservaService();
        cargarDatosIniciales(reservaService);

        ReservaView reservaView = new ReservaView(reservaService);

        Scene scene = new Scene(reservaView.crearVista(), 980, 680);

        stage.setTitle("Reserva de Salas - Proyecto base para refactorización SOLID");
        stage.setScene(scene);
        stage.show();
    }

    private void cargarDatosIniciales(ReservaService reservaService) {
        reservaService.registrarSala("S001", "Aula 101", "AULA", 35, "Bloque A");
        reservaService.registrarSala("S002", "Laboratorio de Sistemas", "LABORATORIO", 25, "Bloque B");
        reservaService.registrarSala("S003", "Auditorio Principal", "AUDITORIO", 120, "Bloque Central");

        reservaService.crearReserva("R001", "S001", "2026-05-25", 8, 10, "CLASE", "Ing. Pérez", 30);
        reservaService.crearReserva("R002", "S002", "2026-05-25", 10, 12, "PRACTICA", "Ing. Gómez", 20);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
