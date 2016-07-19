package com.example.tony.dash_till_puff;

import android.graphics.Bitmap;

/**
 * Created by LindaVang on 5/3/2015.
 */
public class Cosmic {
    Bitmap CosmicBitmap;
    int x;
    int y;
    int gameWidth, gameHeight;
    Trajectory trajectory;
    int flag;

    public Cosmic(Bitmap bitmap, int _gameWidth, int _gameHeight, Trajectory t, int _flag, int xcoord, int ycoord){
        CosmicBitmap = bitmap;
        ScaleBitmap();
        x = xcoord;
        y = ycoord;
        gameWidth = _gameWidth;
        trajectory = t;
        flag = _flag; //if ==1 then above tra, else below tra
        gameHeight = _gameHeight;
    }

    public void SetY( int _y){y = _y;}
    public void SetX( int _x){x = _x;}
    public Bitmap getBitmap(){return CosmicBitmap;}
    public int getX(){return x;}
    public int getY(){return y;}
    public int getFlag(){return flag;}

    private void ScaleBitmap(){
        CosmicBitmap = CosmicBitmap.createScaledBitmap(CosmicBitmap, CosmicBitmap.getWidth()/3, CosmicBitmap.getHeight()/3, false);
    }
    public void MoveCosmic(){ //moving cosmics to the left
        x -= 10;
    }


    private int aboveTrajectory(){
        int position;
        int traY;
        traY = trajectory.GetY(x);
        position = (int) ((Math.random() * traY));
        if (position+CosmicBitmap.getHeight()> traY)
            position = position + (traY-(position+CosmicBitmap.getHeight()))-150;
        return position-50;
    }

    private int RandNum(){
        return (int) (Math.random()*100);
    }

    private int belowTrajectory(){
        int position;
        int traY;
        traY = trajectory.GetY(x);
        position = (int) ((Math.random() * gameHeight));
        if (position <traY){
            position = traY+RandNum()+50;
        }
        if (position+CosmicBitmap.getHeight() > gameHeight){
            position = position + ((gameHeight-(position+CosmicBitmap.getHeight())));//+150;
        }
        return position;
    }

    public void fixY(){
        if (x < gameWidth+500 && x> gameWidth-15 || x == gameWidth){
            if (flag == 1){
                y = aboveTrajectory();
                if(trajectory.GetY(x)-(y+CosmicBitmap.getHeight()) < 20) {
                    y -= 30;
                }
            }
            else {
                y = belowTrajectory();
                if(y-trajectory.GetY(x) < 30) {
                    y += 50;
                }
            }
        }
    }
}
