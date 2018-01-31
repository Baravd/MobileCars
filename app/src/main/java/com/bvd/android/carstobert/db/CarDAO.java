package com.bvd.android.carstobert.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bvd.android.carstobert.model.Car;

import java.util.List;

/**
 * Created by bara on 1/31/2018.
 */

@Dao
public interface CarDAO {


    @Query("SELECT * FROM cars ")
    List<Car> getAll();

    @Insert
    void insertAll(Car... obj);

    @Update
    void updateCars(Car... obj);

    @Delete
    void delete(Car obj);

    /*@Query("SELECT * FROM Expense WHERE category=:category ")
    List<Expense> getByCategory(String category);
*/
}