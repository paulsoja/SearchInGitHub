package com.paulsojaoutlook.searchingithub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.paulsojaoutlook.searchingithub.R;
import com.paulsojaoutlook.searchingithub.adapter.RepoAdapter;
import com.paulsojaoutlook.searchingithub.model.GitHubRepo;
import com.paulsojaoutlook.searchingithub.rest.GitHubApiClient;
import com.paulsojaoutlook.searchingithub.rest.GitHubRepoCall;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by p-sha on 07.10.2017.
 */

public class RepoFragment extends Fragment {

    private List<GitHubRepo> repoList;
    private String username;
    private RepoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_repo, container, false);

        TextView userNameText = root.findViewById(R.id.RepoFragment_Username_Text);
        ListView listRepo = root.findViewById(R.id.RepoFragment_userRepo_ListView);

        Bundle bundle = getArguments();
        username = bundle.getString(UserFragment.KEY_USERNAME);
        userNameText.setText(username);

        repoList = new ArrayList<>();
        adapter = new RepoAdapter(getContext(), repoList);
        listRepo.setAdapter(adapter);

        loadRepositiries();

        return root;
    }

    private void loadRepositiries() {
        GitHubRepoCall apiService = GitHubApiClient.getClient().create(GitHubRepoCall.class);
        Call<List<GitHubRepo>> call = apiService.getRepo(username);
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                repoList.clear();
                repoList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(getContext(), "wrong data on server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
