package myPort.storage;

import myPort.docks.Package;

import java.util.concurrent.ArrayBlockingQueue;

public class Storage extends ArrayBlockingQueue<Package> {

    private String name;
    public static final int CAPACITY = 100;

    private class Distributor extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Package p = Storage.this.take();
                    System.out.println("Dumping package " + p.getId() + " from sdtorage " + Storage.this.name);
                    Thread.sleep(5000);
                    System.out.println("Package dumped" + p.getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Storage(String name) {
        super(CAPACITY);
        this.name = name;
        new Distributor().start();
    }

    public String getName() {
        return name;
    }
}
