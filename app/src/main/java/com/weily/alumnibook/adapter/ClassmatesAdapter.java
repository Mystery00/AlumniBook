package com.weily.alumnibook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weily.alumnibook.R;
import com.weily.alumnibook.classs.Classmates;

import java.util.List;

public class ClassmatesAdapter extends RecyclerView.Adapter<ClassmatesAdapter.ViewHolder>
{
    private List<Classmates> list;
    private Context context;

    public ClassmatesAdapter(List<Classmates> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_classmates, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.textView.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
