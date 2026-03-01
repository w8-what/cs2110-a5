package cs2110;

/**
 * A player who can equip a single weapon that modifies their power and toughness.
 */
public class Fighter extends Player {

    /**
     * The weapon currently equipped by this fighter, or null if no weapon is equipped.
     */
    private Weapon equippedWeapon;

    /**
     * Constructs a new fighter with no equipped weapon.
     */
    public Fighter(String name, GameEngine engine) {
        super(name, engine);
        equippedWeapon = null;
    }

    @Override
    public int power() {
        int weaponBonus = 0;
        if (equippedWeapon != null) {
            weaponBonus = equippedWeapon.power();
        }
        return super.power() + weaponBonus;
    }

    @Override
    public int toughness() {
        int weaponBonus = 0;
        if (equippedWeapon != null) {
            weaponBonus = equippedWeapon.toughness();
        }
        return super.toughness() + weaponBonus;
    }

    /**
     * Prompts the user to optionally change this fighter's equipment before attacking.
     */
    @Override
    public boolean chooseAction() {
        System.out.print("Would you like to change your current equipment (yes/no)? ");
        String input = engine.getInputLine();
        if (input.equals("yes")) {
            Weapon chosenWeapon = engine.selectWeapon();
            equipWeapon(chosenWeapon);
        }
        return true;
    }

    /**
     * Equips `newWeapon`, unequipping the previously equipped weapon first if one exists. If
     * `newWeapon` is null, this fighter will end with no equipped weapon.
     */
    private void equipWeapon(Weapon newWeapon) {
        if (equippedWeapon != null) {
            equippedWeapon.unequip();
        }
        equippedWeapon = newWeapon;
        if (equippedWeapon != null) {
            equippedWeapon.equip();
        }
    }

    /**
     * Unequips this fighter's weapon if they currently have one equipped.
     */
    private void dropWeapon() {
        if (equippedWeapon != null) {
            equippedWeapon.unequip();
            equippedWeapon = null;
        }
    }

    /**
     * Drops this fighter's weapon and then processes death as a normal player.
     */
    @Override
    protected void processDeath() {
        dropWeapon();
        super.processDeath();
    }
}
