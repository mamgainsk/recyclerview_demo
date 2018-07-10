package com.org.demowipro.networking_service;

import retrofit2.Call;
import retrofit2.Response;

public interface APICallback {
    void onResponse(Call<?> call, Response<?> response, int requestCode);

    void onFailure(Call<?> call, Throwable t, int requestCode);
}
