package de.phl.crashkurs.k10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 10, Aufgabe 1: Seats.printSeats")
class SeatsTest {

    @Test
    @DisplayName("3 Plätze: Platz 1 bis Platz 3")
    void dreiPlaetze() {
        String aus = Pruefen.ausgabe(() -> Seats.printSeats(3));
        assertEquals("Platz 1\nPlatz 2\nPlatz 3\n", aus,
                "Bei printSeats(3) müssen genau drei Zeilen kommen: Platz 1, Platz 2, Platz 3. Zählt deine Schleife mit i < count genau count Mal? Gibst du i + 1 aus, damit die Plätze bei 1 beginnen?");
    }

    @Test
    @DisplayName("20 Plätze: erste und letzte Zeile")
    void zwanzigPlaetze() {
        String[] zeilen = Pruefen.ausgabe(() -> Seats.printSeats(20)).split("\n");
        assertEquals(20, zeilen.length, "Bei printSeats(20) müssen genau 20 Zeilen kommen. Prüfe die Bedingung im Kopf der Schleife: i < count.");
        assertEquals("Platz 1", zeilen[0], "Die erste Zeile muss \"Platz 1\" heißen.");
        assertEquals("Platz 20", zeilen[19], "Die letzte Zeile muss \"Platz 20\" heißen.");
    }

    @Test
    @DisplayName("0 Plätze: keine Ausgabe")
    void keinePlaetze() {
        assertEquals("", Pruefen.ausgabe(() -> Seats.printSeats(0)), "Bei printSeats(0) darf nichts ausgegeben werden.");
    }

    @Test
    @DisplayName("Die Methode benutzt for")
    void benutztFor() throws IOException {
        assertTrue(Pruefen.benutzt("Seats.java", "for"), "In der Methode printSeats soll eine for-Schleife stehen.");
    }
}
