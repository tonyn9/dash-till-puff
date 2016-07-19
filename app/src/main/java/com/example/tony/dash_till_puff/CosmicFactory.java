package com.example.tony.dash_till_puff;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.content.Context;
import android.content.res.Resources;


import java.util.ArrayList;
import java.util.Random;
import java.util.zip.CheckedOutputStream;

/**
 * Created by Tony on 4/20/2015.
 * This class is to create clusters, move them, and then delete them if they are out of screen
 */
public class CosmicFactory implements TimeConscious {

    private int boundary;
    //private int LastXPosition;
    private int offset;
    private int cosmicFactoryX, cosmicFactoryY;
    private Trajectory trajectory;
    Spaceship spaceship;
    //Bitmap cosmicFactory;
    Paint paint = new Paint();
    private ArrayList<Cosmic> cosmics = new ArrayList<>();
    //int Ys[10];
    //int Xs[10];
    private int LastX;
    private int last_x;
    //static int x;   //for choosing the cosmics from array
    static int gameWidth, gameHeight;
    static int counter; //counter for 10 objects
    static int SetPointsX;
    int reset;
    //DashTillPuffRenderThread thread1;

    Bitmap blackhole;
    Bitmap blueplanet;
    Bitmap cloud;
    Bitmap earth;
    Bitmap glossyplanet;
    Bitmap goldenstar;
    Bitmap neutronstar;
    Bitmap shinystar;
    Bitmap star1;
    Bitmap star2;
    Bitmap array[];

    public CosmicFactory(Context context, Trajectory t, Spaceship ss, int _gameWidth, int _gameHeight, int _reset){
        array = new Bitmap[10];
        BitmapFactory.Options options = new BitmapFactory.Options();
        array[0] = blackhole = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffblackhole, options);
        array[1]=blueplanet = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffblueplanet, options);
        array[2]=cloud = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffcloud, options);
        array[3]=earth = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffearth, options);
        array[4]=glossyplanet = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffglossyplanet, options);
        array[5]=goldenstar = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffgoldenstar, options);
        array[6]=neutronstar = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffneutronstar, options);
        array[7]=shinystar = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffshinystar, options);
        array[8]=star1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffstar1, options);
        array[9]=star2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.dashtillpuffstar2, options);
        gameHeight = _gameHeight;
        gameWidth = _gameWidth;
        offset = _gameWidth;
        boundary = t.GetLastX() - _gameWidth;
        trajectory = t;
        spaceship = ss;
        SetPointsX = gameWidth;
        for(int j = 0; j<3; ++j) {
            int x = ChooseCosmic();
            ++counter;
            for (int i = 0; i < 10; ++i) {
                if(counter%2 ==1) {
                    Cosmic cosmic = new Cosmic(array[x], gameWidth, gameHeight, trajectory, counter%2, SetPointsX, trajectory.GetY(SetPointsX));
                    cosmics.add(cosmic);
                    SetPointsX += RandIncrement();
                }
                else{
                    Cosmic cosmic = new Cosmic(array[x], gameWidth, gameHeight, trajectory, counter%2, SetPointsX, trajectory.GetY(SetPointsX));
                    cosmics.add(cosmic);
                    SetPointsX += RandIncrement();
                }
            }
       }

        //for future ticks
        last_x = SetPointsX - cosmicFactoryX/5;
        LastX = SetPointsX;

    }


    //If you make a cluster, make it outside of the screen I will make a random X function to accomplish this
    private int RandomX(){
        return (int) ((Math.random() * boundary ) + offset);
    }

    private int RandIncrement(){
        return (int) (Math.random()*(gameWidth/10));
    }

    private int ChooseCosmic(){
        return (int) (Math.random()*10);
    }

    private void SetY( int _y){cosmicFactoryY = _y;}
    private void SetX( int _x){cosmicFactoryX = _x;}
    private int getX() { return cosmicFactoryX; }
    private int getY() { return cosmicFactoryY; }


    public int Collision(){
        for (int i = 0; i < cosmics.size(); ++i){
            if (cosmics.get(i).getFlag() == 1 && spaceship.GetX()+spaceship.getSSBitmap().getWidth() < cosmics.get(i).getX()+cosmics.get(i).getBitmap().getWidth() &&
                    spaceship.GetX()+spaceship.getSSBitmap().getWidth() > cosmics.get(i).getX() &&
                    spaceship.GetY() < cosmics.get(i).getY()+cosmics.get(i).getBitmap().getHeight()){// && cosmics.get(i).getY < trajectory.GetY()){
                //System.out.println("Collision1 returning 1");
                return 1;
            }
            if(cosmics.get(i).getFlag() != 1 && spaceship.GetX()+spaceship.getSSBitmap().getWidth() < cosmics.get(i).getX()+cosmics.get(i).getBitmap().getWidth() &&
                    spaceship.GetX()+spaceship.getSSBitmap().getWidth() > cosmics.get(i).getX() &&
                    spaceship.GetY()+spaceship.getSSBitmap().getHeight() > cosmics.get(i).getY()){
                //System.out.println("Collision1 returning 1");
                return 1;
            }
        }
        //System.out.println("Collision returning 0");
        return 0;
    }


    @Override
    public void tick ( Canvas canvas ) {
// Create new ‘‘ clusters ’’ of cosmic objects . Alternately place
// clusters of cosmic objects above and below the guiding
// trajectory .
        //...
// Randomly select the type of cluster objects from a list
// ( e . g . , stars , clouds , planets , etc .) . So all objects in
// a cluster are of the same type .
        //...
// Remove cosmic objects ( stars , planets , etc .) that moved out
// of the scene .
        //...

        if (cosmics.get(11).getX() <= 0){
            ++counter;

            for(int j=0; j<10; ++j) {
                cosmics.remove(j);
            }
            int x = ChooseCosmic();
            SetPointsX = gameWidth;
            for(int j=0; j<10; ++j){
                if (counter%2 == 1) {
                    Cosmic cosmic = new Cosmic(array[x], gameWidth, gameHeight, trajectory, counter%2, SetPointsX, 0);
                    cosmics.add(cosmic);
                    SetPointsX += RandIncrement();
                }
                else{
                    Cosmic cosmic = new Cosmic(array[x], gameWidth, gameHeight, trajectory, counter%2, SetPointsX, 400);
                    cosmics.add(cosmic);
                    SetPointsX += RandIncrement();
                }
            }
        }
        draw(canvas);
    }

    private void draw(Canvas c){
        //what to draw on canvas, the cosmics
        for(int i=0; i<cosmics.size(); ++i){
            c.drawBitmap(cosmics.get(i).getBitmap(), cosmics.get(i).getX(), cosmics.get(i).getY(), paint);//cosmics.get(1).getX(), cosmics.get(1).getY(), paint);//
            cosmics.get(i).MoveCosmic();
            cosmics.get(i).fixY();
        }
    }

}
