package de.phl.crashkurs.k05;

/**
 * Eine Station, an der Räder stehen. Vorgegeben: Diese Datei änderst du nicht.
 */
public class Station {

    private final String name;
    private final int capacity;
    private int bikeCount;
    private Bike lastBike;

    /**
     * Baut eine neue, leere Station.
     *
     * @param name der Name der Station
     * @param capacity wie viele Räder Platz haben
     */
    public Station(final String name, final int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    /**
     * Stellt ein Rad an die Station.
     *
     * @param bike das Rad
     */
    public void addBike(final Bike bike) {
        lastBike = bike;
        bikeCount = bikeCount + 1;
    }

    public int getBikeCount() {
        return bikeCount;
    }

    public Bike getLastBike() {
        return lastBike;
    }

    public String getInfo() {
        return "Station " + name + ": " + bikeCount + " von " + capacity + " Plätzen belegt";
    }
}
