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
     * Selects a monster to attack with a fireball, which doubles the roll of an ordinary attack.
     * Regardless of the success of the fireball attack, its caster always takes damage equal to
     * one quarter (rounded down to the nearest integer) of the fireball's damage.
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
