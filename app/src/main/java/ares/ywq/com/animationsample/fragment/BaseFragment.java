package ares.ywq.com.animationsample.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * 所有fragment继承此fragment
 * Created by ares on 16/1/26.
 */
public abstract class BaseFragment extends Fragment {


    /**
     * 当视图消失时
     */
    public abstract void onViewDestroy();
    /**
     * 绑定布局ID
     * @return
     */
    public abstract int bindLayoutId();

    /**
     * ui操作
     * @param rootView
     */
    public abstract void doBusinessWithView(View rootView,Bundle savedInstanceState);


    /**
     * 判断是否可见
     * @param isVisible
     */
    public abstract void isVisible(boolean isVisible);

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        onViewDestroy();

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView  =inflater.inflate(bindLayoutId(), null, false);
        doBusinessWithView(rootView, savedInstanceState);
        return rootView;
    }



    /**
     * 显示toast信息
     * @param text
     */
    protected void showToast(String text)

    {
        Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
    }



    /**
     * 检测网络是否可用
     *
     * @return true为网络可用，否则不可用
     */
    protected boolean networkEnable() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected());
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible(isVisibleToUser);
    }

    /**
     * 放大动画
     * @param view
     * @param duartion
     */
    protected  void scaleAnimation(final View view,long duartion){

        if(view!=null){

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
        });}

    }

    /**
     * 缩小动画
     * @param view
     * @param duartion
     */
    protected  void smallAnimation(final View view,long duartion){

        if(view!=null){
        final ObjectAnimator smallAnim = ObjectAnimator//
                .ofFloat(view, "alpha", 1.0F,  0.0F)//
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
        });}

    }








}
