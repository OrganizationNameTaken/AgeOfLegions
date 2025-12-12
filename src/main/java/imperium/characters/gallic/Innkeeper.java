package imperium.characters.gallic;

import imperium.characters.Character;
import imperium.characters.CharacterAttributes;
import imperium.characters.Gallic;
import imperium.enums.Gender;
import imperium.food.Food;
import imperium.interfaces.Worker;

import java.util.ArrayList;
import java.util.List;

public class Innkeeper extends Gallic implements Worker {
    private List<Food> foodStock;

    public Innkeeper(CharacterAttributes attributes) {
        super(attributes);
        this.foodStock = new ArrayList<>();
    }

    public Innkeeper(String name) {
        this(new CharacterAttributes.Builder(name)
                .gender(Gender.MALE)
                .height(1.80)
                .age(45)
                .strength(35)
                .stamina(40)
                .health(100)
                .belligerence(30)
                .build());
    }

    @Override
    public void work() {
        setStamina(Math.max(0, getStamina() - 5));
    }

    public void addToStock(Food food) {
        foodStock.add(food);
    }

    public Food serveFood(Character c) {
        if (!foodStock.isEmpty()) {
            Food f = foodStock.remove(0);
            c.eat(f);
            return f;
        }
        return null;
    }

    public List<Food> getFoodStock() {
        return new ArrayList<>(foodStock);
    }
}
