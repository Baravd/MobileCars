package com.bvd.android.carstobert.customer.my_cars;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.bvd.android.carstobert.model.Car;

import java.util.Collection;
import java.util.List;

/**
 * Created by bara on 1/31/2018.
 */

public class PurchasedCarsAdapter extends ArrayAdapter<Car> {
    private List<Car> cars;

    public PurchasedCarsAdapter(@NonNull Context context, int resource, @NonNull List<Car> objects) {
        super(context, resource, objects);
        cars = objects;
    }


}
