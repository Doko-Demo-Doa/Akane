package com.acaziasoft.akane.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acaziasoft.akane.Adapter.DashboardAdapter;
import com.acaziasoft.akane.R;
import com.acaziasoft.akane.model.Item;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment {

    public static DashboardFragment newInstance() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        return dashboardFragment;
    }

    @BindView(R.id.recyclerDash)
    RecyclerView recyclerDash;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerDash.setLayoutManager(layoutManager);
        List<Item> items = Item.listAll(Item.class);
        DashboardAdapter adapter = new DashboardAdapter((ArrayList<Item>) items);
        recyclerDash.setAdapter(adapter);
    }
}
