package imperium.magic;

import imperium.characters.Character;
import imperium.food.Food;

import java.util.ArrayList;
import java.util.List;

public class MagicPotion {
    private int doses;
    private List<Food> ingredients;

    public MagicPotion(int doses) {
        this.doses = doses;
        this.ingredients = new ArrayList<>();
    }

    public MagicPotion() {
        this(3);
    }

    public void addIngredient(Food f) {
        if (ingredients.size() < 10) {
            ingredients.add(f);
        }
    }

    public void doseEffect(Character c) {
        if (doses > 0) {
            int power = calculatePower();
            c.setPotionLevel(c.getPotionLevel() + power);
            c.setStrength(c.getStrength() + power * 2);
            doses--;
        }
    }

    public void cauldronEffect(Character c) {
        int power = calculatePower() * 3;
        c.setPotionLevel(c.getPotionLevel() + power);
        c.setStrength(c.getStrength() + power * 2);
        c.setStamina(c.getStamina() + power);
    }

    public void doubleEffect(Character c) {
        if (doses >= 2) {
            int power = calculatePower() * 2;
            c.setPotionLevel(c.getPotionLevel() + power);
            c.setStrength(c.getStrength() + power * 3);
            doses -= 2;
        }
    }

    private int calculatePower() {
        int basePower = 10;
        for (Food i : ingredients) {
            basePower += i.getMagicPower();
        }
        return basePower;
    }

    public boolean isValid() {
        return PotionRecipe.defaultRecipe().validate(ingredients);
    }

    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }

    public List<Food> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    @Override
    public String toString() {
        return "MagicPotion{doses=" + doses + "}";
    }
}
