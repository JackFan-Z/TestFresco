package example.jackf.testfresco;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackf on 2016/8/1.
 */
public class AppPermission {

    public static int REQUEST_MULTI_PERMISSION = 0;
    final String[] wantedPermissions;

    public AppPermission(String[] wantedPermissions) {
        this.wantedPermissions = wantedPermissions;
    }

    public boolean requestMultiPermission(Activity activity) {

        List<String> missingPermissions = new ArrayList<>();
        List<String> rationalePermissions = new ArrayList<>();
        for (String permission : wantedPermissions) {
            checkPermission(activity, permission, missingPermissions, rationalePermissions);
        }

        if (missingPermissions.size() == 0) {
            return true;
        }
        // Camera permission has not been granted yet. Request it directly.
        ActivityCompat.requestPermissions(activity, missingPermissions.toArray(new String[missingPermissions.size()]),
                REQUEST_MULTI_PERMISSION);

        return false;
    }

    private void checkPermission(Activity activity, String permission,
                                 List<String> missingPermissions, List<String> rationalePermissions) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            missingPermissions.add(permission);
            // Check for Rationale Option
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                rationalePermissions.add(permission);
            }
        }
    }
}


