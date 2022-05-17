package Controller;

import DAO.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * mainController controls the main menu for the application
 */
public class mainController implements Initializable {

    /**
     * Initializes the main menu
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Transitions to the customer screen
     * @param actionEvent When the manage customers button is pressed
     * @throws IOException
     */
    public void toCustomerController(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/View/customerView.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 915, 445);
            stage.setTitle("Manage Customers");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
    }

    /**
     * Transitions to the appointment screen
     * @param actionEvent When the manage schedule button is pressed
     * @throws IOException
     */
    public void toAppointmentController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/appointmentView.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1025, 560 );
        stage.setTitle("Manage Schedule");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Transitions to the reporting screen
     * @param actionEvent When the reporting button is pressed
     * @throws IOException
     */
    public void toReportingController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/reportingView.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 330);
        stage.setTitle("Reporting");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Transitions back to the login screen
     * @param actionEvent When the logout button is pressed
     * @throws IOException
     */
    public void logout(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/loginView.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
        stage.centerOnScreen();
        DBConnection.closeConnection();
    }

    /**
     * Closes application
     * @param actionEvent When the exit button is pressed
     */
    public void exitApplication(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DBConnection.closeConnection();
            System.exit(0);
        }
    }
}

