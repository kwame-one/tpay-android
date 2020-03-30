package com.kwame.tpay.remote;

import com.kwame.tpay.remote.response.AuthResponse;
import com.kwame.tpay.remote.response.BaseResponse;
import com.kwame.tpay.remote.response.DriverResponse;
import com.kwame.tpay.remote.response.WalletResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    Call<AuthResponse> login(@Field("contact") String contact, @Field("password") String password);


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("register")
    Call<AuthResponse> register(
            @Field("surname") String surname,
            @Field("other_names") String otherNames,
            @Field("contact") String contact,
            @Field("password") String password,
            @Field("role") int role
    );


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("driver/save")
    Call<DriverResponse> saveDriverDetails(
            @Header("Authorization") String token,
            @Field("vehicle_model") String vehicleModel,
            @Field("vehicle_number") String vehicleNumber
    );

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("user/wallet/setup")
    Call<WalletResponse> setupWallet(@Header("Authorization") String token, @Field("wallet") int wallet);


    @Headers("Accept: application/json")
    @PUT("user/wallet/activate")
    Call<BaseResponse> activateWallet(@Header("Authorization") String token);


    @Headers("Accept: application/json")
    @PUT("user/wallet/deactivate")
    Call<BaseResponse> deactivateWallet(@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @GET("user/wallet/balance")
    Call<WalletResponse> checkWalletBalance(@Header("Authorization") String token);


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("password/change")
    Call<BaseResponse> changePassword(
            @Header("Authorization") String token,
            @Field("old_password") String oldPassword,
            @Field("password") String password,
            @Field("password_confirmation") String confirmPassword
    );


}
