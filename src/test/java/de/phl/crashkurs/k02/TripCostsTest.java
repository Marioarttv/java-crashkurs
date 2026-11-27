package de.phl.crashkurs.k02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 2, Aufgabe 1: TripCosts.main")
class TripCostsTest {

    static final String ZIEL = "Fahrt für 24 Personen: 2088 Euro";

    @Test
    @DisplayName("gibt Fahrt für 24 Personen: 2088 Euro aus")
    void ausgabe() {
        List<String> zeilen = Quelltext.zeilen(() -> TripCosts.main(new String[0]));
        String text = String.join("\n", zeilen);
        String hinweis;
        if (text.contains("21888")) {
            hinweis = "Du hast zuerst busPrice + students gerechnet und dann mal entryFee, zum Beispiel mit Klammern. "
                    + "Richtig ist: zuerst students * entryFee, dann busPrice dazu. Wegen Punkt vor Strich brauchst du dafür keine Klammern.";
        } else if (text.contains("289.8")) {
            hinweis = "Hast du 1.800 mit Punkt geschrieben? Für Java ist das 1,8. Schreib 1800 ohne Tausenderpunkt.";
        } else {
            hinweis = "1800 + 24 * 12 ist 2088. Vergleiche jeden Text und jedes Leerzeichen mit der Zeile oben.";
        }
        assertEquals(List.of(ZIEL), zeilen, "Erwartet ist genau eine Zeile: " + ZIEL + ". " + hinweis);
    }

    @Test
    @DisplayName("totalCost wird mit den Variablen ausgerechnet, nicht mit Zahlen")
    void rechnung() {
        String text = Quelltext.lies("TripCosts.java");
        Matcher m = Pattern.compile("(?<![\\w.])totalCost\\s*=(?!=)([^;]*);").matcher(text);
        boolean gefunden = false;
        while (m.find()) {
            gefunden = true;
            if (m.group(1).strip().equals("0")) {
                continue; // Startwert 0 ist erlaubt, wenn die Rechnung später folgt.
            }
            assertFalse(m.group(1).matches("(?s).*\\d.*"),
                    "Rechts von totalCost = steht eine Zahl: " + m.group(1).strip()
                            + ". Rechne mit den Namen der Variablen (busPrice, students, entryFee), nicht mit den Zahlen.");
        }
        assertTrue(gefunden, "Speichere die Gesamtkosten in einer Variable mit dem Namen totalCost, zum Beispiel int totalCost = …;");
    }

    @Test
    @DisplayName("die Ausgabe benutzt die Variablen students und totalCost")
    void variablenInDerAusgabe() {
        String text = Quelltext.lies("TripCosts.java");
        assertFalse(Pattern.compile("(?<![\\d.])(2088|288)(?![\\d.])").matcher(text).find(),
                "Im Code steht ein fertiges Ergebnis (2088 oder 288). Lass Java rechnen und gib die Variable aus.");
        for (String name : new String[] {"students", "totalCost"}) {
            Quelltext.enthaelt(text, "println\\s*\\([^;]*\\b" + name + "\\b",
                    "In der Ausgabe fehlt die Variable " + name + ". Die Zahlen in der Zeile sollen aus den Variablen kommen, nicht fest im Text stehen.");
        }
    }
}
