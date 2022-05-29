package Controller;

import DAO.DBAppointment;
import DAO.DBUser;
import DAO.DBnAppointment;
import Model.*;
import Utility.LoginTracking;
import Utility.TimeComparison;
import Utility.TimeManipulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import DAO.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * loginController controls the login screen for the application. Updates
 * the zoneID and language (EN or FR) on this page based on the language and
 * time zone setting of the user. Logs user activity after each login attempt
 */
public class loginController implements Initializable {
    public AnchorPane anchor;
    public Label headerLabel;
    public Label nameLabel;
    public Label passwordLabel;
    public TextField userText;
    public TextField passwordText;
    public Button loginButton;
    public Button exitButton;
    public Label zoneLabel;
    public String error;
    public String error2;
    public String errorTitle;
    public String errorConfirm;

    /**
     * Translates page based on system settings
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /*
        ObservableList<nAppointment> test = FXCollections.observableArrayList();
        nAppointment office;
        nAppointment virtual;
        nAppointment phone;
        int i = 1;
        String s = "Test";

        office = new OfficeAppointment(i, i,s,i,s,AppointmentType.Office,s,s,s,s,s);
        virtual = new VirtualAppointment(i,i,s,i,s,AppointmentType.Virtual,s,s,s,s,s);
        phone = new PhoneAppointment(i,i,s,i,s,AppointmentType.Phone,s,s,s,s,s);


        test.add(office);
        test.add(virtual);
        test.add(phone);
        System.out.println("test size: " + test.size());
        for(nAppointment n: test) {
            System.out.println(n.getType());
        }

        String test = "Office";
        AppointmentType type = AppointmentType.valueOf(test);
        System.out.println(type.toString());*/


        try {
            ResourceBundle rb = ResourceBundle.getBundle("Main/Nat", Locale.getDefault());

            //issue with the exception seems to be the rb being null
            zoneLabel.setText(TimeManipulation.getSystemZone().toString());
            //zoneLabel.setText(rb.getString(ZoneId.systemDefault().toString()));
            if(Locale.getDefault().getLanguage().equals("en"))   {
                error = rb.getString("error");
                error2 = rb.getString("error2");
                errorTitle= rb.getString("errortitle");
                errorConfirm=rb.getString("ok");
            }
            else if (Locale.getDefault().getLanguage().equals("fr")) {
                headerLabel.setText(rb.getString("header"));
                headerLabel.setLayoutX(36.0);
                nameLabel.setText(rb.getString("username"));
                nameLabel.setPrefWidth(125.0);
                nameLabel.setLayoutX(15.0);
                userText.setPromptText(rb.getString("username1"));
                passwordLabel.setText(rb.getString("password"));
                passwordText.setPromptText(rb.getString("password1"));
                loginButton.setText(rb.getString("login"));
                loginButton.setPrefWidth(75.0);
                loginButton.setLayoutX(164.0);
                exitButton.setText(rb.getString("exit"));
                error = rb.getString("error");
                error2 = rb.getString("error2");
                errorTitle= rb.getString("errortitle");
                errorConfirm=rb.getString("ok");
            }
            else {
                System.out.println("Language not supported");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to match username and password with users database values to authenticate
     * user. If successful, transitions to main menu
     * @param actionEvent When the login button is pressed
     * @throws IOException
     */
    public void attemptLogin(ActionEvent actionEvent) throws IOException {
        try {
            DBConnection.startConnection();
            ObservableList<User> users = DBUser.getUsers();
            emptyFieldsException();
            User loginUser = new User(userText.getText(), passwordText.getText());
            authorizeCheck(loginUser, users);

            Parent root = FXMLLoader.load(getClass().getResource("/View/mainView.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 310, 395);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch(EmptyFields e) {
                ButtonType okButton = new ButtonType(errorConfirm, ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage(),okButton);
                alert.setTitle(errorTitle);
                alert.setHeaderText(errorTitle);
                alert.showAndWait();
        }
        catch(NotAuthorized e) {
                ButtonType okButton = new ButtonType(errorConfirm, ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage(),okButton);
                alert.setTitle(errorTitle);
                alert.setHeaderText(errorTitle);
                alert.showAndWait();
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes application
     * @param actionEvent When the exit button is pressed
     */
    public void exitApplication(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Exception thrown when there are empty form fields and the save button is pressed
     */
    public class EmptyFields extends Exception {
        public EmptyFields() {
            super(error2);
        }
    }

    /**
     * Checks for empty form fields
     * @throws EmptyFields When any empty field is found
     */
    public void emptyFieldsException() throws EmptyFields {
        Boolean nullFields = false;
        if(userText.getText().isEmpty()) {
            nullFields = true;
        }
        if(passwordText.getText().isEmpty()) {
            nullFields = true;
        }
        if(nullFields) {
            throw new EmptyFields();
        }
    }

    /**
     * Exception thrown when the user enters an invalid username/password combination
     */
    public class NotAuthorized extends Exception{
        public NotAuthorized(){super(error);}
    }

    /**
     * Checks the user against an arraylist of users(database) and attempts to
     * match username and password
     * @param user User provided values
     * @param users Database values
     * @throws NotAuthorized When user is not authorized
     * @throws IOException
     */
    public void authorizeCheck(User user, ObservableList<User> users) throws NotAuthorized, IOException {
        Boolean authorized = false;

            LoginTracking tracker = new LoginTracking("login_activity.txt");
            tracker.logger.setLevel(Level.INFO);
            StringBuilder message = new StringBuilder("[" + TimeManipulation.utcTimestamp() + " " + TimeManipulation.getUtcZone() + "] | User Name: " + user.getUserName() + " | Login Result:" );

        for (User u : users) {
            if(u.equals(user)){
                AuthorizedUser.setAuthorizedID(u.getUserID());
                AuthorizedUser.setAuthorizedName(u.getUserName());
                authorized = true;
                message.append(" SUCCESS");
                tracker.logger.info(message.toString());
                //appointmentAlert();
                break;
            }
        }
        if(!authorized) {
            message.append(" FAIL");
            tracker.logger.info(message.toString());
            throw new NotAuthorized();
        }
    }

    /**
     * Checks the database for any appointment within the next 15 minutes of the user
     * logging in. If there is an upcoming appointment, the information is provided
     * in an alert. If there are no upcoming appointments, an alert will pop up stating
     * no upcoming appointments are scheduled
     */
    public void appointmentAlert() {
        StringBuilder message = new StringBuilder();
        boolean upcomingAppointment = false;
        LocalDateTime now = LocalDateTime.now(TimeManipulation.getSystemZone());
        for(nAppointment a : DBnAppointment.getAppointments()) {
            TimeComparison tc = new TimeComparison(LocalDateTime.parse(a.getStartTime(), TimeManipulation.formatter));

            if(tc.getStagedStart().isAfter(now) && tc.getStagedStart().isBefore(tc.getCompareStart()))
            {
                upcomingAppointment = true;
                message.append(a.getAppointmentID() + " | " + a.getClientID() + "\n" +
                        a.getStartTime() + " | " + a.getEndTime() + "\n");
            }
        }
        if(upcomingAppointment) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointments:");
            alert.setContentText("Upcoming appointments:\n" + message.toString());
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointments:");
            alert.setContentText("No upcoming appointments.");
            alert.showAndWait();
        }
    }
}