package me.aungkooo.geologist.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.activity.TapeLocationDetailActivity;
import me.aungkooo.geologist.model.TapeLocation;

/**
 * Created by Ko Oo on 20/4/2018.
 */

public class TapeLocationViewHolder extends RecyclerViewHolder<TapeLocation> implements View.OnClickListener
{
    @BindView(R.id.txt_location_title) TextView txtTitle;
    @BindView(R.id.txt_location_time) TextView txtTime;

    private TapeLocation location;

    public TapeLocationViewHolder(View itemView, Context context) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);
        setOnClickListener(this);
    }

    @Override
    public void onBind(ArrayList<TapeLocation> itemList) {
        location = itemList.get(getAdapterPosition());
        txtTitle.setText(location.getTitle());
        txtTime.setText(location.getTime());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), TapeLocationDetailActivity.class);
        intent.putExtra(TapeLocation.ID, location.getId());
        intent.putExtra(TapeLocation.TITLE, location.getTitle());
        if(intent.resolveActivity(getContext().getPackageManager()) != null)
        {
            getContext().startActivity(intent);
        }
    }
}
