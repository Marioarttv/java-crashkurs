package de.phl.crashkurs.k05;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Hilfe für die Tests zu Kapitel 5: startet BikeRentalApp.main und liefert die Ausgabe.
 */
final class AppRun {

    private AppRun() {
    }

    /** Startet main und gibt alles zurück, was mit System.out.println ausgegeben wurde. */
    static String ausgabe() {
        PrintStream vorher = System.out;
        ByteArrayOutputStream puffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(puffer, true, StandardCharsets.UTF_8));
        try {
            BikeRentalApp.main(new String[0]);
        } catch (RuntimeException e) {
            System.setOut(vorher);
            fail("Dein Programm bricht beim Start mit einer Exception ab: " + e);
        } finally {
            System.setOut(vorher);
        }
        return puffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    /** Der Quelltext von BikeRentalApp.java ohne Kommentare. */
    static String quelltext() {
        Path p = Path.of("src", "main", "java", "de", "phl", "crashkurs", "k05", "BikeRentalApp.java");
        try {
            String s = Files.readString(p, StandardCharsets.UTF_8);
            return s.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\\n]*", "");
        } catch (IOException e) {
            fail("Die Datei " + p + " wurde nicht gefunden. Starte den Test aus dem Projektordner.");
            return "";
        }
    }

    static String zeigen(String ausgabe) {
        return ausgabe.isBlank() ? "(nichts)" : "\n" + ausgabe;
    }
}
