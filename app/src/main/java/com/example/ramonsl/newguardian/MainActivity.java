package com.example.ramonsl.newguardian;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public static String mUrl="http://content.guardianapis.com/search?q=money&order-by=newest&order-date=published&show-fields=headline,thumbnail&show-references=author&show-tags=contributor&page=1&page-size=20&api-key=2d96f27b-6c25-40e7-ac6f-236b586abf0b";


    BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_food:
                    mTextMessage.setText(R.string.title_food);
                    return true;
                case R.id.navigation_money:
                    mTextMessage.setText(R.string.title_money);
                    return true;
                case R.id.navigation_technology:
                    mTextMessage.setText(R.string.title_technology);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage =  findViewById(R.id.message);
        navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.navigation_food:
                        mUrl="http://content.guardianapis.com/search?q=foodtruck&order-by=newest&show-fields=headline,thumbnail&show-references=author&api-key=2d96f27b-6c25-40e7-ac6f-236b586abf0b";
                        fragment = new NewsFragment();
                        break;
                    case R.id.navigation_money:
                        mUrl="http://content.guardianapis.com/search?q=economy&order-by=newest&show-fields=headline,thumbnail&show-references=author&api-key=2d96f27b-6c25-40e7-ac6f-236b586abf0b";
                        fragment = new NewsFragment();
                        break;
                    case R.id.navigation_technology:
                        mUrl="http://content.guardianapis.com/search?q=tech&order-by=newest&show-fields=headline,thumbnail&show-references=author&api-key=2d96f27b-6c25-40e7-ac6f-236b586abf0b";
                        fragment = new NewsFragment();
                        break;
                    default:
                        mUrl="http://content.guardianapis.com/search?q=tech&order-by=newest&show-fields=headline,thumbnail&show-references=author&api-key=2d96f27b-6c25-40e7-ac6f-236b586abf0b";
                        fragment = new NewsFragment();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });

    }

}
