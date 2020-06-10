package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**Основний клас програми
 * @author Іванішин Іван
 * @version 1.0
 */
public class Main extends Application  {

    /**
     * Встановлює додаток інтерфейсу користувача з основним заголовком, піктограмами та вікном
     * @param primaryStage первинне вікно від JavaFx
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CarServiceView.fxml"));
        primaryStage.setTitle("Practical work №2");
        //Image image = new Image("/res/logo.png");
        primaryStage.getIcons().add(new Image("/res/icon.png"));
        primaryStage.setScene(new Scene(root, 1200, 800));
        root.getStylesheets().add((getClass().getResource("/sample/application.css")).toExternalForm());
        primaryStage.show();
    }
    public static void main(String[] args)  throws IOException {
    	launch(args); 
    }
}
