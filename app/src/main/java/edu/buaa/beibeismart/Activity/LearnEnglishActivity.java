package edu.buaa.beibeismart.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import edu.buaa.beibeismart.Fragment.BaseFragment;
import edu.buaa.beibeismart.Fragment.MovieFragment;
import edu.buaa.beibeismart.Fragment.VedioFragment;
import edu.buaa.beibeismart.Fragment.WordFragment;
import edu.buaa.beibeismart.R;

public class LearnEnglishActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    BottomNavigationBar  bottomNavigationBar;
    MovieFragment movieFragment;
    VedioFragment vedioFragment;
    WordFragment wordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_english);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.word_64,"Word")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.vedio_64,"Vedio")).setActiveColor(R.color.btnClick)
                .addItem(new BottomNavigationItem(R.drawable.movie_64,"Movie")).setActiveColor(R.color.btnClick)
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
                if (vedioFragment == null){
                    vedioFragment = new VedioFragment();
                }
                transaction.replace(R.id.fragments,vedioFragment);
                break;
            case 2:
                if (movieFragment == null){
                    movieFragment = new MovieFragment();
                }
                transaction.replace(R.id.fragments,movieFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {}

    @Override
    public void onTabReselected(int position) {}
}
