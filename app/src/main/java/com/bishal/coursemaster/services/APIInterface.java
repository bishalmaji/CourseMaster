package com.bishal.coursemaster.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIInterface {
    String BASE_URL = "https://drive.google.com/";

    @GET
    Call<ResponseBody> getHtmlResponse(@Url String downloadLink);
}
