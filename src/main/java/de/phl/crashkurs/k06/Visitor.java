package de.phl.crashkurs.k06;

/**
 * Kapitel 6, Aufgabe 1: mitzählen, wie viele Objekte erzeugt wurden.
 * Jedes Mal, wenn jemand ins Bürgeramt kommt, entsteht ein neues Visitor-Objekt.
 * Die Klasse soll zählen, wie viele Besucherinnen und Besucher es insgesamt gab.
 */
public class Visitor {

    // TODO: So gehört der Zähler jedem Objekt einzeln. Er soll aber der Klasse gehören.
    private int count;

    public Visitor() {
        // TODO: Zähle hier den Zähler um 1 hoch.
    }

    /**
     * Gibt zurück, wie viele Visitor-Objekte bisher erzeugt wurden.
     * Diese Methode ist static: Man ruft sie ohne Objekt auf, mit Visitor.getCount().
     */
    public static int getCount() {
        // TODO: Ersetze die nächste Zeile. Die Methode soll den Zähler zurückgeben.
        return 0;
    }
}
