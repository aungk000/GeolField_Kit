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
import me.aungkooo.geologist.database.MyNotesLocationDb;
import me.aungkooo.geologist.model.MyNotesLocation;
import me.aungkooo.geologist.viewholder.LocationViewHolder;

/**
 * Created by Ko Oo on 11/21/17.
 */

public class LocationAdapter extends RecyclerAdapter<LocationViewHolder, MyNotesLocation>
{
    private MyNotesLocationDb locationDb;

    public LocationAdapter(Context context, ArrayList<MyNotesLocation> itemList, MyNotesLocationDb locationDb) {
        super(context, itemList);
        this.locationDb = locationDb;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createView(R.layout.view_location, parent);
        return new LocationViewHolder(view, getContext());
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
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
