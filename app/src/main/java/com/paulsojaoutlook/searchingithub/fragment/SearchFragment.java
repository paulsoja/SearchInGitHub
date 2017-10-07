package com.paulsojaoutlook.searchingithub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.paulsojaoutlook.searchingithub.R;

/**
 * Created by p-sha on 04.10.2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_SEARCH_USERNAME = "username";

    private String username;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        Button searchBtn = root.findViewById(R.id.FindUser_Btn);
        editText = root.findViewById(R.id.FindUser_EditText);
        searchBtn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        username = editText.getText().toString();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserFragment userFragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SEARCH_USERNAME, username);
        userFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.UserFragmentContainer, userFragment);
        fragmentTransaction.commit();
    }
}
