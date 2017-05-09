package com.weily.alumnibook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.weily.alumnibook.App;
import com.weily.alumnibook.R;

import java.util.List;
import java.util.Map;

public class ChoosePeopleAdapter extends RecyclerView.Adapter<ChoosePeopleAdapter.ViewHolder>
{
    private List<Map<String, String>> list;
    private List<Map<String, String>> checkedList;
    private OnCheckListener listener;

    public ChoosePeopleAdapter(List<Map<String, String>> list, List<Map<String, String>> checkedList)
    {
        this.list = list;
        this.checkedList = checkedList;
    }

    public interface OnCheckListener
    {
        void onCheck(Map<String, String> map);

        void onNotCheck(Map<String, String> map);
    }

    public void setOnCheckListener(OnCheckListener listener)
    {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.item_choose_people, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.checkBox.setText(list.get(position).get("name"));
        for (Map<String, String> map : checkedList)
        {
            if (list.get(position).get("name").equals(map.get("name"))&&
                    list.get(position).get("type").equals(map.get("type")))
            {
                holder.checkBox.setChecked(true);
                break;
            }
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    listener.onCheck(list.get(holder.getAdapterPosition()));
                } else
                {
                    listener.onNotCheck(list.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        CheckBox checkBox;

        public ViewHolder(View itemView)
        {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }
}
