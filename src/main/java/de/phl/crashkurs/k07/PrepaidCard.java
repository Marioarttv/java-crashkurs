package de.phl.crashkurs.k07;

/**
 * Kapitel 7, Aufgabe 3: Die Prepaid-Karte warnt, wenn das Guthaben knapp wird.
 * Die Karte hat ein Attribut: ihr Guthaben in Euro (credit).
 */
public class PrepaidCard {

    private int credit;

    public PrepaidCard(int credit) {
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    /**
     * Gibt nach dem Guthaben (credit) einen Hinweis zurück:
     * ab 20 Euro: "genug Guthaben".
     * 5 bis 19 Euro: "bald aufladen".
     * unter 5 Euro: "jetzt aufladen".
     *
     * @return der Hinweis als Text
     */
    public String warning() {
        // TODO: Ersetze die nächste Zeile durch eine Kette aus if, else if und else. Die Zeile darüber bleibt unverändert.
        return "";
    }
}
