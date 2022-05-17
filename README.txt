TITLE AND PURPOSE OF APPLICATION

Application: GTQAM2.115.1
Purpose: GUI Desktop Application for maintaining customer information and scheduling appointments


AUTHOR, CONTACT INFORMATION, STUDENT APPLICATION VERSION, DATE, CLASS

Developed by:
Name: Gregory Brady
Email: gbrady5@wgu.edu
Phone: 816-304-8697

Date: 3/9/2022

Application Version: 115.1
Developed for: C195 - Software II - Advanced Java Concepts
Assessment: QAM2 - QAM2 Task 1: Java Application Development


DRIVER/VERSION INFORMATION

IDE: IntelliJ IDEA 2021.3 (Community Edition)
JDK: Java SE 17.0.1
JavaFX version: JavaFX-SDK-17.0.1
MySQL Connector Driver: mysql-connector-java-8.0.22

APPLICATION DIRECTIONS

1.Open the application
2.Login with a username and password
	- username/password are test/test or admin/admin
	- all login attempts are saved to the login_activity.txt file
	- Alert will display on successful login of upcoming appointments
3.Once logged in, taken to the main menu. The user has the following options:
	A: Manage Customers
		- View/Add/Edit/Delete customers
			-select add to enable form fields to add a customer
			-make a selection and select delete or edit to start an edit or delete
			-save will save the edit or added customer to the database
			-cancel clears the fields and returns the form to the original state
			-back returns user to the main menu
	B:Manage Schedule
		- View/Add/Edit/Delete appointments
			-select add to enable form fields to add an appointment
			-make a selection and select delete or edit to start an edit or delete
			-save will save the edit or added appointment to the database
			-cancel clears the fields and returns the form to the original state
			-back returns user to the main menu
			-Radio buttons filter the appointments to show either all appointments,
			appointments this month, or appointments this week.
			-All times reflect the user's system time but are stored in UTC time
	C.Reporting
		-Displays the three requested reports
			I. Appointments by Type/Month
			II. Schedule for each contact
				-requires the user to select which contact's schedule to view
			III. Customers by Country (*Additional Report)
		-back returns user to the main menu
	D.Logout
		-Logs the user out and returns the user to the login screen
	E.Exit
		-Closes the application


ADDITIONAL INFORMATION

*Additional Report: Customers by Country
Report Description: Displays the total number of customers by country

***Lambda Expression Usage***
Both found in appointmentController class. Filter and streams appointments
by week (#1) or month (#2). Also notated in the JavaDoc.