package de.phl.crashkurs.k07;

/**
 * Kapitel 7, Aufgabe 3: eine Prepaid-Karte ändert ihren Status.
 * Die Karte hat zwei Variablen: ein Guthaben (balance) und einen Status (status).
 */
public class PrepaidCard {

    private int balance;
    private CardStatus status = CardStatus.ACTIVE;

    public PrepaidCard(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public CardStatus getStatus() {
        return status;
    }

    /**
     * Bezahlt einen Betrag mit der Karte.
     * Zuerst ziehst du den Betrag vom Guthaben (balance) ab. Danach gilt:
     * Guthaben größer als 0: Status bleibt ACTIVE (CardStatus.ACTIVE).
     * Guthaben genau 0: Status wird EMPTY (CardStatus.EMPTY).
     * Guthaben kleiner als 0: Status wird OVERDRAWN (CardStatus.OVERDRAWN).
     *
     * @param amount der Betrag in Euro
     */
    public void pay(int amount) {
        // TODO: Schreib deinen Code hier zwischen die geschweiften Klammern. Die Zeile darüber bleibt unverändert.
    }
}
