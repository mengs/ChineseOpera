import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class chinaOperaFaceChangeNoVideo extends PApplet {

/////////////////////////////////////////////////////////////////
// Jan 26, 2013
// Meng Shi (MTID, CMU)
// lolaee@gmail.com
// for the IACD Spring 2013 class at the CMU School of Art
/////////////////////////////////////////////////////////////////

// FaceOSC, video Lib

OscP5 oscP5;
//import processing.video.*;

// Declaration
// Because FaceOSC application is using FaceTimeHD cam, So I plug into another one.
//Capture video;

PImage[] images = new PImage[20];// There is totally 20 OperaFace in my data folder.
PImage[] imageQRs = new PImage[20];
PFont myFont;
int found;

// pose
float poseScale;
PVector posePosition = new PVector();
PVector poseOrientation = new PVector();


// gesture
float mouthHeight;
float mouthWidth;
float eyeLeft;
float eyeRight;
float eyebrowLeft;
float eyebrowRight;
float jaw;
float nostrils;
int num;

public void setup() {
  size(640, 480, P3D);
  myFont = createFont("Cochin", 14);
  textFont(myFont);
  frameRate(30);
  imageMode(CENTER);
  //  video = new Capture(this, width, height);
  //  video.start();
  smooth();

  oscP5 = new OscP5(this, 8338);
  oscP5.plug(this, "found", "/found");
  oscP5.plug(this, "poseScale", "/pose/scale");
  oscP5.plug(this, "posePosition", "/pose/position");
  oscP5.plug(this, "poseOrientation", "/pose/orientation");
  oscP5.plug(this, "mouthWidthReceived", "/gesture/mouth/width");
  oscP5.plug(this, "mouthHeightReceived", "/gesture/mouth/height");
  oscP5.plug(this, "eyeLeftReceived", "/gesture/eye/left");
  oscP5.plug(this, "eyeRightReceived", "/gesture/eye/right");
  oscP5.plug(this, "eyebrowLeftReceived", "/gesture/eyebrow/left");
  oscP5.plug(this, "eyebrowRightReceived", "/gesture/eyebrow/right");
  oscP5.plug(this, "jawReceived", "/gesture/jaw");
  oscP5.plug(this, "nostrilsReceived", "/gesture/nostrils");

  for ( int i = 0; i< images.length; i++ ) {     
    images[i] = loadImage( i + ".png" );
    imageQRs[i] = loadImage( i+"a" + ".png" );
  }
}

public void draw() {  
  //  if (video.available()) {
  //    video.read();
  //  }
  //  imageMode(CENTER);

  background(255);
  fill(0);
  text("Learn or Contribute", 90, 412);
  text("Your Knowledge", 90, 428);
  text("Chinese Opera", 90, 444);
  text("Here", 90, 460);


  if (found > 0) {

    image(imageQRs[num], 50, 432, 80, 80);

    translate(posePosition.x, posePosition.y);
    scale(poseScale);
    //    X,Y position's effect is not so santisfacted, I will keep it 2D currently
    //    rotateX(poseOrientation.x);
    //    rotateY(poseOrientation.y);
    rotateZ(poseOrientation.z);
    image(images[num], 0, 0, 36, 46.08f);


    println("Change Now!");
  }
  else {
    num++;
    if (num==20) {
      num=0;
    }
  }
  //  stroke(0);
  //    noFill();
  //    ellipse(-20, eyeLeft * -9, 20, 7);
  //    ellipse(20, eyeRight * -9, 20, 7);
  //    ellipse(0, 20, mouthWidth* 3, mouthHeight * 3);
  //    ellipse(-5, nostrils * -1, 7, 3);
  //    ellipse(5, nostrils * -1, 7, 3);
  //    rectMode(CENTER);
  //    fill(0);
  //    rect(-20, eyebrowLeft * -5, 25, 5);
  //    rect(20, eyebrowRight * -5, 25, 5);
}


// OSC CALLBACK FUNCTIONS

public void found(int i) {
  println("found: " + i);
  found = i;
}

public void poseScale(float s) {
  println("scale: " + s);
  poseScale = s;
}

public void posePosition(float x, float y) {
  println("pose position\tX: " + x + " Y: " + y );
  posePosition.set(x, y, 0);
}

public void poseOrientation(float x, float y, float z) {
  println("pose orientation\tX: " + x + " Y: " + y + " Z: " + z);
  poseOrientation.set(x, y, z);
}
//
//public void mouthWidthReceived(float w) {
//  println("mouth Width: " + w);
//  mouthWidth = w;
//}
//
//public void mouthHeightReceived(float h) {
//  println("mouth height: " + h);
//  mouthHeight = h;
//}
//
//public void eyeLeftReceived(float f) {
//  println("eye left: " + f);
//  eyeLeft = f;
//}
//
//public void eyeRightReceived(float f) {
//  println("eye right: " + f);
//  eyeRight = f;
//}
//
//public void eyebrowLeftReceived(float f) {
//  println("eyebrow left: " + f);
//  eyebrowLeft = f;
//}
//
//public void eyebrowRightReceived(float f) {
//  println("eyebrow right: " + f);
//  eyebrowRight = f;
//}
//
//public void jawReceived(float f) {
//  println("jaw: " + f);
//  jaw = f;
//}
//
//public void nostrilsReceived(float f) {
//  println("nostrils: " + f);
//  nostrils = f;
//}

// all other OSC messages end up here
public void oscEvent(OscMessage m) {
  if (m.isPlugged() == false) {
    println("UNPLUGGED: " + m);
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "chinaOperaFaceChangeNoVideo" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
