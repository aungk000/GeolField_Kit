package me.aungkooo.geologist.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.aungkooo.geologist.R;
import me.aungkooo.geologist.activity.StratigraphyLocationDetailActivity;
import me.aungkooo.geologist.model.StratigraphyLocation;

/**
 * Created by Ko Oo on 10/4/2018.
 */

public class StratigraphyLocationViewHolder extends RecyclerViewHolder<StratigraphyLocation> implements View.OnClickListener
{
    private TextView txtTitle, txtTime;
    private StratigraphyLocation location;

    public StratigraphyLocationViewHolder(View itemView, Context context) {
        super(itemView, context);
        txtTitle = findById(R.id.txt_location_title);
        txtTime = findById(R.id.txt_location_time);
        setOnClickListener(this);
    }

    @Override
    public void onBind(ArrayList<StratigraphyLocation> itemList) {
        location = itemList.get(getAdapterPosition());
        txtTitle.setText(location.getTitle());
        txtTime.setText(location.getTime());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), StratigraphyLocationDetailActivity.class);
        intent.putExtra(StratigraphyLocation.ID, location.getId());
        intent.putExtra(StratigraphyLocation.TITLE, location.getTitle());
        if(intent.resolveActivity(getContext().getPackageManager()) != null)
        {
            getContext().startActivity(intent);
        }
    }
}
