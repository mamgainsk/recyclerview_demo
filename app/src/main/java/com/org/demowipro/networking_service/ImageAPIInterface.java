package com.org.demowipro.networking_service;

import com.org.demowipro.request_pojo.RowContentInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageAPIInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<RowContentInfo> getJSONResponse();

}
