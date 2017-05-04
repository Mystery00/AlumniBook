package com.weily.alumnibook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.mystery0.tools.ImageLoader.ImageCache;
import com.weily.alumnibook.App;
import com.weily.alumnibook.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>
{
    private List<String> pathList;

    public PhotoAdapter(List<String> pathList)
    {
        this.pathList = pathList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.item_photo, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageCache(App.getContext()));
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.imageView, R.mipmap.image_default, R.mipmap.image_faild);
        imageLoader.get(pathList.get(position), listener);
    }

    @Override
    public int getItemCount()
    {
        return pathList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
