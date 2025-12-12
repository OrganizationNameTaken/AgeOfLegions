package imperium.characters.gallic;

import imperium.characters.CharacterAttributes;
import imperium.characters.Gallic;
import imperium.enums.Gender;
import imperium.interfaces.Worker;

public class Blacksmith extends Gallic implements Worker {
    private int itemsForged;

    public Blacksmith(CharacterAttributes attributes) {
        super(attributes);
        this.itemsForged = 0;
    }

    public Blacksmith(String name) {
        this(new CharacterAttributes.Builder(name)
                .gender(Gender.MALE)
                .height(1.85)
                .age(50)
                .strength(60)
                .stamina(50)
                .health(100)
                .belligerence(25)
                .build());
    }

    @Override
    public void work() {
        itemsForged++;
        setStamina(Math.max(0, getStamina() - 10));
    }

    public int getItemsForged() {
        return itemsForged;
    }
}
