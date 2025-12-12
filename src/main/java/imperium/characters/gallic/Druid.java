package imperium.characters.gallic;

import imperium.characters.Character;
import imperium.characters.CharacterAttributes;
import imperium.characters.Gallic;
import imperium.enums.FoodType;
import imperium.enums.Gender;
import imperium.food.Food;
import imperium.interfaces.Fighter;
import imperium.interfaces.Leader;
import imperium.interfaces.PotionMaker;
import imperium.magic.MagicPotion;

import java.util.ArrayList;
import java.util.List;

public class Druid extends Gallic implements PotionMaker, Fighter, Leader {
    private List<Food> ingredientStock;
    private MagicPotion cauldron;

    public Druid(CharacterAttributes attributes) {
        super(attributes);
        this.ingredientStock = new ArrayList<>();
        this.cauldron = null;
    }

    public Druid(String name) {
        this(new CharacterAttributes.Builder(name)
                .gender(Gender.MALE)
                .height(1.75)
                .age(80)
                .strength(30)
                .stamina(40)
                .health(100)
                .belligerence(20)
                .build());
    }

    @Override
    public MagicPotion brewPotion() {
        MagicPotion potion = new MagicPotion(3);
        if (ingredientStock.isEmpty()) {
            potion.addIngredient(new Food(FoodType.MISTLETOE, 5, 20));
            potion.addIngredient(new Food(FoodType.ROCK_OIL, 0, 30));
        } else {
            for (Food i : ingredientStock) {
                if (i.isMagicIngredient()) {
                    potion.addIngredient(i);
                }
            }
            ingredientStock.clear();
        }
        return potion;
    }

    public void brewCauldron() {
        cauldron = new MagicPotion(10);
        cauldron.addIngredient(new Food(FoodType.MISTLETOE, 5, 30));
        cauldron.addIngredient(new Food(FoodType.ROCK_OIL, 0, 40));
    }

    @Override
    public void attack(Character opponent) {
        if (opponent == null || !isAlive() || !opponent.isAlive()) {
            return;
        }
        int damage = (getStrength() + getPotionLevel() * 5) / 2;
        opponent.setHealth(opponent.getHealth() - damage);
        if (opponent.getHealth() <= 0) {
            opponent.die();
        }
    }

    @Override
    public void defend() {
        setHealth(Math.min(100, getHealth() + 5));
    }

    public void addIngredient(Food food) {
        ingredientStock.add(food);
    }

    public MagicPotion getCauldron() {
        return cauldron;
    }

    public void giveCauldronDose(Character c) {
        if (cauldron != null && cauldron.getDoses() > 0) {
            cauldron.doseEffect(c);
        }
    }
}
