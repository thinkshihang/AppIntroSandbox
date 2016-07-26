package com.github.paolorotolo.appintroexample.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.github.paolorotolo.appintroexample.R;

public class MyDialogFragment extends DialogFragment
{

    private DialogButtonClickListener callback;

    public interface DialogButtonClickListener {
        void onDialogButtonClick(String content);
    }

    public MyDialogFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = (DialogButtonClickListener)getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_with_input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
//                Toast.makeText(getActivity(), "hello world", Toast.LENGTH_SHORT).show();
                callback.onDialogButtonClick("test");
            }
        });

        return builder.create();
    }
}
