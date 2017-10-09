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

public class UserFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_COMPANY_NAME = "username";
    public static final String KEY_NUMBER_REPO = "number_repo";

    private RelativeLayout layout;
    private ImageView imageView;
    private TextView usernameText;
    private TextView userLocationText;
    private TextView userBlogText;
    private ProgressBar progressBar;

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

        Bundle bundle = getArguments();
        username = bundle.getString(SearchFragment.KEY_SEARCH_USERNAME);

        loadData();

        layout.setOnClickListener(this);

        return root;
    }

    private void loadData() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        layout.setVisibility(View.GONE);
        final GitHubUserCall apiService = GitHubApiClient.getClient().create(GitHubUserCall.class);
        Call<GitHubUser> call = apiService.getUser(username);
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                usernameText.setText(response.body().getUserName());
                userLocationText.setText(response.body().getUserLocation());
                userBlogText.setText(response.body().getUserBlog());
                Picasso.with(getContext()).load(response.body().getUserAvatar()).resize(150, 150).into(imageView);
                companyName = response.body().getUserName();
                numberRepo = response.body().getUserRepos();
                progressBar.setVisibility(ProgressBar.GONE);
                layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                Toast.makeText(getContext(), "no data on server", Toast.LENGTH_SHORT).show();
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
        fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.SearchFragmentContainer));
        fragmentTransaction.replace(R.id.UserFragmentContainer, repoFragment);
        fragmentTransaction.addToBackStack("userfragment");
        fragmentTransaction.commit();
    }
}
