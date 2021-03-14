
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileTimeComplexity {

    File audioFile;
    Scanner reader = null;

    public void fileReader(String path){
        audioFile= new File(path);
        try{
            reader= new Scanner(audioFile);

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }





    }















}
