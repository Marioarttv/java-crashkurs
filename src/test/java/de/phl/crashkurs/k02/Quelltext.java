package de.phl.crashkurs.k02;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/** Hilfen für die Tests von Kapitel 2: Ausgabe auffangen und den Quelltext lesen. */
final class Quelltext {

    static final String ORDNER = "src/main/java/de/phl/crashkurs/k02";

    private Quelltext() {
    }

    /** Startet das Programm und liefert die Ausgabe Zeile für Zeile. */
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

    /** Liest eine Datei aus dem Ordner k02, ohne Kommentare. */
    static String lies(String datei) {
        Path pfad = Path.of(ORDNER, datei);
        if (!Files.exists(pfad)) {
            return fail("Die Datei " + datei + " liegt nicht im Ordner " + ORDNER + ".");
        }
        try {
            String text = Files.readString(pfad, StandardCharsets.UTF_8);
            return text.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\\n]*", "");
        } catch (IOException e) {
            return fail("Die Datei " + datei + " lässt sich nicht lesen: " + e.getMessage());
        }
    }

    /** Prüft, ob der Quelltext zum Muster passt. Leerzeichen sind im Muster mit \s* erlaubt. */
    static void enthaelt(String text, String muster, String meldung) {
        assertTrue(Pattern.compile(muster).matcher(text).find(), meldung);
    }
}
