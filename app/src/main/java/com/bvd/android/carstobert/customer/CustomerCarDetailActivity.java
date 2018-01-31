package com.bvd.android.carstobert.customer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bvd.android.carstobert.R;
import com.bvd.android.carstobert.controllers.CarController;
import com.bvd.android.carstobert.db.AppDatabase;
import com.bvd.android.carstobert.db.CarDAO;
import com.bvd.android.carstobert.model.Car;
import com.bvd.android.carstobert.model.dtos.CarBuyDto;

import org.w3c.dom.Text;

import java.io.IOException;

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


public class CustomerCarDetailActivity extends AppCompatActivity {
    private Car car;
    public static final String TAG = CustomerCarDetailActivity.class.getName();

    @BindView(R.id.carDetailName)
    public TextView nameTxt;

    @BindView(R.id.carDetailType)
    public TextView typeTxt;

    //@BindView(R.id.carQuantity)
    public TextView quantityTxt;

    @BindView(R.id.carDetailNrToBu)
    public EditText quantityToBuy;

    @BindView(R.id.carDetailBuy)
    public Button buyBtn;

    private Retrofit retrofit;
    private CarBuyDto carBuyDto;
    private AppDatabase appDatabase;
    private CarDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_car_detail);
        ButterKnife.bind(this);
        initDB();

        if(!isNetworkAvailable()){
            buyBtn.setEnabled(false);
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(CustomerAllCarsActivity.API_BASE_URL)
                .build();

        quantityTxt = findViewById(R.id.carQuantity);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            car = (Car) extras.getSerializable("car");
            Log.v(TAG, "DETAILS: Car with details" + car);
            if (car != null) {
                nameTxt.setText(car.getName());
//                quantityTxt.setText(car.getQuantity());
                typeTxt.setText(car.getType());
            } else {
                Log.v(TAG, "Key is null");
            }
        }

    }

    @OnClick(R.id.carDetailBuy)
    public void buyCar() {
        CarController carController = retrofit.create(CarController.class);
        carBuyDto = new CarBuyDto(car.getId(), Integer.parseInt(quantityToBuy.getText().toString()));
        Call<Car> carCall = carController.buyCar(carBuyDto);
        carCall.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                Log.v(TAG, "We bought the car" + car + " |response code=" + response.code());
                if(response.code()==200) {
                    Toast.makeText(CustomerCarDetailActivity.this, "Succesful Buy", Toast.LENGTH_SHORT).show();
                    saveToDB(car);
                    finish();
                }else if(response.code() ==404){
                    try {
                        Toast.makeText(CustomerCarDetailActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {

            }
        });


    }

    private void saveToDB(Car car) {
        Car car1 = new Car(car.getId(), car.getName(), car.getType(), car.getStatus(), carBuyDto.getQuantity());
        dao.insertAll(car1);
    }

    private void initDB() {
        appDatabase = AppDatabase.getAppDatabase(this);
        dao = appDatabase.getDAO();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }
}
