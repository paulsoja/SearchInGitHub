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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paulsojaoutlook.searchingithub.R;
import com.paulsojaoutlook.searchingithub.model.GitHubUser;
import com.paulsojaoutlook.searchingithub.rest.GitHubApiClient;
import com.paulsojaoutlook.searchingithub.rest.GitHubUserCall;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by p-sha on 04.10.2017.
 */

public class UserFragment extends Fragment implements View.OnClickListener, TextWatcher {

    public static final String KEY_COMPANY_NAME = "username";
    public static final String KEY_NUMBER_REPO = "number_repo";

    private RelativeLayout layout;
    private ImageView imageView;
    private TextView usernameText;
    private TextView userLocationText;
    private TextView userBlogText;
    private ProgressBar progressBar;
    private EditText editText;

    private String username;
    private String companyName;
    private String numberRepo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        layout = root.findViewById(R.id.companyLayout);
        imageView = root.findViewById(R.id.UserAvatar);
        usernameText = root.findViewById(R.id.UserName);
        userLocationText = root.findViewById(R.id.UserLocation);
        userBlogText = root.findViewById(R.id.UserBlog);
        progressBar = root.findViewById(R.id.progressBar_userInfo);
        editText = root.findViewById(R.id.FindUser_EditText);
        editText.addTextChangedListener(this);
        layout.setOnClickListener(this);

        layout.setVisibility(View.GONE);
        progressBar.setVisibility(ProgressBar.GONE);

        return root;
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void loadData() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        final GitHubUserCall apiService = GitHubApiClient.getClient().create(GitHubUserCall.class);
        Call<GitHubUser> call = apiService.getUser(username);
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                if (response.body() != null) {
                    progressBar.setVisibility(ProgressBar.GONE);
                    layout.setVisibility(View.VISIBLE);
                    usernameText.setText(response.body().getUserName());
                    userLocationText.setText(response.body().getUserLocation());
                    userBlogText.setText(response.body().getUserBlog());
                    Picasso.with(getContext()).load(response.body().getUserAvatar()).resize(150, 150).into(imageView);
                    companyName = response.body().getUserName();
                    numberRepo = response.body().getUserRepos();
                } else {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RepoFragment repoFragment = new RepoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_COMPANY_NAME, companyName);
        bundle.putString(KEY_NUMBER_REPO, numberRepo);
        repoFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.UserFragmentContainer, repoFragment);
        fragmentTransaction.addToBackStack("userfragment");
        fragmentTransaction.commit();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (editText.getText().length() >= 3) {
            username = editText.getText().toString();
            if (isInternetConnected(getContext())) {
                loadData();
            } else {
                Toast.makeText(getContext(), R.string.internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
