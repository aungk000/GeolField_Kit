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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.adapter.TapeTraverseAdapter;
import me.aungkooo.geologist.database.TapeLocationDb;
import me.aungkooo.geologist.database.TapeTraverseDb;
import me.aungkooo.geologist.dialog.TraverseNewDialog;
import me.aungkooo.geologist.listener.OnDialogDismissListener;
import me.aungkooo.geologist.model.Traverse;


public class TapeAndCompassFragment extends Fragment implements OnDialogDismissListener
{
    @BindView(R.id.recycler_view_tape_traverse) RecyclerView recyclerView;
    @BindView(R.id.fab_tape_add) FloatingActionButton fabAdd;

    private TapeTraverseDb traverseDb;
    private TapeLocationDb locationDb;
    private ArrayList<Traverse> traverseList;
    private TapeTraverseAdapter traverseAdapter;
    private int FRAGMENT_REQUEST = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        traverseDb = new TapeTraverseDb(getContext());
        traverseList = traverseDb.getAllTraverse();
        locationDb = new TapeLocationDb(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tape_and_compass, container, false);
        ButterKnife.bind(this, view);

        traverseAdapter = new TapeTraverseAdapter(getContext(), traverseList, traverseDb, locationDb);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(traverseAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState > 0)
                {
                    fabAdd.hide();
                }
                else {
                    fabAdd.show();
                }
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Traverse.SIZE, traverseAdapter.getSize());

                TraverseNewDialog dialog = new TraverseNewDialog();
                dialog.setTargetFragment(TapeAndCompassFragment.this, FRAGMENT_REQUEST);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "Traverse New");
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        traverseDb.close();
    }

    @Override
    public void onDialogDismissed(Traverse traverse) {
        int id = (int) traverseDb.insertTraverse(traverse);
        traverseAdapter.addTraverse(new Traverse(id, traverse.getTitle(), traverse.getDate()));
    }
}
