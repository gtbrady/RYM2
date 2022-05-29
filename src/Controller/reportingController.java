package Controller;

import DAO.DBContact;
import DAO.DBCounselor;
import Model.Contact;
import Model.Counselor;
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
    public ComboBox counselorDropDown;
    private ObservableList<Counselor> counselors = DBCounselor.getCounselors();
    private static Counselor selectedCounselor;

    /**
     * Gets the selected contact for report 2
     * @return The contact the user selects via the combo box drop down
     */
    public static Counselor getSelectedCounselor() {
        return selectedCounselor;
    }

    /**
     * Initializes the reporting menu screen and sets the contacts for the combo box
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        counselorDropDown.setItems(counselors);
    }

    /**
     * Transitions to the main menu screen
     * @param actionEvent When the back button is pressed
     * @throws IOException
     */
    public void toMainController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 310, 395  );
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void toReportOneController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/reportOneView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 600);
        stage.setTitle("Appointments by Type/Month");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void toReportTwoController(ActionEvent actionEvent) throws IOException {
        try {
            if(counselorDropDown.getSelectionModel().getSelectedItem() == null){
                throw new NullPointerException();
            }
            else {
                selectedCounselor= (Counselor) counselorDropDown.getSelectionModel().getSelectedItem();
                Parent root = FXMLLoader.load(getClass().getResource("/View/reportTwoView.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 696, 570);
                stage.setTitle("Counselor Schedules");
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            }

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing selected");
            alert.setContentText("Please select a counselor to view their schedule.");
            alert.showAndWait();
        }
    }

    public void toReportThreeController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/reportThreeView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 340, 470);
        stage.setTitle("Customers by Country");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
