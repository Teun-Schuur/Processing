
int Xas = width/2;
int Yas = height/2;

void setup(){
  size(600, 600);
  stroke(255);
  fill(255);
  frameRate(TWO_PI);
}

void draw(){
  background(0);
  shapeMode(CENTER);
  if(mousePressed){
    rect(mouseX-50, mouseY-50, 100, 100);
    Xas = mouseX-50;
    Yas = mouseY-50;
    
  }
  Xas++;
  Yas++;
  rect(Xas, Yas, 100, 100);
  if(frameCount % 10 == 0){
    println(frameRate/2);
  }
}



void mousePressed(){
  fill(250);
}
