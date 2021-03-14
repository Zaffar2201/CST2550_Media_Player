
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;


public class AddNewRecords {

    // GUI features
    private  static Stage newRecordWindow;
    private  static TextField songTitleField;
    private  static TextField artistField;
    private  static TextField playingTimeField;
    private  static TextField videoFileNameField;


    /*
    * Displays an interface to add new records
    * @param KaraokeST libraryHash byRef {add new data directly}
    * */
    public static void insertNewRecord(KaraokeST<String,Song> libraryHash) {
        
        newRecordWindow = new Stage();
        newRecordWindow.setTitle("Karaoke - Add New Record");
        newRecordWindow.setResizable(false);
        newRecordWindow.initModality(Modality.APPLICATION_MODAL);
        

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(8);
        pane.setHgap(10);


        Label title = new Label("Enter title: ");
        GridPane.setConstraints(title, 1, 2);

        songTitleField = new TextField();
        GridPane.setConstraints(songTitleField, 2, 2);

        Label artist = new Label("Enter artist: ");
        GridPane.setConstraints(artist, 1, 3);

        artistField = new TextField();
        GridPane.setConstraints(artistField, 2, 3);

        Label playingTime = new Label("Enter Duration: ");
        GridPane.setConstraints(playingTime, 1, 4);

        playingTimeField = new TextField();
        GridPane.setConstraints(playingTimeField, 2, 4);

        Label videoFileName = new Label("Enter Video File Name: ");
        GridPane.setConstraints(videoFileName, 1, 5);

        videoFileNameField = new TextField();
        GridPane.setConstraints(videoFileNameField, 2, 5);


       // Calls validation method on key pressed
        Button submitBtn = new Button(" Submit ");
        submitBtn.setFont(Font.font(15));
        submitBtn.setOnAction( e ->{
            if(passValidation()){
                addProcess(songTitleField.getText(),artistField.getText(),Integer.parseInt(playingTimeField.getText()),videoFileNameField.getText(),libraryHash);
                MessageBox.displayMessage("Song successfully added to library!");
                newRecordWindow.close();
            }
        });

        Button backBtn = new Button(" Back ");
        backBtn.setFont(Font.font(15));
        backBtn.setOnAction( e-> {
            newRecordWindow.close();
        });
        HBox buttons = new HBox(5, submitBtn,backBtn);
        GridPane.setConstraints(buttons, 2, 10);
        
        pane.getChildren().addAll(title, songTitleField, artist, artistField, playingTime, playingTimeField, videoFileName, videoFileNameField, buttons);
        
        Scene addNewRecordScene = new Scene(pane, 450, 350);
        addNewRecordScene.getStylesheets().add("file:stylesheet/Menu.css");

        newRecordWindow.setScene(addNewRecordScene);
        newRecordWindow.showAndWait();

    }


    /*
    * Validates  inputs from user with regex
    *
    * @param KaraokeST library
    *
    * @return {@code true} if pass validations
    *         {@code false} if fails validations
    * */
    
    private static boolean passValidation(){


        if(!songTitleField.getText().matches("^[a-zA-z0-9. ]+$")){
            MessageBox.displayMessage("Invalid title format!");
            return false;
        }

        if(!artistField.getText().matches("^[a-zA-z ]+$")){
            MessageBox.displayMessage("Invalid artist name!");
            return false;
        }

        try{
           
            if (Integer.parseInt(playingTimeField.getText()) <= 0) {
                MessageBox.displayMessage("Invalid playing time!");
                return false;
            }
            
        }catch(NumberFormatException e){
            MessageBox.displayMessage("Invalid playing time!");
            return false;
            
        }
        
        if(!videoFileNameField.getText().matches("([a-zA-Z0-9\\s_\\\\.\\-\\(\\):])+(.mp4|.flv|.mp3|.mkv)$")){
            MessageBox.displayMessage("Invalid video file format!");
            return false;
        }
        return true;
    }

    /*
    * Adds the new record to library
    *
    * @param KaraokeST library
    * */
    public static void addProcess(String songTitle,String songArtist,int playingTime,String videoName,KaraokeST<String,Song> libraryHash){
      
        libraryHash.put(songTitle,new Song(songTitle,songArtist,playingTime,videoName));


    }


}
