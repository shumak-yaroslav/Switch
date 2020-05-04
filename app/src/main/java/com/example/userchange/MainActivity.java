package com.example.userchange;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.View;
import android.widget.Button;

import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {



    public static final String EXTRA_AFFILIATION_ID = "affiliation_id";

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        // If possible, reuse an existing affiliation ID across the
// primary user and (later) the ephemeral user.
        assert dpm != null;
        ComponentName adminName = new ComponentName(MainActivity.this,MyDeviceAdminReceiver.class);
        Set<String> identifiers = dpm.getAffiliationIds(adminName);
        if (identifiers.isEmpty()) {
            identifiers.add(UUID.randomUUID().toString());
            dpm.setAffiliationIds(adminName, identifiers);
        }

        // Pass an affiliation ID to the ephemeral user in the admin extras.
        PersistableBundle adminExtras = new PersistableBundle();
        adminExtras.putString(EXTRA_AFFILIATION_ID, identifiers.iterator().next());
        // Include any other config for the new user here ...

        // Create the ephemeral user, using this component as the admin.
        try {
            UserHandle ephemeralUser = dpm.createAndManageUser(
                    adminName,
                    "tmp_user",
                    adminName,
                    adminExtras,
                    DevicePolicyManager.MAKE_USER_EPHEMERAL |
                            DevicePolicyManager.SKIP_SETUP_WIZARD);
            dpm.switchUser(adminName, ephemeralUser);

        } catch (UserManager.UserOperationException e) {
            e.getUserOperationResult();// Find a way to free up users...
        }



    }

}
