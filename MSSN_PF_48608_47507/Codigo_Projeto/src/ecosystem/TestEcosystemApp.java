package ecosystem;

import fractalsh.Rule;
import fractalsh.Tree;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;
import tools.TimeGraph;

import java.util.ArrayList;
import java.util.List;

public class TestEcosystemApp implements IProcessingApp {

    private final float timeDuration = 60;
    private final float refPopulation = 100;
    private final float refPred = 100;
    private final float refApex = 100;
    private final float refMeanSpeed = 1;

    private final float[] viewport = {0f, 0f, 1f, .8f};

    private final double[] winGraph1 = {0, timeDuration, 0, 2 * refPopulation};
    private final double[] winGraph4 = {0,timeDuration, 0, 2*refMeanSpeed};

    private final float[] viewGraph1 = {0.15f, .8f, .32f, .2f};
    private final float[] viewGraph2 = {.679f, .8f, .32f, .2f};

    private SubPlot plt, pltGraph1, pltGraph2;
    private TimeGraph tg1, tg2, tg3, tg4, tg5, tg6;

    private Terrain terrain;
    private Population population;
    private float timer, updateGraphTime;
    private final float intervalUpdate = 1;

    private List<Tree> forest;


    @Override
    public void setup(PApplet p) {
        forest = new ArrayList<>();

        plt = new SubPlot(WorldConstants.WINDOW, viewport, p.width, p.height);
        pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
        pltGraph2 = new SubPlot(winGraph4, viewGraph2, p.width, p.height);

        tg1 = new TimeGraph(p, pltGraph1, p.color(255, 0, 0), refPopulation);
        tg2 = new TimeGraph(p, pltGraph1, p.color(0, 255, 0), refPred);
        tg3 = new TimeGraph(p, pltGraph1, p.color(0, 0, 255), refApex);

        tg4 = new TimeGraph(p, pltGraph2, p.color(255,0,0), refMeanSpeed);
        tg5 = new TimeGraph(p, pltGraph2, p.color(0,255,0), refMeanSpeed);
        tg6 = new TimeGraph(p, pltGraph2, p.color(0,0,255), refMeanSpeed);

        terrain = new Terrain(p, plt);
        terrain.setStateColors(getColors(p));
        terrain.initRandomCustom(WorldConstants.PATCH_TYPE_PROB);
        for (int i = 0; i < 3; i++) terrain.majorityRule();
        population = new Population(p, plt, terrain);
        timer = 0;
        updateGraphTime = timer + intervalUpdate;

        System.out.println("""
                Bem vindo à simulação dum ecossistema!\s
                Neste ecossistema estão presentes 3 espécies diferentes: presa, predador e o predador Apex!\s
                Como presa temos um coelho, como predador um lobo e como apex é o próprio ser humano!
                Ao longo do tempo observamos como os predadores buscam comida e como as presas pretendem encontrar comida.
                Se tocam nos obstáculos a vermelho, os seres morrem. Se tocam na água ganham mais energia.
                As presas comem pastagem, os predadores comem as presas e os apex comem os predadores!
                De acordo com a quantidade de alimento ingerido, ganham mais energia o que leva à reprodução, criando filhos
                que herdam as características dos pais, aprendendo também com os seus erros!
                Mas atenção, pouco alimento e os seres morrem!
                É completamente interativo:
                   tecla 0 e arrastar clique de rato para criar terra fértil
                   tecla 1 e arrastar clique de rato para criar lagos
                   tecla 2 e arrastar clique de rato para cirar obstáculos
                   tecla 3 e apenas clique de rato na posição pretendida para criar árvores. (meramente ilustrativo)
                Divirta-se com esta história!

                """);

        p.pushStyle();
        p.noStroke();
        p.fill(255);
        p.rect(0, 0, 165, 140);
        p.textSize(12);
        p.fill(255,0,0);
        p.text("Presas: " + population.getNumAnimals(), 25, 55);
        p.fill(22,165,62);
        p.text("Predadores: " + population.getNumPredators(), 25, 70);
        p.fill(0,0,255);
        p.text("Predadores Apex: " + population.getNumApex(), 25, 85);
        p.popStyle();

        p.pushStyle();
        p.noStroke();
        p.fill(255);
        p.rect(519, 0, 226f, 140);
        p.textSize(12);
        p.fill(255,0,0);
        p.text("Velocidade Presas: " + population.getNumAnimals(), 565, 55);
        p.fill(22,165,62);
        p.text("Velocidade Predadores: " + population.getNumPredators(), 565, 70);
        p.fill(0,0,255);
        p.text("Velocidade Apex: " + population.getNumApex(), 565, 85);
        p.popStyle();
    }

