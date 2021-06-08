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
        root = new Directory("root");
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


    public Directory getDirectory(Directory search, String[] parts, int index, String type) {

        if (type.equalsIgnoreCase("short")) {
            if (index + 1 == parts.length - 1) {
                return search;
            }
        } else if (type.equalsIgnoreCase("full")) {
            if (index + 1 == parts.length) {
                return search;
            }
        }
        for (int i = 0; i < search.subDirectories.size(); i++) {
            if (search.subDirectories.get(i).name.equals(parts[index + 1])) {
                if (index + 1 == parts.length) {
                    return search.subDirectories.get(i);
                } else {
                    return getDirectory(search.subDirectories.get(i), parts, index + 1, type);
                }
            }
        }
        return null;
    }


    public void createfile(String path, int n, Boolean mark, String type) {
        boolean flag = false;
        String parts[] = path.split("/");
        if (n > this.N - this.totalspace)
            flag = false;
        Directory found;
        found = getDirectory(root, parts, 0, "short");
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

    public void createfolder(String path, Boolean mark, user c) {
        String[] parts = path.split("/");
        Directory found, founded;
        found = getDirectory(root, parts, 0, "short");
        int ability = -1;
        for (int i = 0; i < c.getCap().size(); i++) {
            if (path.contains(c.getCap().get(i).path)) {
                ability = c.getCap().get(i).code;
                break;
            }
        }
        boolean flag = false;
        if (ability != -1) {
            if (ability == 10 || ability == 11)
                flag = true;
        } else {
            if (c.username.equals("admin"))
                flag = true;
        }
        if (flag) {
            if (found != null) {
                if (allocation.createDirectory(found, parts[parts.length - 1])) {
                    if (mark == true) {
                        System.out.println("Folder is created successfully!");
                    }
                } else
                    System.out.println("Can't create folder!");
            }
        } else {
            System.out.println("Not available for this user to create folder here");
        }
    }

    public void deletefile(String path) {
        String[] parts = path.split("/");
        Directory found;
        boolean flag = false;
        found = getDirectory(root, parts, 0, "short");
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

    public void deletefolder(String path, user c) {
        String[] parts = path.split("/");
        Directory found;
        boolean flag2 = false;
        found = getDirectory(root, parts, 0, "full");
        int ability = -1;
        for (int i = 0; i < c.getCap().size(); i++) {
            if (path.contains(c.getCap().get(i).path)) {
                ability = c.getCap().get(i).code;
                break;
            }
        }
        boolean flag = false;
        if (ability != -1) {
            if (ability == 01 || ability == 11)
                flag = true;
        } else {
            if (c.username.equals("admin"))
                flag = true;
        }
        if (flag) {
            if (found != null && found != root) {
                int deallocatedSize = allocation.deleteDirectory(found, periods, status);
                if (deallocatedSize != 0) {
                    this.totalspace -= deallocatedSize;

                }
                flag2 = true;
            }
            if (flag2)
                System.out.println("Folder is deleted successfully!");
            else
                System.out.println("Can't delete folder!");
        } else {
            System.out.println("Not available for this user to delete folder here");
        }
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
