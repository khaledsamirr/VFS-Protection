import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public interface Allocation {
    public boolean createFile(Directory dir, String name, int filesize, ArrayList<allocated> periods, ArrayList<Boolean> status, int totalSpace);

    public boolean createDirectory(Directory dir, String name);

    public int deleteFile(Directory dir, String name, ArrayList<allocated> periods, ArrayList<Boolean> status);

    public int deleteDirectory(Directory dir, ArrayList<allocated> periods, ArrayList<Boolean> status);
    //  public void write(system sys,String filePath);
    //  public void readTree(system sys, ObjectInputStream os, int currentSize,int n) throws ClassNotFoundException, IOException;
}
