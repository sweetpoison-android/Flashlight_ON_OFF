package com.company.flashlight_on_off;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
ImageView img;
Button bt;
final int CAMERA_REQUEST=50;
boolean flashlightstatus=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img=findViewById(R.id.image);
        bt=findViewById(R.id.button);

        flashligh_on();

        final  boolean hascameraflash=getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hascameraflash)
                {
                    if(flashlightstatus) {
                        flashlight_OFF();
                    }
                    else {

                            flashligh_on();

                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this,"No flashlight on your device",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case CAMERA_REQUEST :
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                   bt.setEnabled(false);
                   bt.setText("Flashlight_ON");
                   img.setEnabled(true);

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Permission denied for camera",Toast.LENGTH_SHORT).show();
                }
                break;

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flashlight_OFF();
    }

    @Override
    protected void onPause() {
        super.onPause();
        flashlight_OFF();
    }

    @Override
    protected void onResume() {
        super.onResume();
        flashligh_on();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void flashligh_on() {
       CameraManager mngr=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
       try {
           String cameraid=mngr.getCameraIdList()[0];
           mngr.setTorchMode(cameraid,true);
           flashlightstatus=true;
           img.setImageResource(R.drawable.ic_bulb_on);
           bt.setText("Touch for flash light oFF");
       }catch (Exception e)
       {

       }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void flashlight_OFF()
    {
        CameraManager mngr=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraid=mngr.getCameraIdList()[0];
            mngr.setTorchMode(cameraid,false);
            flashlightstatus=false;
            img.setImageResource(R.drawable.ic_bulb_off);
            bt.setText("Touch for flash light ON");
        }catch (Exception e)
        {

        }


    }
}
