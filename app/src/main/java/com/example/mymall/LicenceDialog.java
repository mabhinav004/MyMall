package com.example.mymall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LicenceDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view=getActivity().getLayoutInflater().inflate(R.layout.dialog_licences,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setView(view);

        TextView textView=view.findViewById(R.id.txtLicences);
        Button btnDismiss=view.findViewById(R.id.btnDismiss);

        textView.setText(Utils.getLicences());
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }
}
