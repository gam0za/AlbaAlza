package com.example.albaalza.P_AlbaTing;

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


public class AlbaTing extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FrameLayout frame;
    private AlbaTing1Fragment albaTing1Fragment= new AlbaTing1Fragment();
    private AlbaTing2Fragment albaTing2Fragment=new AlbaTing2Fragment();
    private AlbaTing3Fragment albaTing3Fragment=new AlbaTing3Fragment();
    private AlbaTing4Fragment albaTing4Fragment=new AlbaTing4Fragment();

    public AlbaTing(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_alba_ting, container, false);

        viewPager= (ViewPager)view.findViewById(R.id.albating_viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

        tabLayout=(TabLayout)view.findViewById(R.id.albating_tab);
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
        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(albaTing1Fragment);
        viewPagerAdapter.addFragment(albaTing2Fragment);
        viewPagerAdapter.addFragment(albaTing3Fragment);
        viewPagerAdapter.addFragment(albaTing4Fragment);
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
                    return "대타";
                case 1:
                    return "공지";
                case 2:
                    return "이벤트";
                case 3:
                    return "잡담";
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