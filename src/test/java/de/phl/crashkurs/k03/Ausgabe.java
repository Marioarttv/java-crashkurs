package de.phl.crashkurs.k03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/** Hilfen für die Tests von Kapitel 3: Ausgabe auffangen und den Quelltext lesen. */
final class Ausgabe {

    private Ausgabe() {
    }

    /** Startet den Programmteil und liefert die Ausgabe Zeile für Zeile. */
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

    /** Quelltext einer Datei im Ordner k03, ohne Kommentare (leer, wenn die Datei nicht zu finden ist). */
    static String quelltext(String datei) {
        try {
            String text = Files.readString(Path.of("src/main/java/de/phl/crashkurs/k03", datei), StandardCharsets.UTF_8);
            return text.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\\n]*", "");
        } catch (IOException e) {
            return "";
        }
    }
}
