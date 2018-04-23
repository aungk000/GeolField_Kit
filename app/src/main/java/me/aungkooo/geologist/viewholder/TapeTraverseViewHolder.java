package me.aungkooo.geologist.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.activity.TapeTraverseActivity;
import me.aungkooo.geologist.model.Traverse;

/**
 * Created by Ko Oo on 20/4/2018.
 */

public class TapeTraverseViewHolder extends RecyclerViewHolder<Traverse> implements View.OnClickListener
{
    @BindView(R.id.txt_traverse_title) TextView txtTitle;
    @BindView(R.id.txt_traverse_date) TextView txtDate;

    private Traverse traverse;

    public TapeTraverseViewHolder(View itemView, Context context) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);
        setOnClickListener(this);
    }

    @Override
    public void onBind(ArrayList<Traverse> itemList) {
        traverse = itemList.get(getAdapterPosition());
        txtTitle.setText(traverse.getTitle());
        txtDate.setText(traverse.getDate());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), TapeTraverseActivity.class);
        intent.putExtra(Traverse.ID, traverse.getId());
        intent.putExtra(Traverse.TITLE, traverse.getTitle());
        if(intent.resolveActivity(getContext().getPackageManager()) != null)
        {
            getContext().startActivity(intent);
        }
    }
}
