package de.phl.crashkurs.k13;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Kapitel 13, Aufgabe 2: Campsite.price")
class CampsiteTest {

    @Test
    @DisplayName("Zelt, 2 Nächte: 12 Euro")
    void zelt() {
        assertEquals(12, Campsite.price(Pitch.TENT, 2), "Ein Zelt kostet 6 Euro pro Nacht. Für 2 Nächte sind das 12 Euro.");
    }

    @Test
    @DisplayName("Auto, 3 Nächte: 30 Euro")
    void auto() {
        assertEquals(30, Campsite.price(Pitch.CAR, 3), "Ein Auto mit Zelt kostet 10 Euro pro Nacht. Für 3 Nächte sind das 30 Euro. Hat jeder case sein break?");
    }

    @Test
    @DisplayName("Wohnmobil, 1 Nacht: 18 Euro")
    void wohnmobil() {
        assertEquals(18, Campsite.price(Pitch.VAN, 1), "Ein Wohnmobil kostet 18 Euro pro Nacht. Für 1 Nacht sind das 18 Euro.");
    }

    @Test
    @DisplayName("4 Nächte sind noch erlaubt")
    void vierNaechte() {
        assertEquals(72, Campsite.price(Pitch.VAN, 4), "4 Nächte sind erlaubt. Ein Wohnmobil kostet dann 4 mal 18, also 72 Euro. Prüf die Grenze: Nur mehr als 4 Nächte sind verboten.");
    }

    @Test
    @DisplayName("0 Nächte: IllegalArgumentException mit dem richtigen Text")
    void nullNaechte() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Campsite.price(Pitch.TENT, 0),
                "price(TENT, 0) muss eine IllegalArgumentException werfen. Prüfst du am Anfang der Methode, ob nights kleiner als 1 ist?");
        assertEquals("Erlaubt sind 1 bis 4 Nächte", e.getMessage(), "Der Text der Exception stimmt noch nicht. Schreib ihn genau so wie in der Aufgabe.");
    }

    @Test
    @DisplayName("5 Nächte: IllegalArgumentException")
    void fuenfNaechte() {
        assertThrows(IllegalArgumentException.class, () -> Campsite.price(Pitch.CAR, 5),
                "price(CAR, 5) muss eine IllegalArgumentException werfen. Mehr als 4 Nächte sind nicht erlaubt.");
    }
}
