import java.util.Random;

public class EnemyManager {

    private Enemy[] enemies;

    private Enemy boss;

    private Random random;


    public EnemyManager() {

        random = new Random();


        // ==========================================
        // NORMAL ENEMIES
        // ==========================================

        Enemy bat = new Enemy(
            "Bat",
            "assets/Enemies/Bat/fly.png",
            11,
            "assets/Enemies/Bat/attack.png",
            11

        );

        Enemy mimic = new Enemy(
            "Mimic",
            "assets/Enemies/Mimic/idle_transformed.png",
            9,
            "assets/Enemies/Mimic/attack_1.png",
            14

        );

        Enemy rat = new Enemy(
            "Rat",
            "assets/Enemies/Rat/idle.png",
            10,
            "assets/Enemies/Rat/attack_bite.png",
            12

        );

        Enemy slime = new Enemy(
                "Slime",
                "assets/Enemies/Slime/idle.png",
                14,
                "assets/Enemies/Slime/Attack.png",
                19
        );

        Enemy flyingeye = new Enemy(
            "Flying Eye",
            "assets/Enemies/Flying eye/Flight.png",
            8,
            "assets/Enemies/Flying eye/Attack.png",
            8

        );

        Enemy goblin = new Enemy(
                "Goblin",
                "assets/Enemies/Goblin/Idle.png",
                4,
                "assets/Enemies/Goblin/Attack.png",
                8
        );

        Enemy mushroom = new Enemy(
            "Mushroom",
            "assets/Enemies/Mushroom/Idle.png",
            4,
            "assets/Enemies/Mushroom/Attack.png",
            8

        );
        
        Enemy skeleton = new Enemy(
            "Skeleton",
            "assets/Enemies/Skeleton/Idle.png",
            4,
            "assets/Enemies/Skeleton/Attack.png",
            8

        );

        enemies = new Enemy[] {
                bat,
                mimic,
                rat,
                slime,
                flyingeye,
                goblin,
                mushroom,
                skeleton


        };


        // ==========================================
        // BOSS
        // ==========================================

        boss = new Enemy(
                "Evil Wizard",
                "assets/Enemies/Evil Wizard/Sprites/Idle.png",
                8,
                "assets/Enemies/Evil Wizard/Sprites/Attack.png",
                8
        );
    }


    // ==========================================
    // GET RANDOM NORMAL ENEMY
    // ==========================================

    public Enemy getRandomEnemy() {

        int index =
                random.nextInt(enemies.length);

        return enemies[index];
    }


    // ==========================================
    // GET BOSS
    // ==========================================

    public Enemy getBoss() {

        return boss;
    }
}
