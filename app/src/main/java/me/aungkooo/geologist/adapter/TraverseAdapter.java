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
import me.aungkooo.geologist.database.MyNotesTraverseDb;
import me.aungkooo.geologist.model.Traverse;
import me.aungkooo.geologist.viewholder.TraverseViewHolder;

/**
 * Created by Ko Oo on 11/21/17.
 */

public class TraverseAdapter extends RecyclerAdapter<TraverseViewHolder, Traverse>
{
    private MyNotesTraverseDb traverseDb;
    private MyNotesLocationDb locationDb;

    public TraverseAdapter(Context context, ArrayList<Traverse> itemList,
                           MyNotesTraverseDb traverseDb, MyNotesLocationDb locationDb) {
        super(context, itemList);
        this.traverseDb = traverseDb;
        this.locationDb = locationDb;
    }

    @Override
    public TraverseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createView(R.layout.view_traverse, parent);
        return new TraverseViewHolder(view, getContext());
    }

    @Override
    public void onBindViewHolder(final TraverseViewHolder holder, int position) {
        holder.onBind(getItemList());

        final int adapterPosition = holder.getAdapterPosition();
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
            {
                menu.add(R.string.delete).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = getItemList().get(adapterPosition).getId();
                        showConfirmDialog(adapterPosition, id);
                        return true;
                    }
                });
            }
        });
    }

    private void showConfirmDialog(final int position, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.warning)
                .setMessage(R.string.warning_message)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getItemList().remove(position);
                        notifyDataSetChanged();
                        traverseDb.deleteTraverse(id);
                        locationDb.deleteAllLocation(id);

                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }
}

