package org.alithk.entity;

import lombok.Data;

@Data
public class AddressCoordinate {
    private String formattedAddress;
    private Double longitude;
    private Double latitude;

    public AddressCoordinate(String formattedAddress, Double longitude, Double latitude) {
        this.formattedAddress = formattedAddress;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public AddressCoordinate(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public AddressCoordinate() {
    }
}