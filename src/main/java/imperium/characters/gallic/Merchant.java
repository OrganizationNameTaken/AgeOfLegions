package imperium.characters.gallic;

import imperium.characters.CharacterAttributes;
import imperium.characters.Gallic;
import imperium.enums.Gender;
import imperium.interfaces.Worker;

public class Merchant extends Gallic implements Worker {
    private int gold;

    public Merchant(CharacterAttributes attributes) {
        super(attributes);
        this.gold = 100;
    }

    public Merchant(String name) {
        this(new CharacterAttributes.Builder(name)
                .gender(Gender.MALE)
                .height(1.70)
                .age(40)
                .strength(20)
                .stamina(30)
                .health(100)
                .belligerence(10)
                .build());
    }

    @Override
    public void work() {
        gold += 10;
        setStamina(Math.max(0, getStamina() - 5));
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
