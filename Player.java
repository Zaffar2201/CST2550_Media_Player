

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.io.File;
import java.util.LinkedHashSet;


public class Player {

    private static Stage mediaPlayerWindow;
    private static MediaPlayer mediaPlayer;
    private static MediaView mediaView;
    private static Label songLabel;
    private static Slider timeSlider,volumeSlider;
    
    /*
    * Plays any audio/video in the playlist
    *
    * @param LinkedHashSet playlistDS : actual playlist
    * */
    public static void playAudio(LinkedHashSet<Song> playlistDS) {

        mediaPlayerWindow = new Stage();
        mediaPlayerWindow.setTitle("Karaoke - Media Player");
        mediaPlayerWindow.setMaximized(true);
        mediaPlayerWindow.setResizable(false);

        //Retrieves screen heighy
        double heightOfScreen = Screen.getPrimary().getBounds().getHeight();

        GridPane mediaPane = new GridPane();
        mediaPane.setAlignment(Pos.CENTER);

         songLabel = new Label();
         Label volumeLabel = new Label();
         volumeLabel.setText("Volume: ");

        // Retrieves first playlist audio and insert into mediaplayer
        File filepath = new File(getFirstSong(playlistDS).getSongFileName());
        Media media = new Media(filepath.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(heightOfScreen/10 *8);

        Button rewindBtn = new Button("<<");
        rewindBtn.setFont(Font.font(18));
        rewindBtn.setMinWidth(57);
        rewindBtn.setMinHeight(50);
        rewindBtn.setAlignment(Pos.CENTER);


        Button playBtn = new Button("►");
        playBtn.setFont(Font.font(18));
        playBtn.setMinWidth(57);
        playBtn.setMinHeight(50);
        playBtn.setAlignment(Pos.CENTER);


        Button pauseBtn = new Button("||");
        pauseBtn.setFont(Font.font(18));
        pauseBtn.setMinWidth(57);
        pauseBtn.setMinHeight(50);
        pauseBtn.setAlignment(Pos.CENTER);


        Button fastForwardBtn = new Button(">>");
        fastForwardBtn.setFont(Font.font(18));
        fastForwardBtn.setMinWidth(57);
        fastForwardBtn.setMinHeight(50);
        fastForwardBtn.setAlignment(Pos.CENTER);


        Button skipBtn = new Button("►||");
        skipBtn.setFont(Font.font(18));
        skipBtn.setMinWidth(57);
        skipBtn.setMinHeight(50);
        skipBtn.setAlignment(Pos.CENTER);

	    Button stopBtn = new Button("■");
        stopBtn.setFont(Font.font(18));
        stopBtn.setMinWidth(57);
        stopBtn.setMinHeight(50);
        stopBtn.setAlignment(Pos.CENTER);
        
        //Slider
        timeSlider = new Slider();
        timeSlider.setMinWidth(725);

        volumeSlider = new Slider();
        volumeSlider.setMinWidth(200);


        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.getChildren().add(songLabel);

        HBox mediaBox = new HBox();
        mediaBox.setAlignment(Pos.CENTER);
        mediaBox.getChildren().add(mediaView);
        mediaBox.setMinHeight(heightOfScreen/10 *8);


        HBox karaokeBox = new HBox(15);
        karaokeBox.setAlignment(Pos.CENTER_LEFT);
        karaokeBox.getChildren().addAll(rewindBtn, playBtn, pauseBtn, fastForwardBtn, skipBtn,stopBtn);

        HBox volumeSliderBox = new HBox(15);
        volumeSliderBox.setAlignment(Pos.CENTER);
        volumeSliderBox.getChildren().addAll(volumeLabel,volumeSlider);

        //Hbox
        HBox overallSliderBox = new HBox(20);
        overallSliderBox.setAlignment(Pos.CENTER);
        overallSliderBox.getChildren().addAll(timeSlider,volumeSliderBox);


        HBox mediaPlayerBtnBox = new HBox(20);
        mediaPlayerBtnBox.setAlignment(Pos.CENTER_LEFT);
        mediaPlayerBtnBox.getChildren().addAll(karaokeBox,overallSliderBox);
       
        //VBox
        VBox fullPlayerBox = new VBox(20);
        fullPlayerBox.setAlignment(Pos.CENTER);
        fullPlayerBox.getChildren().addAll(titleBox, mediaBox, mediaPlayerBtnBox);

        mediaPane.add(fullPlayerBox, 0, 0);
        mediaPane.setAlignment(Pos.CENTER);

        //Start mediaPlayer
        mediaPlayer.play();

        //List of actions once mediaPlayer has been successfully loaded
        mediaPlayer.setOnReady(() -> {

                    playBtn.setOnAction(e->{
                        //Retrieve mediaPlayer status
                          if(mediaPlayer.getStatus()== MediaPlayer.Status.STOPPED){
                              //Restart current video
                            mediaPlayer.seek(mediaPlayer.getStartTime());
                            mediaPlayer.play();
                        }else{
                            mediaPlayer.play();
                        }
                    });

                    pauseBtn.setOnAction(e->{
                        mediaPlayer.pause();
                    });


                    fastForwardBtn.setOnAction(e->{

                        //Forward media
                        mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(1.5));

                    });

                    rewindBtn.setOnAction(e->{

                        //Rewind media
                        mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(1.5));

                    });

                    skipBtn.setOnAction(e->{
                        mediaPlayer.stop();

                        playNextAudio(playlistDS);

                    });

		            stopBtn.setOnAction(e->{
                        mediaPlayer.stop();
                    });

                    //Initialse timeSlider values
                    timeSlider.setMin(0.0);
                    timeSlider.setValue(0.0);
                    timeSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());

                    volumeSlider.setMin(0);
                    volumeSlider.setMax(100);
                    volumeSlider.setValue(mediaPlayer.getVolume()*100);
                    songLabel.setText("Now playing: "+getFirstSong(playlistDS).getSongTitle());

                    mediaPlayer.currentTimeProperty().addListener((observableValue, duration, current) -> timeSlider.setValue(current.toSeconds()));

                    //Listener for sliders
                    timeSlider.setOnMousePressed(mouseEvent -> mediaPlayer.seek(Duration.seconds(timeSlider.getValue())));
                    volumeSlider.setOnMousePressed(mouseEvent -> mediaPlayer.setVolume(volumeSlider.getValue()/100));


                }

        );

        //Autoplay functionality
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {

                playNextAudio(playlistDS);
            }
        });


        mediaPlayerWindow.setOnCloseRequest(e->{
            mediaPlayer.stop();
        });


        Scene mediaPlayerScene = new Scene(mediaPane);
        mediaPlayerScene.getStylesheets().add("file:stylesheet/MediaPlayer.css");

        mediaPlayerWindow.setScene(mediaPlayerScene);
        mediaPlayerWindow.setOnCloseRequest(e -> {
            mediaPlayer.stop();
            e.consume();
            mediaPlayerWindow.close();
        });
        mediaPlayerWindow.show();




    }

    /*
    * Plays next audio in playlist
    *
    * @param LinkedHashSet playlistDS
    * */
    private static void playNextAudio(LinkedHashSet<Song> playlistDS){

        //Remove first(current) song in playlist
        playlistDS.remove(getFirstSong(playlistDS));

        //Stop mediaPlayer for empty playlist
        if(playlistDS.isEmpty()){
            MessageBox.displayMessage("Playlist is empty!");
            mediaPlayerWindow.close();
            return;
        }

        //Retrieves and set next song in playlist to mediaPlayer
        Song comingSong = getFirstSong(playlistDS);
        File newFilePath = new File(comingSong.getSongFileName());
        Media newMedia = new Media(newFilePath.toURI().toString());
        mediaPlayer= new MediaPlayer(newMedia);
        mediaView.setMediaPlayer(mediaPlayer);
        songLabel.setText("Now playing: "+comingSong.getSongTitle());
        mediaPlayer.play();
        mediaPlayer.currentTimeProperty().addListener((observableValue, duration, current) -> timeSlider.setValue(current.toSeconds()));


    }

    /*
    * Retrieves next audio in playlist
    *
    * @param LinkedHashSet playlistDS
    *
    * @return {@code next song} if playlist not empty
    *         {@code null} for empty playlist
    * */
    public static Song getFirstSong(LinkedHashSet<Song> playlistDS){

        Iterator<Song> iterator = playlistDS.iterator();

        if(iterator.hasNext()){
            return  iterator.next() ;
        }else{
           return null;
        }
    }


}
