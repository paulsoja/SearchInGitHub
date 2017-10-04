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
    private TextView userRepos;
    private Button userReposBtn;

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
        userRepos = root.findViewById(R.id.UserRepos);
        userReposBtn = root.findViewById(R.id.UserReposBtn);

        Bundle bundle = getArguments();
        String username = bundle.getString(SearchFragment.KEY_USERNAME);
        usernameText.setText(username);

        return root;
    }
}
