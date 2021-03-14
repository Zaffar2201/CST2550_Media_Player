import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;


public class KaraokeApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    /*
    * Retrives and check for console line arguments
    *
    * @return argument(path of file) if pass argument validations
    *         {@code null} for invalid arguments
    * */
    public String retrieveArgument(){

        //Retrieves parameter & converts into string array
        final Parameters parameterInstance = getParameters();
        final List<String> parameterArgument = parameterInstance.getRaw();
        String argsArray[] = parameterArgument.toArray(new String[0]);

        //Argument validations
        if(argsArray.length ==0){
            System.out.println("File data argument missing!");
            System.exit(0);
        }else if(argsArray.length==1){
            String filePath = argsArray[0];
            return filePath;
        }else{
            System.out.println("Too many file arguments!");
            System.exit(0);
        }

        return null;
    }

    @Override
    public void start(Stage primaryStage) {


        KaraokeST<String, Song> libraryHash = new KaraokeST<String, Song>();

        LinkedHashSet<Song> playlist = new LinkedHashSet<Song>();

        //Restores filePath from retrieveArgument
        final String filePath = retrieveArgument();

        //Load and checks file validity
        FileManagement file = new FileManagement();
        file.loadFileData(filePath,libraryHash);

        primaryStage.setTitle("Karaoke - Menu");
        primaryStage.setResizable(false);

        //Insert icon on button
        ImageView playIcon = null;
        try {
            playIcon = new ImageView(new Image(new FileInputStream("images/playIcon.png")));
        } catch (FileNotFoundException e) {
            System.out.println("Error in loading play icon!\n");
            e.printStackTrace();
        }
        playIcon.setFitWidth(25);
        playIcon.setFitHeight(25);
        
        
        Button playMediaBtn = new Button("PLAY NOW", playIcon);
        playMediaBtn.setPrefSize(150, 40);
        playMediaBtn.setOnAction(e -> {
            if (playlist.isEmpty()) {
                MessageBox.displayMessage("Playlist is empty!");
                return;
            }
            Player.playAudio(playlist);

        });


        ImageView playListIcon = null;
        try {
            playListIcon = new ImageView(new Image(new FileInputStream("images/playlistIcon.png")));
        } catch (FileNotFoundException e) {
            System.out.println("Error in loading playlist icon!\n");
            e.printStackTrace();
        }
        playListIcon.setFitWidth(25);
        playListIcon.setFitHeight(25);
        Button playListBtn = new Button("PLAYLIST", playListIcon);
        playListBtn.setPrefSize(150, 40);
        playListBtn.setOnAction(e -> {
            Playlist.displaySong(playlist);
        });


        ImageView libraryIcon = null;
        try {
            libraryIcon = new ImageView(new Image(new FileInputStream("images/libraryIcon.png")));
        } catch (FileNotFoundException e) {
            System.out.println("Error in loading library icon!\n");
            e.printStackTrace();
        }
        libraryIcon.setFitWidth(25);
        libraryIcon.setFitHeight(25);
        Button libraryBtn = new Button("LIBRARY", libraryIcon);
        libraryBtn.setPrefSize(150, 40);
        libraryBtn.setOnAction(e -> {
            Library.displayLibrary(libraryHash, playlist);
        });


        ImageView exitIcon = null;
        try {
            exitIcon = new ImageView(new Image(new FileInputStream("images/exitIcon.png")));
        } catch (FileNotFoundException e) {
            System.out.println("Error in loading exit icon!\n");
            e.printStackTrace();
        }
        exitIcon.setFitWidth(25);
        exitIcon.setFitHeight(25);
        Button exitBtn = new Button("EXIT        ", exitIcon);
        exitBtn.setPrefSize(150, 40);
        exitBtn.setOnAction(e -> {
            promptOnClosing();
        });

        VBox layout = new VBox(60);
        layout.setPrefWidth(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(playMediaBtn, playListBtn, libraryBtn, exitBtn);
        
        Scene karaokeScene = new Scene(layout, 607, 900);
        karaokeScene.getStylesheets().add("file:stylesheet/Menu.css");
        
        primaryStage.setScene(karaokeScene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            promptOnClosing();
        });

    }


    /*
    * Prompts user on exiting the application
    * */
    public static void promptOnClosing() {

            ConfirmationBox exitConfirmation = new ConfirmationBox();
            boolean userChoice = exitConfirmation.promptExitMessage();
            
            //Exit the application
            if (userChoice == true) {
                Platform.exit();

            }

    }

}
