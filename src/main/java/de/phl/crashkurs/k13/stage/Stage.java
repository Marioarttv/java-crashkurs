package de.phl.crashkurs.k13.stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Eine Bühne mit Name und Zahl der Plätze im Programm (capacity).
 * Sie merkt sich in einer Liste, welche Bands auf ihr spielen.
 * Diese Datei ist vorgegeben. Du änderst sie nicht.
 */
public class Stage {

    private final String name;
    private final int capacity;
    private final List<Band> bands;

    /**
     * Erzeugt eine neue Bühne.
     *
     * @param name     der Name der Bühne
     * @param capacity wie viele Bands höchstens auf der Bühne spielen
     */
    public Stage(final String name, final int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.bands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Gibt die Liste aller Bands auf dieser Bühne zurück.
     *
     * @return die Liste der Bands
     */
    public List<Band> getBands() {
        return bands;
    }

    /**
     * Nimmt eine Band ins Programm der Bühne auf, wenn noch Platz ist.
     * Ist die Bühne voll, wirft die Methode eine IllegalStateException (ungültiger Zustand).
     *
     * @param band die Band, die auf der Bühne spielen soll
     */
    public void addBand(final Band band) {
        if (bands.size() >= capacity) {
            throw new IllegalStateException("Die Bühne ist voll.");
        }
        bands.add(band);
        System.out.println(band.getName() + " steht auf dem Programm von " + name + ".");
    }

    /**
     * Gibt einen Text mit Name, Plätzen und der Zahl der Bands zurück.
     *
     * @return zum Beispiel "Bühne: Seebühne, Plätze: 4, Bands: 2"
     */
    public String getInfo() {
        return "Bühne: " + name + ", Plätze: " + capacity + ", Bands: " + bands.size();
    }
}
