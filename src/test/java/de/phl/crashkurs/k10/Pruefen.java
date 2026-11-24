package de.phl.crashkurs.k10;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/** Hilfe für die Tests: Zeitlimit gegen Endlosschleifen, Ausgabe auffangen, Quelltext lesen. */
final class Pruefen {

    static final String FACH = "Dein Code fragt nach einem Fach, das es nicht gibt. Läuft deine Schleife einmal zu oft? Für genau n Durchläufe brauchst du int i = 0 und i < n, nicht <=.";

    static final String ENDLOS = "Deine Schleife hört nicht auf. Ändert sich im Block etwas, das die Bedingung irgendwann falsch macht?";

    private Pruefen() {
    }

    /**
     * Führt den Code mit einem Zeitlimit von 3 Sekunden aus. Er läuft in einem eigenen Hintergrund-Thread
     * (daemon): Hört eine Schleife nie auf, endet der Test trotzdem, und Java kann sich danach beenden.
     */
    static <T> T mitZeitlimit(Callable<T> code) {
        AtomicReference<T> ergebnis = new AtomicReference<>();
        AtomicReference<Throwable> fehler = new AtomicReference<>();
        Thread lauf = new Thread(() -> {
            try {
                ergebnis.set(code.call());
            } catch (Throwable e) {
                fehler.set(e);
            }
        }, "crashkurs-zeitlimit");
        lauf.setDaemon(true);
        lauf.start();
        try {
            lauf.join(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (lauf.isAlive()) {
            fail(ENDLOS);
        }
        Throwable e = fehler.get();
        if (e != null) {
            if (e instanceof IndexOutOfBoundsException) {
                fail(FACH + " Java meldet: " + e.getMessage());
            }
            if (e instanceof RuntimeException r) {
                throw r;
            }
            if (e instanceof Error err) {
                throw err;
            }
            throw new RuntimeException(e);
        }
        return ergebnis.get();
    }

    /** Führt den Code mit Zeitlimit aus und gibt zurück, was er mit System.out.println ausgegeben hat. */
    static String ausgabe(Runnable code) {
        PrintStream alt = System.out;
        ByteArrayOutputStream puffer = new ByteArrayOutputStream();
        AtomicBoolean vorbei = new AtomicBoolean(false);
        OutputStream sammler = new OutputStream() {
            @Override
            public void write(int b) {
                if (vorbei.get()) {
                    throw new IllegalStateException(ENDLOS);
                }
                synchronized (puffer) {
                    if (puffer.size() > 100_000) {
                        throw new IllegalStateException("Dein Programm gibt endlos viel aus. " + ENDLOS);
                    }
                    puffer.write(b);
                }
            }
        };
        System.setOut(new PrintStream(sammler, true, StandardCharsets.UTF_8));
        try {
            mitZeitlimit(() -> {
                code.run();
                return null;
            });
        } finally {
            vorbei.set(true);
            System.setOut(alt);
        }
        synchronized (puffer) {
            return puffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        }
    }

    /** Prüft, ob im Quelltext (ohne Kommentare) das Wort mit einer runden Klammer danach steht, zum Beispiel "while (". */
    static boolean benutzt(String datei, String wort) throws IOException {
        return java.util.regex.Pattern.compile("\\b" + wort + "\\s*\\(").matcher(ohneKommentare(datei)).find();
    }

    static String ohneKommentare(String datei) throws IOException {
        Path pfad = Path.of("src/main/java/de/phl/crashkurs/k10", datei);
        assumeTrue(Files.exists(pfad), "Quelltext nicht gefunden: " + pfad);
        String text = Files.readString(pfad, StandardCharsets.UTF_8);
        return text.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\\n]*", "");
    }
}
