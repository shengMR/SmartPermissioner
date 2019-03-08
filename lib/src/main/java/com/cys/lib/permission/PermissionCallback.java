package com.cys.lib.permission;

public interface PermissionCallback {

    void onGranted();

    void onDenied(String[] permissions);
}
