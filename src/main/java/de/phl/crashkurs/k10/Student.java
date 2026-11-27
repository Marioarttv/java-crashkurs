package de.phl.crashkurs.k10;

/**
 * Eine Schülerin oder ein Schüler mit Name und Reihe im Sitzplan.
 * Diese Datei änderst du nicht.
 */
public class Student {

    private String name;
    private int row;

    public Student(String name, int row) {
        this.name = name;
        this.row = row;
    }

    public String getName() {
        return name;
    }

    public int getRow() {
        return row;
    }
}
