import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class compartor implements Comparator<allocated> {

    @Override
    public int compare(allocated o1, allocated o2) {
        if (o1.length > o2.length)
            return 1;
        if (o1.length < o2.length)
            return -1;
        return 0;
    }
}

public class Contiguous implements Allocation {
    @Override
    public boolean createFile(Directory dir, String name, int filesize, ArrayList<allocated> periods, ArrayList<Boolean> status, int totalSpace) {
        for (allocated period : periods) {
            if (filesize <= period.length && !period.status) {
                ArrayList<node> allocatedBlocks = new ArrayList<>();
                allocatedBlocks.add(new node(period.start));
                allocatedBlocks.add(new node(period.start + filesize - 1));
                Filex file = new Filex(name, filesize, allocatedBlocks);
                dir.files.add(file);
                for (int i = period.start; i < period.start + filesize; i++) {
                    status.set(i, true);
                }
                if (filesize < period.length) {
                    allocated temp = new allocated(period.start + filesize, period.end, false);
                    periods.add(temp);
                    period.end = period.start + filesize - 1;
                }
                period.status = true;
                Collections.sort(periods, new compartor());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean createDirectory(Directory dir, String name) {
        dir.subDirectories.add(new Directory(name));
        return true;
    }

    @Override
    public int deleteFile(Directory dir, String name, ArrayList<allocated> periods, ArrayList<Boolean> status) {
        for (Filex file : dir.files) {
            if (file.name.equals(name)) {
                for (allocated period : periods) {
                    if (period.start == file.allocatedBlocks.get(0).block) {
                        period.status = false;
                        file.deleted = true;
                        for (int i = period.start; i <= period.end; i++) {
                            status.set(i, false);
                        }
                        return (period.end - period.start) + 1;
                    }
                }
                return 0;
            }
        }
        return 0;
    }


    @Override
    public int deleteDirectory(Directory dir, ArrayList<allocated> periods, ArrayList<Boolean> status) {
        int space = 0;
        for (Filex file : dir.files) {
            for (allocated period : periods) {
                if (period.start == file.allocatedBlocks.get(0).block) {
                    period.status = false;
                    file.deleted = true;
                    for (int i = period.start; i < period.end; i++) {
                        status.set(i, false);
                    }
                    space += period.length;
                }
            }
        }
        for (Directory directory : dir.subDirectories) {
            space += deleteDirectory(directory, periods, status);
        }
        dir.deleted = true;
        return space;
    }


    //  @Override
    //   public void write(system sys, String filePath) {
    //}

    //   @Override
    // public void readTree(system sys, ObjectInputStream os, int currentSize, int n) throws ClassNotFoundException, IOException {

    //}
}
