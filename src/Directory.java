import java.util.ArrayList;

public class Directory {
    protected String name = "";
    protected String id = "";
    protected double size = 0.0;
    protected String directoryPath;
    protected ArrayList<Filex> files = new ArrayList<Filex>();
    protected ArrayList<Directory> subDirectories = new ArrayList<Directory>();
    protected boolean deleted = false;

    public Directory() {
    }

    public Directory(String name) {
        this.name = name;
    }

    public void setFiles(ArrayList<Filex> files) {
        this.files = files;
    }

    public void printDirectoryStructure(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print(" ");
        }
        if (!this.deleted) {
            System.out.print("<" + name + ">");
            System.out.println();
            for (Filex file : files) {
                for (int i = 0; i < level + 5; i++) {
                    System.out.print(" ");
                }
                if (!file.deleted) {
                    System.out.println(file.name);
                }
            }
            for (int i = 0; i < subDirectories.size(); i++) {
                subDirectories.get(i).printDirectoryStructure(level + 6);
            }
        } else
            System.out.print("<" + name + "> is not found!");

        System.out.println("\n");
    }

    public ArrayList<Directory> getSubDirectories() {
        return subDirectories;
    }

    public ArrayList<Filex> getFiles() {
        return files;
    }
}
