package de.phl.crashkurs.k01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 1, Aufgabe 1: MensaBoard.main")
class MensaBoardTest {

    /** Startet main und liefert die Ausgabe Zeile für Zeile. */
    static List<String> zeilen(Runnable programm) {
        PrintStream vorher = System.out;
        ByteArrayOutputStream puffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(puffer, true, StandardCharsets.UTF_8));
        try {
            programm.run();
        } finally {
            System.setOut(vorher);
        }
        String text = puffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        if (text.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(text.split("\n")).map(String::stripTrailing).toList();
    }

    @Test
    @DisplayName("gibt genau die drei Zeilen des Aushangs aus")
    void dreiZeilen() {
        List<String> erwartet = List.of(
                "Heute in der Mensa",
                "Essen 1: Nudeln mit Tomatensauce",
                "Essen 2: Linsencurry mit Reis");
        assertEquals(erwartet, zeilen(() -> MensaBoard.main(new String[0])),
                "Die Ausgabe stimmt noch nicht. Erwartet sind genau diese drei Zeilen, in dieser Reihenfolge: "
                        + erwartet + ". Achte auf jeden Buchstaben, jedes Leerzeichen und den Doppelpunkt.");
    }
}
