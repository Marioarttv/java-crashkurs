package de.phl.crashkurs.k13.stage;

import de.phl.crashkurs.k13.Pruefhilfe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 13, Übung 2: FestivalApp (30 Punkte)")
class FestivalAppTest {

    private static String ausgabe() {
        return Pruefhilfe.ausgabeVonMain(FestivalApp.class);
    }

    private static String main() {
        return Pruefhilfe.methodenInhalt(Pruefhilfe.quelltext("de/phl/crashkurs/k13/stage/FestivalApp.java"), "main");
    }

    private static void enthaelt(String aus, String erwartet, String hinweis) {
        assertTrue(aus.contains(erwartet), hinweis + "\nErwartet in der Ausgabe: " + erwartet + "\nAusgegeben wurde:\n" + aus);
    }

    @Test
    @DisplayName("2.1 (3 Punkte): Bühne „Seebühne“ mit 4 Plätzen")
    void task_1_buehne() {
        assertTrue(Pattern.compile("new\\s+Stage\\s*\\(\\s*\"Seebühne\"\\s*,\\s*4\\s*\\)").matcher(main()).find(),
                "In main fehlt new Stage(\"Seebühne\", 4). Prüfe die Reihenfolge der Werte: erst der Name, dann die Plätze.");
    }

    @Test
    @DisplayName("2.2 (6 Punkte): zwei Bands erzeugt und mit getInfo() ausgegeben")
    void task_2_bands() {
        String aus = ausgabe();
        enthaelt(aus, "Band: The Lindens, Genre: Indie, Fans: 1200", "The Lindens fehlt in der Ausgabe. Erzeugst du die Band mit den richtigen Werten und gibst getInfo() aus?");
        enthaelt(aus, "Band: Neon Cats, Genre: Pop, Fans: 950", "Neon Cats fehlt in der Ausgabe. Erzeugst du die Band mit den richtigen Werten und gibst getInfo() aus?");
    }

    @Test
    @DisplayName("2.3 (4 Punkte): beide Bands auf die Bühne")
    void task_3_aufDieBuehne() {
        String aus = ausgabe();
        enthaelt(aus, "The Lindens steht auf dem Programm von Seebühne.", "The Lindens steht noch nicht auf der Bühne. Rufe addBand am Bühnen-Objekt auf.");
        enthaelt(aus, "Neon Cats steht auf dem Programm von Seebühne.", "Neon Cats steht noch nicht auf der Bühne. Rufe addBand am Bühnen-Objekt auf.");
    }

    @Test
    @DisplayName("2.4 (4 Punkte): zwei Songs erzeugt")
    void task_4_songs() {
        String m = main();
        assertTrue(Pattern.compile("new\\s+Song\\s*\\(\\s*\"Summer Lights\"\\s*,\\s*150\\s*\\)").matcher(m).find(),
                "In main fehlt new Song(\"Summer Lights\", 150).");
        assertTrue(Pattern.compile("new\\s+Song\\s*\\(\\s*\"Night Drive\"\\s*,\\s*320\\s*\\)").matcher(m).find(),
                "In main fehlt new Song(\"Night Drive\", 320).");
    }

    @Test
    @DisplayName("2.5 (5 Punkte): die Bands spielen, danach getInfo() erneut")
    void task_5_spielen() {
        String aus = ausgabe();
        enthaelt(aus, "The Lindens spielt Summer Lights.", "The Lindens hat Summer Lights noch nicht gespielt. Rufe play an der Band auf und gib ihr den Song.");
        enthaelt(aus, "Neon Cats spielt Night Drive.", "Neon Cats hat Night Drive noch nicht gespielt. Rufe play an der Band auf und gib ihr den Song.");
        enthaelt(aus, "Band: The Lindens, Genre: Indie, Fans: 1350", "Nach dem Spielen fehlt die neue Info von The Lindens. Gib getInfo() nach play noch einmal aus.");
        enthaelt(aus, "Band: Neon Cats, Genre: Pop, Fans: 1270", "Nach dem Spielen fehlt die neue Info von Neon Cats. Gib getInfo() nach play noch einmal aus.");
    }

    @Test
    @DisplayName("2.6 (8 Punkte): neue Fans mit getName() und Differenz ausgegeben")
    void task_6_zuwachs() {
        String aus = ausgabe();
        assertTrue(Pattern.compile("Neue Fans The Lindens:\\s*150\\b").matcher(aus).find(),
                "Erwartet in der Ausgabe: Neue Fans The Lindens: 150\nAusgegeben wurde:\n" + aus);
        assertTrue(Pattern.compile("Neue Fans Neon Cats:\\s*320\\b").matcher(aus).find(),
                "Erwartet in der Ausgabe: Neue Fans Neon Cats: 320\nAusgegeben wurde:\n" + aus);
        String m = main();
        assertTrue(Pruefhilfe.anzahl(m, "\\.getFans\\s*\\(\\s*\\)") >= 4,
                "Benutze getFans(): für jede Band einmal vor play (Wert merken) und einmal danach.");
        assertTrue(Pruefhilfe.anzahl(m, "\\.getName\\s*\\(\\s*\\)") >= 2,
                "Benutze für den Namen der Band getName(), statt den Namen selbst zu schreiben.");
    }
}
