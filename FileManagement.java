
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class FileManagement {

    /*
    * Load data from filepath argument to Karaoke symbol tbale
    *
    * @params filepath & libraryhash
    *
    * @throws NullPointerExcpetion for invalid data
    * @thorws IOException for invalid file
    *
    * @return KaraokeST libraryhash
    * */
    public void loadFileData(String filePath,KaraokeST<String,Song> libraryHash) {
        
        try {
            FileReader inputFile = new FileReader(filePath);

            try (BufferedReader inputBuffer = new BufferedReader(inputFile)) {
                String fileData;
                fileData = inputBuffer.readLine();

                while(fileData != null) {
                    String[] songData = fileData.split("\t");
                    Song readSong = new Song(songData[0], songData[1], Integer.parseInt(songData[2]), songData[3]);
                    libraryHash.put(songData[0],readSong);
                    fileData = inputBuffer.readLine();
                }
                
            } catch (NullPointerException e) {
                System.out.println("Invalid data present in file!");
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("Invalid file entered!");
            System.exit(0);
        }
        
    }


    
}
