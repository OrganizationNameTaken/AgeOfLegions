package imperium.food;

import imperium.enums.FoodType;

public class Food {
    private FoodType type;
    private boolean fresh;
    private int nutritionValue;
    private int magicPower;

    public Food(FoodType type, int nutritionValue, int magicPower) {
        this.type = type;
        this.fresh = true;
        this.nutritionValue = nutritionValue;
        this.magicPower = magicPower;
    }

    public Food(FoodType type, int nutritionValue) {
        this(type, nutritionValue, 0);
    }

    /**
     * Consumes the food. The actual consumption effect is handled by the Character.eat() method.
     * This method exists for potential future extension (e.g., tracking consumption statistics).
     */
    public void eat() {
        // Food consumption is handled by Character.eat() which manages nutrition and health effects
    }

    public void spoil() {
        this.fresh = false;
        this.nutritionValue = Math.max(0, this.nutritionValue / 2);
    }

    public boolean isFresh() {
        return fresh;
    }

    public boolean isVegetable() {
        return type == FoodType.STRAWBERRIES
                || type == FoodType.CARROTS
                || type == FoodType.FRESH_CLOVER
                || type == FoodType.STALE_CLOVER
                || type == FoodType.MISTLETOE;
    }

    public boolean isMagicIngredient() {
        return type == FoodType.MISTLETOE
                || type == FoodType.ROCK_OIL
                || type == FoodType.UNICORN_MILK
                || type == FoodType.IDEFIX_HAIR
                || type == FoodType.SECRET_INGREDIENT;
    }

    public String getMagicEffect() {
        switch (type) {
            case MISTLETOE:
                return "Increases strength";
            case ROCK_OIL:
                return "Provides invulnerability";
            case UNICORN_MILK:
                return "Enhances magic power";
            case IDEFIX_HAIR:
                return "Boosts stamina";
            case SECRET_INGREDIENT:
                return "Unknown powerful effect";
            default:
                return "No magic effect";
        }
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public int getNutritionValue() {
        return nutritionValue;
    }

    public void setNutritionValue(int nutritionValue) {
        this.nutritionValue = nutritionValue;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }

    @Override
    public String toString() {
        return "Food{type=" + type + ", fresh=" + fresh + ", nutritionValue=" + nutritionValue + "}";
    }
}
