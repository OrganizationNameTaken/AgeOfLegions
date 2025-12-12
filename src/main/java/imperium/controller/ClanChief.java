package imperium.controller;

import imperium.characters.Character;
import imperium.characters.gallic.Blacksmith;
import imperium.characters.gallic.Druid;
import imperium.characters.gallic.Innkeeper;
import imperium.characters.gallic.Merchant;
import imperium.characters.roman.General;
import imperium.characters.roman.Legionary;
import imperium.characters.roman.Prefect;
import imperium.characters.creature.Lycanthrope;
import imperium.enums.Gender;
import imperium.locations.Location;
import imperium.locations.Settlement;
import imperium.magic.MagicPotion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Random;

public class ClanChief {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClanChief.class);
    private static final Random RANDOM = new SecureRandom();

    private String name;
    private Gender gender;
    private int age;
    private Settlement location;

    public ClanChief(String name, Gender gender, int age, Settlement location) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.location = location;
    }

    public ClanChief(String name, Settlement location) {
        this(name, Gender.MALE, 55, location);
    }

    public void examineLocation() {
        if (location != null) {
            LOGGER.info("{} examines {}:", name, location.getName());
            location.display();
        }
    }

    public Character createCharacter() {
        int type = RANDOM.nextInt(8);
        Character character;
        switch (type) {
            case 0:
                character = new Druid("Druid_" + System.currentTimeMillis() % 1000);
                break;
            case 1:
                character = new Merchant("Merchant_" + System.currentTimeMillis() % 1000);
                break;
            case 2:
                character = new Innkeeper("Innkeeper_" + System.currentTimeMillis() % 1000);
                break;
            case 3:
                character = new Blacksmith("Blacksmith_" + System.currentTimeMillis() % 1000);
                break;
            case 4:
                character = new Legionary("Legionary_" + System.currentTimeMillis() % 1000);
                break;
            case 5:
                character = new Prefect("Prefect_" + System.currentTimeMillis() % 1000);
                break;
            case 6:
                character = new General("General_" + System.currentTimeMillis() % 1000);
                break;
            default:
                character = new Lycanthrope("Lycanthrope_" + System.currentTimeMillis() % 1000);
                break;
        }
        if (location != null) {
            location.addCharacter(character);
        }
        return character;
    }

    public void healCharacters() {
        if (location != null) {
            location.healCharacters();
            LOGGER.info("{} ordered healing", name);
        }
    }

    public void feedCharacters() {
        if (location != null) {
            location.feedCharacters();
            LOGGER.info("{} ordered feeding", name);
        }
    }

    public MagicPotion requestPotion(Druid druid) {
        if (druid != null && druid.isAlive()) {
            LOGGER.info("{} requests a potion from {}", name, druid.getName());
            return druid.brewPotion();
        }
        return null;
    }

    public void givePotionTo(Character target, MagicPotion potion) {
        if (target != null && potion != null && target.isAlive() && potion.getDoses() > 0) {
            potion.doseEffect(target);
            LOGGER.info("{} gives a potion dose to {}", name, target.getName());
        }
    }

    public void transferCharacter(Character character, Location dest) {
        if (character != null && dest != null && location != null
                && location.getCharacters().contains(character) && dest.canContain(character)) {
            location.removeCharacter(character);
            dest.addCharacter(character);
            LOGGER.info("{} transferred {}", name, character.getName());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Settlement getLocation() {
        return location;
    }

    public void setLocation(Settlement location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ClanChief{name=" + name + ", location=" + (location != null ? location.getName() : "none") + "}";
    }
}
