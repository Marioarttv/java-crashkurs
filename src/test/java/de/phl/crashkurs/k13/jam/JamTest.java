package de.phl.crashkurs.k13.jam;

import de.phl.crashkurs.k13.Pruefhilfe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 13, Übung 3: Musiker und Publikum (60 Punkte)")
class JamTest {

    private static final String MUSICIAN = "de/phl/crashkurs/k13/jam/Musician.java";
    private static final String FESTIVAL = "de/phl/crashkurs/k13/jam/Festival.java";

    /** Ein Publikum, das sich nur merkt, wie viel Energie es verlieren sollte. So hängt 3.1 nicht an 3.2. */
    private static final class MerkCrowd extends Crowd {
        int bekommen = -1;

        MerkCrowd() {
            super("Probe", 100, 5);
        }

        @Override
        public void loseEnergy(final int amount) {
            bekommen = amount;
        }
    }

    private static void solo(Instrument instrument, String text, int staerke) {
        MerkCrowd crowd = new MerkCrowd();
        String ergebnis = new Musician("Test", instrument, 50).playSolo(crowd);
        assertEquals(text, ergebnis, "playSolo gibt für " + instrument + " den falschen Text zurück.");
        assertEquals(staerke, crowd.bekommen, "Bei " + instrument + " muss playSolo crowd.loseEnergy(" + staerke + ") aufrufen."
                + (crowd.bekommen == -1 ? " Bisher wird loseEnergy gar nicht aufgerufen." : " Aufgerufen wurde es mit " + crowd.bekommen + "."));
    }

    @Test
    @DisplayName("3.1 (18 Punkte): playSolo mit switch über das Instrument")
    void task_1_playSolo() {
        solo(Instrument.GUITAR, "Gitarrensolo", 12);
        solo(Instrument.DRUMS, "Schlagzeugsolo", 18);
        solo(Instrument.KEYBOARD, "Keyboardsolo", 8);
        assertTrue(Pruefhilfe.methodenInhalt(Pruefhilfe.quelltext(MUSICIAN), "playSolo").contains("switch"),
                "playSolo soll ein switch über instrument verwenden.");
    }

    @Test
    @DisplayName("3.2 (6 Punkte): loseEnergy im Musician setzt Energie und Status")
    void task_2_loseEnergyMusician() {
        Musician m = new Musician("Test", Instrument.GUITAR, 30);
        m.loseEnergy(19);
        assertEquals(11, m.getEnergy(), "Nach loseEnergy(19) bei 30 Energie muss die Energie 11 sein.");
        assertEquals(Status.READY, m.getStatus(), "Bei 11 Energie muss der Status READY bleiben. Prüfst du mit energy > 10, und hängen die Fälle mit else if zusammen?");
        m.loseEnergy(1);
        assertEquals(Status.BREAK, m.getStatus(), "Bei genau 10 Energie muss der Status BREAK sein. READY gilt erst ab 11: Prüfst du mit energy > 10?");
        m.loseEnergy(10);
        assertEquals(Status.BREAK, m.getStatus(), "Bei genau 0 Energie muss der Status BREAK sein. Prüfst du mit energy >= 0?");
        m.loseEnergy(5);
        assertEquals(-5, m.getEnergy(), "Nach noch einmal loseEnergy(5) muss die Energie -5 sein.");
        assertEquals(Status.DONE, m.getStatus(), "Bei Energie unter 0 muss der Status DONE sein.");
    }

    @Test
    @DisplayName("3.2 (6 Punkte): loseEnergy in Crowd setzt Energie und Status")
    void task_2_loseEnergyCrowd() {
        Crowd c = new Crowd("Test", 25, 1);
        c.loseEnergy(4);
        assertEquals(21, c.getEnergy(), "Nach loseEnergy(4) bei 25 Energie muss die Energie 21 sein.");
        assertEquals(Status.READY, c.getStatus(), "Bei 21 Energie muss der Status READY bleiben. Prüfst du mit energy > 10, und hängen die Fälle mit else if zusammen?");
        c.loseEnergy(11);
        assertEquals(Status.BREAK, c.getStatus(), "Bei genau 10 Energie muss der Status BREAK sein. READY gilt erst ab 11: Prüfst du mit energy > 10?");
        c.loseEnergy(10);
        assertEquals(Status.BREAK, c.getStatus(), "Bei genau 0 Energie muss der Status BREAK sein. Prüfst du mit energy >= 0?");
        c.loseEnergy(1);
        assertEquals(Status.DONE, c.getStatus(), "Bei Energie unter 0 muss der Status DONE sein.");
    }

