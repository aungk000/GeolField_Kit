package me.aungkooo.geologist.dialog;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.Utility;
import me.aungkooo.geologist.listener.OnDialogDismissListener;
import me.aungkooo.geologist.model.Traverse;


public class TraverseNewDialog extends DialogFragment
{
    @BindView(R.id.edit_traverse_title)
    EditText editTitle;
    @BindView(R.id.edit_traverse_date)
    EditText editDate;
    @BindView(R.id.btn_traverse_cancel)
    Button btnCancel;
    @BindView(R.id.btn_traverse_add)
    Button btnAdd;
    Unbinder unbinder;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_traverse_new, (ViewGroup) getView());
        builder.setView(view);
        unbinder = ButterKnife.bind(this, view);

        Bundle args = getArguments();
        int size = args.getInt(Traverse.SIZE);
        String text = "Traverse " + size;

        editTitle.setText(text);
        editDate.setText(Utility.getDate());

        final OnDialogDismissListener listener = (OnDialogDismissListener) getTargetFragment();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String date = editDate.getText().toString();

                if (title.isEmpty()) {
                    editTitle.setError("This field is required.");
                    editTitle.requestFocus();
                } else if (date.isEmpty()) {
                    editDate.setError("This field is required.");
                    editDate.requestFocus();
                } else {
                    listener.onDialogDismissed(new Traverse(title, date));

                    Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
