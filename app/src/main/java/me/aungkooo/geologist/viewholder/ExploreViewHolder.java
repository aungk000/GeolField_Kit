package me.aungkooo.geologist.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.model.FirebaseLocationModel;
import me.aungkooo.geologist.viewholder.BaseViewHolder;

/**
 * Created by root on 11/27/17.
 */

public class ExploreViewHolder extends RecyclerViewHolder<FirebaseLocationModel>
{
    @BindView(R.id.txt_location_title) TextView txtTitle;
    @BindView(R.id.txt_rock_type) TextView txtRockType;
    @BindView(R.id.txt_rock_unit) TextView txtRockUnit;

    private FirebaseLocationModel location;

    public ExploreViewHolder(View itemView, Context context) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBind(ArrayList<FirebaseLocationModel> itemList) {
        location = itemList.get(getAdapterPosition());
        txtTitle.setText(location.getGps());
        txtRockType.setText(location.getRockType());
        txtRockUnit.setText(location.getRockUnit());
    }
}
