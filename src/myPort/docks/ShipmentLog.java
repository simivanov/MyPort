package myPort.docks;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ShipmentLog {

    private String shipName;
    private int dockId;
    private String craneName;
    private LocalDateTime time;
    private List<Package> packages;

    public ShipmentLog(String shipName, int dockId, String craneName, LocalDateTime time, List<Package> packages) {
        this.shipName = shipName;
        this.dockId = dockId;
        this.craneName = craneName;
        this.time = time;
        this.packages = packages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipmentLog that = (ShipmentLog) o;
        return dockId == that.dockId &&
                Objects.equals(shipName, that.shipName) &&
                Objects.equals(craneName, that.craneName) &&
                Objects.equals(time, that.time) &&
                Objects.equals(packages, that.packages);
    }

    @Override
    public int hashCode() {

        return Objects.hash(shipName, dockId, craneName, time, packages);
    }

    @Override
    public String toString() {
        return "ShipmentLog{" +
                "shipName='" + shipName + '\'' +
                ", dockId=" + dockId +
                ", craneName='" + craneName + '\'' +
                ", time=" + time +
                ", packages=" + packages.size() +
                '}';
    }
}
