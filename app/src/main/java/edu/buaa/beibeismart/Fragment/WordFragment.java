package edu.buaa.beibeismart.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.buaa.beibeismart.Adapter.ImgLoadeAdapter;
import edu.buaa.beibeismart.Bean.EnglishWordBean;
import edu.buaa.beibeismart.Media.OnlineMediaPlayer;
import edu.buaa.beibeismart.R;

@SuppressLint("ValidFragment")
public class WordFragment extends BaseFragment implements View.OnClickListener {

    JSONObject content;
    String chinese_vedio,wordVoice;
    ArrayList<String> imgList = new ArrayList<>();
    GridView gvImg;
    ImgLoadeAdapter adapter;
    Button btnPre,btnNext;

    @Override
    public void onDestroy() {
        super.onDestroy();
        OnlineMediaPlayer.getInstance().clearVedioUrl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_learnenglish_word,container,false);
        TextView tvChacter = view.findViewById(R.id.tv_chacter);
        TextView tvEnglishIntroduction = view.findViewById(R.id.tv_introduction_english);
        TextView tvChineseIntroduction = view.findViewById(R.id.tv_introduction_chinese);
        Button btnEnglish = view.findViewById(R.id.btn_read_english);
        Button btnChinese = view.findViewById(R.id.btn_read_chinese);
        btnPre = view.findViewById(R.id.btn_pre);
        btnNext = view.findViewById(R.id.btn_next);

        tvChacter.setText(curEnglishWord.getEnglishContent());
        tvChacter.setVisibility(View.VISIBLE);
        if (curEnglishWord.getEnglishContent().trim().isEmpty() || curEnglishWord.getEnglishContent().trim().equals("null")){
            tvChacter.setVisibility(View.INVISIBLE);
        }
        tvEnglishIntroduction.setText(curEnglishWord.getEnglishContent());
        tvEnglishIntroduction.setVisibility(View.VISIBLE);
        if (curEnglishWord.getEnglishContent().trim().isEmpty() || curEnglishWord.getEnglishContent().trim().equals("null")){
            tvEnglishIntroduction.setVisibility(View.INVISIBLE);
            btnEnglish.setVisibility(View.INVISIBLE);
        }
        tvChineseIntroduction.setText(curEnglishWord.getChineseContent());
        tvChineseIntroduction.setVisibility(View.VISIBLE);
        if (curEnglishWord.getChineseContent().trim().isEmpty() || curEnglishWord.getChineseContent().trim().equals("null")){
            tvChineseIntroduction.setVisibility(View.INVISIBLE);
            btnChinese.setVisibility(View.INVISIBLE);
        }
        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnEnglish.setOnClickListener(this);
        btnChinese.setOnClickListener(this);

        gvImg = view.findViewById(R.id.gv_english_pic);
        gvImg.setNumColumns(imgList.size());
        adapter = new ImgLoadeAdapter(getContext(),imgList);
        gvImg.setAdapter(adapter);
        disposeButton();
        return view;
    }

    private void disposeButton(){
        btnNext.setVisibility(View.VISIBLE);
        btnPre.setVisibility(View.VISIBLE);
        if (isStartWord == 1){
            btnPre.setVisibility(View.INVISIBLE);
        }
        if(isEndWord == 1){
            btnNext.setVisibility(View.INVISIBLE);
        }

    }

    //记录当前数据位置
    int isStartWord;
    int isEndWord;
    EnglishWordBean curEnglishWord;
    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        JSONObject imgUrl = new JSONObject();
        try {
            isStartWord = bundle.getInt("isStartdWord");
            isEndWord = bundle.getInt("isEndWord");
            curEnglishWord = (EnglishWordBean) bundle.getSerializable("param");

            imgUrl.put("imgUrl",curEnglishWord.getImgPath());

            for(int i =0; i < imgUrl.length();i++){
                System.out.println("json:"+imgUrl.getString(imgUrl.names().getString(i)));
                imgList.add(imgUrl.getString(imgUrl.names().getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void disposeRead(String url){
        //String stringExtra1="http://music.baidu.com/song/569080829?pst=musicsong_play";
        OnlineMediaPlayer.getInstance().play(url);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_read_english:
                disposeRead(curEnglishWord.getEnglishVoicePath());
                break;
            case R.id.btn_read_chinese:
                disposeRead(curEnglishWord.getChineseVoicePath());
                break;
            default:
                iButtonNeighborListener.onNeighborButtonClick(view.getId());
        }

    }
    IButtonNeighborListener iButtonNeighborListener;
    public void setiButtonNeighborListener(IButtonNeighborListener iButtonNeighborListener){
        this.iButtonNeighborListener = iButtonNeighborListener;
    }
    public interface IButtonNeighborListener{
        void onNeighborButtonClick(int viewId);
    }
}
