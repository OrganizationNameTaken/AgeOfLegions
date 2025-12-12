package imperium.locations;

import imperium.characters.Character;
import imperium.food.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Location {
    private static final Logger LOGGER = LoggerFactory.getLogger(Location.class);

    private String name;
    private double area;
    private int nbCharacters;
    private List<Character> characters;
    private List<Food> foods;

    protected Location(String name, double area) {
        this.name = name;
        this.area = area;
        this.nbCharacters = 0;
        this.characters = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    public void display() {
        LOGGER.info("=== {} ===", name);
        LOGGER.info("Area: {} sq units, Population: {}/{}", area, nbCharacters, getMaxCapacity());
        for (Character c : characters) {
            LOGGER.info("  - {}", c);
        }
    }

    public void addCharacter(Character c) {
        if (c != null && canContain(c)) {
            characters.add(c);
            nbCharacters++;
        }
    }

    public void removeCharacter(Character c) {
        if (c != null && characters.remove(c)) {
            nbCharacters--;
        }
    }

    /**
     * Checks if the location can contain an additional character.
     * The parameter is reserved for future character-specific capacity checks.
     * @param c the character to check (currently unused, reserved for future use)
     * @return true if there is room for another character
     */
    public boolean canContain(Character c) {
        return nbCharacters < getMaxCapacity();
    }

    protected int getMaxCapacity() {
        return (int) (area / 10);
    }

    public void healCharacters() {
        for (Character c : characters) {
            if (c.isAlive()) {
                c.heal();
            }
        }
    }

    public void feedCharacters() {
        for (Character c : characters) {
            if (c.isAlive() && !foods.isEmpty() && c.getHunger() > 30) {
                Food f = foods.remove(0);
                c.eat(f);
            }
        }
    }

    public void addFood(Food food) {
        if (food != null) {
            foods.add(food);
        }
    }

    public Food removeFood(int index) {
        if (index >= 0 && index < foods.size()) {
            return foods.remove(index);
        }
        return null;
    }

    public void sortByStrengthDescending() {
        characters.sort((c1, c2) -> Integer.compare(c2.getStrength(), c1.getStrength()));
    }

    public void sortByHealthAscending() {
        characters.sort(Comparator.comparingInt(Character::getHealth));
    }

    public void spoilFood() {
        for (Food f : foods) {
            f.spoil();
        }
        List<Food> toRemove = new ArrayList<>();
        for (Food f : foods) {
            if (f.getNutritionValue() <= 0) {
                toRemove.add(f);
            }
        }
        foods.removeAll(toRemove);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getNbCharacters() {
        return nbCharacters;
    }

    public List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }

    public List<Food> getFoods() {
        return new ArrayList<>(foods);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name=" + name + ", population=" + nbCharacters + "}";
    }
}
