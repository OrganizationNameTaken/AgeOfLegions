package imperium.magic;
import imperium.characters.gallic.Druid;
import imperium.enums.FoodType;
import imperium.food.Food;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class MagicPotionTest {
    @Test void testPotionCreation() { MagicPotion potion = new MagicPotion(3); assertEquals(3, potion.getDoses()); assertTrue(potion.getIngredients().isEmpty()); }
    @Test void testAddIngredient() { MagicPotion potion = new MagicPotion(3); Food mistletoe = new Food(FoodType.MISTLETOE, 5, 20); potion.addIngredient(mistletoe); assertEquals(1, potion.getIngredients().size()); }
    @Test void testDoseEffect() { MagicPotion potion = new MagicPotion(3); potion.addIngredient(new Food(FoodType.MISTLETOE, 5, 20)); Druid druid = new Druid("TestDruid"); int initialStrength = druid.getStrength(); int initialPotionLevel = druid.getPotionLevel(); potion.doseEffect(druid); assertTrue(druid.getStrength() > initialStrength); assertTrue(druid.getPotionLevel() > initialPotionLevel); assertEquals(2, potion.getDoses()); }
    @Test void testDruidBrewPotion() { Druid druid = new Druid("Panoramix"); MagicPotion potion = druid.brewPotion(); assertNotNull(potion); assertEquals(3, potion.getDoses()); assertFalse(potion.getIngredients().isEmpty()); }
}
