package de.phl.crashkurs.k10;

import java.util.ArrayList;
import java.util.List;

/**
 * Kapitel 10, Aufgabe 3: der Sitzplan einer Klasse mit 20 Schülerinnen und Schülern.
 */
public class SeatingPlan {

    public static String[] names = {
            "Ali", "Bea", "Cem", "Dana", "Emil",
            "Fatma", "Gero", "Hanna", "Ivo", "Jule",
            "Kian", "Lina", "Mats", "Nele", "Ole",
            "Paula", "Quirin", "Rana", "Sami", "Tilda"
    };

    /**
     * Erzeugt genau 20 Student-Objekte und legt sie in die Liste students.
     * Für die Nummer i (von 0 bis 19) gilt:
     * Name: names[i]. Sitzplatz: i + 1. Schließfach: 100 + i * 2.
     *
     * @return die Liste mit allen 20 Objekten
     */
    public static List<Student> createStudents() {
        List<Student> students = new ArrayList<>();
        // TODO: Schreib deinen Code hier, zwischen die Zeile darüber und "return students;".
        return students;
    }
}
