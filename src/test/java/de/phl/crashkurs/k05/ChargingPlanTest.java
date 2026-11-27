package de.phl.crashkurs.k05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Kapitel 5, Aufgabe 3: ChargingPlan.main")
class ChargingPlanTest {

    @Test
    @DisplayName("gibt die Ladezeit von B-3 aus")
    void ladezeit() {
        String aus = AppRun.ausgabe(() -> ChargingPlan.main(new String[0]));
        if (!AppRun.hatZeile(aus, "B-3 braucht 15 Minuten bis voll.")) {
            fail("In der Ausgabe fehlt die Zeile \"B-3 braucht 15 Minuten bis voll.\". Erzeuge das Rad mit new Bike(\"B-3\", 40) und die Ladesäule "
                    + "mit new Charger(4). Frag dann die Ladesäule: charger.minutesUntilFull(...) mit dem Rad in den Klammern. Deine Ausgabe: "
                    + AppRun.zeigen(aus));
        }
    }

    @Test
    @DisplayName("gibt die Reichweite nach dem Laden aus")
    void reichweite() {
        String aus = AppRun.ausgabe(() -> ChargingPlan.main(new String[0]));
        if (!AppRun.hatZeile(aus, "Danach schafft B-3 50 km.")) {
            String hinweis = aus.contains("schafft B-3 20 km")
                    ? "Dein Rad hat noch den alten Akkustand. Lädst du es mit charger.plugIn(...) auf, bevor du die Reichweite ausgibst?"
                    : "Lade das Rad mit charger.plugIn(...) so viele Minuten, wie minutesUntilFull zurückgegeben hat. Gib danach getRange() aus.";
            fail("In der Ausgabe fehlt die Zeile \"Danach schafft B-3 50 km.\". " + hinweis + " Deine Ausgabe: " + AppRun.zeigen(aus));
        }
    }

    @Test
    @DisplayName("die Minuten kommen aus minutesUntilFull")
    void rueckgabewert() {
        String q = AppRun.quelltext("ChargingPlan.java");
        String[] noetig = {".minutesUntilFull(", ".plugIn(", ".getRange()", ".getNumber()"};
        for (String n : noetig) {
            if (!q.contains(n)) {
                fail("In ChargingPlan.java fehlt " + n + ". Die Minuten kommen aus minutesUntilFull, geladen wird mit plugIn, "
                        + "Nummer und Reichweite kommen aus getNumber() und getRange().");
            }
        }
        if (AppRun.findet(q, "\\.plugIn\\s*\\([^;]*,\\s*15\\s*\\)")) {
            fail("In ChargingPlan.java steht die 15 fest im Aufruf von plugIn. Nimm den Rückgabewert von minutesUntilFull, zum Beispiel aus einer Variable minutes.");
        }
        if (q.contains("15 Minuten") || q.contains("50 km") || q.contains("B-3 braucht")) {
            fail("In ChargingPlan.java steht ein Ergebnis fest im Text. Setz die Zeilen aus Text und den Rückgabewerten zusammen.");
        }
    }
}
