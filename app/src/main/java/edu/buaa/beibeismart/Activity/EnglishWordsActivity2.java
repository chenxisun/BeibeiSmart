package edu.buaa.beibeismart.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import edu.buaa.beibeismart.Fragment.WordFragment;
import edu.buaa.beibeismart.R;

public class EnglishWordsActivity2 extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    BottomNavigationBar  bottomNavigationBar;
    WordFragment wordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_englishwords);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.word_64,"Word")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.vedio_64,"Vedio")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.vedio_64,"Vedio")).setActiveColor(R.color.btnClick)
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment(){
        wordFragment =  new WordFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragments,wordFragment).commit();
    }

    @Override
    protected void initView() {
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        switch (position){
            case 0:
                if (wordFragment == null){
                    wordFragment = new WordFragment();
                }
                transaction.replace(R.id.fragments,wordFragment);
                break;
            case 1:
                break;
            case 2:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {}

    @Override
    public void onTabReselected(int position) {}
}
