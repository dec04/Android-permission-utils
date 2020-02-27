# Android permission utils

## Simple class for give multiple permissions on android device, with simple explorations. 

In order for each permission to show its explanation, you need to add lines in the **_strings.xml_** file
Required format for string resource names is **PERMISSION_NAME** + **_EXPLANATION**

For example if you request this permissions (_ACCESS_FINE_LOCATION_, _ACCESS_COARSE_LOCATION_, _WRITE_EXTERNAL_STORAGE_) names must be:

```
<string name="ACCESS_FINE_LOCATION_EXPLANATION">Some explanation</string>
<string name="ACCESS_COARSE_LOCATION_EXPLANATION">Some explanation</string>
<string name="WRITE_EXTERNAL_STORAGE_EXPLANATION">Some explanation</string>
```

Basic usage from my google map example:

```
Context applicationContext;
PermissionUtils permissionUtils;
String[] permissions = {
  Manifest.permission.ACCESS_FINE_LOCATION,
  Manifest.permission.ACCESS_COARSE_LOCATION,
  Manifest.permission.WRITE_EXTERNAL_STORAGE
};
```

Usually in a method OnCreate:

```
applicationContext = GoogleMapActivity.this;
permissionUtils = new PermissionUtils(applicationContext);
```

Check permissions:

```
... some code ...

// Check all permissions
if (permissionUtils.check(permissions)) {
    // if permissions already taken
    // do something
} else {
    // request permission
    permissionUtils.requestPermission(permissions);
}
```

If permission not taken, app request it from user. All at once.

![Permission request](example/1.png) ![Permission request](example/2.png)

Usualy in OnResume or OnResumeFragment method:

```
if (permissionUtils.flagPermissionDenied) {
    // Permission was not granted, display "permission rejected" dialog.
    permissionUtils.showMissingPermissionError(permissions);
    permissionUtils.flagPermissionDenied = false;
}
```

Now, if user reject permission app show window, explaining that the permit is not received.

![Permission request](example/3.png)

The next time you request permissions, a window will be displayed explaining why you need each permission individually. In the last window, you can re-request permission.

![Permission request](example/4.png)![Permission request](example/5.png)![Permission request](example/6.png)

You also need implement method onRequestPermissionsResult

```
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode != PERMISSION_REQUEST_CODE) {
        return;
    }

    if (permissions.length != 0) {
        if (!permissionUtils.check(permissions)) {
            // Display the missing permission error dialog when the fragments resume.
            permissionUtils.flagPermissionDenied = true;
        } else {
            // For example enable the "my location layer" if the permission has been granted.
        }
    }
}
```

That's all. Happy hacking. :heart_decoration:

