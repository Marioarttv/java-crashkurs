package de.phl.crashkurs.k09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 9, Aufgabe 3: Streaming.countVideos")
class StreamingTest {

    @Test
    @DisplayName("50 Prozent, HIGH: 2 Videos")
    void hoch() {
        assertEquals(2, (int) Pruefen.mitZeitlimit(() -> Streaming.countVideos(50, Quality.HIGH)),
                "Bei HIGH kostet ein Video 20 Prozent. Bei 50 Prozent laufen 2 Videos (50 und 30 reichen, 10 nicht mehr). Lautet deine Bedingung battery >= cost?");
    }

    @Test
    @DisplayName("50 Prozent, MEDIUM: 5 Videos")
    void mittel() {
        assertEquals(5, (int) Pruefen.mitZeitlimit(() -> Streaming.countVideos(50, Quality.MEDIUM)),
                "Bei MEDIUM kostet ein Video 10 Prozent. Bei 50 Prozent laufen 5 Videos. Gibt es ein case MEDIUM, das mit break endet?");
    }

    @Test
    @DisplayName("50 Prozent, LOW: 10 Videos")
    void niedrig() {
        assertEquals(10, (int) Pruefen.mitZeitlimit(() -> Streaming.countVideos(50, Quality.LOW)),
                "Bei LOW kostet ein Video 5 Prozent. Bei 50 Prozent laufen 10 Videos. Endet case LOW mit break? Sonst überschreiben die Fälle darunter die Kosten.");
    }

    @Test
    @DisplayName("Grenze: 20 Prozent reichen bei HIGH, 19 nicht")
    void grenze() {
        assertEquals(1, (int) Pruefen.mitZeitlimit(() -> Streaming.countVideos(20, Quality.HIGH)),
                "Bei genau 20 Prozent reicht der Akku bei HIGH noch für ein Video. Brauchst du >= statt >?");
        assertEquals(0, (int) Pruefen.mitZeitlimit(() -> Streaming.countVideos(19, Quality.HIGH)),
                "Bei 19 Prozent reicht der Akku bei HIGH für kein Video. Das Ergebnis muss 0 sein.");
    }

    @Test
    @DisplayName("Die Methode benutzt switch und while")
    void benutztSwitchUndWhile() throws IOException {
        assertTrue(Pruefen.benutzt("Streaming.java", "switch"), "In der Methode countVideos soll ein switch über quality stehen.");
        assertTrue(Pruefen.benutzt("Streaming.java", "while"), "In der Methode countVideos soll eine while-Schleife stehen.");
    }
}
