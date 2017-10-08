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

public class UserFragment extends Fragment implements View.OnClickListener{

    public static final String KEY_USERNAME = "username";

    private ImageView imageView;
    private TextView usernameText;
    private TextView userLoginText;
    private TextView userFollowersText;
    private TextView userFollowingText;
    private TextView userEmailText;
    private TextView userReposText;
    private Button userReposBtn;
    private ProgressBar progressBar;

    private String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        imageView = root.findViewById(R.id.UserAvatar);
        usernameText = root.findViewById(R.id.UserName);
        userLoginText = root.findViewById(R.id.UserLogin);
        userFollowersText = root.findViewById(R.id.UserFollowers);
        userFollowingText = root.findViewById(R.id.UserFollowing);
        userEmailText = root.findViewById(R.id.UserEmail);
        userReposText = root.findViewById(R.id.UserRepos);
        userReposBtn = root.findViewById(R.id.UserReposBtn);
        progressBar = root.findViewById(R.id.progressBar_userInfo);

        Bundle bundle = getArguments();
        username = bundle.getString(SearchFragment.KEY_SEARCH_USERNAME);

        loadData();

        userReposBtn.setOnClickListener(this);

        return root;
    }

    private void loadData() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        final GitHubUserCall apiService = GitHubApiClient.getClient().create(GitHubUserCall.class);
        Call<GitHubUser> call = apiService.getUser(username);
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                usernameText.setText("Username: " + response.body().getUserName());
                userFollowersText.setText("Followers: " + response.body().getUserFollowers());
                userFollowingText.setText("Following: " + response.body().getUserFollowing());
                userLoginText.setText("LogIn: " + response.body().getUserLogin());
                if (response.body().getUserEmail() == null) {
                    userEmailText.setText("No email provided");
                } else {
                    userEmailText.setText("email: " + response.body().getUserEmail());
                }
                userReposText.setText("Your repositories: " + response.body().getUserRepos());
                Picasso.with(getContext()).load(response.body().getUserAvatar()).resize(220, 220).into(imageView);
                progressBar.setVisibility(ProgressBar.GONE);
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
        bundle.putString(KEY_USERNAME, username);
        repoFragment.setArguments(bundle);
        fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.SearchFragmentContainer));
        fragmentTransaction.replace(R.id.UserFragmentContainer, repoFragment);
        fragmentTransaction.addToBackStack("userfragment");
        fragmentTransaction.commit();
    }
}
