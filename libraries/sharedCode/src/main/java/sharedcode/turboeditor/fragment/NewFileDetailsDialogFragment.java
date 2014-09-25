/*
 * Copyright (C) 2014 Vlad Mihalachi
 *
 * This file is part of Turbo Editor.
 *
 * Turbo Editor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Turbo Editor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package sharedcode.turboeditor.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import sharedcode.turboeditor.R;
import sharedcode.turboeditor.activity.PreferenceAbout;
import sharedcode.turboeditor.preferences.PreferenceHelper;
import sharedcode.turboeditor.util.SaveFileTask;

// ...
public class NewFileDetailsDialogFragment extends DialogFragment {

    private EditText mName;
    private EditText mFolder;

    public static NewFileDetailsDialogFragment newInstance(String fileText, String fileEncoding) {
        final NewFileDetailsDialogFragment f = new NewFileDetailsDialogFragment();
        final Bundle args = new Bundle();
        args.putString("fileText", fileText);
        args.putString("fileEncoding", fileEncoding);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_new_file_details, null);
        this.mName = (EditText) view.findViewById(android.R.id.text1);
        this.mFolder = (EditText) view.findViewById(android.R.id.text2);

        this.mFolder.setText(PreferenceHelper.getWorkingFolder(getActivity()));

        // Show soft keyboard automatically
        this.mName.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!mName.getText().toString().isEmpty() && !mFolder.getText().toString().isEmpty()) {
                                    File file = new File(mFolder.getText().toString(), mName.getText().toString());
                                    new SaveFileTask(getActivity(), file.getPath(), getArguments().getString("fileText"), getArguments().getString("fileEncoding")).execute();
                                }
                            }
                        }
                )
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                )
                .create();
    }

}