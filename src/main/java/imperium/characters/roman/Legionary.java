package imperium.characters.roman;

import imperium.characters.Character;
import imperium.characters.CharacterAttributes;
import imperium.characters.Roman;
import imperium.enums.Gender;
import imperium.interfaces.Fighter;

public class Legionary extends Roman implements Fighter {

    public Legionary(CharacterAttributes attributes) {
        super(attributes);
    }

    public Legionary(String name) {
        this(new CharacterAttributes.Builder(name)
                .gender(Gender.MALE)
                .height(1.75)
                .age(25)
                .strength(50)
                .stamina(60)
                .health(100)
                .belligerence(60)
                .build());
    }

    @Override
    public void attack(Character opponent) {
        if (opponent == null || !isAlive() || !opponent.isAlive()) {
            return;
        }
        int damage = getStrength() + (getPotionLevel() * 3);
        opponent.setHealth(opponent.getHealth() - damage);
        setStamina(Math.max(0, getStamina() - 5));
        if (opponent.getHealth() <= 0) {
            opponent.die();
        }
    }

    @Override
    public void defend() {
        setStamina(Math.max(0, getStamina() - 3));
    }
}
