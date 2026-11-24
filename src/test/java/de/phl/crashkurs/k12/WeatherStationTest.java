package de.phl.crashkurs.k12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 12, Aufgabe 2: WeatherStation")
class WeatherStationTest {

    @Test
    @DisplayName("Höchstwert im Sommer")
    void hoechstwertSommer() {
        assertEquals(23, WeatherStation.highest(new int[] {18, 23, 21}), "Bei 18, 23 und 21 Grad ist der Höchstwert 23.");
    }

    @Test
    @DisplayName("Höchstwert im Winter, alle Werte unter 0")
    void hoechstwertWinter() {
        assertEquals(-1, WeatherStation.highest(new int[] {-3, -7, -1}), "Bei -3, -7 und -1 Grad ist der Höchstwert -1. Mit welchem Wert startet max?");
    }

    @Test
    @DisplayName("Frosttage zählen")
    void frosttage() {
        assertEquals(2, WeatherStation.countFrostDays(new int[] {3, 0, -2, 5}), "Bei 3, 0, -2 und 5 Grad gibt es 2 Frosttage (0 und -2).");
    }
}
