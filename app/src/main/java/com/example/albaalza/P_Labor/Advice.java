package com.example.albaalza.P_Labor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.albaalza.R;

import java.util.ArrayList;
import java.util.List;


public class Advice extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FrameLayout frame;
    private Advice1Fragment advice1Fragment= new Advice1Fragment();
    private Advice2Fragment advice2Fragment= new Advice2Fragment();
    private Advice3Fragment advice3Fragment= new Advice3Fragment();
    private Advice4Fragment advice4Fragment= new Advice4Fragment();


    public Advice() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_advice, container, false);

        viewPager= (ViewPager)view.findViewById(R.id.advice_viewpager);
        viewPager.setAdapter(new Advice.ViewPagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

        tabLayout=(TabLayout)view.findViewById(R.id.advice_tab);
        tabLayout.setupWithViewPager(viewPager);

        frame=(FrameLayout)view.findViewById(R.id.frame);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Advice.ViewPagerAdapter viewPagerAdapter= new Advice.ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(advice1Fragment);
        viewPagerAdapter.addFragment(advice2Fragment);
        viewPagerAdapter.addFragment(advice3Fragment);
        viewPagerAdapter.addFragment(advice4Fragment);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        List<Fragment> fragmentList= new ArrayList<>();

        public CharSequence getPageTitle(int position){
            switch(position){
                case 0:
                    return "노동법";
                case 1:
                    return "사례찾기";
                case 2:
                    return "상담/신고";
                case 3:
                    return "사이트";
                default:
                    return null;
            }
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        public void addFragment(Fragment fragment){
            fragmentList. add(fragment);
        }
    }
}
