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
import edu.buaa.beibeismart.R;

@SuppressLint("ValidFragment")
public class WordFragment extends BaseFragment implements View.OnClickListener {

    String chacter;
    JSONObject content;
    String chinese_introduction;
    String english_vedio;
    String english_introduction;
    String chinese_vedio,wordVoice;
    ArrayList<String> imgList = new ArrayList<>();
    GridView gvImg;
    ImgLoadeAdapter adapter;
    Button btnPre,btnNext;

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

        tvChacter.setText(chacter);
        tvEnglishIntroduction.setText(english_introduction);
        tvChineseIntroduction.setText(chinese_introduction);
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
        if (isStart == 1){
            btnPre.setVisibility(View.INVISIBLE);
        }
        if(isEnd == 1){
            btnNext.setVisibility(View.INVISIBLE);
        }

    }

    //记录当前数据位置
    int isStart;
    int isEnd;
    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        JSONObject imgUrl = null;
        try {
            isStart = bundle.getInt("isStart");
            isEnd = bundle.getInt("isEnd");
            JSONObject param = new JSONObject(bundle.getString("param"));
            chacter = param.getString("chacter");
            content = param.getJSONObject("content");
            chinese_introduction = content.getString("chineseIntroduction");
            english_vedio = content.getString("englishVoice");
            english_introduction = content.getString("englishIntroduction");
            chinese_vedio = content.getString("chineseVoice");
            //wordVoice = content.getString("wordVoice");
            imgUrl = content.getJSONObject("imgUrl");
            for(int i =0; i < imgUrl.length();i++){
                System.out.println("json:"+imgUrl.getString(imgUrl.names().getString(i)));
                imgList.add(imgUrl.getString(imgUrl.names().getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void disposeRead(String url){
        String url1 = "";

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_read_english:
                disposeRead(english_vedio);
                break;
            case R.id.btn_read_chinese:
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
