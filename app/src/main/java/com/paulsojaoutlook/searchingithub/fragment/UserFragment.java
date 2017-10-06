package com.paulsojaoutlook.searchingithub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class UserFragment extends Fragment {

    private ImageView imageView;
    private TextView usernameText;
    private TextView userLoginText;
    private TextView userFollowersText;
    private TextView userFollowingText;
    private TextView userEmailText;
    private TextView userReposText;
    private Button userReposBtn;

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

        Bundle bundle = getArguments();
        username = bundle.getString(SearchFragment.KEY_USERNAME);

        loadData();

        return root;
    }

    private void loadData() {
        //подключаемся по url
        final GitHubUserCall apiService = GitHubApiClient.getClient().create(GitHubUserCall.class);
        //передаём username
        Call<GitHubUser> call = apiService.getUser(username);
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            //здесь пишем что делать с найденной информацией на сервере
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
            }

            @Override
            //здесь если инфы нет на сервере
            public void onFailure(Call<GitHubUser> call, Throwable t) {

            }
        });
    }
}
