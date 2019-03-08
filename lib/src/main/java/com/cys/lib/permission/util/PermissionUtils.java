package com.cys.lib.permission.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;

public class PermissionUtils {

    public static boolean hasPermission(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        int targetSdkVersion = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        boolean granted;
        for (String permission : permissions) {

            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                granted = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
            } else {
                granted = PermissionChecker.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            }

            if (!granted) {
                return false;
            }
        }
        return true;
    }
}
