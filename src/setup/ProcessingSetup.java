package setup;

import automata.GameofLife;
import dla.DLA;
import fractals.ForestApp;
import fractals.LSystemApp;
import physics.ControlGUIApp;
import physics.ParticleSystemApp;
import physics.SolarSystemApp;
import processing.core.PApplet;
import automata.CellularAutomata;
import automata.Cell;
//import automata.TestCellularAutomata;

public class ProcessingSetup extends PApplet{

    public static IProcessingApp app;
    private int lastUpdate;

    @Override
    public void settings() {
        size(800,600);
    }

    @Override
    public void setup() {
        app.setup(this);
        lastUpdate = millis();
    }

    @Override
    public void draw() {
        int now = millis();
        float dt = (now - lastUpdate)/1000f; //dt -> delta t (intervalo tempo)
        lastUpdate = now;
        app.draw(this, dt);
    }

    @Override
    public void mousePressed(){
        app.mousePressed(this);

    }

    @Override
    public void keyPressed(){
        app.keyPressed(this);
    }

    public static void main(String[] args) {
        app = new SolarSystemApp();
        PApplet.main(ProcessingSetup.class);
    }
}
