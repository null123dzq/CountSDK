package com.jolo.countsdk.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by dzq on 2016/9/13.
 */
public class LocationUtil {

    private static final String TAG = "LocationUtil";
    // 纬度
    private static String latitude = "0.0";
    // 经度
    private static String longitude = "0.0";

    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";

    /**
     * 初始化位置信息
     *
     * @param context
     */
    public static void initLocation(final Context context) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        Location location = null;
        SLog.e(TAG, "start init");

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                SLog.e(TAG, "location get from GPS");
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
                SLog.e(TAG, "get the location info");
            }
        }

        //部分机型不提供network_provider
        if (location == null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latitude = String.valueOf(location.getLatitude()); // 经度
                longitude = String.valueOf(location.getLongitude()); // 纬度
                SLog.e(TAG, "location get from NET_WORK. " + "latitude:" + latitude + ",longitude:" + longitude);
            }
        }

        if (!TextUtils.isEmpty(longitude) && !longitude.equals("0.0")) {
            SharedPreferencesUtil.put(context, LONGITUDE, longitude);
            SharedPreferencesUtil.put(context, LATITUDE, latitude);
        }

    }

    private static LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                longitude = String.valueOf(location.getLongitude());
                latitude = String.valueOf(location.getLatitude());

            }
        }
    };

    public static String getLongitude(Context context) {
        return SharedPreferencesUtil.getString(context, LONGITUDE, "0.0");
    }

    public static String getLatitude(Context context) {
        return SharedPreferencesUtil.getString(context, LATITUDE, "0.0");
    }
}
