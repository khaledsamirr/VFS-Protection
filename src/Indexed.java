import java.util.ArrayList;

public class Indexed implements Allocation {
    @Override
    public boolean createFile(Directory dir, String name, int filesize, ArrayList<allocated> periods, ArrayList<Boolean> status, int totalSpace) {
        if (filesize + 1 <= totalSpace) {
            ArrayList<node> allocatedBlocks = new ArrayList<>();
            int idx = 0;
            int orgFile = filesize;
            for (int i = 0; i < status.size(); i++) {
                if (!status.get(i)) {
                    status.set(i, true);
                    filesize--;
                    allocatedBlocks.add(new node(i));
                    idx = i;
                }

                if (filesize == 0) {
                    Filex file = new Filex(name, orgFile, allocatedBlocks);
                    dir.files.add(file);
                    if (!status.get(idx + 1)) {
                        allocatedBlocks.add(new node(idx + 1));
                        status.set(idx + 1, true);
                    }
                    return true;
                }
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
                for (int i = 0; i < file.allocatedBlocks.size(); i++) {
                    status.set(file.allocatedBlocks.get(i).block, false);
                }
                file.deleted = true;
                return file.allocatedBlocks.size() - 1;
            }
        }
        return 0;
    }

    @Override
    public int deleteDirectory(Directory dir, ArrayList<allocated> periods, ArrayList<Boolean> status) {
        int space = 0;
        for (Filex file : dir.files) {
            for (int i = 0; i < file.allocatedBlocks.size(); i++) {
                status.set(file.allocatedBlocks.get(i).block, false);
            }
            file.deleted = true;
            space += file.allocatedBlocks.size();
        }
        for (Directory directory : dir.subDirectories) {
            space += deleteDirectory(directory, periods, status);
        }
        dir.deleted = true;
        return space;
    }
}
