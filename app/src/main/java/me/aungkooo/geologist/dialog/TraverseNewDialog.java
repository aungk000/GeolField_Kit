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

import me.aungkooo.geologist.R;
import me.aungkooo.geologist.Utility;
import me.aungkooo.geologist.listener.OnDialogDismissListener;
import me.aungkooo.geologist.model.Traverse;


public class TraverseNewDialog extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_traverse_new, (ViewGroup) getView());
        builder.setView(view);

        final AlertDialog dialog = builder.create();

        Bundle args = getArguments();
        int size = args.getInt(Traverse.SIZE);
        final EditText editTitle = view.findViewById(R.id.edit_traverse_title);
        String text = "Traverse " + size;
        editTitle.setText(text);

        final EditText editDate = view.findViewById(R.id.edit_traverse_date);
        editDate.setText(Utility.getDate());

        Button btnCancel = view.findViewById(R.id.btn_traverse_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final OnDialogDismissListener listener = (OnDialogDismissListener) getTargetFragment();
        Button btnAdd = view.findViewById(R.id.btn_traverse_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String date = editDate.getText().toString();

                if (title.isEmpty()) {
                    editTitle.setError("This field is required.");
                    editTitle.requestFocus();
                }
                else if (date.isEmpty()) {
                    editDate.setError("This field is required.");
                    editDate.requestFocus();
                }
                else {
                    listener.onDialogDismissed(new Traverse(title, date));

                    Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }
}
