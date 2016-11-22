package example.jackf.testfresco;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback  {

    MySimpleDraweeView myDraweeView1;
    MySimpleDraweeView myDraweeView2;
    Uri uri1, uri2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDraweeView1 = (MySimpleDraweeView) findViewById(R.id.simpleDraweeView);
        //"https://s3-ap-northeast-1.amazonaws.com/sunray-dev/file/b27456c4-f305-4751-a9b3-ad00dfa5bd9e-preview.png"
        String url1 = "https://files.parsetfss.com/88643df2-0c5d-4c03-a2d1-17143a6bf20e/tfss-2ccd64a3-92eb-476b-a848-be19de94f27a-pic";
        //Uri uri1 = Uri.parse("http://files.parsetfss.com/88643df2-0c5d-4c03-a2d1-17143a6bf20e/tfss-981b1116-9519-4954-bd0f-d3442a6efd8f-pic");
        uri1 = Uri.parse(url1);
        myDraweeView1.setImageUriPhoto(uri1, 0 );

        myDraweeView2 = (MySimpleDraweeView) findViewById(R.id.mySimpleDraweeView);
        //Uri uri2 = Uri.parse("http://files.parsetfss.com/88643df2-0c5d-4c03-a2d1-17143a6bf20e/tfss-a7229751-d824-4c1c-9d10-cb699da21eb6-pic");
        uri2 = Uri.parse("https://files.parsetfss.com/88643df2-0c5d-4c03-a2d1-17143a6bf20e/tfss-891b3efd-7471-45af-befb-729eb9678449-pic");
        myDraweeView2.setImageUriPhoto(uri2, 1 );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean saveUri1 = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.saveToFile:
                saveFile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == AppPermission.REQUEST_MULTI_PERMISSION) {
            boolean allGranted = true;
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            final boolean toContinue = allGranted;
            // Delay 200 ms to avoid IllegalStageException
            // http://stackoverflow.com/questions/33264031/calling-dialogfragments-show-from-within-onrequestpermissionsresult-causes
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (toContinue) {
                        Log.d("JackTest", "Permission are all granted");
                        saveFile();
                    } else {
                        Toast.makeText(MainActivity.this, "Permissions are not granted", Toast.LENGTH_LONG).show();
                    }
                }
            };
            new Handler().postDelayed(runnable, 200);

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void saveFile() {
        AppPermission appPermission = new AppPermission(LoadImageUtil.wantedPermissions);
        if (!appPermission.requestMultiPermission(this)) {
            return;
        }
        LoadImageUtil.getImageDirectly(this, saveUri1 ? uri1 : uri2);
        saveUri1 = !saveUri1;
    }
}
