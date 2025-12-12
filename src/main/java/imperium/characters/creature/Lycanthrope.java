package imperium.characters.creature;

import imperium.characters.Character;
import imperium.characters.CharacterAttributes;
import imperium.characters.FantasticCreature;
import imperium.enums.Gender;
import imperium.interfaces.Fighter;
import imperium.interfaces.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lycanthrope extends FantasticCreature implements Fighter, Transformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Lycanthrope.class);

    private boolean transformed;
    private int baseStrength;
    private int baseStamina;

    public Lycanthrope(CharacterAttributes attributes) {
        super(attributes);
        this.transformed = false;
        this.baseStrength = attributes.getStrength();
        this.baseStamina = attributes.getStamina();
    }

    public Lycanthrope(String name) {
        this(new CharacterAttributes.Builder(name)
                .gender(Gender.MALE)
                .height(1.90)
                .age(100)
                .strength(40)
                .stamina(50)
                .health(100)
                .belligerence(80)
                .build());
    }

    @Override
    public void attack(Character opponent) {
        if (opponent == null || !isAlive() || !opponent.isAlive()) {
            return;
        }
        int damage = getStrength();
        if (transformed) {
            damage = (int) (damage * 1.5);
        }
        damage += getPotionLevel() * 4;
        opponent.setHealth(opponent.getHealth() - damage);
        setStamina(Math.max(0, getStamina() - 6));
        if (opponent.getHealth() <= 0) {
            opponent.die();
        }
    }

    @Override
    public void defend() {
        if (transformed) {
            setStamina(Math.max(0, getStamina() - 2));
        } else {
            setStamina(Math.max(0, getStamina() - 4));
        }
    }

    @Override
    public void transform() {
        if (!transformed) {
            transformed = true;
            setStrength(baseStrength * 2);
            setStamina((int) (baseStamina * 1.5));
            setBelligerence(Math.min(100, getBelligerence() + 30));
            LOGGER.info("{} transforms into a fearsome beast!", getName());
        }
    }

    @Override
    public void revertForm() {
        if (transformed) {
            transformed = false;
            setStrength(baseStrength);
            setStamina(baseStamina);
            setBelligerence(Math.max(0, getBelligerence() - 30));
            LOGGER.info("{} reverts to human form.", getName());
        }
    }

    public boolean isTransformed() {
        return transformed;
    }
}
