package edu.buaa.beibeismart.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.buaa.beibeismart.R;


/**
 * Created by chenshujun on 2016/3/22.
 */
public class LoadingDialog extends Dialog{
    private TextView tv;


    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loadingdialog);
        tv = (TextView) this.findViewById(R.id.tv);
        tv.setText("新闻加载中，请稍后...");
        LinearLayout linearLayout = (LinearLayout) this
                .findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(0);
    }
}
