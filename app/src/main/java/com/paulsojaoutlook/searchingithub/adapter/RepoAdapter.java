package com.paulsojaoutlook.searchingithub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paulsojaoutlook.searchingithub.R;
import com.paulsojaoutlook.searchingithub.model.GitHubRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p-sha on 07.10.2017.
 */

public class RepoAdapter extends BaseAdapter {

    private class ViewHolder {
        TextView repoName;
        TextView repoDesc;
        TextView repoLanguage;
    }

    private LayoutInflater inflater;
    private List<GitHubRepo> repoList = new ArrayList<>();
    private Context context;

    public RepoAdapter(Context context, List<GitHubRepo> repoList) {
        this.context = context;
        this.repoList = repoList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return repoList.size();
    }

    @Override
    public Object getItem(int i) {
        return repoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        ViewHolder viewHolder;
        if (view == null) {
            v = inflater.inflate(R.layout.item_listview_userrepo, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.repoName = v.findViewById(R.id.item_repoName);
            viewHolder.repoDesc = v.findViewById(R.id.item_repoDesc);
            viewHolder.repoLanguage = v.findViewById(R.id.item_repoLanguage);
            v.setTag(viewHolder);
        } else {
            v = view;
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.repoName.setText(repoList.get(i).getRepoName());
        viewHolder.repoDesc.setText(repoList.get(i).getRepoDescription());
        viewHolder.repoLanguage.setText(repoList.get(i).getRepoLanguage());
        return v;
    }
}
