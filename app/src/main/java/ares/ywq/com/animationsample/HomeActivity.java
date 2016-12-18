package ares.ywq.com.animationsample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ares.ywq.com.animationsample.fragment.LightClockFragment;
import ares.ywq.com.animationsample.fragment.NumClockFragment;
import ares.ywq.com.animationsample.fragment.WatchFragment;

/**
 * Created by ares on 2016/12/5.
 */
public class HomeActivity extends BaseActivity {


    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;


    private List<Fragment> list_fragment=new ArrayList<>();
    private LightClockFragment lightClockFragment;
    private NumClockFragment numClockFragment;
    private WatchFragment watchFragment;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected int bindContentViewId() {
        return R.layout.main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        initUI(savedInstanceState);
    }

    private String [] mTitles={"荧光","简约","LED"};

    private void initClockFragment(){
        lightClockFragment=LightClockFragment.newInstance("light");
        numClockFragment=NumClockFragment.newInstance("num");
        watchFragment=WatchFragment.newInstance("watch");
        list_fragment.add(lightClockFragment);

        list_fragment.add(watchFragment);
        list_fragment.add(numClockFragment);


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list_fragment.get(position);
            }

            @Override
            public int getCount() {
                return list_fragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }





        });
        //设置支持水平滑动
        //projectTab.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(mTitles.length);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                int currentItem=viewPager.getCurrentItem();
                if(position==0){
                    lightClockFragment.setLightClockAlpha(1-positionOffset);
                    watchFragment.setLightClockAlpha(positionOffset);

                }

                if(position==1){

                    numClockFragment.setLightClockAlpha(positionOffset);

                    watchFragment.setLightClockAlpha(1-positionOffset);
                }




                Log.v("positionOffset","position="+position+",currentItem="+viewPager.getCurrentItem()+",positionOffset="+positionOffset+",positionOffsetPixels="+positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {





            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }


    private void initUI(Bundle savedInstanceState){


        tabLayout=(TabLayout)findViewById(R.id.clockTab);
        viewPager=(ViewPager)findViewById(R.id.viewpager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("时钟");// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(toolbar);



        //设置侧滑
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open,
                R.string.close);

        // mDrawerLayout.setScrimColor(getResources().getColor(R.color.white));
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        initClockFragment();
    }

}
