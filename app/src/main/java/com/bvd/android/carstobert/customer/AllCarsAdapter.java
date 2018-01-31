package com.bvd.android.carstobert.customer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bvd.android.carstobert.R;
import com.bvd.android.carstobert.model.Car;

import java.util.Collection;
import java.util.List;

/**
 * Created by bara on 1/30/2018.
 */

public class AllCarsAdapter extends ArrayAdapter<Car> {

    private List<Car> cars;

    public AllCarsAdapter(@NonNull Context context, int resource, List<Car> cars) {
        super(context, resource);
        this.cars = cars;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_1_layout, parent,false);
        TextView row1 = (TextView)rowView.findViewById(R.id.firstLine);
        TextView row2 = rowView.findViewById(R.id.secondLine);

        Car car = cars.get(position);
        row1.setText(car.getName());
        row2.setText(car.toString1());

        return rowView;
    }

    public void add(Car car) {
        cars.add(car);
    }
    public void removeAll(){
        cars.clear();
    }

    public void addAll(@NonNull Collection<? extends Car> cars) {
        this.cars.addAll(cars);
    }
}
