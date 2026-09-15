import java.util.Set;

public class WordDictionary {

    // Hardcoded dictionary for Wizard's Escape
    private static final Set<String> WORDS = Set.of(

        // 3-letter words
        "arc",
        "ash",
        "axe",
        "bat",
        "bow",
        "cat",
        "elf",
        "fog",
        "ice",
        "key",
        "map",
        "oak",
        "orb",
        "owl",
        "ray",
        "sun",

        // 4-letter words
        "arch",
        "bane",
        "bard",
        "bark",
        "beam",
        "beast",
        "book",
        "cave",
        "dark",
        "dawn",
        "dear",
        "door",
        "dust",
        "earth",
        "fire",
        "flame",
        "frost",
        "gold",
        "helm",
        "mage",
        "moon",
        "myth",
        "rune",
        "seal",
        "soul",
        "star",
        "sword",
        "tome",
        "wand",
        "wolf",

        // 5-letter words
        "altar",
        "angel",
        "blade",
        "crown",
        "curse",
        "demon",
        "fairy",
        "ghost",
        "manor",
        "realm",
        "robed",
        "spell",
        "staff",
        "stone",
        "torch",
        "tower",
        "witch",
        "world",

        // 6-letter words
        "arcane",
        "castle",
        "dragon",
        "enchant",
        "flames",
        "forest",
        "giants",
        "knight",
        "legend",
        "magic",
        "mystic",
        "oracle",
        "potion",
        "prince",
        "scroll",
        "shadow",
        "shield",
        "spells",
        "wizard",

        // 7-letter words
        "ancient",
        "crystal",
        "dragons",
        "enchanted",
        "fantasy",
        "fireball",
        "kingdom",
        "monster",
        "mystery",
        "phoenix",
        "princess",
        "sorcery",
        "warlock",

        // 8-letter words
        "adventure",
        "darkness",
        "guardian",
        "magician",
        "spellbook",
        "treasure",
        "wizardry"
    );


    // Check if a word exists in the dictionary
    public static boolean contains(String word) {

        if (word == null) {
            return false;
        }

        return WORDS.contains(word.toLowerCase());
    }


    // Return the complete dictionary
    public static Set<String> getWords() {
        return WORDS;
    }
}
