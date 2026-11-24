package de.phl.crashkurs.k13.jam;

/**
 * Ein Musiker mit Name, Instrument, Energie und Status.
 */
public class Musician {

    private final String name;
    private final Instrument instrument;
    private int energy;
    private Status status = Status.READY;

    public Musician(final String name, final Instrument instrument, final int energy) {
        this.name = name;
        this.instrument = instrument;
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getEnergy() {
        return energy;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Übung 3.1: Der Musiker spielt ein Solo für das Publikum.
     * Die Stärke des Solos und seine Beschreibung hängen vom Instrument ab (siehe Aufgabe).
     * Das Publikum verliert so viel Energie, wie das Solo stark ist.
     *
     * @param crowd das Publikum
     * @return die Beschreibung des Solos
     */
    public String playSolo(final Crowd crowd) {
        // TODO: Übung 3.1. Ersetze die Zeile mit return "" durch deinen Code.
        return "";
    }

    /**
     * Übung 3.2: Der Musiker verliert Energie. Danach wird der Status angepasst.
     *
     * @param amount so viel Energie verliert der Musiker
     */
    public void loseEnergy(final int amount) {
        // TODO: Übung 3.2
    }
}
