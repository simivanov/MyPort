package myPort.docks;

import myPort.Port;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ship extends Thread{

    public static Port port;
    private boolean unloading = false;
    private List<Package> packages;

    public Ship(String name, int packages) {
        setName(name);
        this.packages = new ArrayList<>();
        for (int i = 0; i < packages; i++) {
            this.packages.add(new Package());
        }
    }

    public int timeToUnload() {
        return packages.size()*2000; //2 seconds for each package
    }

    @Override
    public void run() {
        enterPort();
    }

    private void enterPort() {
        port.accept(this);
    }

    public int getNumberOfPackages() {
        return packages.size();
    }

    public List<Package> getPackages() {
        return Collections.unmodifiableList(packages);
    }

    public boolean isUnloading() {
        return unloading;
    }

    public void setUnloading(boolean unloading) {
        this.unloading = unloading;
    }
}
