package au.edu.cqu.scgrp.propertyshop.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import au.edu.cqu.scgrp.propertyshop.AddPropertyActivity;
import au.edu.cqu.scgrp.propertyshop.PropertyListActivity;
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
    private Button mNewPropertyBtn;
    private Button mSavePropertyBtn;

    public PropertyFragment() {}

    public static PropertyFragment newInstance(UUID propertyId) {
        PropertyFragment fragment = new PropertyFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROPERTY_ID, propertyId);
        fragment.setArguments(args);
        return fragment;
    }

    public static PropertyFragment newInstance() {
        PropertyFragment fragment = new PropertyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().getSerializable(ARG_PROPERTY_ID) != null) {
            UUID propertyId = (UUID) getArguments().getSerializable(ARG_PROPERTY_ID);
            Optional<Property> propertyOptional = PropertyDao.getInstance().getById(propertyId);
            if(propertyOptional.isPresent()) {
                mProperty = propertyOptional.get();
            } else {
                Toast.makeText(getActivity(), "Property not available.", Toast.LENGTH_SHORT).show();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        } else {
            mProperty = null;
        }
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
        mNewPropertyBtn = v.findViewById(R.id.new_property_btn);
        mSavePropertyBtn = v.findViewById(R.id.save_property_btn);

        if(mProperty != null) {
            mAddressEditText.setText(mProperty.getStreetName());
            mSuburbEditText.setText(mProperty.getSuburb());
            mStateEditText.setText(mProperty.getState());
            mPostcodeEditText.setText(mProperty.getPostcode());
            mPriceEditText.setText(String.valueOf(mProperty.getSalesPrice()));

            mSavePropertyBtn.setVisibility(View.GONE);
            mNewPropertyBtn.setVisibility(View.VISIBLE);
            mNewPropertyBtn.setOnClickListener(v1 -> {
                Intent intent = new Intent(getActivity(), AddPropertyActivity.class);
                startActivity(intent);
            });
        } else {
            mNewPropertyBtn.setVisibility(View.GONE);
            mSavePropertyBtn.setVisibility(View.VISIBLE);
            mSavePropertyBtn.setOnClickListener(v12 -> {
                Property property = new Property();

                try {
                    int price = Integer.parseInt(mPriceEditText.getText().toString());
                    if(price < 1000) {
                        Toast.makeText(getActivity(), "Price must be more than $1000.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    property.setSalesPrice(price);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Price must be a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mAddressEditText.getText().toString().isEmpty()
                        || mSuburbEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Address and Suburb field must not be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                property.setStreetName(mAddressEditText.getText().toString());
                property.setSuburb(mSuburbEditText.getText().toString());
                property.setState(mStateEditText.getText().toString());
                property.setPostcode(mPostcodeEditText.getText().toString());

                PropertyDao.getInstance().save(property);

                Intent intent = new Intent(getActivity(), PropertyListActivity.class);
                startActivity(intent);
                getActivity().finish();
            });
        }

        return v;
    }
}