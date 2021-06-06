public class allocated {
    public int start, end, length;
    boolean status;

    public allocated(int start, int end, boolean status) {
        this.start = start;
        this.end = end;
        this.status = status;
        length = end - start;
    }
}
