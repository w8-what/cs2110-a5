package cs2110;

/**
 * A mage who can cast a fire spell against a monster.
 */
public class FireMage extends Mage {

    /**
     * Class constructor.
     */
    public FireMage(String name, GameEngine engine) {
        super(name, engine);
    }

    @Override
    protected String spellName() { return "fire spell"; }

    /**
     * Identifies the target, rolls the random die, doubles the attack damage, and deals and takes damage in turn with the rules of the gam - full damage for the dude, 1/4 for the player.
     */
    @Override
    protected void castSpell() {
        Monster target = engine.selectMonsterTarget();
        int baseAttackRoll = engine.diceRoll(1, power());
        int fireballDamage = baseAttackRoll * 2;

        target.defend(fireballDamage);
        takeDamage(fireballDamage / 4);
    }
}
