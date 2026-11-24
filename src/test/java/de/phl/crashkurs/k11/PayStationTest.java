package de.phl.crashkurs.k11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 11, Aufgabe 2: PayStation.priceText")
class PayStationTest {

    @Test
    @DisplayName("3 Stunden: Gebühr 6 Euro")
    void dreiStunden() {
        assertEquals("Gebühr: 6 Euro", PayStation.priceText(3), "Für 3 Stunden gibt fee 6 zurück. priceText(3) muss genau \"Gebühr: 6 Euro\" zurückgeben.");
    }

    @Test
    @DisplayName("1 Stunde: Gebühr 2 Euro")
    void eineStunde() {
        assertEquals("Gebühr: 2 Euro", PayStation.priceText(1), "Für 1 Stunde gibt fee 2 zurück. priceText(1) muss genau \"Gebühr: 2 Euro\" zurückgeben.");
    }

    @Test
    @DisplayName("0 Stunden: kein Absturz, sondern der Fehlertext")
    void nullStundenStuerztNichtAb() {
        String text = assertDoesNotThrow(() -> PayStation.priceText(0),
                "priceText(0) darf nicht abbrechen. Steht der Aufruf von ParkingGarage.fee in einem try-Block mit passendem catch?");
        assertEquals("Fehler: Parkdauer muss mindestens 1 Stunde sein", text,
                "Bei 0 Stunden muss \"Fehler: \" und der Text der Exception zurückkommen. Den Text holst du mit e.getMessage().");
    }

    @Test
    @DisplayName("-2 Stunden: kein Absturz, sondern der Fehlertext")
    void negativeStundenStuerztNichtAb() {
        String text = assertDoesNotThrow(() -> PayStation.priceText(-2),
                "priceText(-2) darf nicht abbrechen. Fang die IllegalArgumentException mit catch ab.");
        assertEquals("Fehler: Parkdauer muss mindestens 1 Stunde sein", text,
                "Bei -2 Stunden muss \"Fehler: Parkdauer muss mindestens 1 Stunde sein\" zurückkommen.");
    }
}