    @Override
    public void draw(PApplet p, float dt) {

            terrain.display(p);
            timer += dt;

            terrain.regenerate();
            population.update(p, dt, terrain);
            population.display(p, plt);
            population.die();


            for(Tree tree : forest){
                tree.grow(dt);
                tree.display(p, plt);
            }


            if (timer > updateGraphTime) {

                tg1.plot(timer, population.getNumAnimals());
                tg2.plot(timer, population.getNumPredators());
                tg3.plot(timer,population.getNumApex());
                tg4.plot(timer, population.getMeanMaxSpeed(population.getAllAnimals()));
                tg5.plot(timer, population.getMeanMaxSpeed(population.getAllPredator()));
                tg6.plot(timer, population.getMeanMaxSpeed(population.getAllApex()));

                updateGraphTime = timer + intervalUpdate;

                p.pushStyle();
                p.noStroke();
                p.fill(255);
                p.rect(0, 0, 165, 140);
                p.textSize(12);
                p.fill(255,0,0);
                p.text("Presas: " + population.getNumAnimals(), 25, 55);
                p.fill(22,165,62);
                p.text("Predadores: " + population.getNumPredators(), 25, 70);
                p.fill(0,0,255);
                p.text("Predadores Apex: " + population.getNumApex(), 25, 85);
                p.popStyle();

                p.pushStyle();
                p.noStroke();
                p.fill(255);
                p.rect(519, 0, 226f, 140);
                p.textSize(12);
                p.fill(255,0,0);
                p.text("Velocidade Presas: " + population.getNumAnimals(), 565, 55);
                p.fill(22,165,62);
                p.text("Velocidade Predadores: " + population.getNumPredators(), 565, 70);
                p.fill(0,0,255);
                p.text("Velocidade Apex: " + population.getNumApex(), 565, 85);
                p.popStyle();
            }
    }

    @Override
    public void mousePressed(PApplet p) {}


    @Override
    public void keyPressed(PApplet p) {
        if(p.key == '1'){
            System.out.println("Está a criar lagos!");
        }
        if(p.key == '2'){
            System.out.println("Está a criar obstáculos!");
        }
        if(p.key == '3'){
            System.out.println("Está a criar árvores!");
        }


    }

    @Override
    public void mouseDragged(PApplet p) {
        if (p.key == '0'){
            terrain.pixel2Cell(p.mouseX, p.mouseY).setState(WorldConstants.PatchType.FERTILE.ordinal());
        }
        if(p.key == '1'){
            terrain.pixel2Cell(p.mouseX, p.mouseY).setState(WorldConstants.PatchType.WATER.ordinal());
        }
        if(p.key == '2'){
            terrain.pixel2Cell(p.mouseX, p.mouseY).setState(WorldConstants.PatchType.OBSTACLE.ordinal());
        }
    }

    @Override
    public void mouseReleased(PApplet p) {
        if(p.key == '3'){

            double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
            PVector pos = new PVector((float)w[0], (float)w[1]);
            Tree tree;
            if(p.random(100) < 50){
                Rule[] rules = new Rule[1];
                rules[0] = new Rule('F',"FF+[+F-F-F]-[-F+F+F]");
                tree = new Tree("F", rules, pos, .4f, PApplet.radians(22.5f),
                                    3, 0.5f, 1f, p, p.color(255,0,0));
            } else {
                Rule[] rules = new Rule[2];
                rules[0] = new Rule('X',"F+[[X]-X]-F[-FX]+X");
                rules[1] = new Rule('F' ,"FF");
                tree = new Tree("X", rules, pos, .4f, PApplet.radians(22.5f),
                                    3, 0.5f, 1f, p, p.color(255,0,0));
            }
            forest.add(tree);
        }

    }

    private int[] getColors(PApplet p) {
        int[] colors = new int[WorldConstants.NSTATES];
        for (int i = 0; i < WorldConstants.NSTATES; i++) {
            colors[i] = p.color(WorldConstants.TERRAIN_COLORS[i][0],
                    WorldConstants.TERRAIN_COLORS[i][1],
                    WorldConstants.TERRAIN_COLORS[i][2]);
            }
        return colors;
    }

}
