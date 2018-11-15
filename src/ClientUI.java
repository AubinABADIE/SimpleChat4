

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import client.ChatClient;
import common.ChatIF;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * UI class of SimpleChat4. Its implements the chat interface in order to activate
 * display() method.
 * 
 * @author Marie Salelles
 * @author Aubin ABADIE
 *
 */
public class ClientUI extends Application implements ChatIF {
	
	/* UI Variables */
	
	private Stage primaryStage;
	private Scene connectionScene;
	private Scene principaleScene;
	
	private String name;
	private String host;
	private int port;

	private Label nameLabel;
	private TextArea messages;
	
	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	private ChatClient client;

	/* Getters and Setters */
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
    
	/**
	 * Create the window with the two scene and set the first scene as main.
	 * 
	 * @param primaryStage Frame window
	 * @throws Exception
	 */
    @Override
    public void start(Stage primaryStage) throws Exception {
    	this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Connection");
        
        // Scene 2
        // Create the registration form grid pane
        StackPane root = createChatPane();
        // Add UI controls to the registration form grid pane
        addUIControls2(root);
        // Create a scene with registration form grid pane as the root node
        principaleScene = new Scene(root, 800, 500);
        
        // Scene 1
        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        this.connectionScene = new Scene(gridPane, 800, 500);
        
        // Set the scene in primary stage	
        this.primaryStage.setScene(connectionScene);
        // Set the stage visible
        this.primaryStage.show();
    }
    
    /**
     * design pane : placement of components
     * @return
     */
    private GridPane createRegistrationFormPane() {
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

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        
        return gridPane;
    }
    
    /**
     * design the primary scene with the connection interface and handle the events
     * @param gridPane
     */
    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Connection");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add Name Label
        Label nameLabel = new Label("Name : ");
        gridPane.add(nameLabel, 0,1);

