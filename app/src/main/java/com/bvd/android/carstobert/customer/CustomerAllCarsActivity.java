package com.bvd.android.carstobert.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bvd.android.carstobert.R;
import com.bvd.android.carstobert.controllers.CarController;
import com.bvd.android.carstobert.model.Car;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerAllCarsActivity extends AppCompatActivity {

    @BindView(R.id.customerCarList)
    public ListView carListView;
    private List<Car> cars;
    private Retrofit retrofit;
    public static final String API_BASE_URL = "http://10.0.2.2:4000/";
    public static final String TAG = CustomerAllCarsActivity.class.getName();
    //private AllCarsAdapter carArrayAdapter;
    private ArrayAdapter<Car> carArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);
        cars = new ArrayList<>();

       /* Car car = new Car("P100D", "Tesla", 1, "sold");
        cars.add(car);*/

        //carArrayAdapter = new AllCarsAdapter(this, -1, cars);
        carArrayAdapter = new ArrayAdapter<Car>(this, android.R.layout.simple_list_item_1, cars);
        carListView.setAdapter(carArrayAdapter);


        callBackend();
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Car car1 = carArrayAdapter.getItem(i);
                Intent intent = new Intent(view.getContext(), CustomerCarDetailActivity.class);
                intent.putExtra("car", car1);
                startActivity(intent);
            }
        });

    }

    private void callBackend() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .build();


        CarController carController = retrofit.create(CarController.class);
        Call<List<Car>> getCarsCall = carController.getAvailableCars();
        Log.v(TAG, "Calling api for getting all my cars");
        getCarsCall.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                Log.v(TAG, "getting the cars...");
                cars = response.body();
                Log.v(TAG, "Items from api=" + cars);
                //carArrayAdapter.removeAll();
                carArrayAdapter.clear();
                carArrayAdapter.addAll(cars);
                carArrayAdapter.notifyDataSetChanged();


                Log.v(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Log.v(TAG, "Failed call");
                launchRetryQuestion();

            }
        });


    }

    private void launchRetryQuestion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("can't reach server");
        builder.setMessage(" Do you want to retry?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();
                callBackend();

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }
}
