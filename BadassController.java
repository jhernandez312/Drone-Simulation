import java.io.File;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.List;

public class BadassController implements Initializable{

    @FXML
    private GridPane mappy;

    Media spooky;
    MediaPlayer player;

    Media haha;
    MediaPlayer player2;

    Media haha2;
    MediaPlayer player22;

    

    public void goBackButton(VBox currentVbox) {
        Button button = new Button("Go Back");
        currentVbox.getChildren().add(button);

        EventHandler<ActionEvent> goBackBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Main.getStage().setScene(Main.getScene());
            }
        };

        // when enter is pressed
        button.setOnAction(goBackBox);
    }

    @FXML
    private ImageView slowFly;


    @FXML
    private ImageView yelldrone;

        // translate
        @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(yelldrone);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        //translate.setByX(300);
        translate.setByY(350);
        translate.setAutoReverse(true);
        translate.play();

        // rotate
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(yelldrone);
        rotate.setDuration(Duration.millis(600));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.play();

        //second drone
        TranslateTransition slowMoving = new TranslateTransition();
        slowMoving.setNode(slowFly);
        slowMoving.setDuration(Duration.millis(900));
        slowMoving.setCycleCount(TranslateTransition.INDEFINITE);
        slowMoving.setByX(800);
        slowMoving.setByY(10);
        slowMoving.setAutoReverse(true);
        slowMoving.play();

        haha = new Media(new File("happy.mp3").toURI().toString());
        player2 = new MediaPlayer(haha);
        player2.setCycleCount(MediaPlayer.INDEFINITE);
        player2.play();

    }
    public void setCurrentScene(VBox vbox) {
        Scene scene = new Scene(vbox, 600,400);
        Main.getStage().setScene(scene);
        Main.getStage().show();
    }
        //these two can be combined using generics somehow. i forgot how
    public void setCurrentScene(HBox hbox) {
        Scene scene = new Scene(hbox, 1000,400);
        Main.getStage().setScene(scene);
        Main.getStage().show();
    }

    public VBox newVbox() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 50, 50, 50));
        vBox.setSpacing(10);
        return vBox;
    }

    public HBox newHbox() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 50 , 50, 10));
        hBox.setSpacing(10);
        return hBox;
    }


    public void newPromt(VBox vBox, String wantedCreation) {
        Text promt = new Text();
        String text = String.format("%s", wantedCreation);
        promt.setText(text);
        vBox.getChildren().add(promt);
    }

    public TextField newTextField(VBox vBox, String input) {
        TextField newField = new TextField(input);
        vBox.getChildren().add(newField);
        return newField;
    }

    public ComboBox<String> locationComboBox(VBox vBox) {
        ComboBox<String> location = new ComboBox<>();
        List<String> locationNames = Location.getLocationNames();
        location.getItems().addAll(locationNames);
        location.setValue("Location");
        vBox.getChildren().add(location);
        return location;
    }

    public ComboBox<String> serviceComboBox(VBox vBox) {
        ComboBox<String> service = new ComboBox<>();
        List<String> serviceNames = DeliveryService.getServiceNames();
        service.getItems().addAll(serviceNames);
        service.setValue("Delivery Service");
        vBox.getChildren().add(service);
        return service;
    }

    public ComboBox<String> personComboBox(VBox vBox) {
        ComboBox<String> person = new ComboBox<>();
        List<String> personNames = Person.getPersonNames();
        person.getItems().addAll(personNames);
        person.setValue("Person");
        vBox.getChildren().add(person);
        return person;
    }

    public ComboBox<Integer> droneComboBox(VBox vBox) {
        ComboBox<Integer> drone = new ComboBox<>();
        List<Integer> droneIDs = Drone.getDroneNames();
        drone.getItems().addAll(droneIDs);
        Text promt = new Text();
        String text = String.format("DroneIDs:");
        promt.setText(text);
        vBox.getChildren().add(promt);
        vBox.getChildren().add(drone);
        return drone;
    }

    private ComboBox<String> ingredientComboBox(VBox vBox) {
        ComboBox<String> barcode = new ComboBox<>();
        barcode.getItems().addAll(Ingredient.getIngredientBarcodes());
        vBox.getChildren().add(barcode);
        barcode.setValue("Barcode");
        return barcode;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void ClickFlyDrone(ActionEvent e) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Fly Drone Pleaes Input the Following (Whee!!!)"); //creates a new promt

        ComboBox<String> serviceName = new ComboBox<>();
        serviceName.getItems().addAll(DeliveryService.getServiceNames());
        serviceName.setValue("Delivery Service");
        //setOnAction(update the IDs)

        vBox.getChildren().add(serviceName);

        //DroneID ComboBox
        ComboBox<String> droneID = new ComboBox<>();
        droneID.setValue("Drone Tag");
        vBox.getChildren().add(droneID);

        serviceName.setOnAction(updateTags -> {
            droneID.getItems().clear();
            droneID.setValue("Drone Tag");
            droneID.getItems().addAll(DeliveryService.getService(serviceName.getValue()).getDroneTagsAsStrings());
        });

        ComboBox<String> destination = locationComboBox(vBox);
        destination.setValue("Destination");

        Button submit = new Button("Submit");
        vBox.getChildren().add(submit);
        goBackButton(vBox);
        Text success = new Text();
        vBox.getChildren().add(success);
        submit.setOnAction(submitEvent -> {
            try {
                InterfaceLoop.flyDrone(serviceName.getValue(), Integer.parseInt(droneID.getValue()), destination.getValue());
                success.setText("Yay the drone flew!!");
                haha2 = new Media(new File("droneSound.mp3").toURI().toString());
                player22 = new MediaPlayer(haha2);
                player22.play();

            } catch (IllegalArgumentException poop) {
                success.setText(poop.getLocalizedMessage());
            }
        });

        setCurrentScene(vBox);
    }

    @FXML
    void ClickCheckDistance(ActionEvent event) {
        VBox vbox = newVbox();
        newPromt(vbox, "In Order to Check the Distance, Give Two Locations!");
        ComboBox<String> locationOne = locationComboBox(vbox);
        ComboBox<String> locationTwo = locationComboBox(vbox);
        locationOne.setValue("Location 1");
        locationTwo.setValue("Location 2");

        Button submit = new Button("Submit");
        vbox.getChildren().add(submit);
        goBackButton(vbox);
        Text result = new Text();
        vbox.getChildren().add(result);
        submit.setOnAction(e -> {
                try {
                    int dist = InterfaceLoop.checkDistance(locationOne.getValue(), locationTwo.getValue());
                    result.setText(("The total distance between the given locations is " + (Integer)dist).toString());
                } catch (Exception exception) {
                    result.setText("Stop fooling around!");
                }
        });



        setCurrentScene(vbox);
    }

    @FXML
    void ClickLoadIngredient(ActionEvent event) {
        VBox vbox = newVbox();

        newPromt(vbox, "In Order to Load Ingredients Please Input the Following!");

        ComboBox<String> serviceName = new ComboBox<>();
        serviceName.getItems().addAll(DeliveryService.getServiceNames());
        serviceName.setValue("Delivery Service");
        //setOnAction(update the IDs)

        vbox.getChildren().add(serviceName);


        //DroneID ComboBox
        ComboBox<String> droneID = new ComboBox<>();
        droneID.setValue("Drone Tag");
        vbox.getChildren().add(droneID);

        serviceName.setOnAction(updateTags -> {
            droneID.getItems().clear();
            droneID.setValue("Drone Tag");
            droneID.getItems().addAll(DeliveryService.getService(serviceName.getValue()).getHomeBaseDroneTagsAsStrings());
        });

        ComboBox<String> barcode = ingredientComboBox(vbox);

        TextField quantity = new TextField("Quantity");
        vbox.getChildren().add(quantity);

        TextField price = new TextField("Unit Price");
        vbox.getChildren().add(price);

        Button submit = new Button("Submit");
        vbox.getChildren().add(submit);

        goBackButton(vbox);

        Text success = new Text();

        vbox.getChildren().add(success);
        submit.setOnAction(submitEvent -> {
            try {
                InterfaceLoop.loadIngredient(serviceName.getValue(), Integer.parseInt(droneID.getValue()),
                 barcode.getValue(), Integer.parseInt(quantity.getText()), Integer.parseInt(price.getText()));
                 success.setText("Yippee!! ingredient loaded.");
            } catch (NumberFormatException badNum) {
                success.setText("Please make sure quantity and price are non-negative integers!");
            } catch (IllegalArgumentException otherStuff) {
                success.setText(otherStuff.getLocalizedMessage());
            }
        });

        setCurrentScene(vbox);
    }

    private ComboBox<String> restaurantComboBox(VBox vbox) {
        ComboBox<String> restaurantName = new ComboBox<>();
        restaurantName.setValue("Restaurant");
        restaurantName.getItems().addAll(Restaurant.getRestaurantNames());
        return restaurantName;
    }

    @FXML
    void ClickPurchaseIngredient(ActionEvent event) {
        VBox vbox = newVbox();
        newPromt(vbox, "In Order to Purchase an Ingredient Please Input the Following!");

        ComboBox<String> restaurantName = restaurantComboBox(vbox);

        ComboBox<String> serviceName = serviceComboBox(vbox);

        ComboBox<String> droneID = new ComboBox<>();
        droneID.setValue("Drone Tag");
        vbox.getChildren().add(droneID);

        serviceName.setOnAction(updateTags -> {
            droneID.getItems().clear();
            droneID.setValue("Drone Tag");
            droneID.getItems().addAll(DeliveryService.getService(serviceName.getValue()).getDroneTagsAsStrings());
        });

        ComboBox<String> barcode = new ComboBox<>();
        barcode.setValue("Ingredient");

        droneID.setOnAction(tagChange -> {
            barcode.getItems().clear();
            barcode.setValue("Ingredient");
            barcode.getItems().addAll(Drone.getDrone(serviceName.getValue(), Integer.parseInt(droneID.getValue())).getIngredientBarcodes()); //PLEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASE UNCOMMENT
        });

        vbox.getChildren().add(barcode);

        TextField quantity = new TextField("Quantity");

        vbox.getChildren().add(quantity);

        Button submit = new Button("Submit");
        vbox.getChildren().add(submit);
        goBackButton(vbox);

        Text success = new Text();
        vbox.getChildren().add(success);
        submit.setOnAction(submitEvent -> {
            try {
                InterfaceLoop.purchaseIngredient(restaurantName.getValue(), serviceName.getValue(),
                Integer.parseInt(droneID.getValue()), barcode.getValue(), Integer.parseInt(quantity.getText()));
                success.setText("Ingredient Purchased Successfully");
            } catch (NumberFormatException badNum) {
                success.setText("Please make sure quantity is positive integer!");
            } catch (IllegalArgumentException other) {
                success.setText(other.getLocalizedMessage() + " (Task failed successfully)");
            }
        });
        setCurrentScene(vbox);
    }

    @FXML
    void ClickLoadFuel(ActionEvent event) {

        VBox vbox = newVbox();
        newPromt(vbox, "In Order to Load Fuel Please Input the Following!");

        ComboBox<String> serviceName = serviceComboBox(vbox);

        ComboBox<String> droneID = new ComboBox<>();
        droneID.setValue("Drone Tag");
        vbox.getChildren().add(droneID);

        serviceName.setOnAction(updateTags -> {
            droneID.getItems().clear();
            droneID.setValue("Drone Tag");
            droneID.getItems().addAll(DeliveryService.getService(serviceName.getValue()).getDroneTagsAsStrings());
        });

        Text explanation = new Text("Please specify the amount of fuel below!");
        vbox.getChildren().add(explanation);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999);
        valueFactory.setValue(0);
        Spinner<Integer> amountFuel = new Spinner<>();
        amountFuel.setValueFactory(valueFactory);
        vbox.getChildren().add(amountFuel);

        Button submit = new Button("Submit");
        vbox.getChildren().add(submit);
        goBackButton(vbox);
        Text success = new Text();
        vbox.getChildren().add(success);

        submit.setOnAction(submitEvent -> {
            try {
                InterfaceLoop.loadFuel(serviceName.getValue(), Integer.parseInt(droneID.getValue()), amountFuel.getValue());
                success.setText("Fuel Loaded Successfully!");
            } catch (NumberFormatException badNum) {
                success.setText("How did this even happen!? The code is broken.");
            } catch (IllegalArgumentException other) {
                success.setText(other.getLocalizedMessage());
            }
        });

        setCurrentScene(vbox);
    }
    @FXML
    private GridPane mapGridPane;


    @FXML
    void ButtonClonk(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Create a new Location Please Input the Following"); //creates a new promt

        TextField locationName = newTextField(vBox, "Location");
        TextField xCoord = newTextField(vBox, "X-Coordinate");
        TextField yCoord = newTextField(vBox, "Y-Coordinate");
        TextField capacity = newTextField(vBox, "Capacity");

        

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.makeLocation(locationName.getText(), Integer.parseInt(xCoord.getText()),
                    Integer.parseInt(yCoord.getText()), Integer.parseInt(capacity.getText()));
                    String text = String.format("New Location Created with name %s, coordinates: %s,%s and capacity [%s/%s]",
                                                locationName.getText(),
                                                xCoord.getText(), yCoord.getText(), capacity.getText(), capacity.getText());
                    Label newLocName = new Label(locationName.getText().length() < 2 ? locationName.getText() : locationName.getText().substring(0, 1));
                    newLocName.setStyle("-fx-border-color:orange; -fx-background-color: yellow;");
                    newLocName.setTextFill(Color.web("#ed0909"));
                    newLocName.setFont(Font.font("Comic Sans MS", 12));
                    newLocName.setWrapText(true);
                    newLocName.setTextAlignment(TextAlignment.CENTER);
                    //Text newText = new Text("PENIS");
                    mappy.add(newLocName, Integer.parseInt(xCoord.getText()), Integer.parseInt(yCoord.getText()));
                    GridPane.setHalignment(newLocName, HPos.CENTER);
                    GridPane.setValignment(newLocName, VPos.CENTER);

                    Tooltip tippy = new Tooltip(Location.getLocation(locationName.getText()).toString());
                    tippy.setShowDelay(Duration.millis(50));
                    tippy.setShowDuration(Duration.millis(10000));
                    newLocName.setTooltip(tippy);

                    result.setText(text);
                    Media jack = new Media(new File("jacky.mp3").toURI().toString());
                    MediaPlayer jacker = new MediaPlayer(jack);
                    jacker.setStopTime(Duration.millis(4000));
                    jacker.play();
                }
                catch (NumberFormatException exception) {
                   result.setText("Please ensure xCoord, yCoord, and Capacity are all integers!");
                }
                catch (IllegalArgumentException exception) {
                    result.setText(exception.getLocalizedMessage());
                }
            }
        };

        button.setOnAction(submitBox); // when enter is pressed

        setCurrentScene(vBox); //Sets scene

    }

    @FXML
    void ClickMakeDeliveryService(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Create a new Delivery Service Please Input the Following"); //creates a new promt

        TextField serviceName = newTextField(vBox, "Service Name");

        TextField revenue = newTextField(vBox, "Revenue");

        ComboBox<String> locationAt = locationComboBox(vBox); //Makes a combobox for location
        //so you might be curious; what happens if we make the combobox contain Location objects instead of Strings?
        //Well, it DOES WORK with toString; however, we can't make the default input "Location" because the
        //value HAS to be a Location object. So we have the trouble of making hte new Location method. Oh well!
        //At least it works!!!!

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.makeDeliveryService(serviceName.getText(), Integer.parseInt(revenue.getText()), locationAt.getValue());
                    String text = String.format("New Service Created with name: %s, revenue: %s and location: %s.", serviceName.getText(), revenue.getText(),
                    locationAt.getValue());
                    result.setText(text);
                }
                catch (NumberFormatException exception) {
                    result.setText("Please ensure Revenue is an integer!");
                }
                catch (IllegalArgumentException exception) {
                     result.setText(exception.getLocalizedMessage());
                }
             }
        };

        // when enter is pressed
        button.setOnAction(submitBox);

        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void ClickMakeIngredient(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Create a new Ingredient Please Input the Following"); //creates a new promt

        TextField barcode = newTextField(vBox, "Barcode");
        TextField name = newTextField(vBox, "Name");
        TextField weight = newTextField(vBox, "Weight");

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.makeIngredient(barcode.getText(), name.getText(), Integer.parseInt(weight.getText()));
                    String text = String.format("New Ingredient Created with barcode: %s, name: %s, and weight: %s.", barcode.getText(), name.getText(),
                    weight.getText());
                    result.setText(text);
                } catch (NumberFormatException exception) {
                    result.setText("Please ensure weight is an integer!");;
                } catch (IllegalArgumentException exception) {
                    result.setText(exception.getLocalizedMessage());;
                }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);

        setCurrentScene(vBox); //Sets scene
    }


    @FXML
    void ClickMakePerson(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Create a new Person Please Input the Following"); //creates a new promt

        TextField username = newTextField(vBox, "User Name");
        TextField fname = newTextField(vBox, "First Name");
        TextField lname = newTextField(vBox, "Last Name");
        TextField birthYear = newTextField(vBox, "Birth Year");
        TextField birthMonth = newTextField(vBox, "Birth Month");
        TextField birthDate = newTextField(vBox, "Birth Date");
        TextField address = newTextField(vBox, "Address");

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.makePerson(username.getText(), fname.getText(), lname.getText(), Integer.parseInt(birthYear.getText()),
                    Integer.parseInt(birthMonth.getText()), Integer.parseInt(birthDate.getText()), address.getText());
                    String text = String.format("New Person Added with username: %s, name: %s %s, birthdate: %d.%d.%d and address: %s.", username.getText(), fname.getText(), lname.getText(), Integer.parseInt(birthMonth.getText()),
                    Integer.parseInt(birthDate.getText()), Integer.parseInt(birthYear.getText()), address.getText());
                    result.setText(text);
                    Media laugh = new Media(new File("laugh.mp3").toURI().toString());
                    MediaPlayer laugher = new MediaPlayer(laugh);
                    laugher.play();
                }
                catch (NumberFormatException exception) {
                    result.setText("Please ensure birth year, birth, and birth date are all integers!");
                }
                catch (IllegalArgumentException exception) {
                     result.setText(exception.getLocalizedMessage());
                }

            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);

        setCurrentScene(vBox); //Sets scene

    }

    @FXML
    void ClickMakeRestaurant(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Create a new Restaurant Please Input the Following"); //creates a new promt

        TextField name = newTextField(vBox, "Name");

        ComboBox<String> location = locationComboBox(vBox); //creates a combobox for location

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.makeRestaurant(name.getText(), location.getValue());
                    String text = String.format("New Restaurant Created with name: %s and location: %s.", name.getText(), location.getValue());
                    result.setText(text);
                    Media fry = new Media(new File("fry.wav").toURI().toString());
                    MediaPlayer fryer = new MediaPlayer(fry);
                    fryer.setStopTime(Duration.millis(3000));
                    fryer.play();
                }
                catch (IllegalArgumentException exception) {
                    result.setText(exception.getLocalizedMessage());
                }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);


        setCurrentScene(vBox); //Sets scene

    }

    @FXML
    void ClickMakeDrone(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Create a new Drone Please Input the Following"); //creates a new promt

        ComboBox<String> service = serviceComboBox(vBox);
        TextField tag = newTextField(vBox, "Drone Tag");
        TextField capacity = newTextField(vBox, "Capacity");
        TextField fuel = newTextField(vBox, "Fuel");

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.makeDrone(service.getValue(), Integer.parseInt(tag.getText()), Integer.parseInt(capacity.getText()), Integer.parseInt(fuel.getText()));
                    String text = String.format("New Drone Created with Service: %s, tag: %s, capacity: %s, and fuel: %s.", service.getValue(), tag.getText(), capacity.getText(), fuel.getText());
                    result.setText(text);
                }
                catch (NumberFormatException exception) {
                    result.setText("Please ensure tag, capacity, and fuel are all integers!");
                }
                catch (IllegalArgumentException exception) {
                    result.setText(exception.getLocalizedMessage());
                }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);

        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void clickDisplayButton() {
        HBox hBox = newHbox(); // Creates new Hbox. This is the outer pane
        VBox vBox = newVbox(); //Creates new Vbox
        hBox.getChildren().add(vBox);

        Text promt = new Text();
        promt.setText("Choose an object to display");
        vBox.getChildren().add(promt);

        Button buttonLoc = new Button("Location");
        vBox.getChildren().add(buttonLoc);

        Button buttonRest = new Button("Restaurants");
        vBox.getChildren().add(buttonRest);

        Button buttonDelServ = new Button("Delivery Service");
        vBox.getChildren().add(buttonDelServ);

        Button buttonPerson = new Button("Person");
        vBox.getChildren().add(buttonPerson);

        Button buttonIng = new Button("Ingredients");
        vBox.getChildren().add(buttonIng);

        Button buttonDrones = new Button("Drones");
        vBox.getChildren().add(buttonDrones);

        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        hBox.getChildren().add(result);

        EventHandler<ActionEvent> locBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                    String text = Location.getDisplayLocationsString();
                    result.setText(text);;
            }
        };
        EventHandler<ActionEvent> restBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String text = Restaurant.getDisplayRestaurantsAsString();
                result.setText(text);
            }
        };
        EventHandler<ActionEvent> delServBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String text = DeliveryService.getDisplayServicesString();
                result.setText(text);;
            }
        };
        EventHandler<ActionEvent> personBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String text = Person.getDisplayPersonsAsString();
                result.setText(text);;
            }
        };
        EventHandler<ActionEvent> ingBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                    String text = InterfaceLoop.displayIngredients();
                    result.setText(text);
            }
        };
        EventHandler<ActionEvent> dronesBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                    String text = Drone.displayDrones();
                    result.setText(text);
            }
        };

        buttonLoc.setOnAction(locBox);
        buttonRest.setOnAction(restBox);
        buttonDelServ.setOnAction(delServBox);
        buttonPerson.setOnAction(personBox);
        buttonIng.setOnAction(ingBox); // for display ing box
        buttonDrones.setOnAction(dronesBox);

        setCurrentScene(hBox); //Sets scene
    }

    @FXML
    void ClickHireWorker(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Hire a Worker Please Input the Following"); //creates a new promt

        ComboBox<String> service = serviceComboBox(vBox);
        ComboBox<String> person = new ComboBox<>();
        person.setValue("Person");
        vBox.getChildren().add(person);

        service.setOnAction(serviceEvent -> {
            person.getItems().clear();
            person.setValue("Person");
            person.getItems().addAll(Person.getPersonNamesNotEmployedAt(DeliveryService.getService(service.getValue())));
        });

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.hireWorker(service.getValue(), person.getValue());
                    String text = String.format("%s has been hired at %s.", person.getValue(), service.getValue());
                    result.setText(text);
                }
                catch (NumberFormatException exception) {
                    result.setText("Your entry is not meeting the requirements, please ensure you are selecting a valid person or service!");
                }
                catch (IllegalArgumentException exception) {
                     result.setText(exception.getLocalizedMessage());
                }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);

        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void ClickFireWorker(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Fire a Worker Please Input the Following");

        ComboBox<String> service = serviceComboBox(vBox);
        ComboBox<String> person = personComboBox(vBox);

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        service.setOnAction(updateTags -> {
            person.getItems().clear();
            person.setValue("People");
            person.getItems().addAll(DeliveryService.getService(service.getValue()).getEmployeeNames());
        });

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.fireWorker(service.getValue(), person.getValue());
                    String text = String.format("%s has been fired from %s.", person.getValue(), service.getValue());
                    result.setText(text);
                } catch (NumberFormatException exception) {
                    result.setText("Your entry is not meeting the requirements, please ensure you are selecting a valid person or service!");;
                }
                catch (IllegalArgumentException exception) {
                     result.setText(exception.getLocalizedMessage());
                }

            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);

        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void ClickAppointManager(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Appoint a Manager Please Input the Following");

        ComboBox<String> service = serviceComboBox(vBox);
        ComboBox<String> person = new ComboBox<>();
        person.setValue("Employee");
        vBox.getChildren().add(person);

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        service.setOnAction(updateTags -> {
            person.getItems().clear();
            person.setValue("Employee");
            person.getItems().addAll(DeliveryService.getService(service.getValue()).getEmployeeNames());
        });

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.appointManager(service.getValue(), person.getValue());
                    String text = String.format("%s Has been appointed manager at %s.", person.getValue(), service.getValue());
                    result.setText(text);
                }
                catch (NumberFormatException exception) {
                    result.setText("Your entry is not meeting the requirements, please ensure you are selecting a valid person or service!");
                }
                catch (IllegalArgumentException exception) {
                     result.setText(exception.getLocalizedMessage());
                }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);

        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void ClickAppointPilot(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Appoint a Pilot Please Input the Following");

        ComboBox<String> service = serviceComboBox(vBox);
        ComboBox<String> person = new ComboBox<>();
        person.setValue("Employee");
        vBox.getChildren().add(person);
        //ComboBox<Integer> drone = droneComboBox(vBox);
        ComboBox<String> drone = new ComboBox<>();
        drone.setValue("Drone Tag");
        vBox.getChildren().add(drone);

        service.setOnAction(updateTags -> {
            person.getItems().clear();
            person.setValue("Employee");
            person.getItems().addAll(DeliveryService.getService(service.getValue()).getLicensedEmployeeNames());
            drone.getItems().clear();
            drone.setValue("Drone Tag");
            drone.getItems().addAll(DeliveryService.getService(service.getValue()).getDroneTagsAsStrings());
        });





        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.appointPilot(service.getValue(), person.getValue(), Integer.parseInt(drone.getValue()));
                    String text = String.format("%s Has been appointed pilot at %s with drone %s.", person.getValue(), service.getValue(), drone.getValue());
                    result.setText(text);
                    }
                    catch (NumberFormatException exception) {
                        result.setText("Your entry is not meeting the requirements, please ensure you are selecting a valid person or service!");
                    }
                    catch (IllegalArgumentException exception) {
                         result.setText(exception.getLocalizedMessage());
                    }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);


        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void ClickTrainPilot(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Train a Pilot Please Input the Following");

        ComboBox<String> service = serviceComboBox(vBox);
        ComboBox<String> person = personComboBox(vBox);
        TextField license = newTextField(vBox, "License");
        TextField experience = newTextField(vBox, "Number of Successful Flights");

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        service.setOnAction(updateTags -> {
            person.getItems().clear();
            person.setValue("People");
            person.getItems().addAll(DeliveryService.getService(service.getValue()).getTrainableEmployeeNames());
        });

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.trainPilot(service.getValue(), person.getValue(), license.getText(), Integer.parseInt(experience.getText()));
                    String text = String.format("%s has been trained as a pilot by %s with license %s and experience %d.", person.getValue(), service.getValue(), license.getText(), Integer.parseInt(experience.getText()));
                    result.setText(text);
                    }
                    catch (NumberFormatException exception) {
                        result.setText("Your entry is not meeting the requirements, please ensure you are selecting a valid person or service!");
                    }
                    catch (IllegalArgumentException exception) {
                         result.setText(exception.getLocalizedMessage());
                    }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);

        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void ClickJoinSwarm(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Have a Drone Join a Swarm Please Input the Following!");

        ComboBox<String> service = serviceComboBox(vBox);

        ComboBox<String> drone1 = new ComboBox<>();
        drone1.setValue("Lead Drone Tag");
        vBox.getChildren().add(drone1);

        // service.setOnAction(updateTags -> {
        //     drone1.getItems().clear();
        //     drone1.setValue("Lead Drone Tag");
        //     drone1.getItems().addAll(DeliveryService.getService(service.getValue()).getDroneTagsAsStrings());
        // });

        ComboBox<String> drone2 = new ComboBox<>();
        drone2.setValue("Swarm Drone Tag");
        vBox.getChildren().add(drone2);

        service.setOnAction(updateTags -> {
            drone1.getItems().clear();
            drone1.setValue("Lead Drone Tag");
            drone1.getItems().addAll(DeliveryService.getService(service.getValue()).getDroneTagsAsStrings());
            drone2.getItems().clear();
            drone2.setValue("Swarm Drone Tag");
            drone2.getItems().addAll(DeliveryService.getService(service.getValue()).getDroneTagsAsStrings());
        });

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.joinSwarm(service.getValue(), Integer.parseInt(drone1.getValue()), Integer.parseInt(drone2.getValue()));
                    String text = String.format("%s has joined the swarm with lead drone %s.", drone2.getValue(), drone1.getValue());
                    result.setText(text);
                    }
                    catch (NumberFormatException exception) {
                        result.setText("Your entry is not meeting the requirements, please ensure you are selecting a valid drone or service!");
                    }
                    catch (IllegalArgumentException exception) {
                         result.setText(exception.getLocalizedMessage());
                    }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);


        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void ClickLeaveSwarm(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Have a Drone Leave a Swarm Please Input the Following!");

        ComboBox<String> service = serviceComboBox(vBox);

        ComboBox<String> drone2 = new ComboBox<>();
        drone2.setValue("Swarm Drone Tag");
        vBox.getChildren().add(drone2);

        service.setOnAction(updateTags -> {
            drone2.getItems().clear();
            drone2.setValue("Swarm Drone Tag");
            drone2.getItems().addAll(DeliveryService.getService(service.getValue()).getDroneTagsAsStrings());
        });

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.leaveSwarm(service.getValue(), Integer.parseInt(drone2.getValue()));
                    String text = String.format("%s has leaft the swarm.", drone2.getValue());
                    result.setText(text);
                    }
                    catch (NumberFormatException exception) {
                        result.setText("Your entry is not meeting the requirements, please ensure you are selecting a valid drone or service!");
                    }
                    catch (IllegalArgumentException exception) {
                         result.setText(exception.getLocalizedMessage());
                    }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);


        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void ClickCollectRevenue(ActionEvent event) {
        VBox vBox = newVbox(); //Creates new Vbox

        newPromt(vBox, "In Order to Collect Revenue Please Input the Following");

        ComboBox<String> service = serviceComboBox(vBox);

        Button button = new Button("Submit");
        vBox.getChildren().add(button);
        Text result = new Text();
        goBackButton(vBox); //Adds a go-back button
        vBox.getChildren().add(result);

        EventHandler<ActionEvent> submitBox = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    InterfaceLoop.collectRevenue(service.getValue());
                    String text = String.format("%s has successfully collected revenue!.", service.getValue());
                    result.setText(text);
                    }
                    catch (NumberFormatException exception) {
                        result.setText("Your entry is not meeting the requirements, please ensure you are selecting a valid service!");
                    }
                    catch (IllegalArgumentException exception) {
                         result.setText(exception.getLocalizedMessage());
                    }
            }
        };

        // when enter is pressed
        button.setOnAction(submitBox);

        setCurrentScene(vBox); //Sets scene
    }

    @FXML
    void initialize() {

    }

    @FXML void ClickStop() {
        player2.stop();

        VBox vbox = newVbox();
        vbox.setAlignment(Pos.BOTTOM_RIGHT);

        ImageView yellowDrone = new ImageView("yelldrone.png");
        vbox.getChildren().add(yellowDrone);

        Text acknowledgement = new Text("STOP ACKNOWLEDGED");
        vbox.getChildren().add(acknowledgement);

        Button exit = new Button("Yes Exit");
        exit.setOnAction(exitEvent -> System.exit(0));
        vbox.getChildren().add(exit);

        String filePath = new File("spook.mp3").toURI().toString();
        Media media = new Media(filePath);
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        // player = new MediaPlayer(spooky);
        //player.play();

        ScaleTransition scale = new ScaleTransition();
        scale.setNode(yellowDrone);
        scale.setDuration(Duration.millis(6000));
        scale.setCycleCount(1);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(2.5);
        scale.setByY(2.5);
        scale.setAutoReverse(false);
        scale.play();

        setCurrentScene(vbox);






        // Button sure = new Button("Exit. :(");
        // vbox.getChildren().add(sure);
        // sure.setOnAction(exitEvent -> {
        // });

        // goBackButton(vbox);


    }
}
