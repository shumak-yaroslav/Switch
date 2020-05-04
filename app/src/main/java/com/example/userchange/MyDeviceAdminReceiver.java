package com.example.userchange;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onUserSwitched(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle switchedUser) {
        Toast.makeText(context,"User has switched!",Toast.LENGTH_SHORT).show();
    }

}
