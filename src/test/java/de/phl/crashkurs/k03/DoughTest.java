package de.phl.crashkurs.k03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 3, Aufgabe 1: Dough.totalWeight")
class DoughTest {

    @Test
    @DisplayName("500 g Mehl, 200 g Zucker, 250 g Butter: 950 g")
    void beispiel() {
        assertEquals(950, Dough.totalWeight(500, 200, 250),
                "totalWeight(500, 200, 250) muss 950 zurückgeben. Zähl alle drei Parameter zusammen und gib die Summe mit return zurück.");
    }

    @Test
    @DisplayName("300 g Mehl, 100 g Zucker, 120 g Butter: 520 g")
    void andereWerte() {
        assertEquals(520, Dough.totalWeight(300, 100, 120),
                "totalWeight(300, 100, 120) muss 520 zurückgeben. Rechnest du mit den Parametern flour, sugar und butter oder mit festen Zahlen?");
    }

    @Test
    @DisplayName("ohne Butter: 700 g")
    void ohneButter() {
        assertEquals(700, Dough.totalWeight(500, 200, 0),
                "totalWeight(500, 200, 0) muss 700 zurückgeben.");
    }
}
