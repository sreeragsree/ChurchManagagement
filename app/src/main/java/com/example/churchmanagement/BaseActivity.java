package com.example.churchmanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.ProgressWheel;
import com.example.churchmanagement.utils.Utils;

public class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ProgressWheel progress;
    private ActionBar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void setTitle(String title) {
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if (getActiveActionBar() != null) {
            getActiveActionBar().setTitle(title);
            Utils.setSystemBarColor(this, R.color.indigo_50);
            Utils.setSystemBarLight(this);
        }
    }
    protected void requestFocus(EditText view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    protected ActionBar getActiveActionBar() {
        if (actionBar == null) {
            ActionBarUtils.setActionBar(this, false);
            actionBar = getSupportActionBar();
        }
        return actionBar;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){ ;
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
    public void showAlert(String message, String title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);

        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public ProgressWheel getProgress() {
        if (progress == null) {
            progress = new ProgressWheel(this);
        }
        return progress;
    }

    public void showProgressWheel() {
        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if (parent != null) {
            parent.removeView(getProgress());
        }

        FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
        rootLayout.addView(getProgress());
    }

    public void hideProgressWheel(boolean animation) {
        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if (parent != null) {
            parent.removeView(getProgress());
            progress = null;
        }
    }
    public void progressWheelSetTitle(String title) {

        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if(parent != null) {
            getProgress().setTitleText(title);
        }
    }

    public void progressWheelHideTitle() {

        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if(parent != null) {
            getProgress().setTitleText("");
            getProgress().hideTitleView();
        }
    }




}
