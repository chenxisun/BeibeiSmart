package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import edu.buaa.beibeismart.R;

/**
 * Created by fan on 2018/4/12.
 */

public class My_story_list extends BaseActivity {

    public static final String Action = "edu.buaa.beibeismart.intent.story_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_list);
        setListener();

    }

    private void setListener() {
        findViewById(R.id.chinese_story_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_story_list.this, Album_list.class);
                intent.putExtra("title", "中文故事");
                startActivity(intent);

            }
        });
        findViewById(R.id.english_story_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_story_list.this, Album_list.class);
                intent.putExtra("title", "英文故事");
                startActivity(intent);

            }
        });
        findViewById(R.id.back_btn_story_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(My_story_list.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
