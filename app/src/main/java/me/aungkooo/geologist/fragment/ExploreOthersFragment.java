package me.aungkooo.geologist.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.Utility;
import me.aungkooo.geologist.adapter.ExploreRecyclerAdapter;
import me.aungkooo.geologist.model.FirebaseLocationModel;


public class ExploreOthersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.txt_no_traverse) TextView txtNoTraverse;
    @BindView(R.id.txt_no_network) TextView txtNoNetwork;
    @BindView(R.id.refresh_explore_others) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view_explore_others) RecyclerView recyclerView;

    private DatabaseReference databaseReference;
    private ExploreRecyclerAdapter exploreAdapter;
    private ArrayList<FirebaseLocationModel> locationList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_explore_others, container, false);

        ButterKnife.bind(this,view);

        locationList = new ArrayList<>();
        exploreAdapter = new ExploreRecyclerAdapter(getContext(), locationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(exploreAdapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorAccent));

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("gpslogist_location");
        loadData();

        return view;
    }

    private void loadData()
    {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                FirebaseLocationModel data = dataSnapshot.getValue(FirebaseLocationModel.class);
                locationList.add(data);
                exploreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onRefresh()
    {
        if (!Utility.isNetworkAvailable(getContext()))
        {
            txtNoNetwork.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
        }
        else {
            loadData();

            txtNoNetwork.setVisibility(View.GONE);
            refreshLayout.setRefreshing(false);
        }
    }
}
