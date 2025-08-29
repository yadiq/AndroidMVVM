package com.hqumath.demo.base;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    protected BaseModelOld mModel;

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
