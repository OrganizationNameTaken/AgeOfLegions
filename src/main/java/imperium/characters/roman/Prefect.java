package imperium.characters.roman;

import imperium.characters.CharacterAttributes;
import imperium.characters.Roman;
import imperium.enums.Gender;
import imperium.interfaces.Leader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Prefect extends Roman implements Leader {
    private static final Logger LOGGER = LoggerFactory.getLogger(Prefect.class);

    public Prefect(CharacterAttributes attributes) {
        super(attributes);
    }

    public Prefect(String name) {
        this(new CharacterAttributes.Builder(name)
                .gender(Gender.MALE)
                .height(1.78)
                .age(45)
                .strength(30)
                .stamina(35)
                .health(100)
                .belligerence(40)
                .build());
    }

    public void giveOrders() {
        LOGGER.info("{} is giving orders.", getName());
    }
}
