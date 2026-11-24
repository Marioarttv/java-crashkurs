package de.phl.crashkurs.k08;

/**
 * Eine Kaffeekarte mit Guthaben in Cent.
 * Diese Datei änderst du nicht.
 */
public class CoffeeCard {

    private int balance;

    public CoffeeCard(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * Zieht einen Betrag vom Guthaben ab.
     *
     * @param amount der Betrag in Cent
     */
    public void pay(int amount) {
        balance = balance - amount;
    }
}
