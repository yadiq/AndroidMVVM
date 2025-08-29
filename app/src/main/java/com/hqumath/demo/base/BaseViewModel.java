package com.hqumath.demo.base;

import androidx.lifecycle.ViewModel;

import com.hqumath.demo.repository.MyModel;

public class BaseViewModel extends ViewModel {
    protected BaseModel mModel;

    /**
     * 解除model中所有订阅者
     */
    public void dispose() {
        if (mModel != null) {
            mModel.dispose();
            mModel = null;
        }
    }
}
