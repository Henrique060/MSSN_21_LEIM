package ecosystem;

public class WorldConstants {

    //WORLD
    public final static double[] WINDOW = {-10,10,-10,10};

    //TERRAIN
    public final static int NROWS = 45;
    public final static int NCOLS = 60;
    public enum PatchType{
        EMPTY, OBSTACLE, FERTILE, FOOD
    }
    public final static double[] PATCH_TYPE_PROB = {0.1f, 0.1f, .4f, 1f};
    public final static int NSTATES = PatchType.values().length;
    public static int[][] TERRAIN_COLORS = {
            {250, 200, 60}, {160, 30, 70}, {200, 200, 60}, {40, 200, 0}
    };
    public final static float[] REGENERATION_TIME = {5.f, 10.f}; // seconds

    public final static float PREY_SIZE = .2f;
    public final static float PREY_MASS = 1f;
    public final static int INI_PREY_POPULATION = 100;
    public final static float INI_PREY_ENERGY = 10f;
    public final static float ENERGY_FROM_PLANT = 4f;
    public final static float PREY_ENERGY_TO_REPRODUCE = 25f;
    public static int[] PREY_COLOR = {80,100,220};

    public final static float PREDATOR_SIZE = .5f;
    public final static float PREDATOR_MASS = 1f;
    public final static int INI_PREDATOR_POPULATION = 20;
    public final static float INI_PREDATOR_ENERGY = 10f;
    public final static float ENERGY_FROM_PREY = 4f;
    public final static float PREDATOR_ENERGY_TO_REPRODUCE = 25f;
    public static int[] PREDATOR_COLOR = {80,100,220};



}
