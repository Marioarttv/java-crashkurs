package de.phl.crashkurs.k08;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

/** Hilfe für die Tests: liest eine Aufgabendatei ohne Kommentare. */
final class Quelltext {

    private Quelltext() {
    }

    /** Prüft, ob im Quelltext (ohne Kommentare) das Wort mit einer runden Klammer danach steht, zum Beispiel "switch (". */
    static boolean benutzt(String datei, String wort) throws IOException {
        return java.util.regex.Pattern.compile("\\b" + wort + "\\s*\\(").matcher(ohneKommentare(datei)).find();
    }

    static String ohneKommentare(String datei) throws IOException {
        Path pfad = Path.of("src/main/java/de/phl/crashkurs/k08", datei);
        assumeTrue(Files.exists(pfad), "Quelltext nicht gefunden: " + pfad);
        String text = Files.readString(pfad, StandardCharsets.UTF_8);
        return text.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\\n]*", "");
    }
}
