package com.example.mnahm5.task_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class taskCardCustomAdapter extends ArrayAdapter<taskCard> {

    taskCardCustomAdapter(Context context, ArrayList<taskCard> taskCards) {
        super(context, R.layout.task_card, taskCards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.task_card, parent, false);

        taskCard taskCard = getItem(position);
        final TextView tvTaskTitle = (TextView) customView.findViewById(R.id.tvTaskTitle);
        final TextView tvDueDate = (TextView) customView.findViewById(R.id.tvDueDate);
        tvTaskTitle.setText(taskCard.taskTitle);
        String tvMsg = "Due Date:\n" + taskCard.dueDate;
        tvDueDate.setText(tvMsg);
        return customView;
    }
}