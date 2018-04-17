package me.aungkooo.geologist.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.aungkooo.geologist.R;
import me.aungkooo.geologist.model.FirebaseLocationModel;
import me.aungkooo.geologist.viewholder.ExploreViewHolder;

/**
 * Created by Ko Oo on 15/4/2018.
 */

public class ExploreRecyclerAdapter extends RecyclerAdapter<ExploreViewHolder, FirebaseLocationModel>
{

    public ExploreRecyclerAdapter(Context context, ArrayList<FirebaseLocationModel> itemList) {
        super(context, itemList);
    }

    @Override
    public ExploreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createView(R.layout.view_in_explore, parent);
        return new ExploreViewHolder(view, getContext());
    }

    @Override
    public void onBindViewHolder(ExploreViewHolder holder, int position) {
        holder.onBind(getItemList());
    }
}
