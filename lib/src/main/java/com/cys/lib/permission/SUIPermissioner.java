package com.cys.lib.permission;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.cys.lib.permission.bean.PermissionBean;
import com.cys.lib.permission.util.PermissionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SUIPermissioner {

    public static final String TAG = SUIPermissioner.class.getSimpleName();

    public static int requestCode;

    private Object o;
    private PermissionCallback mCallback;
    private List<PermissionBean> mPermissions;
    private List<PermissionBean> mGrantedPermissions;
    private List<PermissionBean> mDeniedPermissions;

    private SUIPermissioner(Object context) {
        this.o = context;
        this.mPermissions = new ArrayList<>();
        this.mGrantedPermissions = new ArrayList<>();
        this.mDeniedPermissions = new ArrayList<>();
    }

    public static SUIPermissioner newInstance(Object context) {
        SUIPermissioner suiPermissioner = new SUIPermissioner(context);
        return suiPermissioner;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            Iterator<PermissionBean> iterator = mDeniedPermissions.iterator();
            while (iterator.hasNext()) {
                PermissionBean bean = iterator.next();
                if (bean.getPermission().equals(permission)) {
                    if (grantResults[i] == 0) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }

        if (mDeniedPermissions.isEmpty()) {
            if (mCallback != null) {
                mCallback.onGranted();
            }
        } else {
            if (mCallback != null) {
                String[] perms = new String[mDeniedPermissions.size()];
                for (int i = 0; i < mDeniedPermissions.size(); i++) {
                    perms[i] = mDeniedPermissions.get(i).getPermission();
                }
                mCallback.onDenied(perms);
            }
        }

    }

    //region 链式调度
    public SUIPermissioner permission(String... strings) {
        mPermissions.clear();
        for (int i = 0; i < strings.length; i++) {
            String perm = strings[i];
            PermissionBean bean = new PermissionBean();
            bean.setGranted(false);
            bean.setPermission(perm);
            bean.setRequestCode(requestCode);
            mPermissions.add(bean);
        }
        requestCode++;
        return this;
    }

    public SUIPermissioner callback(PermissionCallback callback) {
        this.mCallback = callback;
        return this;
    }

    @SuppressLint("NewApi")
    public void request() {

        if (mPermissions.isEmpty() || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (mCallback != null) {
                mCallback.onGranted();
            }
            return;
        }

        this.mGrantedPermissions.clear();
        this.mDeniedPermissions.clear();

        for (PermissionBean bean : mPermissions) {

            if (PermissionUtils.hasPermission(getContext(), bean.getPermission())) {
                bean.setGranted(true);
                this.mGrantedPermissions.add(bean);
            } else {
                this.mDeniedPermissions.add(bean);
            }
        }

        if (this.mDeniedPermissions.isEmpty()) {
            if (mCallback != null) {
                mCallback.onGranted();
            }
        } else {
            String[] permissions = new String[mDeniedPermissions.size()];
            for (int i = 0; i < mDeniedPermissions.size(); i++) {
                permissions[i] = mDeniedPermissions.get(i).getPermission();
            }

            if (this.o instanceof Activity) {
                requestPermissions((Activity) this.o, 0, permissions);
            } else if (this.o instanceof Fragment) {
                requestPermissions((Fragment) this.o, 0, permissions);
            }
        }
    }
    //endregion

    private void requestPermissions(Activity host, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(host, permissions, requestCode);
    }

    private void requestPermissions(Fragment host, int requestCode, String... permissions) {
        host.requestPermissions(permissions, requestCode);
    }

    private Context getContext() {
        if (o instanceof Activity) {
            return (Activity) o;
        } else if (o instanceof Fragment) {
            return ((Fragment) o).getActivity();
        }
        throw new RuntimeException("context object is not Activity or Fragment");
    }
}
