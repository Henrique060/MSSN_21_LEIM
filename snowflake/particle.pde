class Particle{
  float x, y;
  float r;
  
  Particle(float x_, float y_){
    x = x_;
    y = y_;
    r = 3;
  }
  
  void update(){
    x -= 1;
    y += random(-1,1);
  }


  void show(){
    fill (255);
    stroke(255);
    ellipse(x,y,r * 2, r * 2);
  }
  
  boolean finished(){
    return (x < 0);
  }
}
