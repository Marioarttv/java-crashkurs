package de.phl.crashkurs.k05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Kapitel 5, Aufgabe 3: BikeRentalApp.main, vorher und nachher vergleichen")
class BikeRentalApp3Test {

    @Test
    @DisplayName("Der Akkuverbrauch von B-12 wird ausgegeben")
    void akku() {
        String aus = AppRun.ausgabe();
        String hinweis = aus.contains("Akkuverbrauch B-12: 0 Prozent")
                ? "Dein Programm gibt 0 Prozent aus. Merkst du dir den Akkustand erst nach dem Fahren? Der Vorher-Wert muss vor lena.ride(...) gespeichert werden."
                : "Merk dir den Akku von B-12 mit getBattery() vor der Fahrt, lass Lena 15 Minuten fahren, frag den Akku danach noch einmal und rechne die Differenz aus.";
        if (!aus.contains("Akkuverbrauch B-12: 15 Prozent")) {
            fail("In der Ausgabe fehlt die Zeile \"Akkuverbrauch B-12: 15 Prozent\". " + hinweis + " Deine Ausgabe: " + AppRun.zeigen(aus));
        }
    }

    @Test
    @DisplayName("Die Kosten für Lena werden ausgegeben")
    void kosten() {
        String aus = AppRun.ausgabe();
        String hinweis = aus.contains("Kosten Lena: 0 Cent")
                ? "Dein Programm gibt 0 Cent aus. Merkst du dir das Guthaben erst nach dem Fahren? Der Vorher-Wert muss vor lena.ride(...) gespeichert werden."
                : "Merk dir Lenas Guthaben mit getCredit() vor der Fahrt und danach, und gib die Differenz aus.";
        if (!aus.contains("Kosten Lena: 150 Cent")) {
            fail("In der Ausgabe fehlt die Zeile \"Kosten Lena: 150 Cent\". " + hinweis + " Deine Ausgabe: " + AppRun.zeigen(aus));
        }
    }

    @Test
    @DisplayName("Die Getter werden benutzt")
    void getter() {
        String q = AppRun.quelltext();
        String[] noetig = {"getBattery()", "getCredit()", "getNumber()", "getName()", ".ride("};
        for (String n : noetig) {
            if (!q.contains(n)) {
                fail("In BikeRentalApp.java fehlt " + n + ". Die Aufgabe verlangt: Nummer und Name kommen aus getNumber() und getName(), "
                        + "die Werte vorher und nachher aus getBattery() und getCredit(), und Lena fährt mit ride(...). Schreib die Zahlen nicht von Hand in den Text.");
            }
        }
        String[] verboten = {"15 Prozent", "150 Cent"};
        for (String v : verboten) {
            if (q.contains(v)) {
                fail("In BikeRentalApp.java steht \"" + v + "\" fest im Text. Die Zahl soll aus deiner Rechnung kommen, zum Beispiel: "
                        + "\"Kosten \" + lena.getName() + \": \" + cost + \" Cent\"");
            }
        }
    }
}
