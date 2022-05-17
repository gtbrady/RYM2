package Controller;

import DAO.DBRepOne;
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
 * repOneController controls report one for the application
 */
public class repOneController implements Initializable {
    public TableView repOneTable;
    public TableColumn Month;
    public TableColumn Type;
    public TableColumn TOTAL_APPOINTMENTS;


    /**
     * Populates the tableview with appointments by type/month
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repOneTable.setItems((DBRepOne.getReportOne()));
        Month.setCellValueFactory(new PropertyValueFactory<>("month"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        TOTAL_APPOINTMENTS.setCellValueFactory(new PropertyValueFactory<>("totalAppointments"));

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
