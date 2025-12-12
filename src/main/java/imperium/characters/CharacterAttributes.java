package imperium.characters;

import imperium.enums.Gender;

/**
 * Builder class to hold character attributes, reducing constructor parameters.
 */
public class CharacterAttributes {
    private final String name;
    private final Gender gender;
    private final double height;
    private final int age;
    private final int strength;
    private final int stamina;
    private final int health;
    private final int belligerence;

    private CharacterAttributes(Builder builder) {
        this.name = builder.name;
        this.gender = builder.gender;
        this.height = builder.height;
        this.age = builder.age;
        this.strength = builder.strength;
        this.stamina = builder.stamina;
        this.health = builder.health;
        this.belligerence = builder.belligerence;
    }

    public String getName() { return name; }
    public Gender getGender() { return gender; }
    public double getHeight() { return height; }
    public int getAge() { return age; }
    public int getStrength() { return strength; }
    public int getStamina() { return stamina; }
    public int getHealth() { return health; }
    public int getBelligerence() { return belligerence; }

    public static class Builder {
        private String name;
        private Gender gender = Gender.MALE;
        private double height = 1.75;
        private int age = 30;
        private int strength = 50;
        private int stamina = 50;
        private int health = 100;
        private int belligerence = 50;

        public Builder(String name) {
            this.name = name;
        }

        public Builder gender(Gender gender) { this.gender = gender; return this; }
        public Builder height(double height) { this.height = height; return this; }
        public Builder age(int age) { this.age = age; return this; }
        public Builder strength(int strength) { this.strength = strength; return this; }
        public Builder stamina(int stamina) { this.stamina = stamina; return this; }
        public Builder health(int health) { this.health = health; return this; }
        public Builder belligerence(int belligerence) { this.belligerence = belligerence; return this; }

        public CharacterAttributes build() {
            return new CharacterAttributes(this);
        }
    }
}

