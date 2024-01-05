package au.edu.cqu.scgrp.propertyshop.labs;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import au.edu.cqu.scgrp.propertyshop.models.Property;

public class PropertyLab {

    private static PropertyLab propertyLab;
    private final List<Property> properties;

    private PropertyLab() {
        properties = new ArrayList<>();
        this.save(new Property("360 Pier Point Road", "Cairns", "QLD", "4870", 200000));
        this.save(new Property("250 Sheridan Street", "Cairns", "QLD", "4870", 350000));
        this.save(new Property("86 Taylor Street", "Trinity Beach", "QLD", "4879", 800000));
        this.save(new Property("17 Martin Street", "Cairns", "QLD", "4870", 550000));
        this.save(new Property("715 Mulgrave Road", "Earlville", "QLD", "4898", 400000));
    }

    public static PropertyLab getInstance(Context context) {
        if(propertyLab == null) {
            propertyLab = new PropertyLab();
        }
        return propertyLab;
    }

    public Property save(Property property) {
        property.setId(UUID.randomUUID());
        properties.add(property);
        return property;
    }

    public List<Property> getAll() {
        return properties;
    }

    public Optional<Property> getById(UUID id) {
        return properties.stream()
                .filter(
                        property -> Objects.equals(property.getId(), id)
                )
                .findFirst();
    }

}
