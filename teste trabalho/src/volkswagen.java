import processing.core.PApplet;

public class volkswagen extends PApplet{


    @Override
    public void settings() {
        size(600,600);
    }

    @Override
    public void setup() {
        background(128,128,128);
        //stroke(0);
        noStroke();
        smooth();

    }

    @Override
    public void draw() {
        fill(0);
        ellipse(300,300,310,310);
        fill(256,256,256);
        ellipse(300,300,300,300);
        //boundaries são 200 - y  200 - x
        fill(0);
        ellipse(300,300, 250,250);

        /**
         * primeiro V
         */

        fill(256,256,256);
        beginShape();
        vertex(275,300);
        vertex(325,300);
        vertex(400,200);
        vertex(360, 190);
        endShape();

        beginShape();
        vertex(325,300);
        vertex(275,300);
        vertex(200,200);
        vertex(240, 190);
        endShape();

        /**
         * segundo W
         */
        beginShape();
        vertex(325,310);
        vertex(275,310);
        vertex(250,350);
        vertex(185, 240);
        vertex(170,295);
        vertex(250,430);
        vertex(300,350);
        vertex(350,430);
        vertex(430, 295);
        vertex(425, 240);
        vertex(350,350);
        endShape();


    }

    public static void main(String[] args) {
        PApplet.main(volkswagen.class);
    }
}