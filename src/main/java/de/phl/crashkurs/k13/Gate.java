package de.phl.crashkurs.k13;

import java.util.ArrayList;

/**
 * Kapitel 13, Aufgabe 4: der Einlass zum Festivalgelände.
 * Der Einlass zählt, wie viele Leute er hereinlässt (admitted) und wie viele er abweist (rejected).
 */
public class Gate {

    private int admitted = 0;
    private int rejected = 0;

    public int getAdmitted() {
        return admitted;
    }

    public int getRejected() {
        return rejected;
    }

    /**
     * Prüft alle Tickets der Liste nacheinander mit der Methode check() des Tickets.
     * Wirft check() keine Exception: admitted um 1 hochzählen.
     * Wirft check() eine IllegalArgumentException: rejected um 1 hochzählen und
     * "Abgewiesen: " + den Text der Exception ausgeben, zum Beispiel "Abgewiesen: Ticket 99 gibt es nicht".
     * Die Methode prüft immer alle Tickets und bricht nie mit einer Exception ab.
     *
     * @param tickets die Tickets der Leute, die hereinwollen
     */
    public void checkAll(ArrayList<Ticket> tickets) {
        // TODO: Schreib deinen Code hier zwischen die geschweiften Klammern. Die Zeile darüber bleibt unverändert.
    }
}
