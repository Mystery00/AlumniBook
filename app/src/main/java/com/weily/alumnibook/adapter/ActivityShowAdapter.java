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
import com.weily.alumnibook.activity.ActivityShowActivity;
import com.weily.alumnibook.activity.SendActivity;
import com.weily.alumnibook.classs.ActivityShow;

import java.util.List;

/**
 * Created by HS on 2017/5/7.
 */

public class ActivityShowAdapter extends RecyclerView.Adapter<ActivityShowAdapter.ViewHolder>
{

    private List<ActivityShow> activityShowListList;


    public ActivityShowAdapter(List<ActivityShow> activityShowListList)
    {
        this.activityShowListList = activityShowListList;
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView activityName;
        View activityView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            activityView = itemView;
            activityName = (TextView) itemView.findViewById(R.id.c_name);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ActivityShowAdapter.ViewHolder holder, int position)
    {
        ActivityShow activityShow = activityShowListList.get(position);
        holder.activityName.setText(activityShow.getActivityName());
        holder.activityView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(App.getContext(), SendActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("activity", activityShowListList.get(holder.getAdapterPosition()));
                intent.putExtra("activity", bundle);
                App.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return activityShowListList.size();
    }

}