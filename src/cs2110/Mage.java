package cs2110;

/**
 * A player who can either attack normally or cast a spell that ends their turn.
 */
public abstract class Mage extends Player {

    /**
     * Constructs a new mage.
     */
    public Mage(String name, GameEngine engine) {
        super(name, engine);
    }

    /**
     * Prompts the user to optionally cast this mage's spell.
     */
    @Override
    public boolean chooseAction() {
        System.out.print("Would you like to cast a " + spellName() + " (yes/no)? ");
        String input = engine.getInputLine();
        if (input.equals("yes")) {
            castSpell();
            return false;
        }
        return true;
    }

    /**
     * Returns the name of the spell known to this mage.
     */
    protected abstract String spellName();

    /**
     * Carries out this mage's spell behavior.
     */
    protected abstract void castSpell();
}
