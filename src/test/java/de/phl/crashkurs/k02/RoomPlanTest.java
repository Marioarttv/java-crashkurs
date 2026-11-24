package de.phl.crashkurs.k02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 2, Aufgabe 2: RoomPlan.main")
class RoomPlanTest {

    @Test
    @DisplayName("die vorgegebenen Zeilen sind unverändert")
    void vorgabe() {
        String text = Quelltext.lies("RoomPlan.java");
        Quelltext.enthaelt(text, "\\bint\\s+students\\s*=\\s*26\\s*;", "Die vorgegebene Zeile int students = 26; fehlt oder wurde geändert. Lass sie stehen.");
        Quelltext.enthaelt(text, "\\bint\\s+bedsPerRoom\\s*=\\s*4\\s*;", "Die vorgegebene Zeile int bedsPerRoom = 4; fehlt oder wurde geändert. Lass sie stehen.");
    }

    @Test
    @DisplayName("students wird um 3 erhöht, ohne neue Variable")
    void nachmeldung() {
        Quelltext.enthaelt(Quelltext.lies("RoomPlan.java"), "(?<![\\w.])students\\s*(=\\s*students\\s*\\+\\s*3|\\+=\\s*3)\\s*;",
                "Erhöhe students um 3 mit students = students + 3; (ohne int davor, denn die Variable gibt es schon).");
    }

    @Test
    @DisplayName("fullRooms und leftOver werden aus den Variablen berechnet")
    void rechnung() {
        String text = Quelltext.lies("RoomPlan.java");
        Quelltext.enthaelt(text, "\\bint\\s+fullRooms\\s*=\\s*students\\s*/\\s*bedsPerRoom\\s*;",
                "Es fehlt die Zeile int fullRooms = students / bedsPerRoom;");
        Quelltext.enthaelt(text, "\\bint\\s+leftOver\\s*=\\s*students\\s*%\\s*bedsPerRoom\\s*;",
                "Es fehlt die Zeile int leftOver = students % bedsPerRoom;");
    }

    @Test
    @DisplayName("gibt Personen, volle Zimmer und Rest aus")
    void ausgabe() {
        assertEquals(List.of("Personen: 29", "Volle Zimmer: 7", "Rest: 1"), Quelltext.zeilen(() -> RoomPlan.main(new String[0])),
                "Erwartet sind genau drei Zeilen: Personen: 29, Volle Zimmer: 7, Rest: 1. "
                        + "Hast du students vor der Rechnung um 3 erhöht? 29 / 4 ist 7, Rest 1.");
    }
}
