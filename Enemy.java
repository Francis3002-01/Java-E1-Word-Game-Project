public class Enemy {

    private String name;

    private String idlePath;
    private int idleFrames;

    private String attackPath;
    private int attackFrames;


    public Enemy(
            String name,
            String idlePath,
            int idleFrames,
            String attackPath,
            int attackFrames
    ) {

        this.name = name;

        this.idlePath = idlePath;
        this.idleFrames = idleFrames;

        this.attackPath = attackPath;
        this.attackFrames = attackFrames;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public String getName() {
        return name;
    }


    public String getIdlePath() {
        return idlePath;
    }


    public int getIdleFrames() {
        return idleFrames;
    }


    public String getAttackPath() {
        return attackPath;
    }


    public int getAttackFrames() {
        return attackFrames;
    }
}