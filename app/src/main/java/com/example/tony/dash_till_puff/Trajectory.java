package com.example.tony.dash_till_puff;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

//import java.awt.geom.*;

import java.util.ArrayList;

/**
 * Created by Tony on 4/20/2015 for the dash till puff.
 * This creates the trajectory which will guide the ship
 */


public class Trajectory implements TimeConscious {

    private ArrayList<PointF> points ;


    private int offset;
    private int boundary;

    private int bottom;

    private int last_x;
    private int score = 0;

    private Paint TextPaint = new Paint();
    private Paint DashPaint = new Paint() ;

    private int LastX;


    public Trajectory(int _bottom, int _right){

        points = new ArrayList<>();

        bottom = _bottom;
        int right = _right;

        int top_boundary = _bottom/9;
        int bottom_boundary = top_boundary*8;

        offset = top_boundary;
        boundary = bottom_boundary - top_boundary;



        //System.out.println("top boundary is pixel: " + top_boundary);
        //System.out.println("bottom boundary is pixel: " + bottom_boundary);

        int SetPointsX = 0;

        //Creates the first few points to display into the screen
        for( int i = 0; i < 7; i++){

            PointF point = new PointF(SetPointsX, ((int) (Math.random() * boundary) + offset));

            points.add(point);

            SetPointsX += _right/5;

            //System.out.println("this is the x: " + point.x);
            //System.out.println("this is the y: " + point.y);

        }

        //for future ticks
        last_x = SetPointsX - _right/5;
        LastX = SetPointsX;

        //System.out.println("Size of points is: " + points.size());


        //Sets text default
        TextPaint.setColor(Color.BLACK);
        TextPaint.setTextSize(bottom / 20);
        TextPaint.setAntiAlias(true);
        TextPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //sets line
        DashPaint.setStyle(Paint.Style.STROKE);
        DashPaint.setColor(Color.CYAN);
        DashPaint.setStrokeWidth(10);
        DashPaint.setPathEffect(new DashPathEffect(new float[] {20,20}, 25));


    }


    @Override
    public void tick ( Canvas canvas ) {
// As time ticks , append more points to the trajectory and
// discard those points that have crossed the entire
// span of the screen .


        //moves all points in the array list to the left by 5 pixels
        for (PointF temp: points){

            temp.x -= 10;

        }

        //if the second point is at 0 or less than, create a new point out of the screen
        if (points.get(1).x <= 0){

            points.remove(0);
            //PointF point = new PointF( last_x, SetRandomY() );
            points.add(new PointF(last_x, ((int) (Math.random() * boundary) + offset)));
            score += 1; //whenever new point is created, add one point
        }

        draw ( canvas ) ;
    }

    //draw the points in the array list
    public void draw ( Canvas c ) {

        Path path = new Path() ;
        path . moveTo (points.get(0).x, points.get(0).y) ; // Move to first point
        for ( int i = 1; i < points . size () ; ++ i ) { // For number of points
            PointF p = points.get(i);
            path . lineTo ( p .x , p . y ) ;

        }

        c . drawPath ( path , DashPaint ) ;

        c.drawText(String.valueOf(score), 10, bottom / 20, TextPaint); // draws the score value
    }


    public int GetY( int x ){
        // y = (y2-y1)/(x2-x1) (x-x1) + y1
        // x1 < x, x2 > x
        // (x-x1)/(x2-x1) = (y - y1)/(y2-y1)
        int y;
        PointF p3 = points.get(6);
        if (p3 == null){
        }
        PointF p2 = points.get(5);
        PointF p1 = points.get(4);
        if ( x > p2.x ){
            y = (int) ((((p3.y - p2.y)/(p3.x - p2.x)) * (x - p2.x)) + p2.y) ;
            return y;
        }
        else if ( x < p2.x ){
            y = (int) ((((p2.y - p1.y)/(p2.x - p1.x)) * (x - p1.x)) + p1.y) ;
            return y;
        }
        else{
            return (int) p2.y;
        }

    }

    public int GetLastX(){
        return LastX;
    }

}

