package imperium.locations;
import imperium.characters.gallic.Druid;
import imperium.enums.FoodType;
import imperium.food.Food;
import imperium.locations.settlements.GallicVillage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class LocationTest {
    private GallicVillage village;
    private Druid druid;
    @BeforeEach void setUp() { village = new GallicVillage("TestVillage", 100); druid = new Druid("TestDruid"); }
    @Test void testAddCharacter() { village.addCharacter(druid); assertEquals(1, village.getNbCharacters()); assertTrue(village.getCharacters().contains(druid)); }
    @Test void testRemoveCharacter() { village.addCharacter(druid); village.removeCharacter(druid); assertEquals(0, village.getNbCharacters()); }
    @Test void testCanContain() { for (int i = 0; i < 10; i++) village.addCharacter(new Druid("Druid" + i)); assertFalse(village.canContain(druid)); }
    @Test void testHealCharacters() { druid.setHealth(50); village.addCharacter(druid); village.healCharacters(); assertEquals(70, druid.getHealth()); }
    @Test void testFeedCharacters() { Food boar = new Food(FoodType.WILD_BOAR, 30); village.addFood(boar); druid.setHunger(50); village.addCharacter(druid); village.feedCharacters(); assertTrue(druid.getHunger() < 50); }
    @Test void testSpoilFood() { Food boar = new Food(FoodType.WILD_BOAR, 30); village.addFood(boar); village.spoilFood(); Food spoiled = village.getFoods().get(0); assertFalse(spoiled.isFresh()); }
}
