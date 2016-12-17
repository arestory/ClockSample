package ares.ywq.com.animationsample;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ares.ywq.com.animationsample.MyView.LightClock;
import ares.ywq.com.animationsample.MyView.NumClock;
import ares.ywq.com.animationsample.MyView.WatchView;

public class MainActivity extends Activity {

    private WatchView watchView;
    private LightClock lightClock;
    private NumClock numClock;
    private TextView textView;



    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);
        init();
     }


    public void init(){
        lightClock=(LightClock)findViewById(R.id.lightLock);
        watchView=(WatchView)findViewById(R.id.watchView);
        numClock=(NumClock)findViewById(R.id.numClock);
//        textView=(TextView)findViewById(R.id.tv);
        lightClock.setTimeChangeListener(new LightClock.TimeChangeListener() {
            @Override
            public void onTimeChange(int hour, int minute, int second) {
                String hourStr =(hour<10)?"0"+hour:hour+"";
                String minuteStr =(minute<10)?"0"+minute:minute+"";
                String secondStr =(second<10)?"0"+second:second+"";


//                textView.setText(hourStr+":"+minuteStr+":"+secondStr);

            }
        });

        watchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
            }
        });


       firstAnim(lightClock,800);
        firstAnim(watchView,1000);
        firstAnim(numClock,1200);


//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                lightClock.setColor(LightClock.ColorBelong.centerCircle, Color.parseColor("#ca7070"));
//                lightClock.setColor(LightClock.ColorBelong.triangle,Color.parseColor("#FF4081"));
//            }
//        });

    }

    public void initUi(){
        watchView=(WatchView)findViewById(R.id.watchView);
        textView=(TextView)findViewById(R.id.tv);
        watchView.setTimeChangeListener(new WatchView.TimeChangeListener() {
            @Override
            public void onTimeChange(int hour, int minute, int second) {
                if(second<10){
                    textView.setText(hour+":"+minute+":0"+second);
                }else{
                    textView.setText(hour+":"+minute+":"+second);

                }
            }
        });
    }


    public static void firstAnim(final View view,long duartion){

        final ObjectAnimator smallAnim = ObjectAnimator//
                .ofFloat(view, "alpha", 0.0F,  1.0F)//
                .setDuration(duartion);//

        smallAnim.start();
        smallAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float cVal = (Float) animation.getAnimatedValue();

                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);


            }
        });

    }



}