        // Add Name Text Field
        TextField nameField = new TextField("aubin");
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);

        // Add Host Label
        Label hostLabel = new Label("Host : ");
        gridPane.add(hostLabel, 0, 2);

        // Add Host Text Field
        TextField hostField = new TextField("localhost");
        hostField.setPrefHeight(40);
        gridPane.add(hostField, 1, 2);

        // Add Port Label
        Label portLabel = new Label("Port : ");
        gridPane.add(portLabel, 0, 3);

        // Add Port Text Field
        TextField portField = new TextField("5555");
        portField.setPrefHeight(40);
        gridPane.add(portField, 1, 3);

        // Add Login Button
        Button loginButton = new Button("Login");
        loginButton.setPrefHeight(40);
        loginButton.setDefaultButton(true);
        loginButton.setPrefWidth(100);
        gridPane.add(loginButton, 0, 4, 2, 1);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setMargin(loginButton, new Insets(20, 0,20,0));
        
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
                    return;
                }
                if(hostField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a host");
                    return;
                }
                if(portField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a port");
                    return;
                }
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Registration Successful!");
                alert.setHeaderText(null);
                alert.setContentText("Welcome " + nameField.getText());
                alert.initOwner(gridPane.getScene().getWindow());
                alert.setResizable(false);
                
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent()) {
                	// alert is exited, no button has been pressed.
                } else if(result.get() == ButtonType.OK) {
                	
                	setName(nameField.getText());
                	setHost(hostField.getText());
                	setPort(Integer.parseInt(portField.getText()));
                	
                	createConnection();
                	
                	/*Label nameL = new Label("Hello " + getName() + "!");
                	GridPane g = (GridPane) nameLabel.getParent();
                	g.getChildren().set(1, nameL);
                	g.getChildren().add(nameL);*/
                    
                	primaryStage.setScene(principaleScene);
                	
                } else if(result.get() == ButtonType.CANCEL) {
                	
                	nameField.setText("");
                	hostField.setText("");
                	portField.setText("");
                }
                	
            }
        });
    }
    
    /**
     * create the pane for the simpleChat
     * @return
     */
    private StackPane createChatPane() {
        // Instantiate a new VBox 
    	StackPane root = new StackPane();
        
        return root;
    }
    
    /**
     * design the second scene with the discussion interface and handle the events
     * @param root
     */
    @SuppressWarnings("static-access")
	private void addUIControls2(StackPane root) {
    	
    	// Add Name Label
    	nameLabel = new Label();
        nameLabel.setPrefHeight(40);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        
        // Add Logoff Button
        Button logoffButton = new Button("Logoff");
        logoffButton.setPrefHeight(40);
        
        // Add Quit Button
        Button quitButton = new Button("Quit");
        quitButton.setPrefHeight(40);
        
        // Add Header HBox
    	HBox header = new HBox();
        // Set a spacing of 5px on each side
    	header.setSpacing(5);
    	// create a hbox with a label that grows horizontally
    	header.setHgrow(nameLabel, Priority.ALWAYS);
        header.getChildren().addAll(nameLabel, logoffButton, quitButton);
        
        // Add Messages Text Area
        this.messages = new TextArea();
        messages.setEditable(false);
        messages.setPrefHeight(400);
        messages.setPrefWidth(300);
        messages.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        messages.setWrapText(true);
        
        // Add Input Text Field
        TextField input = new TextField();
        input.setPromptText("Type here...");
        input.setPrefHeight(40);
        
        // Add Send Button
        Button sendButton = new Button("Send");
        sendButton.setPrefHeight(40);
        sendButton.setDefaultButton(true);
        
        // Add EditMessage HBox
    	HBox editMessage = new HBox();
    	// Set a spacing of 5px on each side
    	editMessage.setSpacing(5);
    	// create a hbox with a textfield that grows horizontally
    	editMessage.setHgrow(input, Priority.ALWAYS);
        editMessage.getChildren().addAll(input, sendButton);
        
        // Add VBox
        VBox vbox = new VBox();
        // Set a padding of 5px on each side
        vbox.setPadding(new Insets(5));
        // Set a spacing of 5px on each side
        vbox.setSpacing(5);
        // create a vbox with a textarea that grows vertically
        vbox.setVgrow(messages, Priority.ALWAYS);
        vbox.getChildren().addAll(header, messages, editMessage);
        
        root.getChildren().addAll(vbox);
        
        logoffButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Logoff");
                alert.setHeaderText(null);
                alert.setContentText( "Are you sure to logoff?");
                alert.initOwner(root.getScene().getWindow());
                alert.setResizable(false);
                
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent()) {
                	// alert is exited, no button has been pressed.
                } else if(result.get() == ButtonType.OK) {
                	
                	client.handleMessageFromClientUI("#logoff");
                    
                	primaryStage.setScene(connectionScene);
                	
                } else if(result.get() == ButtonType.CANCEL) {}
                	
            }
        });
        
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit");
                alert.setHeaderText(null);
                alert.setContentText( "Are you sure to quit?");
                alert.initOwner(root.getScene().getWindow());
                alert.setResizable(false);
                
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent()) {
                	// alert is exited, no button has been pressed.
                } else if(result.get() == ButtonType.OK) {
                	
                	client.handleMessageFromClientUI("#quit");
                	Platform.exit();
                    System.exit(0);
                	
                } else if(result.get() == ButtonType.CANCEL) {}
                	
            }
        });
        
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(input.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, root.getScene().getWindow(), "Warning!", "Please enter your message");
                    return;
                }
                
                client.handleMessageFromClientUI(input.getText());
                input.setText("");
            }
        });

    }
    
    /**
     * Show alert to user 
     * @param alertType
     * @param owner
     * @param title
     * @param message
     */
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.setResizable(false);
        alert.show();
    }
    
    /**
     * Create chatClient  to connect with the server
     * 
     */
    private void createConnection() {
    	try {
			client = new ChatClient(name, host, port, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * This method overrides the method in the ChatIF interface.  It
     * displays a message onto the screen.
     *
     * @param message The string to be displayed.
     */
	@Override
	public void display(String message) {
		
		String [] parts = message.split(">");
		String sender = parts[0];
		String content = parts[1];
		
		// Add Name Label
        /*Label nameLabel = new Label(sender);
        nameLabel.setPrefHeight(40);
        nameLabel.setMaxWidth(Double.MAX_VALUE);*/
        
        // Add Time Label
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        /*Label timeLabel = new Label(sdf.format(cal.getTime()));
        timeLabel.setPrefHeight(40);
        
        // Add Content TextArea
        Label contentText = new Label(content);
        
        // Add Header HBox
    	HBox header = new HBox();
        // Set a spacing of 5px on each side
    	header.setSpacing(5);
    	// create a hbox with a label that grows horizontally
    	header.setHgrow(contentText, Priority.ALWAYS);
        header.getChildren().addAll(contentText);*/
        
        messages.appendText(sender + " <" + sdf.format(cal.getTime()) + ">: \n" + content + "\n\n");
	}
	
	/**
	 * Launch the chat interface 
	 * @param args
	 */
	public static void main(String[] args) {
        Application.launch(ClientUI.class, args);
    }
} 