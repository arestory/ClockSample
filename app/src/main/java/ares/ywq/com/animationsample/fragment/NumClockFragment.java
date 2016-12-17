package ares.ywq.com.animationsample.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ares.ywq.com.animationsample.MyView.NumClock;
import ares.ywq.com.animationsample.R;

/**
 * Created by ares on 2016/12/10.
 */
public class NumClockFragment extends BaseFragment {


    private NumClock numClock;


    //通过单例模式生成
    public static NumClockFragment newInstance(String flag) {
        NumClockFragment fragment = new NumClockFragment();
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
        return R.layout.num_fragment;
    }

    @Override
    public void doBusinessWithView(View rootView, Bundle savedInstanceState) {

        numClock=(NumClock)rootView.findViewById(R.id.numClock);
        //numClock.setAlpha(0);

    }

    @Override
    public void isVisible(boolean isVisible) {

        Log.v("num_fragment","num_fragment isVisible:"+isVisible);
        if(isVisible){
           // scaleAnimation(numClock,500);
        }else{
           // if(numClock!=null) numClock.setAlpha(0);
        }
    }

    public void setLightClockAlpha(float alpha){

        if(numClock!=null){
            numClock.setAlpha(alpha);
            numClock.setScaleY(alpha);
            numClock.setScaleX(alpha);
        }

    }
}
