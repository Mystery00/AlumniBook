package com.weily.alumnibook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weily.alumnibook.R;
import com.weily.alumnibook.activity.ClassmatesActivity;
import com.weily.alumnibook.adapter.ClassmatesAdapter;
import com.weily.alumnibook.classs.Classmates;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends Fragment
{
    private static final String ARG_PARAM = "ARG_PARAM";
    private List<Classmates> dataList = new ArrayList<>();

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
            dataList = (List) getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        RecyclerView classmatesRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        classmatesRecycler.setLayoutManager(linearLayoutManager);
        ClassmatesAdapter adapter = new ClassmatesAdapter(dataList);

        adapter.setOnItemClickListener(new ClassmatesAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(Classmates classmates, int position)
            {
                Intent intent = new Intent(getContext(), ClassmatesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("classmates", classmates);
                intent.putExtra("classmates", new Bundle(bundle));
                startActivity(intent);
            }
        });

        classmatesRecycler.setAdapter(adapter);
        return view;
    }
}
