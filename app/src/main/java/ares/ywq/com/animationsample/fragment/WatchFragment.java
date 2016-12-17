package ares.ywq.com.animationsample.fragment;

import android.os.Bundle;
import android.view.View;

import ares.ywq.com.animationsample.MyView.WatchView;
import ares.ywq.com.animationsample.R;

/**
 * Created by ares on 2016/12/10.
 */
public class WatchFragment extends BaseFragment {


    private WatchView watchView;
    //通过单例模式生成
    public static WatchFragment newInstance(String flag) {
        WatchFragment fragment = new WatchFragment();
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
        return R.layout.wath_fragment;
    }

    @Override
    public void doBusinessWithView(View rootView, Bundle savedInstanceState) {

        watchView=(WatchView)rootView.findViewById(R.id.watchView);
        //watchView.setAlpha(0);
    }


    @Override
    public void isVisible(boolean isVisible) {
        if(isVisible){
           // scaleAnimation(watchView,500);
        }else{
//            if(watchView!=null)
//            watchView.setAlpha(0);
        }
    }


    public void setLightClockAlpha(float alpha){

        if(watchView!=null){
            watchView.setAlpha(alpha);
//            watchView.setScaleY(alpha);
//            watchView.setScaleX(alpha);
        }

    }
}
