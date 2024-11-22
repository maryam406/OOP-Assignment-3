package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;

public class HelloApplication extends Application {

    // Array to store the Data
   private final  ArrayList<UserData> userlist=new ArrayList<UserData>();

    @Override
    public void start(Stage stage) throws IOException {

        // label for banner
        Label banner = new Label("Application Form");
        banner.setFont( Font.font("Arial", FontPosture.ITALIC ,35));// font size and style
        banner.setStyle("-fx-background-color:#4682B4; -fx-text-fill: white");// setting color of text and background
        banner.setPadding(new Insets(10)); // setting padding
        banner.setMaxWidth(Double.MAX_VALUE);//banner of maximum width
        banner.setPrefHeight(100);
        banner.setAlignment(Pos.CENTER);// moving the text to center

        Label userlabel= new Label ("Name");// label for name

        Label fatherlabel = new Label("Father Name"); // label for fathername

        Label cniclabel=new Label("CNIC");// label for Cnic


        Label date = new Label("Date"); // label for Date


        DatePicker datepicker=new DatePicker(); // for showing of calender

        Label gender =new Label("Gender"); // label for gender


        Label City =new Label("City");// label for City

        // combobox for city
        ComboBox<String> comboBox=new ComboBox<>();
        comboBox.getItems().addAll("New York","Lahore","Karachi","Sindh","Islamabad"); // options added in combobox
        comboBox.setPromptText("Select City");

        //Actions on pressing combobox
        comboBox.setOnAction(actionEvent -> {
            String selectedcity= comboBox.getValue();
            System.out.println(selectedcity);
        });
        Label image=new Label("Image");
        Label Ione=new Label("Image Selected");
        Button selectimage=new Button("Upload Image");
        ImageView imageView=new ImageView();
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
      //  imageView.setPreserveRatio(true);

        final File[] selectedimage= {null};

        // action while pressing the selectimage

        selectimage.setOnAction(e ->{
            FileChooser filechose=new FileChooser();
            filechose.setTitle("Upload Image"); // file chooser title
            filechose.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg") // make sure u can choose these kind of images
            );
            File file =filechose.showOpenDialog(stage);
            if(file!=null){
                selectedimage[0]=file; // adding the image to array
                Image image1= new Image(file.toURI().toString());
                imageView.setImage(image1); // display of image
            }
        } );


        //hbox for adjusting of the image position
        VBox imageContainer = new VBox(10, Ione,imageView);


         // text field to enter the data
        TextField usernametext= new TextField();
        TextField fathertext=new TextField();
        TextField cnictext =new TextField();

        // buttons for gender
        RadioButton maleradio=new RadioButton("Male");
        RadioButton femaleradio=new RadioButton("Female");

        ToggleGroup toggleGroup=new ToggleGroup();
        maleradio.setToggleGroup(toggleGroup);
        femaleradio.setToggleGroup(toggleGroup);

        // hbox for adgusting the position of button
        HBox hBox=new HBox();
        hBox.getChildren().addAll(maleradio,femaleradio);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0));



       // Buttons for save and exit
        Button saveButton = new Button("Save");
        Button exit=new Button("Exit");

        // Hbox for both save and exit button
        HBox boxes=new HBox();
        boxes.getChildren().addAll(saveButton,exit);
        boxes.setSpacing(10);


        GridPane gridPane = new GridPane();

        gridPane.setStyle("-fx-background-color: lightblue;");
        // Addjusting the height or width
        gridPane.setPadding(new Insets(0,20,15,20));
        gridPane.setHgap(20);
        gridPane.setVgap(30);


        // Adding different labels and buttons to the gridpane
        gridPane.add(userlabel,0,1);
        gridPane.add(usernametext,1,1);
        gridPane.add(fatherlabel,0,2);
        gridPane.add(fathertext,1,2);
        gridPane.add(cniclabel,0,3);
        gridPane.add(cnictext,1,3);
        gridPane.add(date, 0,4);
        gridPane.add(datepicker,1,4);
        gridPane.add(gender,0,5);
        gridPane.add(hBox,1,5);
        gridPane.add(boxes,0,8);
        gridPane.add(City,0,6);
        gridPane.add(comboBox,1,6);
        gridPane.add(image,0,7);
        gridPane.add(selectimage,1,7);
        gridPane.add(imageContainer,10, 1);


        // Action performed on pressing Exit
        exit.setOnAction(actionEvent -> {
            stage.hide();// exits the program
        });

        // Action performed on clicking save
        saveButton.setOnAction(a ->{

            // Storing the Data in variables
            String name=usernametext.getText();
            String FatherName=fathertext.getText();
            String Cnic= cnictext.getText();
            String Date=(datepicker.getValue() != null) ? datepicker.getValue().toString() : "";
            String Gender= maleradio.isSelected() ? "Male" : femaleradio.isSelected() ? "Female" : "";
            String city=comboBox.getValue();
            // calling the constructor of UserData class
            UserData userData=new UserData(name,FatherName,Cnic,Date,Gender,city);
            userlist.add(userData); // adding the Data in the array
            System.out.println(userData);// printing the data for debugging

               // checking if any field is missing
            if (name.isEmpty()|| FatherName.isEmpty()|| Cnic.isEmpty()||Date.isEmpty()||Gender.isEmpty()||city.isEmpty()){
                // if the  if-statement is true
                 Alert alerts= new Alert(Alert.AlertType.ERROR);
                alerts.setTitle("Error");
                alerts.setHeaderText("Missing Fields");
                alerts.setContentText("Please Fill all the Field");
                alerts.showAndWait();
            }
            else {
                // Display a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Data Saved Successfully");
                alert.setContentText("User " + userData.getName() + " has been added.");
                alert.showAndWait();


                // Data Clearing after Saving

                usernametext.clear();
                fathertext.clear();
                cnictext.clear();
                datepicker.setValue(null);
                toggleGroup.selectToggle(null); // Deselect radio buttons
                comboBox.setValue(null); // Clear combo box selection
                imageView.setImage(null); // Remove the image
            }



                }
        );

        // for border
        BorderPane borderone= new BorderPane();
        borderone.setTop(banner);
        borderone.setCenter(gridPane); // calling of upper gridpane statements

       // Setting of scene
        Scene scene = new Scene(borderone, 500, 200);
        stage.setTitle("Application Form");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
    // class to store the data
    static class UserData{
        private final String name;
        private final String Fathername;
        private final String cnic;
        private final String Date;
        private final String Gender;
        private final String city;

        // Constructor
        public UserData(String name, String Fathername ,String cnic,String Date,String Gender, String city){
            this.name = name;
            this.Fathername = Fathername;
            this.cnic = cnic;
            this.Date=Date;
            this.Gender=Gender;
            this.city=city;


        }

        public String getName() {
            return name;
        }


        // for printing of Data
        @Override
        public String toString() {
            return "Name: " + name + "\nFather's Name: " + Fathername+
                    "\n CNIC: " + cnic +"\nDate:"+ Date+
                    " \nGender"+Gender+"\nCity:"+city ;

        };
        }

    }

