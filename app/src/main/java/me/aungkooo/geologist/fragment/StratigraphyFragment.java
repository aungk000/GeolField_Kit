package me.aungkooo.geologist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.adapter.StratigraphyTraverseAdapter;
import me.aungkooo.geologist.database.StratigraphyLocationDb;
import me.aungkooo.geologist.database.StratigraphyTraverseDb;
import me.aungkooo.geologist.dialog.TraverseNewDialog;
import me.aungkooo.geologist.listener.OnDialogDismissListener;
import me.aungkooo.geologist.model.Traverse;

/**
 * Created by Ko Oo on 1/4/2018.
 */

public class StratigraphyFragment extends Fragment implements OnDialogDismissListener
{
    @BindView(R.id.recycler_view_stratigraphy_traverse) RecyclerView recyclerView;
    @BindView(R.id.fab_stratigraphy_traverse_add) FloatingActionButton fabAdd;
    Unbinder unbinder;

    private StratigraphyTraverseAdapter traverseAdapter;
    private ArrayList<Traverse> traverseList;
    private int FRAGMENT_REQUEST = 1;
    private StratigraphyTraverseDb traverseDb;
    private StratigraphyLocationDb locationDb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        traverseDb = new StratigraphyTraverseDb(getContext());
        traverseList = traverseDb.getAllTraverse();
        locationDb = new StratigraphyLocationDb(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stratigraphy, container, false);
        unbinder = ButterKnife.bind(this, view);

        traverseAdapter = new StratigraphyTraverseAdapter(getContext(), traverseList, traverseDb, locationDb);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(traverseAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState > 0) {
                    fabAdd.hide();
                } else {
                    fabAdd.show();
                }
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Traverse.SIZE, traverseList.size() + 1);

                TraverseNewDialog dialog = new TraverseNewDialog();
                dialog.setTargetFragment(StratigraphyFragment.this, FRAGMENT_REQUEST);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "Traverse New");
            }
        });

        return view;
    }

    @Override
    public void onDialogDismissed(Traverse traverse) {
        int id = (int) traverseDb.insertTraverse(traverse);
        Traverse traverseNew = new Traverse(id, traverse.getTitle(), traverse.getDate());
        traverseAdapter.add(traverseNew);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
