package com.example.tony.dash_till_puff;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Tony on 4/20/2015.
 */
public class DashTillPuffSurfaceView extends SurfaceView
        implements SurfaceHolder. Callback , TimeConscious {

    DashTillPuffRenderThread renderThread;


    private int alpha; //set integer for alpha
    private int x2 , y2 ; //coordinates for the first rectangle
    private int  rhs; //know when it finishes scrolling

    private boolean ScreenOn = false;

    Bitmap bitmap;
    Bitmap spaceship;
    Trajectory trajectory;
    Context PassContexttoCosmicFactory;
    private ArrayList<Bitmap> cosmicsArrayList = new ArrayList<>();


    Paint StrokePaint = new Paint();
    Paint TextPaint = new Paint();

    Spaceship ship;
    static int restart = 0;

    CosmicFactory cosmicFactory;

    public DashTillPuffSurfaceView ( Context context ) {
        super(context) ;
        getHolder () . addCallback(this) ;
        PassContexttoCosmicFactory = context;
    }


    @Override
    public void surfaceCreated ( SurfaceHolder holder ) {
        renderThread = new DashTillPuffRenderThread ( this ) ;
        renderThread . start () ;

        //...
// Create the sliding background , cosmic factory , trajectory
// and the space ship
        //...

        rhs = holder.getSurfaceFrame().right; //grabs pixel at the right of the screen
        y2 = holder.getSurfaceFrame().bottom; //grabs pixel at the bottom of the screen
        //x2 = rhs; //initial set at the right hand side

        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.dashtillpuffwallpaper, options);


        spaceship = BitmapFactory.decodeResource(this.getResources(), R.drawable.dashtillpuffspaceship, options);

        ship = new Spaceship(spaceship , (rhs/3), y2/2, y2);
        spaceship = null;

        //Log.d("trajectory", "before trajectory");
        trajectory = new Trajectory( y2, rhs);
        //Log.d("trajectory", "after trajectory");

        cosmicFactory = new CosmicFactory(PassContexttoCosmicFactory, trajectory, ship, rhs, y2, 0);

        StrokePaint.setColor(Color.BLACK);
        StrokePaint.setTextSize(y2 / 5);
        StrokePaint.setAntiAlias(true);
        StrokePaint.setStyle(Paint.Style.STROKE);
        StrokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        StrokePaint.setStrokeWidth(5);

        TextPaint.setColor(Color.WHITE);
        TextPaint.setTextSize(y2/5);
        TextPaint.setAntiAlias(true);
        TextPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void surfaceDestroyed ( SurfaceHolder holder ) {
// The cleanest way to stop a thread is by interrupting it .
// BubbleShooterThread regularly checks its interrupt flag .
        renderThread . interrupt() ;
    }

    @Override
    public void surfaceChanged ( SurfaceHolder holder ,
                                 int format , int width , int height ) {
// Respond to surface changes , e . g . , aspect ratio changes .
    }


    @Override
    public boolean onTouchEvent ( MotionEvent e ) { //sets boolean for the ship's acceleration

        if (ScreenOn == false){
            ScreenOn = true;
        }

        switch ( e . getAction () ) {
            case MotionEvent . ACTION_DOWN : // Thrust the space ship up .
                ship.AccelUp();
                break ;
            case MotionEvent . ACTION_UP : // Let space ship fall freely .
                ship.AccelDown();
                break ;
        }
        return true ;
    }


    @Override
    public void onDraw ( Canvas c ) {
        super . onDraw ( c ) ;
// Draw everything ( restricted to the displayed rectangle ) .

    }


    @Override
    public void tick ( Canvas c ) {
// Tick background , space ship , cosmic factory , and trajectory .
// Draw everything ( restricted to the displayed rectangle ) .

        draw(c);

        if (ScreenOn == false){
            ship.draw(c);
            trajectory.draw(c);
            DrawClickToStart(c);
        }
        else {
            trajectory.tick(c);
            ship.tick(c);
            cosmicFactory.tick(c);
            if (cosmicFactory.Collision()== 1){
                DrawGameOver(c);
                surfaceDestroyed(getHolder());
                Restart();
                ScreenOn = false;
                surfaceCreated(getHolder());
            }
        }
    }



    public void Restart(){
        trajectory = null;
        cosmicFactory = null;
        ship = null;
    }


    public void draw ( Canvas c ) {
        Paint paint = new Paint () ;


        //paint . setAlpha ( 1 ) ; // Control transparency
        // if alpha is 1 then its gone?

        Rect dst = new Rect ( 0 , 0 , rhs , y2 ) ; // Where to draw .
        c . drawBitmap(bitmap, null, dst, paint) ;

    }
    private void DrawGameOver( Canvas c){
        c.drawText("GameOver", rhs/3 , y2/2 , StrokePaint);
        c.drawText("GameOver", rhs/3, y2/2, TextPaint);
    }

    private void DrawClickToStart( Canvas c ) {

        c.drawText("Click to Start", rhs/3 , y2/2 , StrokePaint);
        c.drawText("Click to Start", rhs/3 , y2/2, TextPaint);
    }

}