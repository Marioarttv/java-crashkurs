package de.phl.crashkurs.k09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 9, Aufgabe 1: Charger.quarterHours")
class ChargerTest {

    @Test
    @DisplayName("Akku 40: 3 Viertelstunden")
    void vierzig() {
        assertEquals(3, (int) Pruefen.mitZeitlimit(() -> Charger.quarterHours(40)),
                "Bei 40 Prozent sind es 3 Viertelstunden: 40, 60 und 80 sind unter 100, danach sind es genau 100. Zählst du in jedem Durchlauf um 1 hoch?");
    }

    @Test
    @DisplayName("Akku 30: 4 Viertelstunden")
    void dreissig() {
        assertEquals(4, (int) Pruefen.mitZeitlimit(() -> Charger.quarterHours(30)),
                "Bei 30 Prozent sind es 4 Viertelstunden: 30, 50, 70 und 90 sind unter 100. Lautet deine Bedingung battery < 100?");
    }

    @Test
    @DisplayName("Akku 95: 1 Viertelstunde")
    void fastVoll() {
        assertEquals(1, (int) Pruefen.mitZeitlimit(() -> Charger.quarterHours(95)),
                "Bei 95 Prozent fehlt nur noch wenig. Eine Viertelstunde reicht.");
    }

    @Test
    @DisplayName("Akku 100: keine Viertelstunde")
    void voll() {
        assertEquals(0, (int) Pruefen.mitZeitlimit(() -> Charger.quarterHours(100)),
                "Bei 100 Prozent ist die Bedingung von Anfang an falsch. Der Block läuft kein einziges Mal, das Ergebnis ist 0.");
    }

    @Test
    @DisplayName("Die Methode benutzt while")
    void benutztWhile() throws IOException {
        assertTrue(Pruefen.benutzt("Charger.java", "while"), "In der Methode quarterHours soll eine while-Schleife stehen.");
    }
}
