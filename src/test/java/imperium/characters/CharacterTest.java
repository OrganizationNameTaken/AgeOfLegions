package imperium.characters;

import imperium.characters.gallic.Druid;
import imperium.enums.FoodType;
import imperium.enums.Gender;
import imperium.food.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    private Druid druid;

    @BeforeEach
    void setUp() {
        druid = new Druid(new CharacterAttributes.Builder("TestDruid")
                .gender(Gender.MALE)
                .height(1.75)
                .age(50)
                .strength(40)
                .stamina(50)
                .health(100)
                .belligerence(30)
                .build());
    }

    @Test
    void testCharacterCreation() {
        assertEquals("TestDruid", druid.getName());
        assertEquals(Gender.MALE, druid.getGender());
        assertEquals(100, druid.getHealth());
        assertEquals(40, druid.getStrength());
    }

    @Test
    void testEatDifferentFood() {
        Food boar = new Food(FoodType.WILD_BOAR, 30);
        Food fish = new Food(FoodType.PASSABLE_FISH, 20);
        druid.setHunger(50);
        druid.eat(boar);
        assertTrue(druid.getHunger() < 50);
        druid.eat(fish);
    }

    @Test
    void testEatSameFoodCausesIndigestion() {
        Food boar1 = new Food(FoodType.WILD_BOAR, 30);
        Food boar2 = new Food(FoodType.WILD_BOAR, 30);
        druid.setHunger(50);
        druid.eat(boar1);
        int healthAfterFirstMeal = druid.getHealth();
        druid.eat(boar2);
        assertEquals(healthAfterFirstMeal - 10, druid.getHealth());
    }

    @Test
    void testHeal() {
        druid.setHealth(50);
        druid.heal();
        assertEquals(70, druid.getHealth());
        druid.setHealth(90);
        druid.heal();
        assertEquals(100, druid.getHealth());
    }

    @Test
    void testFight() {
        Druid opponent = new Druid("Opponent");
        int initialHealth = opponent.getHealth();
        druid.fight(opponent);
        assertTrue(opponent.getHealth() < initialHealth);
    }

    @Test
    void testDie() {
        druid.setHealth(0);
        druid.die();
        assertFalse(druid.isAlive());
        assertEquals(0, druid.getHealth());
    }

    @Test
    void testIncreaseHunger() {
        druid.setHunger(0);
        druid.increaseHunger();
        assertEquals(5, druid.getHunger());
    }

    @Test
    void testDrinkPotion() {
        druid.drinkPotion(2);
        assertEquals(20, druid.getPotionLevel());
    }
}
