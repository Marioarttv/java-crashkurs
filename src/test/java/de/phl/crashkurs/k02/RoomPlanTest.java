package de.phl.crashkurs.k02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 2, Aufgabe 2: RoomPlan.main")
class RoomPlanTest {

    static final String ZIEL = "29 Personen: 7 volle Zimmer, 1 Person übrig";

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
    @DisplayName("fullRooms und leftOver werden mit den Variablen ausgerechnet, nicht mit Zahlen")
    void rechnung() {
        String text = Quelltext.lies("RoomPlan.java");
        for (String name : new String[] {"fullRooms", "leftOver"}) {
            Matcher m = Pattern.compile("(?<![\\w.])" + name + "\\s*=(?!=)([^;]*);").matcher(text);
            boolean gefunden = false;
            while (m.find()) {
                gefunden = true;
                if (m.group(1).strip().equals("0")) {
                    continue; // Startwert 0 ist erlaubt, wenn die Rechnung später folgt.
                }
                assertFalse(m.group(1).matches("(?s).*\\d.*"),
                        "Rechts von " + name + " = steht eine Zahl: " + m.group(1).strip()
                                + ". Rechne mit den Namen der Variablen (students, bedsPerRoom), nicht mit Zahlen.");
            }
            assertTrue(gefunden, "Speichere das Ergebnis in einer Variable mit dem Namen " + name + ".");
        }
    }

    @Test
    @DisplayName("gibt 29 Personen: 7 volle Zimmer, 1 Person übrig aus")
    void ausgabe() {
        List<String> zeilen = Quelltext.zeilen(() -> RoomPlan.main(new String[0]));
        String text = String.join("\n", zeilen);
        String hinweis;
        if (text.contains("26 Personen") || text.contains("6 volle Zimmer, 2")) {
            hinweis = "Java hat noch mit 26 Personen gerechnet. Erhöhe students um 3, bevor du fullRooms und leftOver ausrechnest.";
        } else {
            hinweis = "29 / 4 ist 7, Rest 1. Vergleiche jeden Text und jedes Leerzeichen mit der Zeile oben.";
        }
        assertEquals(List.of(ZIEL), zeilen, "Erwartet ist genau eine Zeile: " + ZIEL + ". " + hinweis);
    }

    @Test
    @DisplayName("die Ausgabe benutzt die Variablen students, fullRooms und leftOver")
    void variablenInDerAusgabe() {
        String text = Quelltext.lies("RoomPlan.java");
        assertFalse(text.contains("\"29") || text.contains("29 Personen") || text.contains(" 7 volle"),
                "Im Text der Ausgabe steht ein fertiges Ergebnis. Lass Java rechnen und setze die Variablen in die Zeile ein.");
        for (String name : new String[] {"students", "fullRooms", "leftOver"}) {
            Quelltext.enthaelt(text, "println\\s*\\([^;]*\\b" + name + "\\b",
                    "In der Ausgabe fehlt die Variable " + name + ". Die Zahlen in der Zeile sollen aus den Variablen kommen.");
        }
    }
}
