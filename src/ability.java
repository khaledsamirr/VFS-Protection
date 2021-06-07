public class ability {
    String folder;
    int code;

    public ability(String a, int c) {
        folder = a;
        code = c;
    }

    public int getCode() {
        return code;
    }

    public String getFolder() {
        return folder;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
