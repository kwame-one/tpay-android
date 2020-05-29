package com.kwame.tpay.contracts.fcm_token;

import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.BaseResponse;
import com.kwame.tpay.utils.AppUtils;
import com.kwame.tpay.utils.GoodPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FcmTokenPresenterImp implements FcmTokenPresenter {

    private FcmTokenListener listener;

    public FcmTokenPresenterImp(FcmTokenListener listener) {
        this.listener = listener;
    }

    @Override
    public void sendTokenToServer() {
        String fcm = GoodPrefs.getInstance().getString("cloud_token", null);
        boolean isUpdated = GoodPrefs.getInstance().getBoolean("fcm_updated", false);

        if (fcm != null && !isUpdated) {
            Call<BaseResponse> call = ApiClient.createService().updateFcmToken(
                    "Bearer "+ AppUtils.getToken(),
                    fcm
            );

            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        GoodPrefs.getInstance().saveBoolean("fcm_updated", true);
                        listener.onSaveTokenSuccess();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });
        }
    }
}
