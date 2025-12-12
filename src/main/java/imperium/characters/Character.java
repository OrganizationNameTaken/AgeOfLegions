package imperium.characters;

import imperium.enums.Gender;
import imperium.food.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Character {
    private static final Logger LOGGER = LoggerFactory.getLogger(Character.class);

    private String name;
    private Gender gender;
    private double height;
    private int age;
    private int strength;
    private int stamina;
    private int health;
    private int hunger;
    private int belligerence;
    private int potionLevel;
    private Food stomach;

    protected Character(CharacterAttributes attributes) {
        this.name = attributes.getName();
        this.gender = attributes.getGender();
        this.height = attributes.getHeight();
        this.age = attributes.getAge();
        this.strength = attributes.getStrength();
        this.stamina = attributes.getStamina();
        this.health = attributes.getHealth();
        this.hunger = 0;
        this.belligerence = attributes.getBelligerence();
        this.potionLevel = 0;
        this.stomach = null;
    }

    public void fight(Character opponent) {
        if (opponent == null || !this.isAlive() || !opponent.isAlive()) {
            return;
        }
        int myPower = strength + (stamina / 2) + (potionLevel * 10);
        int opponentPower = opponent.getStrength() + (opponent.getStamina() / 2) + (opponent.getPotionLevel() * 10);
        int damageToOpponent = Math.max(1, myPower - opponentPower / 2);
        int damageToMe = Math.max(1, opponentPower - myPower / 2);
        opponent.setHealth(opponent.getHealth() - damageToOpponent);
        this.setHealth(this.getHealth() - damageToMe);
        this.stamina = Math.max(0, this.stamina - 5);
        opponent.setStamina(Math.max(0, opponent.getStamina() - 5));
        if (opponent.getHealth() <= 0) {
            opponent.die();
        }
        if (this.getHealth() <= 0) {
            this.die();
        }
    }

    public void heal() {
        if (isAlive()) {
            this.health = Math.min(100, this.health + 20);
        }
    }

    public void eat(Food f) {
        if (f == null || !isAlive()) {
            return;
        }
        if (stomach != null && stomach.getType() == f.getType()) {
            this.health = Math.max(0, this.health - 10);
            LOGGER.info("{} suffers from indigestion!", name);
        } else {
            int nutritionValue = f.getNutritionValue();
            if (!f.isFresh()) {
                nutritionValue /= 2;
            }
            this.hunger = Math.max(0, this.hunger - nutritionValue);
            this.health = Math.min(100, this.health + nutritionValue / 2);
        }
        this.stomach = f;
        f.eat();
    }

    public void drinkPotion(int doses) {
        this.potionLevel += doses * 10;
    }

    public void die() {
        this.health = 0;
        LOGGER.info("{} has died.", name);
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void increaseHunger() {
        if (isAlive()) {
            this.hunger = Math.min(100, this.hunger + 5);
            if (this.hunger >= 100) {
                this.health = Math.max(0, this.health - 5);
                if (this.health <= 0) {
                    die();
                }
            }
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }
    public int getStamina() { return stamina; }
    public void setStamina(int stamina) { this.stamina = stamina; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = Math.max(0, health); }
    public int getHunger() { return hunger; }
    public void setHunger(int hunger) { this.hunger = hunger; }
    public int getBelligerence() { return belligerence; }
    public void setBelligerence(int belligerence) { this.belligerence = belligerence; }
    public int getPotionLevel() { return potionLevel; }
    public void setPotionLevel(int potionLevel) { this.potionLevel = potionLevel; }
    public Food getStomach() { return stomach; }
    public void setStomach(Food stomach) { this.stomach = stomach; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name=" + name + ", health=" + health + ", strength=" + strength + "}";
    }
}
