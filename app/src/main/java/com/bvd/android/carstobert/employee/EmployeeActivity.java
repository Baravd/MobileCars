package com.bvd.android.carstobert.employee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.bvd.android.carstobert.R;
import com.bvd.android.carstobert.controllers.CarController;
import com.bvd.android.carstobert.customer.CustomerAllCarsActivity;
import com.bvd.android.carstobert.model.Car;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeActivity extends AppCompatActivity {


    private static final String TAG = EmployeeActivity.class.getName();
    @BindView(R.id.employeeCarList)
    ListView carsListView;
    private List<Car> cars;
    private CarEmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        ButterKnife.bind(this);
        cars = new ArrayList<>();
        //cars.add(new Car(1, "n1", "t1", "st1", 2));
        retrieveAllCars();

        adapter = new CarEmployeeAdapter(this, R.layout.list_item_2_layout, cars);
        carsListView.setAdapter(adapter);
    }


    private void retrieveAllCars() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(CustomerAllCarsActivity.API_BASE_URL)
                .build();


        CarController carController = retrofit.create(CarController.class);
        Call<List<Car>> getCarsCall = carController.getAllForEmployee();
        Log.v(TAG, "Calling api for getting all my cars");
        getCarsCall.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                Log.v(TAG, "getting the cars...");
                cars = response.body();
                Log.v(TAG, "Items from api=" + cars);
                //carArrayAdapter.removeAll();
                adapter.clear();
                adapter.addAll(cars);
                adapter.notifyDataSetChanged();


                Log.v(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Log.v(TAG, "Failed call");
                //launchRetryQuestion();

            }
        });
    }

    @OnClick(R.id.addCarBtn)
    public void returnBtn() {
        Intent intent = new Intent(this, AddCarActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.deleteCarBtn)
    public void removeCarBtn() {
        Intent intent = new Intent(this, RemoveCarActivity.class);
        startActivity(intent);
    }




}
