package edu.buaa.beibeismart;

import android.content.Context;
import android.widget.Toast;
import java.io.IOException;
import edu.buaa.beibeismart.Service.RecordBackground;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class requestClient {
    public void get(final Context context, String url) {
        final OkHttpClient client = new OkHttpClient();
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get().url(url).build();
        Call call = client.newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context, "Get 失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String severResult = response.body().string();

                RecordBackground.callback(severResult);
            }
        });
    }
}
