package Controller;

import DAO.DBContact;
import Model.Contact;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * reportingController controls the reporting menu for the application
 */
public class reportingController implements Initializable {
    public ComboBox contactDropDown;
    private ObservableList<Contact> contacts = DBContact.getContacts();
    private static Contact selectedContact;

    /**
     * Gets the selected contact for report 2
     * @return The contact the user selects via the combo box drop down
     */
    public static Contact getSelectedContact() {
        return selectedContact;
    }

    /**
     * Initializes the reporting menu screen and sets the contacts for the combo box
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactDropDown.setItems(contacts);
    }

    /**
     * Transitions to the main menu screen
     * @param actionEvent When the back button is pressed
     * @throws IOException
     */
    public void toMainController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 310, 335  );
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Transitions to the report one screen
     * @param actionEvent When the Appointments by Type/Month button is pressed
     * @throws IOException
     */
    public void toRepOneController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/repOneView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 600);
        stage.setTitle("Appointments by Type/Month");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Transitions to the report two screen
     * @param actionEvent When the go button is pressed. Requires a contact to be selected
     * @throws IOException
     */
    public void toRepTwoController(ActionEvent actionEvent) throws IOException {
        try {
            if(contactDropDown.getSelectionModel().getSelectedItem() == null){
                throw new NullPointerException();
            }
            else {
                selectedContact = (Contact) contactDropDown.getSelectionModel().getSelectedItem();
                Parent root = FXMLLoader.load(getClass().getResource("/View/repTwoView.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 880, 570);
                stage.setTitle("Contact Schedules");
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            }

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing selected");
            alert.setContentText("Please select a contact to view their schedule.");
            alert.showAndWait();
        }

    }

    /**
     * Transitions to the report three screen
     * @param actionEvent When the customers by country button is pressed
     * @throws IOException
     */
    public void toRepThreeController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/repThreeView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 307, 470);
        stage.setTitle("Customers by Country");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
