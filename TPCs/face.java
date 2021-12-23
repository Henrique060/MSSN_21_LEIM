import processing.core.PApplet;

public class face extends PApplet{

    @Override
    public void settings() {
        size(400,400);
    }

    @Override
    public void setup() {

        stroke(0);
        smooth();
        background(256,256,256);
    }

    @Override
    public void draw() {
        //corpo
        fill(256,0,0);
        ellipse(200,200,200,200);
        //olhos
        fill(256,256,256);
        ellipse(150,150 , 100, 100);
        ellipse(250,150,90,90);

        //pupilas
        if(mousePressed == true){
            fill(0);
            ellipse(120,130,40,40);
            ellipse(280,130,40,40);
        } else {
            fill(0);
            ellipse(150,130,40,40);
            ellipse(250,130,40,40);
        }

        //boca
        triangle(120,200,230,250,180,290);
    }

    public static void main(String[] args) {
        PApplet.main(face.class);
    }
}
