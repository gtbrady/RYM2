package Controller;

import DAO.DBCounselor;
import DAO.DBOffice;
import DAO.DBSuite;
import DAO.DBAppointment;
import Model.Counselor;
import Model.Office;
import Model.Suite;
import Model.Appointment;
import javafx.collections.FXCollections;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class counselorController implements Initializable {

    //tableview
    public TableView counselorTable;
    public TableColumn coIDCol;
    public TableColumn counselorNameCol;
    public TableColumn counselorUsernameCol;
    public TableColumn counselorEmailCol;
    public TableColumn counselorPhoneCol;
    public TableColumn counselorOfficeCol;
    public TableColumn counselorSuiteCol;

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
    public ComboBox<Office> officeDropDown;
    public ComboBox<Suite> suiteDropDown;
    public TextField counselorSearchBar;


    private int buttonStatus = -1;

    private static Office stagedOffice;
    private static Suite stagedSuite;

    private static Counselor stagedCounselor;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        counselorTable.setItems(DBCounselor.getCounselors());
        coIDCol.setCellValueFactory(new PropertyValueFactory<>("counselorID"));
        counselorNameCol.setCellValueFactory(new PropertyValueFactory<>("counselorName"));
        counselorUsernameCol.setCellValueFactory(new PropertyValueFactory<>("counselorUsername"));
        counselorEmailCol.setCellValueFactory(new PropertyValueFactory<>("counselorEmail"));
        counselorPhoneCol.setCellValueFactory(new PropertyValueFactory<>("counselorPhone"));
        counselorOfficeCol.setCellValueFactory(new PropertyValueFactory<>("officeID"));
        counselorSuiteCol.setCellValueFactory(new PropertyValueFactory<>("suiteID"));

        officeDropDown.setItems(DBOffice.getOffices());
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

    private void enableFields() {
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
        officeDropDown.setDisable(false);
        suiteDropDown.setDisable(false);


    }

    private void populateFields() {


            Counselor theCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();

            nameText.setText(theCounselor.getCounselorName());
            usernameText.setText(theCounselor.getCounselorUsername());
            phoneText.setText(theCounselor.getCounselorPhone());
            emailText.setText(theCounselor.getCounselorEmail());
            Office theOffice = new Office(theCounselor.getOfficeID());
            officeDropDown.setValue(theOffice);
            Suite theSuite = new Suite(theCounselor.getSuiteID());
            suiteDropDown.setValue(theSuite);


    }

    private void clearFields() {
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

        officeDropDown.setDisable(true);
        officeDropDown.getSelectionModel().clearSelection();
        officeDropDown.setValue(null);
        suiteDropDown.setDisable(true);
        suiteDropDown.getSelectionModel().clearSelection();
        suiteDropDown.setValue(null);
    }

    public void onAdd(ActionEvent actionEvent) {
        headerLabel.setText("Add Counselor");
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        buttonStatus = 1;
        enableFields();
    }

    public void onEdit(ActionEvent actionEvent) {
        try{
            Counselor theCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();
            populateFields();
            if(theCounselor == null) {
                throw new NullPointerException();
            }
            headerLabel.setText("Edit Counselor");
            addButton.setDisable(true);
            deleteButton.setDisable(true);
            enableFields();
            buttonStatus = 2;

        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing selected");
            alert.setContentText("Please select counselor first");
            alert.showAndWait();

        }
    }

    public void onDelete(ActionEvent actionEvent) throws IOException {
        try {
            Counselor theCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();
            populateFields();
            suiteDropDown.setDisable(true);
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing selected");
            alert.setContentText("Please select counselor first");
            alert.showAndWait();

        }
        counselorTable.setItems(DBCounselor.getCounselors());
        onCancel(actionEvent);

    }

    public void onSave(ActionEvent actionEvent) {
        try {
            emptyFieldsException();
            stagedCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();
            if(buttonStatus<=0) {
                throw  new IOException();
            }
            if(buttonStatus == 1) {
                Counselor saveCounselor = new Counselor(-1, nameText.getText(),
                        phoneText.getText(),usernameText.getText(), emailText.getText(),
                        officeDropDown.getValue().getOfficeID(), suiteDropDown.getValue().getSuiteID());
                emailCheck(saveCounselor.getCounselorEmail());
                phoneCheck(saveCounselor.getCounselorPhone());
                DBCounselor.addCounselor(saveCounselor);
                onCancel(actionEvent);
            }
            if(buttonStatus == 2) {
                Counselor saveCounselor = new Counselor(stagedCounselor.getCounselorID(), nameText.getText(),
                        phoneText.getText(),usernameText.getText(), emailText.getText(),
                        officeDropDown.getValue().getOfficeID(), suiteDropDown.getValue().getSuiteID());
                emailCheck(saveCounselor.getCounselorEmail());
                phoneCheck(saveCounselor.getCounselorPhone());
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
        catch(InvalidEmail e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid email");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(InvalidPhone e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid phone");
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
        clearFields();
    }

    public void onOffice(ActionEvent actionEvent) {

        ObservableList<Suite> selectedSuites = FXCollections.observableArrayList();
        ObservableList<Suite> availableSuites = FXCollections.observableArrayList();
        ObservableList<Suite> reservedSuites = FXCollections.observableArrayList();
        ObservableList<Counselor> counselors = DBCounselor.getCounselors();

        Office selectedOffice = officeDropDown.getSelectionModel().getSelectedItem();
        if(officeDropDown.getValue() != null) {
            suiteDropDown.setValue(null);
            boolean available = true;
            for (Suite s : DBSuite.getSuites()) {
                if (s.getOfficeID() == selectedOffice.getOfficeID()) {
                    selectedSuites.add(s);
                }
            }

            for(Suite s : selectedSuites) {
                for(Counselor c : counselors) {
                    if(s.getSuiteID() == c.getSuiteID()) {
                        available = false;
                        break;
                    }
                    else {
                        available = true;
                    }
                }
                if(available) {
                    availableSuites.add(s);
                }
            }

        }
        suiteDropDown.setItems(availableSuites);
        suiteDropDown.setDisable(false);

    }

    public void counselorSearch(ActionEvent actionEvent) {
        String searchString = counselorSearchBar.getText().toUpperCase();
        ObservableList<Counselor> allCounselors = DBCounselor.getCounselors();
        ObservableList<Counselor> matchingCounselors = FXCollections.observableArrayList();

        for (Counselor c : allCounselors) {
            if(c.getCounselorName().toUpperCase().startsWith(searchString)) {
                matchingCounselors.add(c);
            }
        }
        counselorTable.setItems(matchingCounselors);
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
        if(officeDropDown.getValue() == null) {
            m.append("Office \n");
            ++errorCounter;
            nullFields = true;
        }
        if(suiteDropDown.getValue() == null) {
            m.append("Suite \n");
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

    public class InvalidEmail extends Exception {
        public InvalidEmail(String s) {
            super(s + " is not a valid email address");
        }
    }

    public void emailCheck (String email) throws InvalidEmail{
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        boolean validEmail = true;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        validEmail = matcher.matches();
        if(!validEmail) {
            throw new InvalidEmail(email);
        }
    }

    public class InvalidPhone extends Exception {
        public InvalidPhone(String s) {
            super(s + " is not a valid phone number");
        }
    }


    public void phoneCheck(String phone) throws InvalidPhone{
        String regex="^\\d{3}-\\d{3}-\\d{4}$";
        boolean validPhone = true;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        validPhone = matcher.matches();
        if(!validPhone) {
            throw new InvalidPhone(phone);
        }
    }


    public void existingAppointmentException() throws ExistingAppointments {
        Boolean exists = false;
        Counselor deleteCounselor = (Counselor) counselorTable.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> appointments = DBAppointment.getAppointments();
        StringBuilder m = new StringBuilder(deleteCounselor.getCounselorName() + ": \n");
        for(Appointment a: appointments) {
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




