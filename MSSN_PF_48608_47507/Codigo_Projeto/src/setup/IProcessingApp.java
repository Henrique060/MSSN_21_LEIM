package setup;

import processing.core.PApplet;

public interface IProcessingApp {
    void setup(PApplet parent);
    void draw(PApplet parent, float dt);
    void mousePressed(PApplet p);
    void keyPressed(PApplet p);
    void mouseDragged(PApplet p);
    void mouseReleased(PApplet p);
}
