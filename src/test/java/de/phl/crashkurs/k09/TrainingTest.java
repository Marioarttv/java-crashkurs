package de.phl.crashkurs.k09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 9, Aufgabe 1: Training.countRounds")
class TrainingTest {

    @Test
    @DisplayName("Konzentration 10: 4 Ballwechsel")
    void zehn() {
        assertEquals(4, (int) Pruefen.mitZeitlimit(() -> Training.countRounds(10)),
                "Bei 10 Konzentration gibt es 4 Ballwechsel: 10, 7, 4, 1 sind größer als 0, danach ist sie -2. Zählst du in jedem Durchlauf rounds um 1 hoch?");
    }

    @Test
    @DisplayName("Konzentration 9: 3 Ballwechsel")
    void neun() {
        assertEquals(3, (int) Pruefen.mitZeitlimit(() -> Training.countRounds(9)),
                "Bei 9 Konzentration gibt es 3 Ballwechsel: 9, 6, 3 sind größer als 0, danach ist sie genau 0. Lautet deine Bedingung concentration > 0?");
    }

    @Test
    @DisplayName("Konzentration 0: kein Ballwechsel")
    void keineKonzentration() {
        assertEquals(0, (int) Pruefen.mitZeitlimit(() -> Training.countRounds(0)),
                "Bei 0 Konzentration ist die Bedingung von Anfang an falsch. Der Block läuft kein einziges Mal, das Ergebnis ist 0.");
    }

    @Test
    @DisplayName("Die Methode benutzt while")
    void benutztWhile() throws IOException {
        assertTrue(Pruefen.benutzt("Training.java", "while"), "In der Methode countRounds soll eine while-Schleife stehen.");
    }
}
