package fractalsh;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class FractalApp implements IProcessingApp {

    private double[] window = {-15,15,0,15};
    private float[] viewport = {0, 0, 1 ,1};
    private SubPlot plt;
    private List<Tree> forest;


    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport,p.width, p.height);
        forest = new ArrayList<Tree>();
        //Rule[] rules = new Rule[1];

        //rules[0] = new Rule('F', "FF+[+F-F-F]-[-F+F+F]");

        //lsys = new LSystem("F", rules);
        //turtle = new Turtle(5, PApplet.radians(22.5f));
    }

    @Override
    public void draw(PApplet p, float dt) {
        float[] bb = plt.getBoundingBox();
        p.rect(bb[0], bb[1],bb[2], bb[3]);


        for(Tree tree : forest){
            p.color(255,255,0);
            tree.grow(dt);
            tree.display(p, plt);


        }

    }

    @Override
    public void mousePressed(PApplet p) {
        double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
        PVector pos = new PVector((float)w[0], (float)w[1]);
        Tree tree;
            Rule[] rules = new Rule[1];
            rules[0] = new Rule('F',"F-F++F-F");
            tree = new Tree("F++F++F ", rules, pos, .2f, PApplet.radians(60f), 5, 0.5f, 1f, p, p.color(255,0,0));



        forest.add(tree);
    }

    @Override
    public void keyPressed(PApplet p) {
        double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
        PVector pos = new PVector((float)w[0], (float)w[1]);
        Tree tree;
        Rule[] rules = new Rule[2];
        //rules[0] = new Rule('F',"FF+[+F-F-F]-[-F+F+F]");
        rules[0] = new Rule('F',"G+F+G");
        rules[1] = new Rule('G' ,"F-G-F");
        tree = new Tree("G", rules, pos, .7f, PApplet.radians(60f), 7, 0.5f, 1f, p, p.color(255,0,0));

        forest.add(tree);
    }

    @Override
    public void mouseDragged(PApplet p) {

    }

    @Override
    public void mouseReleased(PApplet p) {

    }
}
