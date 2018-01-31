package com.bvd.android.carstobert.employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bvd.android.carstobert.R;
import com.bvd.android.carstobert.controllers.CarController;
import com.bvd.android.carstobert.customer.CustomerAllCarsActivity;
import com.bvd.android.carstobert.model.Car;
import com.bvd.android.carstobert.model.dtos.CarIdDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoveCarActivity extends AppCompatActivity {
    private static final String TAG = RemoveCarActivity.class.getName();
    @BindView(R.id.idToRemoveTxt)
    public EditText idCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_car);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.removeRemoveBtn)
    public void removeCar() {
        callBackend();
    }

    private void callBackend() {
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
        String id = idCar.getText().toString();
        Call<Car> getCarsCall = carController.removeCar(new CarIdDto(Integer.parseInt(id)));
        Log.v(TAG, "Calling api for getting all my cars");
        getCarsCall.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                Log.v(TAG, "removing car...");

                if (response.code() == 200) {

                    Toast.makeText(RemoveCarActivity.this, "Succesful delete", Toast.LENGTH_SHORT).show();
                }
                Log.v(TAG, "not removed");
                finish();

            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.v(TAG, "Failed call");
                //launchRetryQuestion();

            }
        });

    }
}
