import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class KaraokeAppTest {

    /*
    * Test if data can be loaded from file to karaoke symbol table
    * */
    @Test
    public void testLoadFileData(){

        KaraokeST<String, Song> libraryHash = new KaraokeST<String,Song>();
        String filePath="sample_song_data.txt";

        FileManagement file = new FileManagement();
        file.loadFileData(filePath,libraryHash);
        assertNotNull("Failed to load data",libraryHash);

    }

    /*
    * Test if additional data can be added into the Karaoke symbol table
    * */
    @Test
    public void testAddProcess(){

        KaraokeST<String, Song> libraryHash = new KaraokeST<String,Song>();

        AddNewRecords.addProcess("MySong","Adele",125,"test.mp4",libraryHash);
        
        assertTrue("Failure to add new data",libraryHash.contains("MySong"));

    }

    /*
    * Test to search for a song in the karaoke symbol table
    * */
    @Test
    public void testSearchSong(){

        KaraokeST<String, Song> libraryHash = new KaraokeST<String,Song>();

        libraryHash.put("Fire in the rain",new Song("Fire in the rain","Adele",325,"test.mp4"));
        
        assertEquals(0,(Library.searchSong(libraryHash,"Hello").size()));

    }

    /*
    * Test if can access a song object attribute
    * */
    @Test
    public void testGetSongArtist(){

        Song newSong = new Song();

        assertNull("Object should be null",newSong.getSongArtist());

    }


    /*
    * Test the karaoke symbol table performance of sample data
    * */
    @Test(timeout = 180)
    public void testPerformance(){

        KaraokeST<String, Song> libraryHash = new KaraokeST<String,Song>();
        String filePath="sample_song_data.txt";

        FileManagement file = new FileManagement();
        file.loadFileData(filePath,libraryHash);

    }

    /*
    * Test to see if can retrieve playlist first song
    * */
    @Test
    public void testGetFirstSong(){

        Song firstSong = new Song("Fire in the rain","Adele",200,"test.mp4");
        Song otherSong = new Song("Hello","Adele",400,"test.mp4");

        LinkedHashSet<Song> playlist = new LinkedHashSet<Song>();

        playlist.add(firstSong);
        playlist.add(otherSong);

        assertNotSame("Same song objects",otherSong,Player.getFirstSong(playlist));

    }


    /*
    * Test to determine if return all library song.
    * */
    @Test
    public void testLoadTableDataItems(){

        KaraokeST<String, Song> libraryHash = new KaraokeST<String,Song>();

        Song firstTestSong = new Song("Fire in the rain","Adele",100,"test.mp4");
        Song secondTestSong = new Song("Skyfall","Adele",200,"test.mp4");
        Song thirdTestSong = new Song("Rumour Has It","Adele",300,"test.mp4");

        libraryHash.put(firstTestSong.getSongTitle(),firstTestSong);
        libraryHash.put(secondTestSong.getSongTitle(),secondTestSong);
        libraryHash.put(thirdTestSong.getSongTitle(),thirdTestSong);

        ObservableList<Song> libraryTableSong = Library.loadTableDataItems(libraryHash);

        assertTrue("Error in loading table items",(libraryTableSong.contains(secondTestSong) && libraryTableSong.size() == 3));

    }
    /*
    * Checks for duplicated song in playlist data structure.
    * */
    @Test
    public void testCheckPlaylistSongDuplication(){

        LinkedHashSet<Song> playlist = new LinkedHashSet<Song>();

        Song firstSong = new Song("Fire in the rain","Adele",200,"test.mp4");
        Song otherSong = new Song("Hello","Adele",200,"test.mp4");

        playlist.add(firstSong);

        assertFalse("Song already in playlist!",Library.checkPlaylistSongDuplication(playlist,otherSong));

    }

    /*
    * Test to see if deletion in playlist works.
    * */
    @Test
    public void testDeletePlaylistSong(){

        LinkedHashSet<Song> playlist = new LinkedHashSet<Song>();

        Song selectedSong = new Song("Fire in the rain","Adele",200,"test.mp4");

        playlist.add(selectedSong);

        Playlist.deletePlaylistSong(selectedSong,playlist);

        assertFalse(playlist.contains(selectedSong));

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