    @Test
    @DisplayName("3.3 (14 Punkte): jam mit while, bis einer nicht mehr kann")
    void task_3_jam() {
        Musician probeM = new Musician("Probe", Instrument.GUITAR, 20);
        probeM.loseEnergy(15);
        Crowd probeC = new Crowd("Probe", 20, 1);
        probeC.loseEnergy(15);
        assertTrue(probeM.getStatus() == Status.BREAK && probeC.getStatus() == Status.BREAK,
                "Der Test für jam lässt den Musiker wirklich spielen. Dafür müssen beide loseEnergy-Methoden fertig sein (Übung 3.2). Mach zuerst 3.2 fertig, sonst endet die Schleife nie.");
        Musician tom = new Musician("Tom", Instrument.GUITAR, 50);
        Crowd probe = new Crowd("Probe", 45, 15);
        String aus = Pruefhilfe.ausgabe("jam", () -> Festival.jam(tom, probe));
        assertEquals(3, Pruefhilfe.anzahl(aus, "Tom spielt Gitarrensolo"),
                "Tom (Gitarre, 12) gegen ein Publikum mit 45 Energie: Nach 3 Soli hat das Publikum noch 9 Energie und ist BREAK. Es muss genau 3 Zeilen „Tom spielt Gitarrensolo“ geben.\nAusgegeben wurde:\n" + aus);
        assertEquals(Status.BREAK, probe.getStatus(), "Nach dem Auftritt muss das Publikum den Status BREAK haben.");
        assertEquals(20, tom.getEnergy(), "Das Publikum jubelt nur, solange es noch READY ist: zweimal 15. Tom muss danach noch 20 Energie haben. Prüfst du mit if, ob das Publikum noch READY ist?");
        String[] zeilen = aus.strip().split("\n");
        String letzte = zeilen[zeilen.length - 1];
        assertTrue(letzte.contains("READY") && letzte.contains("BREAK"),
                "Gib nach der Schleife den Status beider aus, zum Beispiel: Ende: Tom ist READY, Probe ist BREAK\nDie letzte Zeile war: " + letzte);

        Musician leo = new Musician("Leo", Instrument.KEYBOARD, 28);
        Crowd laut = new Crowd("Laut", 100, 15);
        String aus2 = Pruefhilfe.ausgabe("jam", () -> Festival.jam(leo, laut));
        assertEquals(2, Pruefhilfe.anzahl(aus2, "Leo spielt Keyboardsolo"),
                "Leo hat 28 Energie, das Publikum kostet ihn 15 pro Jubel. Nach 2 Soli hat Leo -2 Energie und ist DONE, dann muss die Schleife enden.\nAusgegeben wurde:\n" + aus2);
        assertEquals(Status.DONE, leo.getStatus(), "Leo muss nach dem Auftritt den Status DONE haben.");
        assertEquals(84, laut.getEnergy(), "Das Publikum muss nach 2 Keyboardsoli noch 84 Energie haben.");
        assertTrue(Pruefhilfe.methodenInhalt(Pruefhilfe.quelltext(FESTIVAL), "jam").contains("while"), "jam soll eine while-Schleife verwenden.");
    }

    @Test
    @DisplayName("3.4 (6 Punkte): main erzeugt Mia Stern und die Zeltbühne und ruft jam auf")
    void task_4_main() {
        String m = Pruefhilfe.methodenInhalt(Pruefhilfe.quelltext(FESTIVAL), "main");
        assertTrue(Pattern.compile("new\\s+Musician\\s*\\(\\s*\"Mia Stern\"\\s*,\\s*Instrument\\s*\\.\\s*DRUMS\\s*,\\s*95\\s*\\)").matcher(m).find(),
                "In main fehlt new Musician(\"Mia Stern\", Instrument.DRUMS, 95).");
        assertTrue(Pattern.compile("new\\s+Crowd\\s*\\(\\s*\"Zeltbühne\"\\s*,\\s*75\\s*,\\s*12\\s*\\)").matcher(m).find(),
                "In main fehlt new Crowd(\"Zeltbühne\", 75, 12).");
        assertTrue(Pattern.compile("\\bjam\\s*\\(").matcher(m).find(),
                "main muss jam mit dem Musiker und dem Publikum aufrufen.");
    }

    @Test
    @DisplayName("3.5 (10 Punkte): createCrowds erstellt mit for genau 20 Crowd-Objekte")
    void task_5_createCrowds() {
        assertTrue(Pruefhilfe.methodenInhalt(Pruefhilfe.quelltext(FESTIVAL), "createCrowds").contains("for"),
                "createCrowds soll eine for-Schleife verwenden.");
        List<Crowd> crowds = Festival.createCrowds();
        assertNotNull(crowds, "createCrowds darf nicht null zurückgeben.");
        assertEquals(20, crowds.size(), "createCrowds muss genau 20 Crowd-Objekte in die Liste legen. Läuft die Schleife von 0 bis 19 (i < 20)?");
        for (int i = 0; i < 20; i++) {
            Crowd c = crowds.get(i);
            assertEquals(Festival.names[i], c.getName(), "Das Publikum mit Index " + i + " muss names[" + i + "] als Namen haben.");
            assertEquals(30 + i * 4, c.getEnergy(), "Das Publikum mit Index " + i + " muss 30 + " + i + " * 4 = " + (30 + i * 4) + " Energie haben.");
            assertEquals(8 + i * 3, c.getVolume(), "Das Publikum mit Index " + i + " muss die Lautstärke 8 + " + i + " * 3 = " + (8 + i * 3) + " haben.");
        }
    }
}
