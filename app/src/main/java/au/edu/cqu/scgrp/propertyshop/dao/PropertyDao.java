package au.edu.cqu.scgrp.propertyshop.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import au.edu.cqu.scgrp.propertyshop.models.Property;

public class PropertyDao {

    private static PropertyDao propertyDao;
    private final List<Property> properties;

    private PropertyDao() {
        properties = new ArrayList<>();

        this.save(new Property("380 Forest Road", "Hurstville", "NSW", "2220", 200000));
        this.save(new Property("38-42 Woniora Road", "Hurstville", "NSW", "2220", 300000));
        this.save(new Property("West Street", "Hurstville", "NSW", "2220", 4000000));
    }

    public static PropertyDao getInstance() {
        if(propertyDao == null) {
            propertyDao = new PropertyDao();
        }
        return propertyDao;
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

    public void deleteById(Long id) {
        properties.removeIf(property -> Objects.equals(property.getId(), id));
    }
}
