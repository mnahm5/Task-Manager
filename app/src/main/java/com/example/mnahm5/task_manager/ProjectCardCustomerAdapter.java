package com.example.mnahm5.task_manager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProjectCardCustomerAdapter extends ArrayAdapter<ProjectCard> {

    ProjectCardCustomerAdapter(Context context, ArrayList<ProjectCard> projectCards) {
        super(context, R.layout.project_card, projectCards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.project_card, parent, false);

        ProjectCard projectCard = getItem(position);
        final TextView tvProjectName = (TextView) customView.findViewById(R.id.tvProjectName);
        final TextView tvCreationDate = (TextView) customView.findViewById(R.id.tvCreationDate);

        tvProjectName.setText(projectCard.projectName);
        String creationDateMsg = "Date Created: \n" + projectCard.dateCreated;
        tvCreationDate.setText(creationDateMsg);
        return customView;
    }
}