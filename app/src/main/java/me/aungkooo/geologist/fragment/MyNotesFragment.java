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

import me.aungkooo.geologist.R;
import me.aungkooo.geologist.database.MyNotesLocationDb;
import me.aungkooo.geologist.database.MyNotesTraverseDb;
import me.aungkooo.geologist.listener.OnDialogDismissListener;
import me.aungkooo.geologist.model.Traverse;
import me.aungkooo.geologist.adapter.TraverseAdapter;
import me.aungkooo.geologist.dialog.TraverseNewDialog;


public class MyNotesFragment extends Fragment implements OnDialogDismissListener
{
    private TraverseAdapter traverseAdapter;
    private ArrayList<Traverse> traverseList;
    private int FRAGMENT_REQUEST = 1;
    private MyNotesTraverseDb traverseDb;
    private MyNotesLocationDb locationDb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        traverseDb = new MyNotesTraverseDb(getContext());
        traverseList = traverseDb.getAllTraverse();
        locationDb = new MyNotesLocationDb(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_notes, container, false);

        traverseAdapter = new TraverseAdapter(getContext(), traverseList, traverseDb, locationDb);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_traverse);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false));
        recyclerView.setAdapter(traverseAdapter);

        final FloatingActionButton fabAdd = view.findViewById(R.id.fab_traverse_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Traverse.SIZE, traverseList.size() + 1);

                TraverseNewDialog dialog = new TraverseNewDialog();
                dialog.setTargetFragment(MyNotesFragment.this, FRAGMENT_REQUEST);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "Traverse New");
            }
        });

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

        return view;
    }

    @Override
    public void onDialogDismissed(Traverse traverse) {
        int id = (int) traverseDb.insertTraverse(traverse);
        Traverse traverseNew = new Traverse(id, traverse.getTitle(), traverse.getDate());
        traverseList.add(traverseNew);
        traverseAdapter.notifyDataSetChanged();
    }
}
