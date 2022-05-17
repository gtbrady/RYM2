package Controller;

import DAO.DBRepThree;
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
 * repThreeController controls report three for the application
 */
public class repThreeController implements Initializable {
    public TableView repThreeTable;
    public TableColumn Country;
    public TableColumn CUSTOMER_COUNT;

    /**
     * Populates the tableview with total number of customers by country
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repThreeTable.setItems((DBRepThree.getRepThree()));
        Country.setCellValueFactory(new PropertyValueFactory<>("country"));
        CUSTOMER_COUNT.setCellValueFactory(new PropertyValueFactory<>("customerCount"));

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
