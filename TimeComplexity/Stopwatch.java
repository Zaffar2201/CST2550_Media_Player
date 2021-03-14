
public class Stopwatch {

    private long startTime;

    public Stopwatch(){
        startTime=0;
    }

    public void start(){
        startTime =System.currentTimeMillis();
    }

    public double stop(){

        return (System.currentTimeMillis()-startTime)/1000.0;
    }



}
