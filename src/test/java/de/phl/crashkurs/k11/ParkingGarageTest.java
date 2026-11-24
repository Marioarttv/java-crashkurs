package de.phl.crashkurs.k11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Kapitel 11, Aufgabe 1: ParkingGarage.enter")
class ParkingGarageTest {

    @Test
    @DisplayName("2 Autos fahren ein: 3 von 5 Plätzen bleiben frei")
    void zweiAutosFahrenEin() {
        ParkingGarage garage = new ParkingGarage(5);
        garage.enter(2);
        assertEquals(3, garage.getFreeSpots(), "Bei 5 freien Plätzen müssen nach enter(2) noch 3 frei sein. Ziehst du cars von freeSpots ab?");
    }

    @Test
    @DisplayName("genau so viele Autos wie freie Plätze: erlaubt, danach 0 frei")
    void genauSoVieleWiePlaetze() {
        ParkingGarage garage = new ParkingGarage(5);
        garage.enter(5);
        assertEquals(0, garage.getFreeSpots(), "5 Autos passen genau in 5 freie Plätze. Danach muss getFreeSpots() 0 liefern. Prüfe die Grenze: Nur mehr Autos als freie Plätze sind verboten.");
    }

    @Test
    @DisplayName("0 Autos: IllegalArgumentException mit dem richtigen Text")
    void nullAutosWirft() {
        ParkingGarage garage = new ParkingGarage(5);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> garage.enter(0),
                "enter(0) muss eine IllegalArgumentException werfen. Prüfst du am Anfang der Methode, ob cars kleiner als 1 ist?");
        assertEquals("Anzahl muss mindestens 1 sein", e.getMessage(), "Der Text der Exception stimmt noch nicht. Schreib ihn genau so wie in der Aufgabe.");
        assertEquals(5, garage.getFreeSpots(), "Nach der Exception müssen weiterhin 5 Plätze frei sein. Wirf die Exception, bevor du etwas abziehst.");
    }

    @Test
    @DisplayName("-3 Autos: IllegalArgumentException")
    void negativeAnzahlWirft() {
        ParkingGarage garage = new ParkingGarage(5);
        assertThrows(IllegalArgumentException.class, () -> garage.enter(-3),
                "enter(-3) muss eine IllegalArgumentException werfen. Eine negative Anzahl von Autos ist ungültig.");
        assertEquals(5, garage.getFreeSpots(), "Nach der Exception müssen weiterhin 5 Plätze frei sein.");
    }

    @Test
    @DisplayName("6 Autos bei 5 freien Plätzen: IllegalArgumentException mit dem richtigen Text")
    void zuVieleAutosWirft() {
        ParkingGarage garage = new ParkingGarage(5);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> garage.enter(6),
                "Bei 5 freien Plätzen muss enter(6) eine IllegalArgumentException werfen. Vergleichst du cars mit freeSpots?");
        assertEquals("Nicht genug freie Plätze", e.getMessage(), "Der Text der Exception stimmt noch nicht. Schreib ihn genau so wie in der Aufgabe.");
        assertEquals(5, garage.getFreeSpots(), "Nach der Exception müssen weiterhin 5 Plätze frei sein. Wirf die Exception, bevor du etwas abziehst.");
    }
}
