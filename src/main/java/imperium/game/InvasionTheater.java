package imperium.game;

import imperium.characters.Character;
import imperium.characters.gallic.Blacksmith;
import imperium.characters.gallic.Druid;
import imperium.characters.gallic.Merchant;
import imperium.characters.roman.Legionary;
import imperium.characters.roman.Prefect;
import imperium.characters.creature.Lycanthrope;
import imperium.controller.ClanChief;
import imperium.enums.FoodType;
import imperium.food.Food;
import imperium.locations.Location;
import imperium.locations.settlements.GallicVillage;
import imperium.locations.settlements.RomanFortifiedCamp;
import imperium.locations.wilderness.Battlefield;
import imperium.magic.MagicPotion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InvasionTheater {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvasionTheater.class);
    private static final Random RANDOM = new SecureRandom();

    private static final int TIME_PER_TURN = 10;
    private static final int COST_POTION = 4;
    private static final int COST_FEED = 2;
    private static final int COST_HEAL = 3;
    private static final int COST_BATTLE = 5;
    private static final int COST_GATHER = 3;

    private static final String CHOICE_PROMPT = "Choice: ";
    private static final String HP_PREFIX = " (HP:";
    private static final String TIME_SUFFIX = " time]";

    private String name;
    private int maxLocations;
    private List<Location> locations;
    private List<ClanChief> clanChiefs;
    private int turn;
    private Scanner scanner;
    private ClanChief player;
    private GallicVillage playerVillage;
    private Druid druid;
    private boolean running;
    private int timeRemaining;

    public InvasionTheater(String name, int maxLocations) {
        this.name = name;
        this.maxLocations = maxLocations;
        this.locations = new ArrayList<>();
        this.clanChiefs = new ArrayList<>();
        this.turn = 1;
        this.timeRemaining = TIME_PER_TURN;
        this.running = true;
    }

    public void displayLocations() {
        LOGGER.info("\n=== WORLD MAP ===");
        for (Location loc : locations) {
            loc.display();
            LOGGER.info("");
        }
    }

    public int countCharacters() {
        int total = 0;
        for (Location loc : locations) {
            total += loc.getNbCharacters();
        }
        return total;
    }

    public boolean addLocation(Location loc) {
        if (loc != null && locations.size() < maxLocations) {
            locations.add(loc);
            return true;
        }
        return false;
    }

    public void addClanChief(ClanChief chief) {
        if (chief != null) {
            clanChiefs.add(chief);
        }
    }

    private void setup() {
        scanner = new Scanner(System.in);
        LOGGER.info("========================================");
        LOGGER.info("   AGE OF LEGIONS - ROMAN GAUL");
        LOGGER.info("========================================");
        LOGGER.info("Enter your name: ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) {
            playerName = "Abraracourcix";
        }
        LOGGER.info("Enter your village name: ");
        String villageName = scanner.nextLine().trim();
        if (villageName.isEmpty()) {
            villageName = "Aremorica";
        }
        playerVillage = new GallicVillage(villageName, 500);
        addLocation(playerVillage);
        RomanFortifiedCamp romanCamp = new RomanFortifiedCamp("Camp Babaorum", 800);
        Battlefield battlefield = new Battlefield("Plains of Alesia", 5000);
        addLocation(romanCamp);
        addLocation(battlefield);
        player = new ClanChief(playerName, playerVillage);
        addClanChief(player);
        druid = new Druid("Panoramix");
        playerVillage.addCharacter(druid);
        playerVillage.addCharacter(new Blacksmith("Cetautomatix"));
        playerVillage.addCharacter(new Merchant("Bonemine"));
        romanCamp.addCharacter(new Legionary("Marcus"));
        romanCamp.addCharacter(new Legionary("Brutus"));
        romanCamp.addCharacter(new Prefect("Caius Bonus"));
        battlefield.addCharacter(new Lycanthrope("Fenrir"));
        playerVillage.addFood(new Food(FoodType.WILD_BOAR, 30));
        playerVillage.addFood(new Food(FoodType.WILD_BOAR, 30));
        playerVillage.addFood(new Food(FoodType.PASSABLE_FISH, 20));
        LOGGER.info("\nWelcome Chief {}!", playerName);
        LOGGER.info("Each turn you have {} time points to spend.", TIME_PER_TURN);
        LOGGER.info("Choose your actions wisely!\n");
    }

    private void menu() {
        LOGGER.info("\n--- TURN {} | Time left: {}/{} ---", turn, timeRemaining, TIME_PER_TURN);
        LOGGER.info("1. View village     [FREE]");
        LOGGER.info("2. View world       [FREE]");
        LOGGER.info("3. Request potion   [{}" + TIME_SUFFIX, COST_POTION);
        LOGGER.info("4. Feed villagers   [{}" + TIME_SUFFIX, COST_FEED);
        LOGGER.info("5. Heal villagers   [{}" + TIME_SUFFIX, COST_HEAL);
        LOGGER.info("6. Go to battle     [{}" + TIME_SUFFIX, COST_BATTLE);
        LOGGER.info("7. Gather food      [{}" + TIME_SUFFIX, COST_GATHER);
        LOGGER.info("8. End turn");
        LOGGER.info("0. Quit");
        LOGGER.info(CHOICE_PROMPT);
    }

    private int readChoice() {
        try {
            String line = scanner.nextLine();
            if (line == null || line.trim().isEmpty()) {
                return -1;
            }
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) { // NOSONAR - unnamed patterns require Java 21+
            return -1;
        }
    }

    private boolean spendTime(int cost) {
        if (cost > timeRemaining) {
            LOGGER.info("Not enough time! Need {}, have {}", cost, timeRemaining);
            return false;
        }
        timeRemaining -= cost;
        return true;
    }

    private void viewVillage() {
        playerVillage.display();
        LOGGER.info("Food stock: {}", playerVillage.getFoods().size());
        List<Food> foods = playerVillage.getFoods();
        for (int i = 0; i < foods.size(); i++) {
            Food f = foods.get(i);
            LOGGER.info("  {}. {} (N:{})", i + 1, f.getType(), f.getNutritionValue());
        }
    }

    private void requestPotion() {
        if (!spendTime(COST_POTION)) {
            return;
        }
        if (druid == null || !druid.isAlive()) {
            LOGGER.info("No druid available!");
            return;
        }
        MagicPotion potion = player.requestPotion(druid);
        LOGGER.info("Potion ready! Doses: {}", potion.getDoses());
        List<Character> villagers = playerVillage.getCharacters();
        LOGGER.info("Give to whom?");
        for (int i = 0; i < villagers.size(); i++) {
            Character character = villagers.get(i);
            LOGGER.info("{}. {} (STR:{})", i + 1, character.getName(), character.getStrength());
        }
        LOGGER.info("0. Nobody");
        LOGGER.info(CHOICE_PROMPT);
        int choice = readChoice();
        if (choice > 0 && choice <= villagers.size()) {
            Character target = villagers.get(choice - 1);
            int oldStr = target.getStrength();
            player.givePotionTo(target, potion);
            LOGGER.info("{} drinks! STR: {} -> {}", target.getName(), oldStr, target.getStrength());
        }
    }

    private void feedVillagers() {
        if (!spendTime(COST_FEED)) {
            return;
        }
        List<Food> foods = playerVillage.getFoods();
        if (foods.isEmpty()) {
            LOGGER.info("No food! Gather some first.");
            return;
        }
        player.feedCharacters();
        LOGGER.info("Villagers fed!");
    }

    private void healVillagers() {
        if (!spendTime(COST_HEAL)) {
            return;
        }
        player.healCharacters();
        LOGGER.info("Villagers healed!");
        for (Character character : playerVillage.getCharacters()) {
            LOGGER.info("  {}: HP={}", character.getName(), character.getHealth());
        }
    }

    private void goToBattle() {
        if (!spendTime(COST_BATTLE)) {
            return;
        }
        Location battlefield = findBattlefield();
        if (battlefield == null || battlefield.getCharacters().isEmpty()) {
            LOGGER.info("No enemies on battlefield!");
            return;
        }
        Character warrior = selectWarrior();
        if (warrior == null) {
            return;
        }
        Character enemy = selectEnemy(battlefield);
        if (enemy == null) {
            return;
        }
        if (enemy instanceof Lycanthrope) { // NOSONAR - pattern matching requires Java 16+
            Lycanthrope lycan = (Lycanthrope) enemy;
            if (!lycan.isTransformed()) {
                lycan.transform();
            }
        }
        executeBattle(warrior, enemy, battlefield);
    }

    private Location findBattlefield() {
        for (Location loc : locations) {
            if (loc instanceof Battlefield) {
                return loc;
            }
        }
        return null;
    }

    private Character selectWarrior() {
        List<Character> villagers = playerVillage.getCharacters();
        LOGGER.info("Send who to battle?");
        for (int i = 0; i < villagers.size(); i++) {
            Character character = villagers.get(i);
            LOGGER.info("{}. {}" + HP_PREFIX + "{} STR:{})", i + 1, character.getName(), character.getHealth(), character.getStrength());
        }
        LOGGER.info("0. Cancel");
        LOGGER.info(CHOICE_PROMPT);
        int choice = readChoice();
        if (choice < 1 || choice > villagers.size()) {
            return null;
        }
        return villagers.get(choice - 1);
    }

    private Character selectEnemy(Location battlefield) {
        List<Character> enemies = battlefield.getCharacters();
        LOGGER.info("\nEnemies:");
        for (int i = 0; i < enemies.size(); i++) {
            Character enemy = enemies.get(i);
            LOGGER.info("{}. {}" + HP_PREFIX + "{} STR:{})", i + 1, enemy.getName(), enemy.getHealth(), enemy.getStrength());
        }
        LOGGER.info("Attack who? ");
        int eChoice = readChoice();
        if (eChoice < 1 || eChoice > enemies.size()) {
            return null;
        }
        return enemies.get(eChoice - 1);
    }

    private void executeBattle(Character warrior, Character enemy, Location battlefield) {
        LOGGER.info("\n=== BATTLE ===");
        LOGGER.info("{}" + HP_PREFIX + "{}) vs {}" + HP_PREFIX + "{})", warrior.getName(), warrior.getHealth(), enemy.getName(), enemy.getHealth());
        warrior.fight(enemy);
        LOGGER.info("Result: {} HP:{} | {} HP:{}", warrior.getName(), warrior.getHealth(), enemy.getName(), enemy.getHealth());
        if (!warrior.isAlive()) {
            LOGGER.info("{} is dead!", warrior.getName());
            playerVillage.removeCharacter(warrior);
        }
        if (!enemy.isAlive()) {
            LOGGER.info("VICTORY! {} defeated!", enemy.getName());
            battlefield.removeCharacter(enemy);
        }
    }

    private void gatherFood() {
        if (!spendTime(COST_GATHER)) {
            return;
        }
        LOGGER.info("Gathering food...");
        int count = 1 + RANDOM.nextInt(3);
        FoodType[] types = {FoodType.WILD_BOAR, FoodType.PASSABLE_FISH, FoodType.STRAWBERRIES, FoodType.CARROTS};
        for (int i = 0; i < count; i++) {
            FoodType type = types[RANDOM.nextInt(types.length)];
            int nutrition = 10 + RANDOM.nextInt(20);
            playerVillage.addFood(new Food(type, nutrition));
            LOGGER.info("  Found: {}", type);
        }
        if (RANDOM.nextDouble() < 0.15) {
            playerVillage.addFood(new Food(FoodType.MISTLETOE, 5, 20));
            LOGGER.info("  RARE: MISTLETOE!");
        }
    }

    private void endTurn() {
        LOGGER.info("\n=== END OF TURN {} ===", turn);
        turn++;
        timeRemaining = TIME_PER_TURN;
        for (Location loc : locations) {
            for (Character character : loc.getCharacters()) {
                character.increaseHunger();
            }
            loc.spoilFood();
        }
        LOGGER.info("Time passes... Villagers grow hungry.");
        for (Character character : playerVillage.getCharacters()) {
            if (character.getHunger() > 50) {
                LOGGER.info("  {} is hungry! (Hunger:{})", character.getName(), character.getHunger());
            }
            if (!character.isAlive()) {
                LOGGER.info("  {} has starved to death!", character.getName());
            }
        }
        LOGGER.info("\nTurn {} begins. Time: {}", turn, timeRemaining);
    }

    private void gameLoop() {
        while (running) {
            menu();
            int choice = readChoice();
            switch (choice) {
                case 1:
                    viewVillage();
                    break;
                case 2:
                    displayLocations();
                    break;
                case 3:
                    requestPotion();
                    break;
                case 4:
                    feedVillagers();
                    break;
                case 5:
                    healVillagers();
                    break;
                case 6:
                    goToBattle();
                    break;
                case 7:
                    gatherFood();
                    break;
                case 8:
                    endTurn();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    LOGGER.info("Invalid choice. Enter 0-8.");
                    break;
            }
            if (playerVillage.getNbCharacters() == 0) {
                LOGGER.info("\nAll villagers are dead! GAME OVER.");
                running = false;
            }
        }
        LOGGER.info("\n=== GAME OVER ===");
        LOGGER.info("Chief: {}", player.getName());
        LOGGER.info("Turns played: {}", turn);
        LOGGER.info("Survivors: {}", playerVillage.getNbCharacters());
        LOGGER.info("Thanks for playing!");
        scanner.close();
    }

    public String getName() {
        return name;
    }

    public int getMaxLocations() {
        return maxLocations;
    }

    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }

    public List<ClanChief> getClanChiefs() {
        return new ArrayList<>(clanChiefs);
    }

    public int getTime() {
        return turn;
    }

    public static void main(String[] args) {
        InvasionTheater theater = new InvasionTheater("Roman Gaul", 10);
        theater.setup();
        theater.gameLoop();
    }
}
