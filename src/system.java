import java.time.Period;
import java.util.ArrayList;

public class system {
    int totalspace = 0;
    int N = 0;
    Directory root;
    Allocation allocation;
    ArrayList<allocated> periods;
    ArrayList<Boolean> status = new ArrayList<Boolean>();

    public system() {
    }

    public system(int n, Allocation a) {
        this.N = n;
        this.allocation = a;
        root = new Directory("root");
        periods = new ArrayList<allocated>();
        for (int i = 0; i < n; i++) {
            status.add(false);
        }
        periods.add(new allocated(0, n - 1, false));
    }

    public Directory getDirectory(Directory search, String path, int level) {
        String[] parts = path.split("/");
        if (parts.length == 1)
            return search;

        for (Directory s : search.subDirectories) {
            if (parts[level + 1].equals(s.name) && level != parts.length - 2) {
                return getDirectory(s, path, level + 1);
            }
        }
        if (parts[level].equals(search.name) && level == parts.length - 2) {
            return search;
        }

        return null;
    }

    public void createfile(String path, int n, Boolean mark, String type) {
        boolean flag = false;
        String parts[] = path.split("/");
        if (n > this.N - this.totalspace)
            flag = false;
        Directory found;
        found = getDirectory(root, path, 0);
        if (found != null) {
            if (allocation.createFile(found, parts[parts.length - 1], n, periods, status, this.N - totalspace)) {
                this.totalspace += n;
                if (type.equals("Indexed"))
                    this.totalspace++;
                flag = true;
            }
        }
        if (flag && found != null) {
            if (mark == true) {
                System.out.println("File is created successfully!");
            }
        } else
            System.out.println("Can't create file!");
    }

    public void createfolder(String path, Boolean mark) {
        String[] parts = path.split("/");
        Directory found;
        found = getDirectory(root, path, 0);
        if (found != null)
            if (allocation.createDirectory(found, parts[parts.length - 1])) {
                if (mark == true) {
                    System.out.println("Folder is created successfully!");
                }
            } else
                System.out.println("Can't create folder!");
    }

    public void deletefile(String path) {
        String[] parts = path.split("/");
        Directory found;
        boolean flag = false;
        found = getDirectory(root, path, 0);
        if (found != null) {
            int deallocatedSize = allocation.deleteFile(found, parts[parts.length - 1], periods, status);
            System.out.println("Deallocated space: " + deallocatedSize);
            if (deallocatedSize != 0) {
                this.totalspace -= deallocatedSize;
                flag = true;
            }
            if (flag)
                System.out.println("File is deleted successfully!");
            else
                System.out.println("Can't delete file!");
        }
    }

    public void deletefolder(String path) {
        String[] parts = path.split("/");
        boolean flag = false;
        Directory found;
        found = getDirectory(root, path, 0);
        if (found != null) {
            int deallocatedSize = allocation.deleteDirectory(found, periods, status);
            if (deallocatedSize != 0) {
                this.totalspace -= deallocatedSize;
                flag = true;
            }
        }
        if (flag)
            System.out.println("Folder is deleted successfully!");
        else
            System.out.println("Can't delete folder!");
    }

    public void DisplayDiskStatus() {
        System.out.println("Empty space: " + (N - totalspace) + " KB");
        System.out.println("Allocated space:" + (totalspace) + " KB");
        System.out.println("Blocks status in the Disk:");
        for (int i = 0; i < status.size(); i++) {
            System.out.println("Blocks:[" + i + "] is " + (status.get(i) ? "Allocated" : "Empty"));
        }
    }

    public void DisplayDiskStructure() {
        root.printDirectoryStructure(0);
    }
}
