package com.example.tony.dash_till_puff;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.widget.Space;

/**
 * Created by Tony Nguyen on 4/23/2015.
 */
public class Spaceship {

    private Rect SpaceshipRect;
    private int SpaceshipX, SpaceshipY;
    private int SpaceshipVel = 0;
    private int gameHeight;

    Bitmap ship;

    private boolean isAccelerating = false;

    Paint paint = new Paint();

    public Spaceship( Bitmap _ship, int _SpawnX, int _SpawnY, int _gameHeight){

        ship = _ship;
        ScaleShip();

        SpaceshipRect = new Rect(0, 0,  (ship.getWidth()), (ship.getHeight()));

        SpaceshipX = _SpawnX;
        SpaceshipY = _SpawnY;
        gameHeight = _gameHeight;


        /*System.out.println("X axis of spaceship is: " + SpaceshipX);
        System.out.println("Y axis of spaceship is: " + SpaceshipY);
        System.out.println("Bottom of rectangle is: " + ship.getHeight());
        System.out.println("I think the bottom is at: " + (SpaceshipY + ship.getHeight()));*/
    }

    private void ScaleShip(){
        ship = ship.createScaledBitmap(ship, ship.getWidth()/2, ship.getHeight()/2, false);
    }

    private void SetY( int _y){
        SpaceshipY = _y;
    }
    public int GetY(){
        return SpaceshipY;
    }
    public int GetX(){
        return SpaceshipX;
    }
    public Bitmap getSSBitmap(){return ship;}



    public void AccelUp(){ //thrusts up
        isAccelerating = true;
    }

    public void AccelDown(){ //falls down
        isAccelerating = false;
    }

    public void tick(Canvas c){

        updateVelocity();

        updateY();

        checkWall();

        draw(c);
    }

    // checks for wall collision
    private void checkWall(){

        if (SpaceshipY < 0){ // if the top of the ship hits the wall
            SpaceshipY = SpaceshipVel = 0;

        }

        if ((SpaceshipY + ship.getHeight()) > gameHeight){ // if the bottom of the ship hits the wall
            SpaceshipY = gameHeight - ship.getHeight();
            SpaceshipVel = 0;
        }

    }

    //updates the ship's velocity due to acceleration
    private void updateVelocity(){

        if(isAccelerating){ // move up
            // should be true
            SpaceshipVel -= 2;
        }
        else{ // moves down
            // should be false
            SpaceshipVel += 2;
        }

        if(SpaceshipVel > 30){// limits velocity
            SpaceshipVel = 30;
        }

        if(SpaceshipVel < -30){ //limits velocity
            SpaceshipVel = -30;
        }
    }

    // updates the Y coordinate of the ship
    private void updateY(){
        SpaceshipY += SpaceshipVel;

    }

    public void draw(Canvas c){
        //Paint paint = new Paint();

        //point for drawing is on the top left corner
        c.drawBitmap(ship, SpaceshipX, SpaceshipY, paint);
    }
}
