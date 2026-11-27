package de.phl.crashkurs.k09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 9, Aufgabe 2: BusRide.watchVideos")
class BusRideTest {

    @Test
    @DisplayName("Voller Akku, kurze Fahrt: genau maxVideos Videos")
    void grenzeDerFahrt() {
        Phone phone = new Phone(100);
        assertEquals(3, (int) Pruefen.mitZeitlimit(() -> BusRide.watchVideos(phone, 3)),
                "Bei 100 Prozent Akku und maxVideos = 3 laufen genau 3 Videos. Prüfst du videos < maxVideos in der Bedingung?");
        assertEquals(40, phone.getBattery(), "Nach 3 Videos hat das Handy 100 - 3 * 20 = 40 Prozent. Rufst du in jedem Durchlauf phone.playVideo() auf?");
    }

    @Test
    @DisplayName("Akku reicht nicht für die ganze Fahrt")
    void akkuReichtNicht() {
        Phone phone = new Phone(50);
        assertEquals(2, (int) Pruefen.mitZeitlimit(() -> BusRide.watchVideos(phone, 5)),
                "Bei 50 Prozent laufen 2 Videos: Danach hat der Akku 10 Prozent, und das reicht nicht für ein drittes. Prüfst du phone.getBattery() >= 20 in der Bedingung?");
        assertEquals(10, phone.getBattery(), "Nach 2 Videos muss das Handy noch 10 Prozent haben. Startet bei dir ein Video, obwohl der Akku unter 20 Prozent ist?");
    }

    @Test
    @DisplayName("Genau 20 Prozent: noch ein Video")
    void grenzeZwanzig() {
        Phone phone = new Phone(20);
        assertEquals(1, (int) Pruefen.mitZeitlimit(() -> BusRide.watchVideos(phone, 4)),
                "Bei genau 20 Prozent reicht der Akku noch für ein Video (20 zählt mit). Brauchst du >= statt >?");
    }

    @Test
    @DisplayName("Fast leer: kein Video")
    void fastLeer() {
        Phone phone = new Phone(10);
        assertEquals(0, (int) Pruefen.mitZeitlimit(() -> BusRide.watchVideos(phone, 4)),
                "Bei 10 Prozent ist die Bedingung von Anfang an falsch. Es läuft kein Video, das Ergebnis ist 0.");
        assertEquals(10, phone.getBattery(), "Läuft kein Video, bleibt der Akku bei 10 Prozent.");
    }

    @Test
    @DisplayName("Die Methode benutzt while")
    void benutztWhile() throws IOException {
        assertTrue(Pruefen.benutzt("BusRide.java", "while"), "In der Methode watchVideos soll eine while-Schleife stehen.");
    }
}
