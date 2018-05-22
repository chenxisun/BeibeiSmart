package edu.buaa.beibeismart.Interface;

import edu.buaa.beibeismart.Activity.Translation;
import retrofit2.http.GET;
import retrofit2.Call;

public interface GetRequest_Interface{
    @GET("wikipedia/list?pageNo=0&pageSize=60")
        Call<Translation> getCall();

}
