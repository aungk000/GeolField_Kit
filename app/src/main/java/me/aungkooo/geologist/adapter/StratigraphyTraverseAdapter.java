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
import me.aungkooo.geologist.database.StratigraphyTraverseDb;
import me.aungkooo.geologist.model.Traverse;
import me.aungkooo.geologist.viewholder.StratigraphyTraverseViewHolder;

/**
 * Created by Ko Oo on 9/4/2018.
 */

public class StratigraphyTraverseAdapter extends RecyclerAdapter<StratigraphyTraverseViewHolder, Traverse>
{
    private StratigraphyTraverseDb traverseDb;
    private StratigraphyLocationDb locationDb;

    public StratigraphyTraverseAdapter(Context context, ArrayList<Traverse> itemList,
                                       StratigraphyTraverseDb traverseDb, StratigraphyLocationDb locationDb) {
        super(context, itemList);
        this.traverseDb = traverseDb;
        this.locationDb = locationDb;
    }

    @Override
    public StratigraphyTraverseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createView(R.layout.traverse, parent);
        return new StratigraphyTraverseViewHolder(view, getContext());
    }

    @Override
    public void onBindViewHolder(final StratigraphyTraverseViewHolder holder, int position) {
        holder.onBind(getItemList());

        final int adapterPosition = holder.getAdapterPosition();
        holder.getView().setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
        {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
            {
                menu.add(R.string.delete).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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
