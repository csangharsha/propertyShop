package au.edu.cqu.scgrp.propertyshop.models;

import java.util.UUID;

public class Property {

    private UUID id;
    private String streetName;
    private String suburb;
    private String state;
    private String postcode;
    private Integer salesPrice;

    public Property() {
    }

    public Property(String streetName, String suburb, String state, String postcode, Integer salesPrice) {
        this.streetName = streetName;
        this.suburb = suburb;
        this.state = state;
        this.postcode = postcode;
        this.salesPrice = salesPrice;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Integer salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getStreetName());
        sb.append(", ");
        sb.append(this.getSuburb());
        if(!this.getState().isEmpty()) {
            sb.append(", ");
            sb.append(this.getState());
        }
        if(!this.getPostcode().isEmpty()) {
            sb.append(", ");
            sb.append(this.getPostcode());
        }
        return sb.toString();
    }
}
