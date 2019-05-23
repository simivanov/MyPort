package myPort.docks;

import myPort.Port;
import myPort.ShipNotFoundExeption;
import myPort.storage.Storage;

public class Crane extends Thread{

    public static Port port;

    public Crane(String name) {
        setName(name);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " - Crane checks for ships");
            if(port.hasShipToUnload()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " - Ship found, unloading!");
                    Ship s = port.unloadShip();
                    System.out.println(Thread.currentThread().getName() + " - Ship unloaded " + s.getNumberOfPackages() + " packages successfully");
                    Storage storage = port.storePackages(s.getPackages());
                    System.out.println(Thread.currentThread().getName() + " - " + s.getNumberOfPackages() + " packages stored into store " + storage.getName());
                }
                catch (ShipNotFoundExeption e) {
                    System.out.println(e.getMessage());
                }
                //put packages to storage
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
