
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class ConfirmationBox {

    private  Stage confirmationWindow;
    private boolean answer;

    /* 
     * Display exit confirmation message
     *
     * @returns {@code true} if user wants to exit
     *          {@code false} if user wants to stay
     * */
    public  boolean promptExitMessage(){


        confirmationWindow = new Stage();
        confirmationWindow.initModality(Modality.APPLICATION_MODAL);
        confirmationWindow.setTitle("EXIT");
        confirmationWindow.setMinWidth(400);
        confirmationWindow.setResizable(true);

        // Label Text
        Label message = new Label();
        message.setText("Do you really want to exit the application?");
        message.setAlignment(Pos.TOP_CENTER);


        // Insert icon
        ImageView tickIcon = null;
        try {
            tickIcon = new ImageView(new Image(new FileInputStream("images/yesIcon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tickIcon.setFitWidth(25);
        tickIcon.setFitHeight(25);
        Button yesButton = new Button("", tickIcon);


        ImageView crossIcon = null;
        try {
            crossIcon = new ImageView(new Image(new FileInputStream("images/noIcon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        crossIcon.setFitWidth(25);
        crossIcon.setFitHeight(25);
        Button noButton = new Button("", crossIcon);



        yesButton.setOnAction(e -> {
            this.answer = true;
            confirmationWindow.close();
        });

        noButton.setOnAction(e -> {
            e.consume();
            confirmationWindow.close();
            this.answer = false;

        });


        HBox confirmationButton = new HBox(25);
        confirmationButton.getChildren().addAll( yesButton, noButton);
        confirmationButton.setAlignment(Pos.BOTTOM_CENTER);

        VBox content = new VBox(20);
        content.getChildren().addAll(message,confirmationButton);
        content.setAlignment(Pos.CENTER);

        Scene alertScene = new Scene(content,250,250);
        alertScene.getStylesheets().add("file:stylesheet/MessageBox.css");
        confirmationWindow.setScene(alertScene);
        confirmationWindow.showAndWait();


        return answer;


    }















}







