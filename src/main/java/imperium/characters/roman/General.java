package imperium.characters.roman;

import imperium.characters.Character;
import imperium.characters.CharacterAttributes;
import imperium.characters.Roman;
import imperium.enums.Gender;
import imperium.interfaces.Fighter;
import imperium.interfaces.Leader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class General extends Roman implements Fighter, Leader {
    private static final Logger LOGGER = LoggerFactory.getLogger(General.class);

    public General(CharacterAttributes attributes) {
        super(attributes);
    }

    public General(String name) {
        this(new CharacterAttributes.Builder(name)
                .gender(Gender.MALE)
                .height(1.82)
                .age(50)
                .strength(55)
                .stamina(45)
                .health(100)
                .belligerence(70)
                .build());
    }

    @Override
    public void attack(Character opponent) {
        if (opponent == null || !isAlive() || !opponent.isAlive()) {
            return;
        }
        int damage = (int) (getStrength() * 1.5) + (getPotionLevel() * 5);
        opponent.setHealth(opponent.getHealth() - damage);
        setStamina(Math.max(0, getStamina() - 7));
        if (opponent.getHealth() <= 0) {
            opponent.die();
        }
    }

    @Override
    public void defend() {
        setStamina(Math.max(0, getStamina() - 4));
    }

    public void commandTroops() {
        LOGGER.info("{} is commanding troops!", getName());
    }
}
