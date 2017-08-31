package com.gouiranlink.franois.gouiranlinkproject.Gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by François on 28/08/2017.
 */

public class SecondActivity extends Activity{

    ImageAdapter adapter_image;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("omg70");
        if (requestCode == 1) {
            System.out.println("omg71");
            if (resultCode == Activity.RESULT_OK) {

                //adapter_image.notifyDataSetChanged();
                finish();
            }
        }

    }

    public SecondActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //some code
        //Intent returnIntent = new Intent();
        System.out.println("omg222");
        Intent intent = getIntent();
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //adapter_image = (ImageAdapter)intent.getSerializableExtra("adapter");
        //cameraIntent.putExtra("result", result);
        setResult(Activity.RESULT_OK,cameraIntent);
        startActivityForResult(cameraIntent,1);

        //finish();
    }
}
