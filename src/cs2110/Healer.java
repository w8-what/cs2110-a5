package cs2110;

public class Healer extends Mage() {

    public Healer(String name, GameEngine engine) {
        super(name, engine);
    }

    @Override
    protected String spellName() {
        return "healing spell";
    }

    """
      heals players!!!!!!!!! - also rolls the die. yes.
    """
    @Override
    protected void castSpell() {
        Player target = engine.selectPlayerTarget();
        int healingPoints = engine.diceRoll(0, power());
        target.heal(healingPoints);
    }
}
