package de.phl.crashkurs.k10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 10, Aufgabe 2: Results.countPassed")
class ResultsTest {

    @Test
    @DisplayName("62, 49, 50, 80: drei bestanden")
    void dreiBestanden() {
        int[] points = {62, 49, 50, 80};
        assertEquals(3, (int) Pruefen.mitZeitlimit(() -> Results.countPassed(points)),
                "Bei 62, 49, 50 und 80 Punkten haben 3 bestanden (50 zählt mit). Gehst du mit points[i] durch alle Fächer und zählst nur bei points[i] >= 50?");
    }

    @Test
    @DisplayName("Das erste und das letzte Fach zählen mit")
    void ersteUndLetzte() {
        int[] points = {90, 10, 10, 75};
        assertEquals(2, (int) Pruefen.mitZeitlimit(() -> Results.countPassed(points)),
                "Bei 90, 10, 10 und 75 haben 2 bestanden: das erste und das letzte Fach. Beginnt deine Schleife bei 0? Läuft sie bis i < points.length?");
    }

    @Test
    @DisplayName("Niemand bestanden: 0")
    void niemand() {
        int[] points = {12, 49};
        assertEquals(0, (int) Pruefen.mitZeitlimit(() -> Results.countPassed(points)), "Bei 12 und 49 Punkten hat niemand bestanden. Das Ergebnis muss 0 sein.");
    }

    @Test
    @DisplayName("Andere Länge: 6 Punktzahlen")
    void andereLaenge() {
        int[] points = {50, 50, 50, 50, 50, 49};
        assertEquals(5, (int) Pruefen.mitZeitlimit(() -> Results.countPassed(points)),
                "Bei sechs Punktzahlen, von denen fünf genau 50 sind, haben 5 bestanden. Benutzt du points.length statt einer festen Zahl?");
    }

    @Test
    @DisplayName("Die Methode benutzt for")
    void benutztFor() throws IOException {
        assertTrue(Pruefen.benutzt("Results.java", "for"), "In der Methode countPassed soll eine for-Schleife stehen.");
    }
}
