package de.phl.crashkurs.k09;

/**
 * Eine Tischtennisspielerin oder ein Tischtennisspieler.
 * Jeder Ball, den der Gegner spielt, kostet Konzentration (concentration).
 * Ist die Konzentration bei 0 oder darunter, ist der Status TIRED.
 * Diese Datei änderst du nicht.
 */
public class Player {

    private String name;
    private int concentration;
    private int power;
    private PlayerStatus status = PlayerStatus.READY;

    public Player(String name, int concentration, int power) {
        this.name = name;
        this.concentration = concentration;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public int getConcentration() {
        return concentration;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * Zieht Konzentration ab. Ist sie danach 0 oder kleiner, wird der Status TIRED.
     *
     * @param amount wie viel Konzentration verloren geht
     */
    public void loseConcentration(int amount) {
        concentration -= amount;
        if (concentration <= 0) {
            status = PlayerStatus.TIRED;
        }
    }

    /**
     * Spielt einen Ball zum Gegner. Das kostet den Gegner so viel Konzentration,
     * wie dieser Spieler Kraft (power) hat.
     *
     * @param opponent der Gegner
     * @return ein Satz, der den Schlag beschreibt, zum Beispiel "Lea spielt zu Jonas"
     */
    public String hit(Player opponent) {
        opponent.loseConcentration(power);
        return name + " spielt zu " + opponent.getName();
    }
}
