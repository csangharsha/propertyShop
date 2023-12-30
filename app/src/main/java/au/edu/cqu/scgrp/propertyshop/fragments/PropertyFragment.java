package au.edu.cqu.scgrp.propertyshop.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

import au.edu.cqu.scgrp.propertyshop.R;
import au.edu.cqu.scgrp.propertyshop.dao.PropertyDao;
import au.edu.cqu.scgrp.propertyshop.models.Property;

public class PropertyFragment extends Fragment {

    private static final String ARG_PROPERTY_ID = "crime_id";

    private Property mProperty;

    private EditText mAddressEditText;
    private EditText mSuburbEditText;
    private EditText mStateEditText;
    private EditText mPostcodeEditText;
    private EditText mPriceEditText;

    public PropertyFragment() {}

    public static PropertyFragment newInstance(UUID propertyId) {
        PropertyFragment fragment = new PropertyFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROPERTY_ID, propertyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID propertyId = (UUID) getArguments().getSerializable(ARG_PROPERTY_ID);
        mProperty = PropertyDao.getInstance()
                .getById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found."));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_property, container, false);

        mAddressEditText = v.findViewById(R.id.address_et);
        mSuburbEditText = v.findViewById(R.id.suburb_et);
        mStateEditText = v.findViewById(R.id.state_et);
        mPostcodeEditText = v.findViewById(R.id.postcode_et);
        mPriceEditText = v.findViewById(R.id.price_et);

        mAddressEditText.setText(mProperty.getStreetName());
        mSuburbEditText.setText(mProperty.getSuburb());
        mStateEditText.setText(mProperty.getState());
        mPostcodeEditText.setText(mProperty.getPostcode());
        mPriceEditText.setText(String.valueOf(mProperty.getSalesPrice()));

        return v;
    }
}