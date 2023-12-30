package au.edu.cqu.scgrp.propertyshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import au.edu.cqu.scgrp.propertyshop.PropertyDetailActivity;
import au.edu.cqu.scgrp.propertyshop.R;
import au.edu.cqu.scgrp.propertyshop.dao.PropertyDao;
import au.edu.cqu.scgrp.propertyshop.models.Property;

public class PropertyListFragment extends Fragment {
    private PropertyDao propertyDao;
    private RecyclerView recyclerView;
    private PropertyListAdapter adapter;

    public PropertyListFragment() {
        propertyDao = PropertyDao.getInstance();
    }

    public static PropertyListFragment newInstance() {
        PropertyListFragment fragment = new PropertyListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_list, container, false);
        recyclerView = view.findViewById(R.id.propertyRecyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        updateUI();

        return view;
    }

    private void updateUI() {
        List<Property> propertyList = propertyDao.getAll();
        adapter = new PropertyListAdapter(propertyList);
        recyclerView.setAdapter(adapter);
    }

    private class PropertyListAdapter extends RecyclerView.Adapter<PropertyViewHolder> {

        private List<Property> propertyList;

        public PropertyListAdapter(List<Property> propertyList) {
            this.propertyList = propertyList;
        }

        @NonNull
        @Override
        public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.property_item_view_adapter, parent, false);
            return new PropertyViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
            Property property = propertyList.get(position);
            holder.bind(property);
        }

        @Override
        public int getItemCount() {
            return propertyList.size();
        }
    }

    private class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Property mProperty;
        private TextView addressTV;
        private TextView priceTV;

        public PropertyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.property_item_view_adapter, parent, false));
            itemView.setOnClickListener(this);
            addressTV = itemView.findViewById(R.id.address_tv);
            priceTV = itemView.findViewById(R.id.price_tv);
        }

        public void bind(Property property) {
            mProperty = property;
            addressTV.setText(mProperty.getFullAddress());
            priceTV.setText(getString(R.string.sales_price, mProperty.getSalesPrice()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = PropertyDetailActivity.newIntent(getActivity(), mProperty.getId());
            startActivity(intent);
        }
    }
}