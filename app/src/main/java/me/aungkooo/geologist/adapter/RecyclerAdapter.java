package me.aungkooo.geologist.adapter;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.aungkooo.geologist.viewholder.RecyclerViewHolder;

/**
 * Created by Ko Oo on 9/4/18.
 */

public abstract class RecyclerAdapter<VH extends RecyclerViewHolder, OBJ> extends RecyclerView.Adapter<VH>
{
    private Context context;
    private ArrayList<OBJ> itemList;

    public RecyclerAdapter(Context context, ArrayList<OBJ> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public View createView(@LayoutRes int resource, @Nullable ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(resource, parent, false);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public ArrayList<OBJ> getItemList() {
        return itemList;
    }

    public Context getContext() {
        return context;
    }

    public void add(OBJ item)
    {
        itemList.add(item);
        notifyDataSetChanged();
    }
}
