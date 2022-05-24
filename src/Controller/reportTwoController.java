package Controller;

import DAO.DBReportTwo;
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

public class reportTwoController implements Initializable {

    public TableView reportTwoTable;
    public TableColumn counselorNameCol;
    public TableColumn descriptionCol;
    public TableColumn typeCol;
    public TableColumn clientCol;

    public TableColumn Start;

    public TableColumn End;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportTwoTable.setItems(DBReportTwo.getReportTwo(reportingController.getSelectedCounselor()));
        counselorNameCol.setCellValueFactory(new PropertyValueFactory<>("counselorName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        clientCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        Start.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        End.setCellValueFactory(new PropertyValueFactory<>("endDate"));

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
