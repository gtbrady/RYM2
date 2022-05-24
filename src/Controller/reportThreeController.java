package Controller;

import DAO.DBReportThree;
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

public class reportThreeController implements Initializable {


    public TableView reportThreeTable;
    public TableColumn Office;
    public TableColumn APPOINTMENT_COUNT;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportThreeTable.setItems(DBReportThree.getReportThree());
        Office.setCellValueFactory(new PropertyValueFactory<>("officeName"));
        APPOINTMENT_COUNT.setCellValueFactory(new PropertyValueFactory<>("count"));

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
