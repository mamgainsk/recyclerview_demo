package com.org.demowipro.NetworkingService;

import com.org.demowipro.RequestPOJO.RowContentInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageAPIInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<RowContentInfo> getJSONResponse();

}
