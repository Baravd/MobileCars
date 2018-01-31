package com.bvd.android.carstobert.model.dtos;

/**
 * Created by bara on 1/31/2018.
 */

public class CarBuyDto {
    private Integer id;
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CarBuyDto(Integer id, Integer quantity) {

        this.id = id;
        this.quantity = quantity;
    }
}
