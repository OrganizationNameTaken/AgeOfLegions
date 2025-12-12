package imperium.food;
import imperium.enums.FoodType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class FoodTest {
    @Test void testFoodCreation() { Food boar = new Food(FoodType.WILD_BOAR, 30); assertEquals(FoodType.WILD_BOAR, boar.getType()); assertEquals(30, boar.getNutritionValue()); assertTrue(boar.isFresh()); }
    @Test void testSpoil() { Food boar = new Food(FoodType.WILD_BOAR, 30); boar.spoil(); assertFalse(boar.isFresh()); assertEquals(15, boar.getNutritionValue()); }
    @Test void testIsVegetable() { Food carrot = new Food(FoodType.CARROTS, 10); Food boar = new Food(FoodType.WILD_BOAR, 30); assertTrue(carrot.isVegetable()); assertFalse(boar.isVegetable()); }
    @Test void testIsMagicIngredient() { Food mistletoe = new Food(FoodType.MISTLETOE, 5); Food boar = new Food(FoodType.WILD_BOAR, 30); assertTrue(mistletoe.isMagicIngredient()); assertFalse(boar.isMagicIngredient()); }
}
