
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.LinkedHashSet;


public class Library {

   private static Stage libraryWindow;
   private static TableView libraryTableView;


   /*
   * Display library table and pane for user choices
   *
   * @params KaraokeST libraryHash & LinkedHashSet playlist
   * */
  public static void displayLibrary(KaraokeST<String,Song> libraryHash, LinkedHashSet<Song> playlist){

      libraryWindow = new Stage();
      libraryWindow.setTitle("Karaoke - Library");
      libraryWindow.setResizable(false);
      libraryWindow.initModality(Modality.APPLICATION_MODAL);

      BorderPane libraryBorderPane = new BorderPane();
      Button addNewRecordBtn,addToPlaylistBtn,playlistBtn,refreshBtn,closeLibraryBtn;


      try {

          // Tableview, columns and properties
          TableColumn<Song,String> TitleColumn = new TableColumn<>("Title");
          TitleColumn.setCellValueFactory(new PropertyValueFactory<>("songTitle"));
          TitleColumn.setResizable(false);
          TitleColumn.setPrefWidth(595);
          TitleColumn.setSortable(false);

          TableColumn<Song, String> ArtistColumn = new TableColumn<>("Artist");
          ArtistColumn.setCellValueFactory(new PropertyValueFactory<>("songArtist"));
          ArtistColumn.setResizable(false);
          ArtistColumn.setMinWidth(220);
          ArtistColumn.setSortable(false);

          TableColumn<Song, Integer> DurationColumn = new TableColumn<>("Duration");
          DurationColumn.setCellValueFactory(new PropertyValueFactory<>("songPlayingTime"));
          DurationColumn.setResizable(false);
          DurationColumn.setPrefWidth(110);
          DurationColumn.setSortable(false);

          
          libraryTableView = new TableView<>();
          libraryTableView.prefHeightProperty().bind(libraryBorderPane.heightProperty());
          libraryTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

          // Set table row items
          libraryTableView.setItems(loadTableDataItems(libraryHash));
          
          libraryTableView.getColumns().addAll(TitleColumn, ArtistColumn, DurationColumn);

      } catch (Exception e) {
          MessageBox.displayMessage("Error in loading library table...");
          e.printStackTrace();
      }

      
      Pane libraryRightPane = new Pane();
      libraryRightPane.setMinSize(300,700);
     
      libraryRightPane.getStyleClass().add("pane");
      libraryBorderPane.setCenter(libraryTableView);
      libraryBorderPane.setRight(libraryRightPane);

      TextField searchSongField = new TextField();
      searchSongField.setPromptText("Search song here...");
      searchSongField.setStyle("-fx-prompt-text-fill: darkgrey;");
      searchSongField.setMinHeight(30);
      searchSongField.setMinWidth(300);
      
      //Listener for Enter key
      searchSongField.setOnKeyPressed(new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent keyEvent) {
              if (keyEvent.getCode().equals(KeyCode.ENTER)) {

                  if(searchSongField.getText().trim().isEmpty()){
                      MessageBox.displayMessage("Please enter a song title!");
                      return;
                  }

                  ObservableList<Song> songsFound =  searchSong(libraryHash,searchSongField.getText());

                  if(songsFound.isEmpty()){
                      MessageBox.displayMessage("No song found!");
                  }else{
                      libraryTableView.getItems().clear();
                      libraryTableView.setItems(songsFound);
                  }



                  searchSongField.clear();
              }
          }
      });


      Image searchIon = new Image(new File("images/search.png").toURI().toString(), 20, 20, false, false);
      
      Button searchBtn = new Button("",new ImageView(searchIon));
      searchBtn.setMaxSize(25,25);

      //Listener on key pressed
      searchBtn.setOnAction(e ->{


         if(searchSongField.getText().trim().isEmpty()){
              MessageBox.displayMessage("Please enter a song title!");
              return;
          }

          ObservableList<Song> songsFound =  searchSong(libraryHash,searchSongField.getText());

          if(songsFound.isEmpty()){
              MessageBox.displayMessage("No song found!");
          }else{
              libraryTableView.getItems().clear();
              libraryTableView.setItems(songsFound);
          }

          searchSongField.clear();
      });

      //HBox
      HBox searchHBox = new HBox(searchSongField, searchBtn);
      searchHBox.setAlignment(Pos.TOP_CENTER);
      searchHBox.setPadding(new Insets(25,10,10,10));

      libraryRightPane.getChildren().add(searchHBox);
      
      addNewRecordBtn = new Button();
      addToPlaylistBtn= new Button();
      playlistBtn = new Button();
      refreshBtn = new Button();
      closeLibraryBtn = new Button();


      addNewRecordBtn.setText("ADD NEW RECORD");
      addNewRecordBtn.setMinSize(170,50);
      addNewRecordBtn.setTooltip(new Tooltip("Add new song(s) to library"));
      addNewRecordBtn.setOnAction(e->{
        AddNewRecords.insertNewRecord(libraryHash);
         refreshLibraryTable(libraryHash);
      });

      addToPlaylistBtn.setText("ADD TO PLAYLIST");
      addToPlaylistBtn.setMinSize(170,50);
      addToPlaylistBtn.setTooltip(new Tooltip("Select song(s) to add to playlist"));
      addToPlaylistBtn.setOnAction(e->{
          //Retrieves index highlighted on tableview
          int selectedRowIndex = libraryTableView.getSelectionModel().getSelectedIndex();
          if (selectedRowIndex != -1) {
              //Convert table item into Song object
              Song selectedSong = (Song) libraryTableView.getSelectionModel().getSelectedItem();

              //Check for duplicate songs in playlist
              if(checkPlaylistSongDuplication(playlist,selectedSong)){
                  MessageBox.displayMessage("Song already in playlist!");
                  return;
              }

              playlist.add(selectedSong);
             MessageBox.displayMessage("Song successfully added!");
          }else{
              MessageBox.displayMessage("Please select a song!");
          }

      });



      playlistBtn.setText("VIEW PLAYLIST");
      playlistBtn.setMinSize(170, 50);
      playlistBtn.setTooltip(new Tooltip("View current playlist"));
      playlistBtn.setOnAction(e->{
          Playlist.displaySong(playlist);
      });
      
      refreshBtn.setText("REFRESH TABLE");
      refreshBtn.setMinSize(170, 50);
      refreshBtn.setTooltip(new Tooltip("Refresh library table to default one"));
      refreshBtn.setOnAction(e ->{
        refreshLibraryTable(libraryHash);
    });



      closeLibraryBtn.setText("CLOSE LIBRARY");
      closeLibraryBtn.setMinSize(170, 50);
      closeLibraryBtn.setTooltip(new Tooltip("Close current library"));
      closeLibraryBtn.setOnAction(e -> {
          libraryWindow.close();

      });


      VBox libraryButtons = new VBox(55);
      libraryButtons.setAlignment(Pos.CENTER);
      
      libraryButtons.getChildren().addAll(searchHBox,addNewRecordBtn,addToPlaylistBtn,playlistBtn,refreshBtn,closeLibraryBtn);

      libraryRightPane.getChildren().add(libraryButtons);

      Scene libraryScene = new Scene(libraryBorderPane,1300,700);

      //Attach stylesheet to libraryScene
      libraryScene.getStylesheets().add("file:stylesheet/Menu.css");
      libraryWindow.setScene(libraryScene);
      libraryWindow.show();

  }

    /*
    * Refresh tableView to default
    *
    * @param KaraokeST libraryHash
    * */
    private static void refreshLibraryTable(KaraokeST<String,Song> libraryHash){

      libraryTableView.getItems().clear();


      libraryTableView.setItems(loadTableDataItems(libraryHash));
    }

    /**
     * Load contents of libraryHash into an observable list
     *
     * @param KaraokeST libraryHash
     *
     * @return ObservableList songList
     */

    public static ObservableList<Song> loadTableDataItems(KaraokeST<String,Song> libraryHash) {

   ObservableList<Song> songList = FXCollections.observableArrayList();

    for (String songTitle : libraryHash.keys()) {
        songList.add(libraryHash.get(songTitle));

    }

    return songList;

}
    /*
    * Search for a song in the libraryHash based on user search
    *
    * @params KaraokeST library & searchCriteria{user search text}
    *
    * @return ObservableList songFound
    * */
    public static ObservableList<Song> searchSong(KaraokeST<String,Song> libraryHash,String searchCriteria) {


        ObservableList<Song> songFound = FXCollections.observableArrayList();

        for (String songTitle : libraryHash.keys()) {

            if (songTitle.toLowerCase().contains(searchCriteria.toLowerCase())) {
                songFound.add(libraryHash.get(songTitle));
            }

        }

        return songFound;

    }

    /*
    * Check for duplicate song in playlist
    *
    * @params LinkedhashSet playlist(the actual playlist data structure)  & selectedSong ( the initial song)
    *
    * @return @code true} if duplicate song found;
     *         {@code false} otherwise
    * */
    public static boolean checkPlaylistSongDuplication(LinkedHashSet<Song> playlist, Song selectedSong) {

        if(playlist.contains(selectedSong)){
                  return true;
        }else{
            return false;
        }

    }


}












