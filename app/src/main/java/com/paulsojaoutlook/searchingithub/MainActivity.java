package com.paulsojaoutlook.searchingithub;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paulsojaoutlook.searchingithub.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UserFragment userFragment = new UserFragment();
        fragmentTransaction.replace(R.id.UserFragmentContainer, userFragment, "userfragment");
        fragmentTransaction.commit();
    }
}
