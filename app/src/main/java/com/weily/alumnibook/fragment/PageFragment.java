package com.weily.alumnibook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weily.alumnibook.App;
import com.weily.alumnibook.R;
import com.weily.alumnibook.adapter.ClassmatesAdapter;
import com.weily.alumnibook.classs.Classmates;

import java.util.ArrayList;
import java.util.List;


public class PageFragment extends Fragment
{
    private static final String ARG_PARAM = "ARG_PARAM";
    private List<Classmates> list;

    public PageFragment()
    {
    }

    public static PageFragment newInstance(ArrayList<Classmates> list)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, list);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            this.list = (List) getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        ClassmatesAdapter adapter = new ClassmatesAdapter(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}
