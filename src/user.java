import java.util.ArrayList;

public class user {
    String username;
    String password;
    ArrayList<ability> cap = new ArrayList<ability>();

    public user(String n, String p) {
        this.username = n;
        this.password = p;
    }

    public void setCap(ArrayList<ability> cap) {
        this.cap = cap;
    }

    public ArrayList<ability> getCap() {
        return cap;
    }
}
