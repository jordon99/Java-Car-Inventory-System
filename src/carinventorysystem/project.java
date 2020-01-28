package carinventorysystem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileNotFoundException;
import java.util.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class project extends Application {

    String user = ""; // no username for testing
    String pw = ""; // no password for testing
    String checkUser, checkPw;
    boolean isLogin = false;
    private CreateInventory inventory = new CreateInventory();
    private ArrayList<Car> sorted = new ArrayList<Car>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Car Rental System");

        // Create the Login form grid pane
        GridPane gridPane = createLogin();
        // Add UI controls to the Login form grid pane
        addUIControls(gridPane);

        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane createLogin() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        /* Add Column Constraints (Defines optional layout constraints for a column in a GridPane. If a ColumnConstraints object is added for a column in a gridpane, 
        the gridpane will use those constraint values when computing the column's width and layout.)*/
        // columnOne will be applied to all the nodes placed in column one.
        ColumnConstraints columnOne = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOne.setHalignment(HPos.RIGHT);

        // columnTwo will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwo = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwo.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOne, columnTwo);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Car Rental Service");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        // Add Name Label
        Label nameLabel = new Label("Employee Login : ");
        gridPane.add(nameLabel, 0, 1);

        // Add Name Text Field
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1, 1);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        gridPane.add(passwordLabel, 0, 3);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 3);

        // Add Submit Button
        Button submitButton = new Button("Login");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 4, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkPw = passwordField.getText();
                checkUser = nameField.getText();
                if (!user.equals(checkUser)) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Login Error!", "Please enter the Correct Login");
                    return;
                }

                if (!pw.equals(checkPw)) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Login Error!", "Please enter the Correct password");
                    return;
                } else {
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Login Successful!", "Welcome " + nameField.getText());
                    isLogin = true;
                    clearLogin(gridPane);
                }
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    private void clearLogin(GridPane gridPane) {
        if (isLogin == true) {
            gridPane.getChildren().clear();
            showMain(gridPane);
        }
    }

    private void clearScreen(GridPane gridPane) {
        gridPane.getChildren().clear();
    }

    private void showMain(GridPane gridPane) {

        // Creating the header text
        Text heading = new Text(260, 40, "Car Rental Inventory System");

        heading.setFill(Color.RED);
        heading.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.REGULAR, 24));

        // creating scroll bar for the list
        ScrollPane scrlPane = new ScrollPane();
        scrlPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrlPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        scrlPane.setMaxSize(250, 300);
        scrlPane.setMinSize(250, 300);
        scrlPane.setPrefViewportWidth(300);
        scrlPane.setPrefViewportHeight(300);

        // Creating the text location that will show the car's infomation (Make,Model,year, price per day)
        TextArea carDesc = new TextArea();
        carDesc.setMinSize(250, 150);
        carDesc.setMaxSize(250, 150);
        carDesc.setEditable(false);
        

        // List to show selected car's information
        ListView<Car> carList = new ListView<>();

        // creating drop down menu that allows to sort by car body type
        ComboBox carTypeCB = new ComboBox();
        carTypeCB.getItems().addAll("All", "Sedan", "Coupe", "Truck", "SUV", "Mini Van", "Convertible");
        carTypeCB.setValue("Select Filter to View Cars");
        carTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldText, String newText) {
                ArrayList<Car> sorted = new ArrayList<Car>();
                if (newText.equals("Sedan")) {
                    sorted = inventory.sortByType("Sedan");
                } else if (newText.equals("Coupe")) {
                    sorted = inventory.sortByType("Coupe");
                } else if (newText.equals("Truck")) {
                    sorted = inventory.sortByType("Truck");
                } else if (newText.equals("SUV")) {
                    sorted = inventory.sortByType("SUV");
                } else if (newText.equals("Mini Van")) {
                    sorted = inventory.sortByType("Mini Van");
                } else if (newText.equals("Convertible")) {
                    sorted = inventory.sortByType("Convertible");
                } else {
                    sorted = inventory.getInventory();
                }

                // this list shows all of the cars info add the cars from input file to this array
                ObservableList<Car> cars = FXCollections.observableArrayList();
                for (Car car : sorted) {
                    cars.add(car);
                }
                carList.setItems(cars);
                carList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                carList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Car>() {
                    @Override
                    public void changed(ObservableValue ov, Car oldCar, Car newCar) {
                        carDesc.setText(newCar.getInfo()); // put all of the cars infomation into a array of strings and insert varaible here
                    }
                });
                carList.setMaxSize(300, 300);
                scrlPane.setContent(carList);
            }
        });

        // Creating pane for buttons
        TilePane paneForBtn = new TilePane();
        paneForBtn.setHgap(5);
        paneForBtn.setVgap(10);

        // Creating pane for drop down menus
        TilePane paneForDrop = new TilePane();
        paneForDrop.setHgap(10);
        paneForDrop.setVgap(10);
        // Creating main buttons
        Button addBtn = new Button("Add");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearScreen(gridPane);
                showAdd(gridPane);
            }
        });

        Button searchBtn = new Button("Find");
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearScreen(gridPane);
                showSearch(gridPane);
                sorted = inventory.getInventory();
            }
        });

        Button rentBtn = new Button("Rent");
        rentBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearScreen(gridPane);
                showRent(gridPane);
            }
        });
        
        Button showClient = new Button("Clients");
        showClient.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e) {
               String customerText = "";
               if (customers.isEmpty()){
                   customerText = "No Clients";
               }
               for (Customer c: customers) {
                   customerText += c.getName()+"\n";
               }
               carDesc.setText(customerText);
           }
        });

        // adding buttons to button pane
        paneForBtn.getChildren().add(addBtn);
        paneForBtn.getChildren().add(searchBtn);
        paneForBtn.getChildren().add(rentBtn);
        paneForBtn.getChildren().add(showClient);

        //adding drop down menus to menu pane
        paneForDrop.getChildren().add(carTypeCB);

        //adding everything to main pane
        gridPane.add(scrlPane, 1, 1);
        gridPane.add(paneForBtn, 3, 2);
        gridPane.add(paneForDrop, 1, 0);
        gridPane.add(carDesc, 3, 1);
        GridPane.setHalignment(heading, HPos.CENTER);
        //GridPane.setMargin(heading, new Insets(20, 0, 20, 0));
        gridPane.add(heading, 3, 0, 1, 1);
        gridPane.setAlignment(Pos.TOP_CENTER);

    }

    public void showSearch(GridPane gridPane) {
        Text sHeading = new Text(260, 40, "Search Inventory");
        sHeading.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.REGULAR, 24));

        TextArea searchBar = new TextArea();
        searchBar.setEditable(true);
        searchBar.setPromptText("Search");
        searchBar.setMinSize(150, 40);
        searchBar.setMaxSize(150, 40);

        RadioButton rbMake = new RadioButton("Search By Make");
        RadioButton rbModel = new RadioButton("Search By Model");
        RadioButton rbYear = new RadioButton("Search By Year");

        ToggleGroup searchGroup = new ToggleGroup();
        rbMake.setToggleGroup(searchGroup);
        rbModel.setToggleGroup(searchGroup);
        rbYear.setToggleGroup(searchGroup);

        VBox paneForRadioButtons = new VBox(20);
        paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));
        paneForRadioButtons.getChildren().addAll(rbMake, rbModel, rbYear);

        Label resultsLabel = new Label("Search Results");

        ScrollPane scrlPane = new ScrollPane();
        scrlPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrlPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        scrlPane.setMaxSize(250, 300);
        scrlPane.setMinSize(250, 300);
        scrlPane.setPrefViewportWidth(300);
        scrlPane.setPrefViewportHeight(300);

        TilePane paneForButtons = new TilePane();
        paneForButtons.setHgap(10);

        // List to show selected car's information
        ListView<Car> carList = new ListView<>();

        Button sSubmitBtn = new Button("Submit");
        sSubmitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (rbMake.isSelected()) {
                    sorted = inventory.searchByMake(searchBar.getText());
                } else if (rbModel.isSelected()) {
                    sorted = inventory.searchByModel(searchBar.getText());
                } else if (rbYear.isSelected()) {
                    sorted = inventory.searchByYear(searchBar.getText());
                }

                // this list shCarows all of the cars info add the cars from input file to this array
                ObservableList<Car> cars = FXCollections.observableArrayList();
                for (Car car : sorted) {
                    cars.add(car);
                }
                carList.setItems(cars);
                carList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                carList.setMaxSize(300, 300);
                scrlPane.setContent(carList);
            }
        });

        Button sRentBtn = new Button("Rent");
        sRentBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e) {
               clearScreen(gridPane);
               showRent(gridPane);
           }
        });

        Button sDelBtn = new Button("Delete");
        sDelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Car delCar = carList.getSelectionModel().getSelectedItem();
                inventory.getInventory().remove(delCar);
            }
        });

        Button sHomeBtn = new Button("Go Back");
        sHomeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearScreen(gridPane);
                showMain(gridPane);
            }
        });

        paneForButtons.getChildren().addAll(sRentBtn, sDelBtn, sHomeBtn);

        gridPane.add(paneForRadioButtons, 1, 1);
        gridPane.add(sHeading, 3, 0);
        gridPane.add(resultsLabel, 2, 1);
        gridPane.add(scrlPane, 3, 1, 2, 2);
        gridPane.add(searchBar, 1, 2);
        gridPane.add(sSubmitBtn, 1, 3);
        gridPane.add(paneForButtons, 3, 3);

    }
    
    public void showCustomers(GridPane gridPane) {
        Label headerLabel = new Label("Customer List");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
        
        
    }
    
    public void showRent(GridPane gridPane) {
        Label headerLabel = new Label("Customer Details");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        Label nameLabel = new Label("Name:");
        gridPane.add(nameLabel, 0, 1);
        TextField nameText = new TextField();
        nameText.setPromptText("Enter Name");
        nameText.setPrefHeight(40);
        gridPane.add(nameText, 1, 1);

        Label addressLabel = new Label("Address:");
        gridPane.add(addressLabel, 0, 2);
        TextField addressText = new TextField();
        addressText.setPromptText("Enter Address");
        addressText.setPrefHeight(40);
        gridPane.add(addressText, 1, 2);
        
        Label lNumLabel = new Label("License Number:");
        gridPane.add(lNumLabel, 0, 3);
        TextField lNumText = new TextField();
        lNumText.setPromptText("Enter License Number");
        lNumText.setPrefHeight(40);
        gridPane.add(lNumText, 1, 3);

        Label ageLabel = new Label("Age:");
        gridPane.add(ageLabel, 0, 4);
        TextField ageText = new TextField();
        ageText.setPromptText("Enter Age");
        ageText.setPrefHeight(40);
        gridPane.add(ageText, 1, 4);
        
        Button submitCustomer = new Button("Submit");
        submitCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int age = Integer.parseInt(ageText.getText());
                Customer newCustomer = new Customer(nameText.getText(), age, addressText.getText(), lNumText.getText());
                customers.add(newCustomer);
                clearScreen(gridPane);
                showMain(gridPane);
            }
        });
        gridPane.add(submitCustomer, 1, 6);

        Button cancelCar = new Button("Cancel");
        cancelCar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearScreen(gridPane);
                showMain(gridPane);
            }
        });
        gridPane.add(cancelCar, 1, 7);
        
    }

    public void showAdd(GridPane gridPane) {

        Label headerLabel = new Label("Add a car model");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        Label yearLabel = new Label("Year:");
        gridPane.add(yearLabel, 0, 1);
        TextField yearText = new TextField();
        yearText.setPromptText("Enter a Year");
        yearText.setPrefHeight(40);
        gridPane.add(yearText, 1, 1);

        Label makeLabel = new Label("Make:");
        gridPane.add(makeLabel, 0, 2);
        TextField makeText = new TextField();
        makeText.setPromptText("Enter Make");
        makeText.setPrefHeight(40);
        gridPane.add(makeText, 1, 2);

        Label modelLabel = new Label("Model:");
        gridPane.add(modelLabel, 0, 3);
        TextField modelText = new TextField();
        modelText.setPromptText("Enter model");
        modelText.setPrefHeight(40);
        gridPane.add(modelText, 1, 3);

        Label typeLabel = new Label("Type:");
        gridPane.add(typeLabel, 0, 4);
        TextField typeText = new TextField();
        typeText.setPromptText("Enter body type");
        typeText.setPrefHeight(40);
        gridPane.add(typeText, 1, 4);

        Label costLabel = new Label("Cost:");
        gridPane.add(costLabel, 0, 5);
        TextField costText = new TextField();
        costText.setPromptText("Enter daily cost");
        costText.setPrefHeight(40);
        gridPane.add(costText, 1, 5);

        Button submitCar = new Button("Submit");
        submitCar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Double cost = Double.parseDouble(costText.getText());
                Car newCar = new Car(yearText.getText(), makeText.getText(), modelText.getText(), typeText.getText(), cost);
                inventory.addCar(newCar);
                clearScreen(gridPane);
                showMain(gridPane);
            }
        });
        gridPane.add(submitCar, 1, 6);
        
        Button cancelCar = new Button("Cancel");
        cancelCar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearScreen(gridPane);
                showMain(gridPane);
            }
        });
        gridPane.add(cancelCar, 1, 7);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
