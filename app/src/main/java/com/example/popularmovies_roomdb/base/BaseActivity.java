package com.example.popularmovies_roomdb.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

public abstract class BaseActivity<T extends BaseViewModel> extends ParentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().loadingProgress.observe(this, load -> {
            if (load != null && load) showDialog();
            else hideDialog();
        });
        getViewModel().showMessage.observe(this, msg -> {
            if (msg != null) showMessage(msg);
        });
        getViewModel().showMessageRes.observe(this, msg -> {
            if (msg != null) showMessage(msg);
        });

    }

    public abstract T getViewModel();


}
