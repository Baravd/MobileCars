package com.bvd.android.carstobert.controllers;

import android.arch.persistence.room.Delete;

import com.bvd.android.carstobert.model.Car;
import com.bvd.android.carstobert.model.dtos.CarBuyDto;
import com.bvd.android.carstobert.model.dtos.CarIdDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @POST("removeCar")
    Call<Car> removeCar(@Body CarIdDto id);

    @POST("addCar")
    Call<Car> addCar(@Body Car car);
}
