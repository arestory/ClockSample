package ares.ywq.com.animationsample.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ares.ywq.com.animationsample.MyView.LightClock;
import ares.ywq.com.animationsample.R;

/**
 * Created by ares on 2016/12/10.
 */
public class LightClockFragment extends BaseFragment {



    private LightClock lightClock;

    //通过单例模式生成
    public static LightClockFragment newInstance(String flag) {
        LightClockFragment fragment = new LightClockFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onViewDestroy() {

    }

    @Override
    public int bindLayoutId() {
        return R.layout.light_fragment;
    }

    @Override
    public void doBusinessWithView(View rootView, Bundle savedInstanceState) {

        lightClock=(LightClock)rootView.findViewById(R.id.lightLock);
        //lightClock.setAlpha(0);
        scaleAnimation(lightClock,1500);

    }

    @Override
    public void isVisible(boolean isVisible) {

        if(isVisible){
            //scaleAnimation(lightClock,500);
        }else{
            if(lightClock!=null)
            lightClock.setAlpha(0);
        }

    }


    public void setLightClockAlpha(float alpha){

        if(lightClock!=null){
            lightClock.setAlpha(alpha);
            lightClock.setScaleY(alpha);
            lightClock.setScaleX(alpha);
        }

    }


}
