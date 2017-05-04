package com.weily.alumnibook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.classs.Classmates;

import java.util.List;

public class ClassmatesAdapter extends RecyclerView.Adapter<ClassmatesAdapter.ViewHolder>
{
    private List<Classmates> list;
    private OnItemClickListener cOnItemClickListener;
    private OnItemLongClickListener cOnItemLongClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(Classmates classmates, int position);
    }

    public interface OnItemLongClickListener
    {
        void onItemLongClick(Classmates classmates, int position);
    }


    public void setOnItemClickListener(OnItemClickListener cOnItemClickListener)
    {
        this.cOnItemClickListener = cOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener cOnItemClickListener)
    {
        this.cOnItemLongClickListener = cOnItemClickListener;
    }

    public ClassmatesAdapter(List<Classmates> list)
    {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.item_classmates, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        if (cOnItemClickListener != null)
        {
            viewHolder.classmatesView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position = viewHolder.getAdapterPosition();
                    cOnItemClickListener.onItemClick(list.get(position), position);
                }
            });
        }
        if (cOnItemLongClickListener != null)
        {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int position = viewHolder.getAdapterPosition();
                    cOnItemLongClickListener.onItemLongClick(list.get(position), position);
                    return true;
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Classmates classmates = list.get(position);
        holder.cName.setText(classmates.getName());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView cName;
        View classmatesView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            classmatesView = itemView;
            cName = (TextView) itemView.findViewById(R.id.c_name);
        }
    }
}
