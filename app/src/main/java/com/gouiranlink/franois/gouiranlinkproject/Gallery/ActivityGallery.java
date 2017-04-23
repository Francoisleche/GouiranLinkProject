package com.gouiranlink.franois.gouiranlinkproject.Gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.AndroidCameraApi;
import com.gouiranlink.franois.gouiranlinkproject.AndroidCameraApi2;
import com.gouiranlink.franois.gouiranlinkproject.R;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityGallery extends Fragment {
    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    Adapter_PhotosFolder obj_adapter;
    GridView gv_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    Cursor cursor;
    private static int RESULT_LOAD_IMAGE = 1;

    public ActivityGallery() {
        // Required empty public constructor
    }

    /*public static ActivityGallery newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        ActivityGallery firstFragment = new ActivityGallery();
        firstFragment.setArguments(args);
        return firstFragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new ActivityGallery();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //setContentView(R.layout.activity_gallery);

        final View view = inflater.inflate(R.layout.activity_gallery, container, false);
        gv_folder = (GridView)view.findViewById(R.id.gv_folder);



        Button selfie = (Button) view.findViewById(R.id.selfie);
        selfie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //CamTestActivity cam = new CamTestActivity();
                //Intent intent = new Intent(getActivity(), AndroidCameraApi.class);
                //AndroidCameraApi2 api = new AndroidCameraApi2();
                Intent intent = new Intent(getActivity(), AndroidCameraApi.class);
                getActivity().startActivity(intent);
                /*Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("value", 0);
                fragment = new AndroidCameraApi3();
                fragment.setArguments(args);

                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();*/
            }
        });
/*
        Button buttonLoadImage = (Button) view.findViewById(R.id.selfie);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                System.out.println(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                Uri selectedImage = i.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ImageView imageView = (ImageView) view.findViewById(R.id.imgView);

                Bitmap bmp = null;
                try {
                    bmp = getBitmapFromUri(selectedImage);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bmp);
            }
        });*/




        gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Fragment fragment = null;
                Bundle args = new Bundle();
                args.putSerializable("value", i);
                fragment = new PhotosActivity();
                fragment.setArguments(args);

                FragmentManager fm = getFragmentManager();
                //FragmentTransaction ft = fm.beginTransaction();
                fm.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("tag").commit();
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //ft.commit();




                /*Intent intent = new Intent(getContext(), PhotosActivity.class);
                intent.putExtra("value",i);
                startActivity(intent);*/
            }
        });


        if ((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            fn_imagespath();
        }


        return view;
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public ArrayList<Model_images> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        boolean_folder = false;
        Uri uri;

        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;



        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        cursor = getContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");





        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));
            System.out.println("al_images.size() :"+al_images.size());

            for (int i = 0; i < al_images.size(); i++) {
                System.out.println("al_images.get(i).getStr_folder():"+al_images.get(i).getStr_folder());
                System.out.println("cursor.getString(column_index_folder_name):"+cursor.getString(column_index_folder_name));
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    System.out.println("ONPASSEPARLA");
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    System.out.println("ONPASSEPARLA");
                    boolean_folder = false;
                }
            }


            if (boolean_folder) {








                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);

                System.out.println("INTPOSITION :" + int_position);
                System.out.println("taille de al_path :" + al_path.size());
                System.out.println("Taille de al_image :" + al_images.size());

                System.out.println("Nom du folder + sa taille :" +  al_images.get(int_position).getStr_folder() + ";    "+al_images.get(int_position).getAl_imagepath().size());
            } else {
                System.out.println("çamarche1");
                ArrayList<String> al_path = new ArrayList<>();
                System.out.println("çamarche2");
                al_path.add(absolutePathOfImage);
                System.out.println("çamarche3");
                Model_images obj_model = new Model_images();
                System.out.println("çamarche4");
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                System.out.println("çamarche5");
                obj_model.setAl_imagepath(al_path);
                System.out.println("çamarche6");

                al_images.add(obj_model);

                System.out.println("Nom du folder + sa taille :" +  al_images.get(int_position).getStr_folder() + ";    "+al_images.get(int_position).getAl_imagepath().size());

                Log.e("STR FOLDER + STRalimage",obj_model.getStr_folder() + ";"+ obj_model.getAl_imagepath().get(obj_model.getAl_imagepath().size()-1));


            }


        }
        cursor.close();


        for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }
        obj_adapter = new Adapter_PhotosFolder(getContext(),al_images);
        gv_folder.setAdapter(obj_adapter);
        return al_images;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_imagespath();
                        break;
                    } else {
                        Toast.makeText(getActivity(), "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
