package com.bvd.android.carstobert.employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bvd.android.carstobert.R;
import com.bvd.android.carstobert.controllers.CarController;
import com.bvd.android.carstobert.customer.CustomerAllCarsActivity;
import com.bvd.android.carstobert.model.Car;
import com.google.gson.Gson;

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

public class AddCarActivity extends AppCompatActivity {
    private static final String TAG = AddCarActivity.class.getName();
    @BindView(R.id.addNameText)
    EditText nameText;
    @BindView(R.id.addQuantityText)
    EditText quantityText;
    @BindView(R.id.addStatusText)
    EditText statusText;
    @BindView(R.id.addTypeText)
    EditText typeText;
    private OkHttpClient okHttpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        ButterKnife.bind(this);


    }

    @OnClick(R.id.addSaveBtn)
    public void save() {
        String name = nameText.getText().toString();
        String type = typeText.getText().toString();
        Integer quantity = Integer.parseInt(quantityText.getText().toString());
        String status = statusText.getText().toString();

        Car car = new Car(name, type, quantity, status);

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
        Call<Car> getCarsCall = carController.addCar(car);
        getCarsCall.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                Log.v(TAG, "adding employeee car...");

                if (response.code() == 200) {

                    Toast.makeText(AddCarActivity.this, "Succesful add", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v(TAG, "not added");
                }


            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.v(TAG, "Failed call");
                //launchRetryQuestion();

            }
        });

        startWebSocketListener();

        //finish();
    }

    //web scoket listener
    private final class MyWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, okhttp3.Response response) {
            super.onOpen(webSocket, response);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output(text);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
            output("Error");
        }
    }

    private void output(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.v(TAG, "Message from server=" + text);
                Gson gson = new Gson();
                Car car = gson.fromJson(text, Car.class);
                Log.v(TAG, "Object from server=" + car);
                finish();

            }

        });
    }

    private void startWebSocketListener() {
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(CustomerAllCarsActivity.API_BASE_URL)
                .build();
        MyWebSocketListener listener = new MyWebSocketListener();
        WebSocket webSocket = okHttpClient.newWebSocket(request, listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        okHttpClient.dispatcher().executorService().shutdown();
    }
}
