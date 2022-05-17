package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main class launches the application
 */
public class Main extends Application{

    /**
     * Sets the window for the application
     * @param primaryStage stage object
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        String title = "Login";
        try{
            ResourceBundle rb = ResourceBundle.getBundle("Main/Nat", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("en")){
                title = rb.getString("title");
            }
            else if (Locale.getDefault().getLanguage().equals("fr")) {
                title = rb.getString("title");
            }
            else {
                System.out.println("Language not supported");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/View/loginView.fxml"));
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    /**
     * main method, launches the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
