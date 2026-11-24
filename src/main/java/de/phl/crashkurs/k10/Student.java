package de.phl.crashkurs.k10;

/**
 * Eine Schülerin oder ein Schüler mit Sitzplatz und Schließfach.
 * Diese Datei änderst du nicht.
 */
public class Student {

    private String name;
    private int seat;
    private int locker;

    public Student(String name, int seat, int locker) {
        this.name = name;
        this.seat = seat;
        this.locker = locker;
    }

    public String getName() {
        return name;
    }

    public int getSeat() {
        return seat;
    }

    public int getLocker() {
        return locker;
    }
}
