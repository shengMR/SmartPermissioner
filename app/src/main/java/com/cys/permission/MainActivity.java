package com.cys.permission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cys.lib.permission.PermissionCallback;
import com.cys.lib.permission.SUIPermissioner;

public class MainActivity extends AppCompatActivity {

    private SUIPermissioner mPermissioner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPermissioner = SUIPermissioner.newInstance(this);

        findViewById(R.id.id_bt_read_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermissioner
                        .permission(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .callback(new PermissionCallback() {
                            @Override
                            public void onGranted() {
                                Toast.makeText(MainActivity.this, "读写权限获取成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(String[] permissions) {
                                Toast.makeText(MainActivity.this, "读写权限获取失败", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .request();
            }
        });

        findViewById(R.id.id_bt_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermissioner
                        .permission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        .callback(new PermissionCallback() {
                            @Override
                            public void onGranted() {
                                Toast.makeText(MainActivity.this, "位置权限获取成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(String[] permissions) {
                                Toast.makeText(MainActivity.this, "位置权限获取失败", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .request();
            }
        });

        findViewById(R.id.id_bt_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermissioner
                        .permission(Manifest.permission.CAMERA)
                        .callback(new PermissionCallback() {
                            @Override
                            public void onGranted() {
                                Toast.makeText(MainActivity.this, "相机权限获取成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(String[] permissions) {
                                Toast.makeText(MainActivity.this, "相机权限获取失败", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .request();
            }
        });

        findViewById(R.id.id_bt_all).setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(MainActivity.this, "全部权限获取成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(String[] permissions) {
                                Toast.makeText(MainActivity.this, "全部权限获取失败", Toast.LENGTH_SHORT).show();
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
