package ares.ywq.com.animationsample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * Created by ares on 2016/11/20.
 */
public abstract class BaseActivity  extends ActionBarActivity {

    /**
     * TODO 设置layout文件
     *
     * @return 目标资源layoutId
     */
    protected abstract int bindContentViewId();


    /**
     * 初始化数据 以及 各种控件的事件
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 显示toast信息
     * @param text
     */
    protected void showToast(String text)

    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindContentViewId());
        initData(savedInstanceState);

    }
}
