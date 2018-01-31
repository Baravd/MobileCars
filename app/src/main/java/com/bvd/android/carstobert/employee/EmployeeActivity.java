package com.bvd.android.carstobert.employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.bvd.android.carstobert.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeActivity extends AppCompatActivity {


    @BindView(R.id.employeeCarList)
    ListView carsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        ButterKnife.bind(this);
    }
}
