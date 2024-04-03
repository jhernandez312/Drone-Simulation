import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // public static void main(String[] args) {
    //     System.out.println("Welcome to the Restaurant Supply Express System!");
    //     InterfaceLoop simulator = new InterfaceLoop();
    //     simulator.commandLoop();
    // }
    private static Stage mainStage;
    private static Scene primaryScene;
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return mainStage;
    }

    public static Scene getScene() {
        return primaryScene;
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try{
            mainStage = primaryStage;
            root = FXMLLoader.load(getClass().getResource("IntroScene.fxml"));
            Scene scene = new Scene(root);
            primaryScene = scene;
            primaryStage.setTitle("WIR TRINKEN BIER Delivery Service");
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Welcome to the Restaurant Supply Express System!");
        } catch (IOException e) {
            //empty
        }
    }
}
