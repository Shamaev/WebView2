package com.geekbrains.mybrowser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class Dialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Выйти из приложения?")
                .setTitle("Выход")
                .setPositiveButton("Выйти", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Objects.requireNonNull(getActivity()).finish();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

}
