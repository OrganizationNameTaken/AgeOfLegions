package imperium.magic;

import imperium.enums.FoodType;
import imperium.food.Food;

import java.util.ArrayList;
import java.util.List;

public class PotionRecipe {
    private List<FoodType> requiredIngredients;
    private List<FoodType> optionalIngredients;

    public PotionRecipe(List<FoodType> requiredIngredients, List<FoodType> optionalIngredients) {
        this.requiredIngredients = new ArrayList<>(requiredIngredients);
        this.optionalIngredients = new ArrayList<>(optionalIngredients);
    }

    public static PotionRecipe defaultRecipe() {
        List<FoodType> req = new ArrayList<>();
        req.add(FoodType.MISTLETOE);
        req.add(FoodType.ROCK_OIL);
        List<FoodType> opt = new ArrayList<>();
        opt.add(FoodType.UNICORN_MILK);
        return new PotionRecipe(req, opt);
    }

    public boolean validate(List<Food> ingredients) {
        if (ingredients == null || ingredients.size() < requiredIngredients.size()) {
            return false;
        }
        List<FoodType> provided = new ArrayList<>();
        for (Food f : ingredients) {
            provided.add(f.getType());
        }
        for (FoodType r : requiredIngredients) {
            if (!provided.contains(r)) {
                return false;
            }
            provided.remove(r);
        }
        return true;
    }

    public String getEffect() {
        return "Grants superhuman strength";
    }

    public List<FoodType> getRequiredIngredients() {
        return new ArrayList<>(requiredIngredients);
    }

    public List<FoodType> getOptionalIngredients() {
        return new ArrayList<>(optionalIngredients);
    }
}
