package com.bvd.android.carstobert.customer.my_cars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bvd.android.carstobert.R;
import com.bvd.android.carstobert.db.AppDatabase;
import com.bvd.android.carstobert.db.CarDAO;
import com.bvd.android.carstobert.model.Car;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCarsActivity extends AppCompatActivity {

    @BindView(R.id.purchasedCarsList)
    public ListView purchasedListView;
    private List<Car> myCars;
    private AppDatabase appDatabase;
    private CarDAO dao;
    private ArrayAdapter<Car> carArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars);
        appDatabase = AppDatabase.getAppDatabase(this);
        dao = appDatabase.getDAO();
        myCars = dao.getAll();
        ButterKnife.bind(this);
        carArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myCars);
        purchasedListView.setAdapter(carArrayAdapter);

    }
}
