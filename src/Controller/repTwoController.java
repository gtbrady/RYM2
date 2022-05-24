package Controller;

import DAO.DBRepTwo;
import Model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * repTwoController controls report two for the application
 */
public class repTwoController implements Initializable {
    public TableView repTwoTable;
    public TableColumn Contact;
    public TableColumn Appointment_ID;
    public TableColumn Title;
    public TableColumn Type;
    public TableColumn Description;
    public TableColumn Start;
    public TableColumn End;
    public TableColumn Customer_ID;

    /**
     * Populates the tableview with the schedules for the previously selected contact
     * @param url
     * @param resourceBundle
     */
    //placeholder for some rewrite - just to make the compiler happy
    Contact contact = new Contact(1,"name", "email");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repTwoTable.setItems((DBRepTwo.getRepTwo(contact)));
        Contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Start.setCellValueFactory(new PropertyValueFactory<>("start"));
        End.setCellValueFactory(new PropertyValueFactory<>("end"));
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /**
     * Transitions back to the reporting menu
     * @param actionEvent When the back button is pressed
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
}
