package com.example.albaalza.P_MyAlba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albaalza.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class MyAlba extends Fragment {

    ViewPager viewPager;
    MyAlba1Fragment myAlba1Fragment= new MyAlba1Fragment();
    MyAlba2Fragment myAlba2Fragment= new MyAlba2Fragment();
    MyAlba3Fragment myAlba3Fragment= new MyAlba3Fragment();

    public MyAlba(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_alba, container, false);
        viewPager=(ViewPager)view.findViewById(R.id.viewpager);

        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(myAlba1Fragment);
        viewPagerAdapter.addFragment(myAlba2Fragment);
        viewPagerAdapter.addFragment(myAlba3Fragment);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }

    }

    public ViewPager getViewPager(){
        return viewPager;
    }

    public MyAlba1Fragment getMyAlba1Fragment(){
        return myAlba1Fragment;
    }
}