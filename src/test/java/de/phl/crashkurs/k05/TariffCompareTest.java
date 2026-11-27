package de.phl.crashkurs.k05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Kapitel 5, Aufgabe 2: TariffCompare.main")
class TariffCompareTest {

    @Test
    @DisplayName("gibt den Preis für eine Fahrt mit 30 Minuten aus")
    void eineFahrt() {
        String aus = AppRun.ausgabe(() -> TariffCompare.main(new String[0]));
        if (!AppRun.hatZeile(aus, "Eine Fahrt mit 30 Minuten: 550 Cent")) {
            fail("In der Ausgabe fehlt die Zeile \"Eine Fahrt mit 30 Minuten: 550 Cent\". Erzeuge den Tarif mit new Tariff(100, 15) "
                    + "(erst das Entsperren, dann der Preis pro Minute) und speichere den Rückgabewert von tariff.priceFor(30). Deine Ausgabe: "
                    + AppRun.zeigen(aus));
        }
    }

    @Test
    @DisplayName("gibt den Preis für zwei Fahrten mit je 15 Minuten aus")
    void zweiFahrten() {
        String aus = AppRun.ausgabe(() -> TariffCompare.main(new String[0]));
        if (!AppRun.hatZeile(aus, "Zwei Fahrten mit je 15 Minuten: 650 Cent")) {
            String hinweis = aus.contains("325")
                    ? "325 Cent ist der Preis für eine Fahrt mit 15 Minuten. Du brauchst den Preis für zwei solche Fahrten."
                    : "Jede Fahrt kostet tariff.priceFor(15). Rechne mit diesem Rückgabewert den Preis für zwei Fahrten aus.";
            fail("In der Ausgabe fehlt die Zeile \"Zwei Fahrten mit je 15 Minuten: 650 Cent\". " + hinweis + " Deine Ausgabe: " + AppRun.zeigen(aus));
        }
    }

    @Test
    @DisplayName("die Preise kommen aus priceFor")
    void rueckgabewerte() {
        String q = AppRun.quelltext("TariffCompare.java");
        if (!AppRun.findet(q, "\\.priceFor\\s*\\(\\s*30\\s*\\)") || !AppRun.findet(q, "\\.priceFor\\s*\\(\\s*15\\s*\\)")) {
            fail("In TariffCompare.java fehlt tariff.priceFor(30) oder tariff.priceFor(15). Die Preise soll der Tarif ausrechnen.");
        }
        if (q.contains("550") || q.contains("650")) {
            fail("In TariffCompare.java steht 550 oder 650 fest im Code. Diese Zahlen sollen aus priceFor und deiner Rechnung kommen.");
        }
    }
}
