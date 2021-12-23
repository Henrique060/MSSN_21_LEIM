Particle current;
ArrayList<Particle> snowflake;

void setup(){
  size(600,600);
  current = new Particle(width/2,0);
  snowflake = new ArrayList<Particle>();
}

void draw(){
  translate(width/2, height/2);
  background(0);
  current.update();
  current.show();
  
  if (current.finished()) {
    snowflake.add(current);
    current = new Particle(width/2,0);
  }
  
  for (Particle p : snowflake){
    p.show();
  }
}
