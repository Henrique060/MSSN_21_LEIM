package setup;

import P01.ProjectApp;
import aaa.BoidApp;
import aaa.Flock;
import aaa.FlockTestApp;
import aaa.ReynoldsTestApp;
import automata.GameofLife;
import automata.TestCellularAutomata;
import automata.TestMajorityCA;
import ecosystem.TestEcosystemApp;
import ecosystem.TestTerrainApp;
import fractalsh.FractalApp;
import processing.core.PApplet;
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
    public void mouseDragged(){
        app.mouseDragged(this);
    }

    public void mouseReleased(){
        app.mouseReleased(this);
    }

    @Override
    public void keyPressed(){
        app.keyPressed(this);
    }



    public static void main(String[] args) {
        app = new TestEcosystemApp();
        PApplet.main(ProcessingSetup.class);
    }
}
