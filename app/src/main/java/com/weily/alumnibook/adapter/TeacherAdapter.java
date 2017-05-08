package com.weily.alumnibook.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.activity.ClassmatesActivity;
import com.weily.alumnibook.activity.TeacherActivity;
import com.weily.alumnibook.classs.Classmates;
import com.weily.alumnibook.classs.Teacher;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder>
{
    private List<Teacher> list;

    public TeacherAdapter(List<Teacher> list)
    {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.item_classmates, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.teacherView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(App.getContext(), TeacherActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("teacher", list.get(viewHolder.getAdapterPosition()));
                intent.putExtra("teacher", new Bundle(bundle));
                App.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Teacher teacher = list.get(position);
        holder.cName.setText(teacher.getName());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView cName;
        View teacherView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            teacherView = itemView;
            cName = (TextView) itemView.findViewById(R.id.c_name);
        }
    }
}
