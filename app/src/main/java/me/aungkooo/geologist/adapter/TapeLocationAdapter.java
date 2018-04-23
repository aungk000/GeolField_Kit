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
import me.aungkooo.geologist.database.TapeLocationDb;
import me.aungkooo.geologist.model.TapeLocation;
import me.aungkooo.geologist.viewholder.TapeLocationViewHolder;

/**
 * Created by Ko Oo on 20/4/2018.
 */

public class TapeLocationAdapter extends RecyclerAdapter<TapeLocationViewHolder, TapeLocation>
{
    private TapeLocationDb locationDb;

    public TapeLocationAdapter(Context context, ArrayList<TapeLocation> itemList,
                               TapeLocationDb locationDb) {
        super(context, itemList);
        this.locationDb = locationDb;
    }

    @Override
    public TapeLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createView(R.layout.view_location, parent);
        return new TapeLocationViewHolder(view, getContext());
    }

    @Override
    public void onBindViewHolder(TapeLocationViewHolder holder, int position) {
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
                        removeLocation(position);
                        locationDb.deleteLocation(id);

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

    public void addLocation(TapeLocation location)
    {
        getItemList().add(location);
        notifyDataSetChanged();
    }

    public void removeLocation(int position)
    {
        getItemList().remove(position);
        notifyDataSetChanged();
    }
}
