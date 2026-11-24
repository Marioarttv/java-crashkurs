package de.phl.crashkurs.k09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 9, Aufgabe 2: Training.warmUp")
class WarmUpTest {

    @Test
    @DisplayName("Ausgeruht: genau 5 Bälle")
    void fuenfBaelle() {
        Player mia = new Player("Mia", 20, 3);
        int[] baelle = new int[1];
        String aus = Pruefen.ausgabe(() -> baelle[0] = Training.warmUp(mia));
        assertEquals(5, baelle[0], "Mia hat 20 Konzentration. Sie spielt genau 5 Bälle, dann ist Schluss. Prüfst du balls < 5 in der Bedingung?");
        assertEquals(10, mia.getConcentration(), "Nach 5 Bällen hat Mia 20 - 5 * 2 = 10 Konzentration. Rufst du in jedem Durchlauf player.loseConcentration(2) auf?");
        assertEquals("", aus, "Mia wird nicht müde. Dann darf nichts ausgegeben werden. Steht die Ausgabe in einem if, das nach TIRED fragt?");
    }

    @Test
    @DisplayName("Wird müde: nach 3 Bällen Schluss, mit Ausgabe")
    void wirdMuede() {
        Player tom = new Player("Tom", 5, 3);
        int[] baelle = new int[1];
        String aus = Pruefen.ausgabe(() -> baelle[0] = Training.warmUp(tom));
        assertEquals(3, baelle[0], "Tom hat 5 Konzentration: nach Ball 1 noch 3, nach Ball 2 noch 1, nach Ball 3 ist er TIRED. Dann muss die Schleife aufhören. Fragst du in der Bedingung nach player.getStatus() == PlayerStatus.READY?");
        assertEquals("Tom ist müde\n", aus, "Wenn Tom müde wird, gibst du genau einmal \"Tom ist müde\" aus, mit player.getName() + \" ist müde\".");
    }

    @Test
    @DisplayName("Schon müde: kein Ball")
    void schonMuede() {
        Player ida = new Player("Ida", 1, 3);
        ida.loseConcentration(1);
        int[] baelle = new int[1];
        String aus = Pruefen.ausgabe(() -> baelle[0] = Training.warmUp(ida));
        assertEquals(0, baelle[0], "Ida ist schon zu Beginn TIRED. Die Bedingung ist von Anfang an falsch, es wird kein Ball gespielt.");
        assertEquals("", aus, "Ida spielt keinen Ball. Dann darf auch nichts ausgegeben werden.");
    }
}
