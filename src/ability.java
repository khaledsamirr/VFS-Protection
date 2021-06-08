public class ability {
    String path;
    int code;

    public ability(String a, int c) {
        path = a;
        code = c;
    }

    public int getCode() {
        return code;
    }

    public String getFolder() {
        return path;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setFolder(String folder) {
        this.path = folder;
    }
}
