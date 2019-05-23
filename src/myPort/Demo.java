package myPort;

import myPort.docks.Crane;
import myPort.docks.Ship;

import java.util.Random;

public class Demo {

    public static void main(String[] args) throws InterruptedException {

        Port port = new Port();
        Ship.port = port;
        Crane.port = port;
        System.out.println("start");
        Ship ship1 = new Ship("Ship 1", random(1, 4));
        Ship ship2 = new Ship("Ship 2", random(1, 4));
        Ship ship3 = new Ship("Ship 3", random(1, 4));

        Crane crane1 = new Crane("Crane 1");
        Crane crane2 = new Crane("Crane 2");
        crane1.start();
        crane2.start();

        Thread.sleep(5000);

        ship1.start();
        ship2.start();
        ship3.start();

    }

    public static int random(int min, int max) {
        return new Random().nextInt(max - min +1) + min;
    }
}
