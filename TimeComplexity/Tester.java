



public class Tester  {




    public static void main(String[] args) {

       FileTimeComplexity audioFile = new FileTimeComplexity();
       audioFile.fileReader("sample_song_data.txt");

	KaraokeST<String, Song> libraryHash = new KaraokeST<String, Song>();
        Stopwatch populateComplexity = new Stopwatch();

        populateComplexity.start();
        while (audioFile.reader.hasNextLine()){
            String content = audioFile.reader.nextLine();

            String split[] = content.split("\t",0);

            Song media = new Song();
            media.setSongTitle(split[0]);
            media.setSongArtist(split[1]);
            media.setSongPlayingTime(Integer.parseInt(split[2]));
            media.setSongFileName(split[3]);
	    libraryHash.put(split[0],new Song(split[0],split[1],Integer.parseInt(split[2]),split[3]));


        }

        System.out.println("Time taken: "+populateComplexity.stop());





    }
}
