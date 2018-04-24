package me.aungkooo.geologist.viewholder;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.activity.LocationDetailActivity;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.model.MyNotesLocation;

/**
 * Created by Ko Oo on 11/21/17.
 */

public class LocationViewHolder extends RecyclerViewHolder<MyNotesLocation> implements View.OnClickListener
{
    @BindView(R.id.txt_location_title) TextView txtTitle;
    @BindView(R.id.txt_location_time) TextView txtTime;

    private MyNotesLocation location;

    public LocationViewHolder(View itemView, Context context) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);
        setOnClickListener(this);
    }

    @Override
    public void onBind(ArrayList<MyNotesLocation> itemList) {
        location = itemList.get(getAdapterPosition());
        txtTitle.setText(location.getTitle());
        txtTime.setText(location.getTime());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), LocationDetailActivity.class);
        intent.putExtra(MyNotesLocation.ID, location.getId());
        intent.putExtra(MyNotesLocation.TITLE, location.getTitle());
        if(intent.resolveActivity(getContext().getPackageManager()) != null)
        {
            getContext().startActivity(intent);
        }
    }
}
