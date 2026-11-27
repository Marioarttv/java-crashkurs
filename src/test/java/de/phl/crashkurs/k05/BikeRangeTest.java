package de.phl.crashkurs.k05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Kapitel 5, Aufgabe 1: BikeRange.main")
class BikeRangeTest {

    private static final String ZEILE = "Rad B-12 schafft noch 32 km.";

    @Test
    @DisplayName("gibt die Reichweite von B-12 aus")
    void ausgabe() {
        String aus = AppRun.ausgabe(() -> BikeRange.main(new String[0]));
        if (!AppRun.hatZeile(aus, ZEILE)) {
            String hinweis = aus.contains("40 km") || aus.contains("32")
                    ? "Prüf die Werte beim Erzeugen: new Bike(\"B-12\", 64), erst die Nummer, dann der Akkustand."
                    : "Erzeuge das Rad mit new Bike(\"B-12\", 64) und setz die Zeile aus Text, bike.getNumber() und bike.getRange() zusammen.";
            fail("In der Ausgabe fehlt die Zeile \"" + ZEILE + "\". " + hinweis + " Deine Ausgabe: " + AppRun.zeigen(aus));
        }
    }

    @Test
    @DisplayName("Nummer und Reichweite kommen aus den Methoden des Rads")
    void methoden() {
        String q = AppRun.quelltext("BikeRange.java");
        if (!AppRun.findet(q, "new\\s+Bike\\s*\\(\\s*\"B-12\"\\s*,\\s*64\\s*\\)")) {
            fail("In BikeRange.java fehlt new Bike(\"B-12\", 64). Erzeuge das Rad mit genau diesen Werten.");
        }
        if (!q.contains(".getNumber()") || !q.contains(".getRange()")) {
            fail("In BikeRange.java fehlt .getNumber() oder .getRange(). Die Nummer und die Kilometer sollen aus den Methoden des Rads kommen.");
        }
        if (q.contains("32 km") || q.contains("B-12 schafft")) {
            fail("In BikeRange.java steht die Nummer oder die 32 fest im Text. Setz die Zeile zusammen, zum Beispiel: "
                    + "\"Rad \" + bike.getNumber() + \" schafft noch \" + bike.getRange() + \" km.\"");
        }
    }
}
