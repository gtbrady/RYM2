package Controller;

import DAO.DBReportOne;
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

public class reportOneController implements Initializable {

    public TableView reportOneTable;
    public TableColumn Month;
    public TableColumn Type;
    public TableColumn TOTAL_APPOINTMENTS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportOneTable.setItems(DBReportOne.getReportOne());
        Month.setCellValueFactory(new PropertyValueFactory<>("month"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        TOTAL_APPOINTMENTS.setCellValueFactory(new PropertyValueFactory<>("totalAppointments"));
    }

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
