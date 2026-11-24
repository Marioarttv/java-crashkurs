package de.phl.crashkurs.k01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 1, Aufgabe 2: OpeningHours.main")
class OpeningHoursTest {

    private static final List<String> ERWARTET = List.of(
            "Zeiten der Mensa",
            "Montag bis Donnerstag: 11 bis 14 Uhr",
            "Freitag: 11 bis 13 Uhr",
            "Samstag und Sonntag: geschlossen");

    @Test
    @DisplayName("alle vier Zeilen sind noch da")
    void vierZeilen() {
        List<String> ist = MensaBoardTest.zeilen(() -> OpeningHours.main(new String[0]));
        for (String zeile : ERWARTET) {
            assertTrue(ist.contains(zeile), "Die Zeile \"" + zeile + "\" fehlt in der Ausgabe. Verschiebe die Zeilen nur, ändere ihren Text nicht.");
        }
        assertEquals(4, ist.size(), "Es sollen genau vier Zeilen ausgegeben werden, nicht " + ist.size() + ".");
    }

    @Test
    @DisplayName("die Zeilen stehen in der richtigen Reihenfolge")
    void reihenfolge() {
        assertEquals(ERWARTET, MensaBoardTest.zeilen(() -> OpeningHours.main(new String[0])),
                "Die Reihenfolge stimmt noch nicht. Oben steht die Überschrift \"Zeiten der Mensa\", "
                        + "dann die Tage von Montag bis zum Wochenende.");
    }
}
