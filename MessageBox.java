
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class MessageBox {

    private  static Stage alertWindow;

    /*
    * Display specified error message
    *
    * @param errorText: Text to be displayed on theinterface
    * */
    public  static void displayMessage(String errorText){

        alertWindow = new Stage();
        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle("ATTENTION!");
        alertWindow.setMinWidth(250);
        alertWindow.setMinHeight(250);
        alertWindow.setResizable(true);
        
        Label errorMessage = new Label();
        errorMessage.setId("errorM");
        errorMessage.setText(errorText);
        
        ImageView okIcon = null;
        try {
            okIcon = new ImageView(new Image(new FileInputStream("images/yesIcon.png")));
        } catch (FileNotFoundException e) {
            System.out.println("Error in loading messageBox icon!");
            e.printStackTrace();
        }
        okIcon.setFitWidth(40);
        okIcon.setFitHeight(40);
        Button okButton = new Button("", okIcon);
        okButton.setMinHeight(40);
        okButton.setMinWidth(40);
        okButton.setId("okBtn");
        
        okButton.setOnAction(e -> {
            alertWindow.close();
        });
        
        VBox messageBoxLayout = new VBox(30);
        messageBoxLayout.getChildren().addAll(errorMessage, okButton);
        messageBoxLayout.setAlignment(Pos.CENTER);

       //Attach stylesheet to scene
        Scene alertScene = new Scene(messageBoxLayout);
        alertScene.getStylesheets().add("file:stylesheet/MessageBox.css");
        alertWindow.setScene(alertScene);
        alertWindow.showAndWait();


    }


}








