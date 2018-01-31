package com.bvd.android.carstobert.employee;

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

import java.util.List;

/**
 * Created by bara on 1/31/2018.
 */

public class CarEmployeeAdapter extends ArrayAdapter<Car> {
    private Context context;

    public CarEmployeeAdapter(@NonNull Context context, int resource, @NonNull List<Car> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_2_layout, parent, false);
        TextView textView = rowView.findViewById(R.id.firstLine2);
        Car car= getItem(position);
        textView.setText(car.getName());

        TextView textView2 = rowView.findViewById(R.id.secondLine2);
        textView2.setText(car.getType()+" | "+car.getQuantity()+" | "+car.getStatus());


        return rowView;
    }
}
