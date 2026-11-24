package de.phl.crashkurs.k13.tickets;

import de.phl.crashkurs.k13.Pruefhilfe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Übung 1 arbeitet mit einer Klasse, die es am Anfang noch nicht gibt. Deshalb sucht der Test sie über ihren Namen.
 */
@DisplayName("Kapitel 13, Übung 1: TicketCalculator (20 Punkte)")
class TicketCalculatorTest {

    private static final String PAKET = "de.phl.crashkurs.k13.tickets";
    private static final String DATEI = "de/phl/crashkurs/k13/tickets/TicketCalculator.java";

    private String quelltext() {
        Pruefhilfe.klasse(PAKET, "TicketCalculator");
        return Pruefhilfe.quelltext(DATEI);
    }

    @Test
    @DisplayName("1.1 (4 Punkte): Klasse TicketCalculator im Paket tickets")
    void task_1_klasseGibtEs() {
        Pruefhilfe.klasse(PAKET, "TicketCalculator");
    }

    @Test
    @DisplayName("1.2 (3 Punkte): public static void main(String[] args)")
    void task_2_mainGibtEs() {
        Pruefhilfe.mainMethode(Pruefhilfe.klasse(PAKET, "TicketCalculator"));
    }

    @Test
    @DisplayName("1.3 (2 Punkte): int ticketPrice = 79")
    void task_3_ticketPrice() {
        assertTrue(Pattern.compile("\\bint\\s+ticketPrice\\s*=\\s*79\\s*;").matcher(quelltext()).find(),
                "In main fehlt die Zeile int ticketPrice = 79; Prüfe den Typ int, den Namen (genau so geschrieben) und den Wert.");
    }

    @Test
    @DisplayName("1.4 (2 Punkte): int groupSize = 5")
    void task_4_groupSize() {
        assertTrue(Pattern.compile("\\bint\\s+groupSize\\s*=\\s*5\\s*;").matcher(quelltext()).find(),
                "In main fehlt die Zeile int groupSize = 5; Prüfe den Typ int, den Namen (genau so geschrieben) und den Wert.");
    }

    @Test
    @DisplayName("1.5 (4 Punkte): int totalPrice = ticketPrice * groupSize")
    void task_5_totalPrice() {
        String q = quelltext();
        assertTrue(Pattern.compile("\\bint\\s+totalPrice\\b").matcher(q).find()
                        && Pattern.compile("\\btotalPrice\\s*=\\s*(ticketPrice\\s*\\*\\s*groupSize|groupSize\\s*\\*\\s*ticketPrice)\\s*;").matcher(q).find(),
                "In main fehlt int totalPrice = ticketPrice * groupSize; Rechne mit den beiden Variablen, nicht mit den Zahlen.");
    }

    @Test
    @DisplayName("1.6 (5 Punkte): Ausgabe „Gesamtpreis: 395“")
    void task_6_ausgabe() {
        String aus = Pruefhilfe.ausgabeVonMain(Pruefhilfe.klasse(PAKET, "TicketCalculator"));
        assertTrue(Pattern.compile("Gesamtpreis:\\s*395").matcher(aus).find(),
                "Die Ausgabe muss „Gesamtpreis:“ und danach 395 enthalten. Ausgegeben wurde:\n" + aus);
    }
}
