void setup(){
size(500,500);
noStroke();

}

void draw(){
  background(0,0,0);
  
  fill(219,171,139);
  ellipse(100, 100, 150, 150);
  ellipse(200, 100, 150, 150);
  
  fill(255,214,221);
  ellipse(150, 100, 25, 25);

  fill(247,172,200);
  ellipse(mouseX, mouseY, 75, 75);
  //ellipse(250,250,75,75);

  fill(247,202,172);
  rect(mouseX-40, mouseY, 80, 100);
  //rect(210,250,80,100);

  //ellipse(200,370,100,100);
  //ellipse(300,370,100,100);
  ellipse(mouseX - 50, mouseY + 120, 100, 100);
  ellipse(mouseX + 50, mouseY + 120, 100, 100);
  
  
  
}
