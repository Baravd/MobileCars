package com.bvd.android.carstobert.controllers;

import com.bvd.android.carstobert.model.Car;
import com.bvd.android.carstobert.model.dtos.CarBuyDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by bara on 1/28/2018.
 */

public interface CarController {
    @GET("cars")
    Call<List<Car>> getAvailableCars();

    @POST("buyCar")
    Call<Car> buyCar(@Body CarBuyDto carBuyDto);

    @GET("all")
    Call<List<Car>> getAllForEmployee();
}
