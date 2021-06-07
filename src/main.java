import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class main {

    public static void updateFile(FileWriter VFS, Directory Dir) throws IOException {
        VFS.write("<" + Dir.name + ">" + "\n");
        for (int i = 0; i < Dir.files.size(); i++) {
            if (Dir.files.get(i).deleted == false) {
                VFS.write(Dir.files.get(i).name + "\t" + Dir.files.get(i).size + "\n");
            }
        }
        for (int i = 0; i < Dir.subDirectories.size(); i++) {
            if (Dir.subDirectories.get(i).deleted == false) {
                updateFile(VFS, Dir.subDirectories.get(i));
            }
        }
        VFS.write("$\n");
    }

    public static void loadFile(Scanner sc, String path, system sys, Directory Dir) {
        String[] temp;
        while (sc.hasNext()) {
            temp = sc.nextLine().split("\t");
            if (temp[0].charAt(0) != '$') {
                if (temp[0].charAt(0) != '<') {
                    sys.createfile(path + temp[0], Integer.valueOf(temp[1]), false, "");
                } else {
                    sys.createfolder(path + temp[0].substring(1, temp[0].length() - 1), false);
                    loadFile(sc, path + temp[0].substring(1, temp[0].length() - 1) + "/", sys, Dir.subDirectories.get(Dir.subDirectories.size() - 1));
                }
            } else {
                return;
            }
        }
    }


    public static void main(String args[]) throws IOException {
        Directory rootDirectory = new Directory();
        File VFS_Read = new File("backupFile.txt");
        FileWriter VFS_Write;
        Scanner cmd = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        String input = "";
        String[] cmds;
        ArrayList<user> users = new ArrayList<user>();
        users.add(new user("admin", "admin"));
        user current = users.get(0);
        system sys = new system();
        Allocation allocation;
        System.out.println("Enter Disk's number of blocks: ");
        int N = in.nextInt();
        System.out.println("Enter Allocation method: ");
        System.out.println("1- Contiguous Allocation");
        System.out.println("2- Indexed Allocation");
        System.out.println("3- Linked Allocation");
        int choice = in.nextInt();
        String t = "";
        switch (choice) {
            case 1:
                allocation = new Contiguous();
                sys = new system(N, allocation);
                t = "Contiguous";
                break;
            case 2:
                allocation = new Indexed();
                sys = new system(N, allocation);
                t = "Indexed";
                break;
            case 3:
                allocation = new Linked();
                sys = new system(N, allocation);
                t = "Linked";
                break;

        }

        Scanner fileSC = new Scanner(VFS_Read);
        if (fileSC.hasNext()) {
            String temp = fileSC.nextLine();
            temp = temp.substring(1, temp.length() - 1);
            if (temp.equals("root")) {
                loadFile(fileSC, temp + "/", sys, sys.root);
            }
        }
        String path = "";
        while (true) {
            System.out.println("Enter Command: ");
            input = cmd.nextLine();
            cmds = input.split(" ");

            if (cmds[0].equals("TellUser")) {
                if (cmds.length == 1) {
                    System.out.println("Current User: " + current.username);
                } else {
                    System.out.println("invalid command!");
                }

            } else if (cmds[0].equals("CUser")) {


            } else if (cmds[0].equals("Grant")) {


            } else if (cmds[0].equals("Login")) {


            } else if (cmds[0].equals("CreateFile")) {
                if (cmds.length >= 3) {
                    sys.createfile(cmds[1], Integer.parseInt(cmds[2]), true, t);
                    VFS_Write = new FileWriter("backupFile.txt");
                    updateFile(VFS_Write, sys.root);
                    VFS_Write.close();
                } else
                    System.out.println("wrong inputs!");
            } else if (cmds[0].equals("CreateFolder")) {
                sys.createfolder(cmds[1], true);
                VFS_Write = new FileWriter("backupFile.txt");
                updateFile(VFS_Write, sys.root);
                VFS_Write.close();
            } else if (cmds[0].equals("DeleteFile")) {
                sys.deletefile(cmds[1]);
                VFS_Write = new FileWriter("backupFile.txt");
                updateFile(VFS_Write, sys.root);
                VFS_Write.close();
            } else if (cmds[0].equals("DeleteFolder")) {
                sys.deletefolder(cmds[1]);
                VFS_Write = new FileWriter("backupFile.txt");
                updateFile(VFS_Write, sys.root);
                VFS_Write.close();
            } else if (cmds[0].equals("DisplayDiskStatus")) {
                sys.DisplayDiskStatus();
            } else if (cmds[0].equals("DisplayDiskStructure")) {
                sys.DisplayDiskStructure();
            } else if (cmds[0].equalsIgnoreCase("exit")) {
                VFS_Write = new FileWriter("backupFile.txt");
                updateFile(VFS_Write, sys.root);
                VFS_Write.close();
                break;

            } else {
                System.out.println("Wrong Command!");
            }

        }


    }

}
