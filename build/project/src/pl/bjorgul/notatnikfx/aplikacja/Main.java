package pl.bjorgul.notatnikfx.aplikacja;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/pl/bjorgul/notatnikfx/estetyka/NotatnikMain.fxml"));
        Main.stage.setTitle("BJORGUL.NOTE (New)");
        Main.stage.setScene(new Scene(root, 900, 650));
        Main.stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void updateTitle(String title) {
    	stage.setTitle("BJORGUL.NOTE (" + title + ")");
    }
}

