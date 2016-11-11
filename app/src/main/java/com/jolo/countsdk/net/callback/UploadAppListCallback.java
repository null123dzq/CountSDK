package com.jolo.countsdk.net.callback;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jolo.countsdk.net.BaseNetData;
import com.jolo.countsdk.net.BaseNetUtil;
import com.jolo.countsdk.net.impl.UploadUserAppListNetUtil;
import com.jolo.countsdk.util.VersionUtil;

/**
 * Description:
 * Created by duzhiqi on 2016/11/10.
 */
public class UploadAppListCallback implements BaseNetUtil.Callbacks {
    private static final int REPOST_REQUEST_COUNT = 3;
    private int index;
    private Context mContext;

    public UploadAppListCallback(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    @Override
    public void onFailed() {
        rePostRequest();
    }

    @Override
    public void onError(Exception e) {
        rePostRequest();
    }

    @Override
    public void onNetError() {

    }

    @Override
    public void onSuccess(@NonNull BaseNetData result) {
        index = 0;
    }


    private void rePostRequest() {
        if (index < REPOST_REQUEST_COUNT) {
            UploadUserAppListNetUtil net = new UploadUserAppListNetUtil(mContext);
            String apps = VersionUtil.getAppNameStr(mContext);
            net.uploadAppList(apps, this);
            index++;
        }
    }
}