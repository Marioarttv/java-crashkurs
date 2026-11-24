package de.phl.crashkurs.k13.jam;

/**
 * Das Publikum vor einer Bühne, mit Name, Energie, Lautstärke (volume) und Status.
 */
public class Crowd {

    private final String name;
    private int energy;
    private final int volume;
    private Status status = Status.READY;

    public Crowd(final String name, final int energy, final int volume) {
        this.name = name;
        this.energy = energy;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public int getVolume() {
        return volume;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Das Publikum jubelt und fordert eine Zugabe. Das kostet den Musiker so viel Energie, wie das Publikum laut ist.
     * Diese Methode ist vorgegeben. Du änderst sie nicht.
     *
     * @param musician der Musiker, dem das Publikum zujubelt
     * @return eine Beschreibung des Jubels
     */
    public String cheer(final Musician musician) {
        musician.loseEnergy(volume);
        return name + " jubelt, " + musician.getName() + " verliert " + volume + " Energie.";
    }

    /**
     * Übung 3.2: Das Publikum verliert Energie. Danach wird der Status angepasst.
     *
     * @param amount so viel Energie verliert das Publikum
     */
    public void loseEnergy(final int amount) {
        // TODO: Übung 3.2
    }
}
