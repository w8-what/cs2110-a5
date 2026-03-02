package cs2110;

public class Healer extends Mage {

    public Healer(String name, GameEngine engine) {
        super(name, engine);
    }

    /**
     * Returns the spellName of the Healer
     */
    @Override
    protected String spellName() {
        return "healing spell";
    }

    /**
     * Selects a player to heal. The number of health points sent to that player is a random roll
     * between 0 and this player's health.
     */
    @Override
    protected void castSpell() {
        Player target = engine.selectPlayerTarget();
        int healingPoints = engine.diceRoll(0, power());
        target.heal(healingPoints);
    }
}
