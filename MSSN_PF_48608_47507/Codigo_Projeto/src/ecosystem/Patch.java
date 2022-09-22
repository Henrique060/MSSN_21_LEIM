package ecosystem;

import automata.MajorityCell;


public class Patch extends MajorityCell {
    private long eatenTime;
    private final int timeToGrow;

    public Patch(Terrain terrain, int row, int col, int timeToGrow){
        super(terrain, row, col);
        this.timeToGrow = timeToGrow;
        eatenTime = System.currentTimeMillis();
    }

    public void setFertile(){
        state = WorldConstants.PatchType.FERTILE.ordinal();
        eatenTime = System.currentTimeMillis();
    }

    //serve para criar água (lagos)
    public void setWater(){
        state = WorldConstants.PatchType.WATER.ordinal();
    }

    public void regenerate(){
        if(state == WorldConstants.PatchType.FERTILE.ordinal()
                    && System.currentTimeMillis() > (eatenTime + timeToGrow)){
            state = WorldConstants.PatchType.FOOD.ordinal();
        }

    }
}
