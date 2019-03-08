package com.cys.permission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cys.lib.permission.PermissionCallback;
import com.cys.lib.permission.SUIPermissioner;

public class MainFragment extends Fragment {

    private SUIPermissioner mPermissioner;
    private View mInflateView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflateView = inflater.inflate(R.layout.fragment_main, container, false);
        return mInflateView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPermissioner = SUIPermissioner.newInstance(this);
        mInflateView.findViewById(R.id.id_bt_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermissioner
                        .permission(Manifest.permission.CAMERA)
                        .callback(new PermissionCallback() {
                            @Override
                            public void onGranted() {
                                Toast.makeText(getActivity(), "相机权限获取成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(String[] permissions) {
                                Toast.makeText(getActivity(), "相机权限获取失败", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .request();
            }
        });

        mInflateView.findViewById(R.id.id_bt_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermissioner
                        .permission(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.CAMERA)
                        .callback(new PermissionCallback() {
                            @Override
                            public void onGranted() {
                                Toast.makeText(getActivity(), "全部权限获取成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(String[] permissions) {
                                Toast.makeText(getActivity(), "全部权限获取失败", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .request();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissioner.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
