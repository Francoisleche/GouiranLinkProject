package com.gouiranlink.franois.gouiranlinkproject.Gallery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.gouiranlink.franois.gouiranlinkproject.R;

import java.io.File;

/**
 * Created by François on 03/08/2017.
 */


public class FragmentGallery6 extends Fragment {

    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private String mPath;
    private ImageView mImageView;
    Bitmap bitmap = null;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_send_image, container, false);
        final String[] items = new String[] { "From Camera", "From SD Card" };
        mImageView = (ImageView)view.findViewById(R.id.iv_pic);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Image");

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(), "tmp_avatar_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg");
                    mImageCaptureUri = Uri.fromFile(file);

                    try {
                        intent.putExtra(
                                android.provider.MediaStore.EXTRA_OUTPUT,
                                mImageCaptureUri);
                        intent.putExtra("return-data", true);

                        getActivity().startActivityForResult(intent,
                                PICK_FROM_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.cancel();
                } else {
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    getActivity().startActivityForResult(
                            Intent.createChooser(intent,
                                    "Complete action using"), PICK_FROM_FILE);
                }
            }
        });
        final AlertDialog dialog = builder.create();

        Button show = (Button) view.findViewById(R.id.btn_choose);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switch the tab content to display the list view.

                Intent i = new Intent(getActivity(),Camera_Activity_Test_Fragment.class);
                startActivity(i);

                //dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("siçamarchejetetabasse!");

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == PICK_FROM_FILE) {
            mImageCaptureUri = data.getData();
            // mPath = getRealPathFromURI(mImageCaptureUri); //from Gallery

            if (mPath == null)
                mPath = mImageCaptureUri.getPath(); // from File Manager

            if (mPath != null)
                bitmap = BitmapFactory.decodeFile(mPath);
        } else {
            mPath = mImageCaptureUri.getPath();
            bitmap = BitmapFactory.decodeFile(mPath);
        }
        mImageView.setImageBitmap(bitmap);
    }

    public String getRealPathFromURI(Uri contentUri) {
        /*String [] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null,null);

        if (cursor == null) return null;

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);*/
        return "ok";
    }
}