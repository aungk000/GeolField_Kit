package me.aungkooo.geologist.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import me.aungkooo.geologist.R;
import me.aungkooo.geologist.database.StratigraphyLocationDb;
import me.aungkooo.geologist.model.StratigraphyLocation;
import me.aungkooo.geologist.viewholder.StratigraphyLocationViewHolder;

/**
 * Created by Ko Oo on 10/4/2018.
 */

public class StratigraphyLocationAdapter extends RecyclerAdapter<StratigraphyLocationViewHolder, StratigraphyLocation>
{
    private StratigraphyLocationDb locationDb;

    public StratigraphyLocationAdapter(Context context, ArrayList<StratigraphyLocation> itemList,
                                       StratigraphyLocationDb locationDb) {
        super(context, itemList);
        this.locationDb = locationDb;
    }

    @Override
    public StratigraphyLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createView(R.layout.location, parent);
        return new StratigraphyLocationViewHolder(view, getContext());
    }

    @Override
    public void onBindViewHolder(StratigraphyLocationViewHolder holder, int position) {
        holder.onBind(getItemList());

        final int adapterPosition = holder.getAdapterPosition();
        holder.getView().setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        int id = getItemList().get(adapterPosition).getId();
                        showConfirmDialog(adapterPosition, id);
                        return true;
                    }
                });
            }
        });
    }

    private void showConfirmDialog(final int position, final int id)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Warning")
                .setMessage("Are you sure that you want to delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getItemList().remove(position);
                        notifyDataSetChanged();
                        locationDb.deleteLocation(id);

                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }
}
