package isi.reservarsalas.mainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        ReservaView reservaView = new ReservaView();

        Scene scene = new Scene(reservaView.crearVista(), 980, 680);

        stage.setTitle("Reserva de Salas - Proyecto base para refactorización SOLID");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
