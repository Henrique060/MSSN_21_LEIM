package ecosystem;

import processing.core.PImage;

public class WorldConstants {

    //WORLD
    public final static double[] WINDOW = {-10,10,-10,10};

    //TERRAIN
    public final static int NROWS = 45;
    public final static int NCOLS = 60;
    public enum PatchType{
        EMPTY, OBSTACLE, FERTILE, FOOD, WATER
    }
    public final static double[] PATCH_TYPE_PROB = {0.11f, 0.17f, 0.05f, 0.5f, 1f};
    public final static int NSTATES = PatchType.values().length;
    public static int[][] TERRAIN_COLORS = {
            {250, 200, 60}, {160, 30, 70}, {200, 200, 60}, {40, 200, 0}, {0,0,255}

    };
    public final static float[] REGENERATION_TIME = {15.f, 20.f}; // seconds

    public final static float PREY_SIZE = .1f;
    public final static float PREY_MASS = 1f;
    public final static int INI_PREY_POPULATION = 25;
    public final static float INI_PREY_ENERGY = 10f;
    public final static float ENERGY_FROM_PLANT = 4f;
    public final static float PREY_ENERGY_TO_REPRODUCE = 60f;

    public final static float PREDATOR_SIZE = .12f;
    public final static float PREDATOR_MASS = 1.1f;
    public final static int INI_PREDATOR_POPULATION = 10;
    public final static float INI_PREDATOR_ENERGY = 60f;
    public final static float ENERGY_FROM_PREY = 25f;
    public final static float PREDATOR_ENERGY_TO_REPRODUCE = 80f;

    public final static float APEX_SIZE = .20f;
    public final static float APEX_MASS = 1.2f;
    public final static int INI_APEX_POPULATION = 4;
    public final static float INI_APEX_ENERGY = 60f;
    public final static float APEX_ENERGY_FROM_PREDATOR = 10f;
    public final static float APEX_ENERGY_FROM_PREY = 3f;
    public final static float APEX_ENERGY_TO_REPRODUCE = 200f;



}
