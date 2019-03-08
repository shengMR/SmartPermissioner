# SmartPermissioner

一个方便动态申请权限的库，适用于Android M(6.0) 以上需要在程序动态申请权限



### 一，基本用法

#### 1.1  导入库

Step1 ： 在根目录下面导入仓库地址

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```


Step2：添加依赖

```groovy
dependencies {
        implementation 'com.github.shengMR:SmartPermissioner:1.0.0'
}
```
#### 1.2  在AndroidManifest.xml添加需要的权限

```java
...
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.CAMERA" />
...
```

#### 1.3 在需要权限的界面，例如 Activity 或者 Fragment 里面 编写代码

```java
private SUIPermissioner mPermissioner;
...
mPermissioner = SUIPermissioner.newInstance(this);
mPermissioner
	.permission(
		Manifest.permission.WRITE_EXTERNAL_STORAGE,
		Manifest.permission.READ_EXTERNAL_STORAGE)
	.callback(new PermissionCallback(){
		@Override 
		public void onGranted() {
			Toast.makeText(MainActivity.this, "读写权限获取成功",Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onDenied(String[] permissions) {
			Toast.makeText(MainActivity.this, "读写权限获取失败",Toast.LENGTH_SHORT).show();
		}
	})
    .request();
...
    
// 需要重写Activity的onRequestPermissionsResult方法并加入语句
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    mPermissioner.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```

