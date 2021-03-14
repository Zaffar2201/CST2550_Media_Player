
public class Song {
    private String songTitle;
    private String songArtist;
    private int songPlayingTime;
    private String songFileName;

    //Constructor
    public Song(){
        this.songTitle=null;
        this.songArtist=null;
        this.songPlayingTime=0;
        this.songFileName=null;

    }

    public Song(String title,String artist,int playingTime,String videoFileName){

        this.songTitle=title;
        this.songArtist=artist;
        this.songPlayingTime=playingTime;
        this.songFileName = videoFileName;
        
    }

    //Setters
    public void setSongTitle(String songTitle) {
        
        this.songTitle = songTitle;
    }

    public void setSongArtist(String songArtist) {
        
        this.songArtist = songArtist;
    }

    public void setSongPlayingTime(int songPlayingTime) {
        
        this.songPlayingTime = songPlayingTime;
    }

    public void setSongFileName(String songFileName)
    {
        this.songFileName = songFileName;
    }

    public String getSongTitle()
    {
        return songTitle;
    }

    public String getSongArtist() {
        
        return songArtist;
    }

    //Getters
    public int getSongPlayingTime() {
        
        return songPlayingTime;
    }

    public String getSongFileName() {
        
        return songFileName;
    }

    //Print method
    public void printAll(){
        System.out.println(getSongTitle()+"-"+getSongArtist()+"-"+getSongPlayingTime()+"-"+getSongFileName());

    }

}
