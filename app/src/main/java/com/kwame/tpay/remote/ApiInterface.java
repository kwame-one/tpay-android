package com.kwame.tpay.remote;

import com.kwame.tpay.remote.response.AuthResponse;
import com.kwame.tpay.remote.response.DriverResponse;
import com.kwame.tpay.remote.response.WalletResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
    @POST("user/wallet/activate")
    Call<WalletResponse> activateWallet(@Header("Authorization") String token, @Field("wallet") int wallet);



}
