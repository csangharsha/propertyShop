package au.edu.cqu.scgrp.propertyshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

import au.edu.cqu.scgrp.propertyshop.dao.PropertyDao;
import au.edu.cqu.scgrp.propertyshop.fragments.PropertyFragment;
import au.edu.cqu.scgrp.propertyshop.models.Property;

public class PropertyDetailActivity extends AppCompatActivity {

    private static final String EXTRA_PROPERTY_ID = "au.edu.cqu.scgrp.propertyshop.property_id";

    private ViewPager mViewPager;
    private List<Property> mProperties;

    public static Intent newIntent(Context packageContext, UUID propertyId) {
        Intent intent = new Intent(packageContext, PropertyDetailActivity.class);
        intent.putExtra(EXTRA_PROPERTY_ID, propertyId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);

        UUID propertyId = (UUID) getIntent().getSerializableExtra(EXTRA_PROPERTY_ID);

        mViewPager = (ViewPager) findViewById(R.id.property_view_pager);

        mProperties = PropertyDao.getInstance().getAll();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Property property = mProperties.get(position);
                return PropertyFragment.newInstance(property.getId());
            }

            @Override
            public int getCount() {
                return mProperties.size();
            }
        });

        for (int i = 0; i < mProperties.size(); i++) {
            if (mProperties.get(i).getId().equals(propertyId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}