package com.paulsojaoutlook.searchingithub.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.paulsojaoutlook.searchingithub.R;

/**
 * Created by p-sha on 04.10.2017.
 */

public class SearchFragment extends Fragment implements TextWatcher {

    public static final String KEY_SEARCH_USERNAME = "username";

    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        editText = root.findViewById(R.id.FindUser_EditText);
        editText.addTextChangedListener(this);
        return root;
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (editText.getText().length() >= 3) {
            String username = editText.getText().toString();

            if (isInternetConnected(getContext())) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                UserFragment userFragment = new UserFragment();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_SEARCH_USERNAME, username);
                userFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.UserFragmentContainer, userFragment);
                fragmentTransaction.addToBackStack("searchfragment");
                fragmentTransaction.commit();
            } else {
                Toast.makeText(getContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
