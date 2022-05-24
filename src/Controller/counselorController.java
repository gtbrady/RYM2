package Controller;

import DAO.DBCounselor;
import DAO.DBnAppointment;
import Model.Counselor;
import Model.nAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class counselorController implements Initializable {

    //tableview
    public TableView counselorTable;
    public TableColumn coIDCol;
    public TableColumn counselorNameCol;
    public TableColumn counselorUsernameCol;
    public TableColumn counselorEmailCol;
    public TableColumn counselorPhoneCol;

    //label
    public Label headerLabel;

    //buttons
    public Button addButton;
    public Button editButton;
    public Button deleteButton;
    public Button saveButton;
    public Button backButton;
    public Button cancelButton;

    //textviews
    public TextField nameText;
    public TextField usernameText;
    public TextField emailText;
    public TextField phoneText;

    private int buttonStatus = -1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        counselorTable.setItems(DBCounselor.getCounselors());
        coIDCol.setCellValueFactory(new PropertyValueFactory<>("counselorID"));
        counselorNameCol.setCellValueFactory(new PropertyValueFactory<>("counselorName"));
        counselorUsernameCol.setCellValueFactory(new PropertyValueFactory<>("counselorUsername"));
        counselorEmailCol.setCellValueFactory(new PropertyValueFactory<>("counselorEmail"));
        counselorPhoneCol.setCellValueFactory(new PropertyValueFactory<>("counselorPhone"));
    }

    public void toMainController(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 310, 395);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    private void enableFields(ActionEvent actionEvent) {
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        nameText.setDisable(false);
        nameText.setEditable(true);
        phoneText.setDisable(false);
        phoneText.setEditable(true);
        usernameText.setDisable(false);
        usernameText.setEditable(true);
        emailText.setDisable(false);
        emailText.setEditable(true);

    }

    private void populateFields(ActionEvent actionEvent) {
        try {
            Counselor theCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();
            if (theCounselor == null) {
                throw new NullPointerException();
            }
            nameText.setText(theCounselor.getCounselorName());
            usernameText.setText(theCounselor.getCounselorUsername());
            phoneText.setText(theCounselor.getCounselorPhone());
            emailText.setText(theCounselor.getCounselorEmail());
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing selected");
            alert.setContentText("Please select counselor to edit");
            alert.showAndWait();
        }
    }

    private void clearFields(ActionEvent actionEvent) {
        headerLabel.setText("Counselors");
        buttonStatus = -1;
        addButton.setDisable(false);
        editButton.setDisable(false);
        deleteButton.setDisable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        nameText.setDisable(true);
        nameText.clear();
        phoneText.setDisable(true);
        phoneText.clear();
        emailText.setDisable(true);
        emailText.clear();
        usernameText.setDisable(true);
        usernameText.clear();
    }

    public void onAdd(ActionEvent actionEvent) {
        headerLabel.setText("Add Counselor");
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        buttonStatus = 1;
        enableFields(actionEvent);
    }

    public void onEdit(ActionEvent actionEvent) {
        try{
            Counselor theCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();
            populateFields(actionEvent);
            if(theCounselor == null) {
                throw new NullPointerException();
            }
            headerLabel.setText("Edit Counselor");
            editButton.setDisable(true);
            deleteButton.setDisable(true);
            enableFields(actionEvent);
            buttonStatus = 2;

        } catch(NullPointerException e) {

        }
    }

    public void onDelete(ActionEvent actionEvent) throws IOException {
        try {
            Counselor theCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();
            populateFields(actionEvent);
            if(theCounselor == null) {
                throw new NullPointerException();
            }
            addButton.setDisable(true);
            editButton.setDisable(true);
            buttonStatus = -1;
            //existingAppointmentException();

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete: " + theCounselor.toString());
            confirm.setTitle("Delete Confirmation");
            confirm.setContentText("Delete: " + theCounselor.toString() + "?");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int deleteCheck = DBCounselor.deleteCounselor(theCounselor);
                if (deleteCheck > 0) {
                    Alert update = new Alert(Alert.AlertType.INFORMATION);
                    update.setTitle("Delete successful");
                    update.setContentText(theCounselor.getCounselorName() + " has been deleted.");
                    update.showAndWait();

                } else {
                    Alert update = new Alert(Alert.AlertType.INFORMATION);
                    update.setTitle("Delete unsuccessful");
                    update.setContentText(theCounselor.getCounselorName() + " hasn't been deleted.");
                    update.showAndWait();

                }
            }
        }/* catch (ExistingAppointments e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot Delete Counselor with Existing Appointments");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }*/
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        counselorTable.setItems(DBCounselor.getCounselors());
        onCancel(actionEvent);

    }

    public void onSave(ActionEvent actionEvent) {
        try {
            emptyFieldsException();
            Counselor stagedCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();
            if(buttonStatus<=0) {
                throw  new IOException();
            }
            if(buttonStatus == 1) {
                Counselor saveCounselor = new Counselor(-1, nameText.getText(),
                        phoneText.getText(),usernameText.getText(), emailText.getText());
                DBCounselor.addCounselor(saveCounselor);
                onCancel(actionEvent);
            }
            if(buttonStatus == 2) {
                Counselor saveCounselor = new Counselor(stagedCounselor.getCounselorID(), nameText.getText(),
                        phoneText.getText(),usernameText.getText(), emailText.getText());
                noChangeCheck(saveCounselor,stagedCounselor);
                DBCounselor.editCounselor(saveCounselor);
                onCancel(actionEvent);

            }
            counselorTable.setItems(DBCounselor.getCounselors());
        }
        catch (EmptyFields e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(NoChanges e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing information");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (IOException e) {
        }
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        clearFields(actionEvent);
    }



    public class EmptyFields extends Exception {
        public EmptyFields(String s, int i) {
            super("The "+ i + " fields below are required:\n" + s);
        }
    }

    public void emptyFieldsException() throws EmptyFields {
        StringBuilder m = new StringBuilder();
        int errorCounter = 0;
        Boolean nullFields = false;
        if (nameText.getText().isEmpty()) {
            m.append("Name \n");
            ++errorCounter;
            nullFields = true;
        }
        if (phoneText.getText().isEmpty()) {
            m.append("Phone Number \n");
            ++errorCounter;
            nullFields = true;
        }
        if (usernameText.getText().isEmpty()) {
            m.append("Username \n");
            ++errorCounter;
            nullFields = true;
        }
        if (emailText.getText().isEmpty()) {
            m.append("Email \n");
            ++errorCounter;
            nullFields = true;
        }
        if(nullFields) {
            throw new EmptyFields(m.toString(),errorCounter);
        }
    }

    public class ExistingAppointments extends Exception {
        public ExistingAppointments(String s) {
            super("The customer below cannot be deleted due to existing scheduled appointments. \n" + s);
        }
    }

    public void existingAppointmentException() throws ExistingAppointments {
        Boolean exists = false;
        Counselor deleteCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();
        ObservableList<nAppointment> appointments = DBnAppointment.getAppointments();
        StringBuilder m = new StringBuilder(deleteCounselor.getCounselorName() + ": \n");
        for(nAppointment a: appointments) {
            if(a.getCounselorID() == deleteCounselor.getCounselorID()) {
                m.append(a.getType().toString() + " appointment: " + a.getAppointmentID() +
                        " scheduled for " + a.getStartTime() + "\n");
                exists = true;
            }
        }
        if(exists) {
            throw new ExistingAppointments(m.toString());
        }

    }


    private class NoChanges extends Exception {
        public NoChanges() {super ("No changes have been made to the selected counselor");}
    }

    public void noChangeCheck(Counselor save, Counselor stage) throws NoChanges {
        if(save.equals(stage)) {
            throw new NoChanges();
        }
    }


}




