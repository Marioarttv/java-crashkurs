package de.phl.crashkurs.k13;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Der Test vergleicht die Summe aller Stände vorher und nachher.
 * So stören ihn die Verkäufe anderer Tests nicht.
 */
@DisplayName("Kapitel 13, Aufgabe 3: MerchStand")
class MerchStandTest {

    @Test
    @DisplayName("Ein Stand zählt seine eigenen Verkäufe")
    void eigeneVerkaeufe() {
        MerchStand stand = new MerchStand();
        stand.sell(2);
        stand.sell(4);
        assertEquals(6, stand.getSold(), "Ein Stand verkauft 2 und dann 4 T-Shirts. getSold() muss 6 liefern.");
    }

    @Test
    @DisplayName("Zwei Stände: die Summe aller Stände zählt beide mit")
    void summeAllerStaende() {
        MerchStand a = new MerchStand();
        MerchStand b = new MerchStand();
        int vorher = a.getTotalSold();
        a.sell(2);
        b.sell(3);
        assertEquals(vorher + 5, a.getTotalSold(), "Stand A verkauft 2 T-Shirts, Stand B verkauft 3. Die Summe aller Stände muss danach um 5 größer sein als vorher. "
                + "Lies die Beschreibung von totalSold: Gehört dieser Wert jedem Stand einzeln?");
        assertEquals(a.getTotalSold(), b.getTotalSold(), "Beide Stände müssen dieselbe Summe aller Stände liefern.");
    }

    @Test
    @DisplayName("Erstes Motiv ausverkauft: true")
    void erstesAusverkauft() {
        assertTrue(MerchStand.hasSoldOut(new int[] {0, 4, 7}), "Im Bestand {0, 4, 7} ist das erste Motiv ausverkauft. hasSoldOut muss true liefern.");
    }

    @Test
    @DisplayName("Nichts ausverkauft: false")
    void nichtsAusverkauft() {
        assertFalse(MerchStand.hasSoldOut(new int[] {3, 4, 7}), "Im Bestand {3, 4, 7} ist kein Motiv ausverkauft. hasSoldOut muss false liefern.");
    }

    @Test
    @DisplayName("Mittleres Motiv ausverkauft: true")
    void mittleresAusverkauft() {
        assertTrue(MerchStand.hasSoldOut(new int[] {5, 0, 3}), "Im Bestand {5, 0, 3} ist das zweite Motiv ausverkauft. hasSoldOut muss true liefern. "
                + "Geh die Schleife mit diesem Beispiel im Kopf durch: Was passiert schon beim ersten Wert 5?");
    }
}
