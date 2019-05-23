package myPort.docks;

public class Package {

    private int id;
    private static int uniqueId = 1;

    public Package() {
        this.id = uniqueId++;
    }

    public int getId() {
        return id;
    }

}
