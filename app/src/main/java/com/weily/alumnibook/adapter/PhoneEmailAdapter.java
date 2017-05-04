package com.weily.alumnibook.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.weily.alumnibook.App;
import com.weily.alumnibook.R;

import java.util.List;
import java.util.regex.Pattern;

public class PhoneEmailAdapter extends RecyclerView.Adapter<PhoneEmailAdapter.ViewHolder>
{
    private List<String> list;

    public PhoneEmailAdapter(List<String> list)
    {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.item_email_phone, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = viewHolder.getAdapterPosition();
                notifyItemRemoved(position);
                list.remove(position);
            }
        });
        viewHolder.textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
                if (pattern.matcher(list.get(viewHolder.getAdapterPosition())).matches())
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.get(viewHolder.getAdapterPosition())));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.getContext().startActivity(intent);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        Button btn_delete;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.email_phone);
            btn_delete = (Button) itemView.findViewById(R.id.delete);
        }
    }
}
