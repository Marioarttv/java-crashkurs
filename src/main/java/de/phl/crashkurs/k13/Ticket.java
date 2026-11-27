package de.phl.crashkurs.k13;

/**
 * Kapitel 13, Aufgabe 4: ein Ticket für das Festival.
 * Diese Datei ist fertig. Ändere sie nicht.
 */
public class Ticket {

    private int code;
    private boolean used = false;

    public Ticket(int code) {
        this.code = code;
    }

    /**
     * Prüft das Ticket am Einlass.
     * Gültig ist ein Ticket, wenn sein Code vierstellig ist (1000 bis 9999) und es noch nicht benutzt wurde.
     * Ist es gültig, gilt es danach als benutzt.
     * Ist es ungültig, wirft die Methode eine IllegalArgumentException. Ihr Text sagt, warum.
     */
    public void check() {
        if (code < 1000 || code > 9999) {
            throw new IllegalArgumentException("Ticket " + code + " gibt es nicht");
        }
        if (used) {
            throw new IllegalArgumentException("Ticket " + code + " wurde schon benutzt");
        }
        used = true;
    }
}
