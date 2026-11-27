package de.phl.crashkurs.k05;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Hilfe für die Tests zu Kapitel 5: startet ein main und liefert die Ausgabe, liest den Quelltext.
 */
final class AppRun {

    /** Ein Programm, das gestartet wird (zum Beispiel () -> BikeRange.main(new String[0])). */
    interface Programm {
        void start();
    }

    private AppRun() {
    }

    /** Startet das Programm und gibt alles zurück, was mit System.out.println ausgegeben wurde. */
    static String ausgabe(Programm programm) {
        PrintStream vorher = System.out;
        ByteArrayOutputStream puffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(puffer, true, StandardCharsets.UTF_8));
        try {
            programm.start();
        } catch (RuntimeException e) {
            System.setOut(vorher);
            fail("Dein Programm bricht beim Start mit einer Exception ab: " + e);
        } finally {
            System.setOut(vorher);
        }
        return puffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    /** Der Quelltext einer Datei im Ordner k05, ohne Kommentare. */
    static String quelltext(String datei) {
        Path p = Path.of("src", "main", "java", "de", "phl", "crashkurs", "k05", datei);
        try {
            String s = Files.readString(p, StandardCharsets.UTF_8);
            return s.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\\n]*", "");
        } catch (IOException e) {
            fail("Die Datei " + p + " wurde nicht gefunden. Starte den Test aus dem Projektordner.");
            return "";
        }
    }

    /** Prüft, ob die Ausgabe genau diese Zeile enthält. */
    static boolean hatZeile(String ausgabe, String zeile) {
        for (String z : ausgabe.split("\n")) {
            if (z.strip().equals(zeile)) {
                return true;
            }
        }
        return false;
    }

    /** Findet das Muster (regulärer Ausdruck) im Quelltext? */
    static boolean findet(String quelltext, String muster) {
        return Pattern.compile(muster).matcher(quelltext).find();
    }

    static String zeigen(String ausgabe) {
        return ausgabe.isBlank() ? "(nichts)" : "\n" + ausgabe;
    }
}
