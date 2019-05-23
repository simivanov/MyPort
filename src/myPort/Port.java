package myPort;

import myPort.docks.Package;
import myPort.docks.Ship;
import myPort.docks.ShipmentLog;
import myPort.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.*;

public class Port {

    public static final int NUMBER_OF_DOCKS = 5;
    public static final int NUMBER_OF_STORAGES = 2;
    public static final int STATISTICS_INTERVAL = 5000;
    private List<Queue<Ship>> docks;
    private List<Storage> storages;
    private Set<ShipmentLog> logs = new HashSet<>();


    Port() {
        this.docks = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DOCKS; i++) {
            docks.add(new LinkedList());
        }
        this.storages = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_STORAGES; i++) {
            storages.add(new Storage("store " + i+1));
        }
        Thread logger = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(STATISTICS_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printStatistics();
            }
        });
        logger.setDaemon(true);
        logger.start();
    }

    public void accept(Ship s) {
        int dockNumber = Demo.random(0, docks.size()-1);
        Queue dock = docks.get(dockNumber);
        synchronized (dock) {
            System.out.println("Ship " + s.getName() + " entered dock " + dockNumber);
            dock.add(s);
        }
    }

    public boolean hasShipToUnload() {
        for(Queue dock : docks) {
            if(dock.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public Ship unloadShip() throws ShipNotFoundExeption{
        for (int i = 0; i < docks.size(); i++) {
            Queue<Ship> dock = docks.get(i);
            synchronized (dock) {
                if (dock.size() > 0 && !dock.peek().isUnloading()) {
                    Ship s = dock.peek();
                    s.setUnloading(true);
                    try {
                        Thread.sleep(s.timeToUnload());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dock.remove(s);
                    logs.add(new ShipmentLog(s.getName(), i, Thread.currentThread().getName(), LocalDateTime.now(), s.getPackages()));
                    return s;
                }
            }
        }
        throw new ShipNotFoundExeption("Ship not found - this should never happend");
    }

    public Storage storePackages(List<Package> packages) {
        int storageNumber = Demo.random(0, storages.size()-1);
        Storage storage = storages.get(storageNumber);
        for(Package p : packages) {
            try {
                storage.put(p);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return storage;
    }

    public void printStatistics() {
        System.out.println("------------- STATS -------------");
        for(ShipmentLog s: logs) {
            System.out.println(s);
        }
        System.out.println("-----------END OF STATS----------");

        File file = new File("report.txt");
        try(PrintStream ps = new PrintStream(file)) {
            for(ShipmentLog s : logs) {
                ps.print(s);
            }
        }
        catch (IOException e) {
            System.out.println("problems writing into file");
        }
    }
}
