/*
*
* teste de narrativa :
* começar por spawnar arvores atraves de fractais
* o sol tera forças aplicadas em pequeno que tera planetas a mexer à volta
* a grelha sera um spawn de alimento, uma simulação com automatos de presas e predadores
* tentar implementar sistema de particulas a cada x tempo (??)
*
*/



package P01;

import physics.CelestialBody;
import fractalsh.Rule;
import fractalsh.Tree;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class ProjectApp implements IProcessingApp {

    // FLORESTA PT 1
    public SubPlot plt;
    public List<Tree> forest;
    public float[] viewport = {0,0,1,1};
    public double[] window = {-15,15,0,15};

    //SISTEMA SOLAR PT 2
    private float sunMass = 1.989e30f;
    private float mercuryMass = 3.285e23f;
    private float distMercurySun = 5.3e10f;
    private float mercurySpeed = 4.8e4f;
    private CelestialBody sun, mercury;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        forest = new ArrayList<Tree>();

    }

    @Override
    public void draw(PApplet p, float dt) {
    float[] bb = plt.getBoundingBox();
    p.rect(bb[0], bb[1], bb[2], bb[3]);
    p.background(184, 255, 250);

    p.fill(0,255,0);
    p.rect(0, p.height/2, p.width, p.height);


    for(Tree a : forest){
        a.grow(dt);
        a.display(p,plt);
    }


    }

    @Override
    public void mousePressed(PApplet p) {
        System.out.println("Aqui começa a nossa historia!");
        double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
        PVector pos = new PVector((float)w[0], (float)w[1]);
        Tree tree;
        if(p.random(100) < 50){
            Rule[] rules = new Rule[2];
            rules[0] = new Rule('X',"F+[[X]-X]-F[-FX]+X");
            rules[1] = new Rule('F' ,"FF");
            tree = new Tree("X", rules, pos, .4f, PApplet.radians(22.5f), 3, 0.5f, 1f, p, p.color(255,0,0));
        } else {
            Rule[] rules = new Rule[2];
            rules[0] = new Rule('X',"F+[[X]-X]-F[-FX]+X");
            rules[1] = new Rule('F' ,"FF");
            tree = new Tree("X", rules, pos, .4f, PApplet.radians(22.5f), 3, 0.5f, 1f, p, p.color(255,0,0));
        }
        forest.add(tree);
    }

    @Override
    public void keyPressed(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }

    @Override
    public void mouseReleased(PApplet p) {

    }
}
