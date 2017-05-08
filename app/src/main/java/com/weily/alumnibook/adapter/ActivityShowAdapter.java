package com.weily.alumnibook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weily.alumnibook.R;
import com.weily.alumnibook.classs.ActivityShow;

import java.util.List;

import static com.weily.alumnibook.R.id.c_name;

/**
 * Created by HS on 2017/5/7.
 */

public class ActivityShowAdapter extends RecyclerView.Adapter<ActivityShowAdapter.ViewHolder>{

    private List<ActivityShow> activityShowListList;
    private ActivityShowAdapter.OnItemClickListener cOnItemClickListener;
    private ActivityShowAdapter.OnItemLongClickListener cOnItemLongClickListener;


    public ActivityShowAdapter(List<ActivityShow> activityShowListList) {
        this.activityShowListList = activityShowListList;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(ActivityShowAdapter.OnItemClickListener cOnItemClickListener){
        this.cOnItemClickListener = cOnItemClickListener;
    }

    public void setOnItemLongClickListener(ActivityShowAdapter.OnItemLongClickListener cOnItemClickListener) {
        this.cOnItemLongClickListener = cOnItemClickListener;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView activityName;
        View activityView;

        public ViewHolder(View itemView) {
            super(itemView);
            activityView=itemView;
            activityName= (TextView) itemView.findViewById(c_name);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        final ActivityShowAdapter.ViewHolder viewHolder= new ViewHolder(view);

        if(cOnItemClickListener!=null){
            viewHolder.activityView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=viewHolder.getAdapterPosition();
                    cOnItemClickListener.onItemClick(viewHolder.itemView,position);
                }
            });
        }
        if (cOnItemLongClickListener!=null){
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = viewHolder.getLayoutPosition();
                    cOnItemLongClickListener.onItemLongClick(viewHolder.itemView,position);
                    return true;
                }
            });
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ActivityShowAdapter.ViewHolder holder, int position) {

        ActivityShow activityShow=activityShowListList.get(position);
        holder.activityName.setText(activityShow.getActivityName());
    }

    @Override
    public int getItemCount() {
        return activityShowListList.size();
    }


}