package com.bvd.android.carstobert;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.bvd.android.carstobert.customer.CustomerAllCarsActivity;
import com.bvd.android.carstobert.customer.my_cars.MyCarsActivity;
import com.bvd.android.carstobert.employee.EmployeeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.mainEmployeBtn)
    public Button employeeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(!isNetworkAvailable()){
            employeeBtn.setEnabled(false);
        }
    }

    @OnClick(R.id.mainCustomerBtn)
    void redirectCustomer() {
        Intent intent = new Intent(this, CustomerAllCarsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.mainEmployeBtn)
    void redirectEmployee() {
        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.mainMyCars)
    void redirectToMyCars() {
        Intent intent = new Intent(this, MyCarsActivity.class);
        startActivity(intent);
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
