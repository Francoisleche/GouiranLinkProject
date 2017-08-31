package com.gouiranlink.franois.gouiranlinkproject.Gallery;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by François on 25/06/2017.
 */

public class FragmentGallery3 extends Fragment {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("omg10");
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("omg10");
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    System.out.println("omg10");
                    /*if (imageLocation != null) {
                        ViewGroup layout = (ViewGroup) getActivity().findViewById(R.id.basic_camera_display_layout);
                        if (layout != null) {
                            layout.removeAllViews();
                            ImageView img = new ImageView(getActivity());
                            img.setImageDrawable(Drawable.createFromPath(imageLocation.getPath()));
                            layout.addView(img);
                        }
                    }*/
                }
        }
        System.out.println("omg11");
    }








    public static ArrayList<Model_images> al_images = new ArrayList<>();

    private Uri imageLocation;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    //ArrayAdapter adapter_image;

    boolean boolean_folder;
    Adapter_PhotosFolder obj_adapter;
    private GridView gv_folder = null;
    private TextView tv_folder = null;
    private static final int REQUEST_PERMISSIONS = 100;
    Cursor cursor;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    ImageAdapter adapter_image;



    //Essaie
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<String> listOfImagesPath;

    public static final String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GouiranLinkPhoto/images/";



    public FragmentGallery3() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new ActivityGallery();
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_gallery, container, false);
        gv_folder = (GridView) view.findViewById(R.id.gv_folder);
        tv_folder = (TextView) view.findViewById(R.id.textview_magallery);

        gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Fragment fragment = null;
                FragmentTransaction ft = null;
                Bundle args = new Bundle();
                args.putSerializable("Pathimage", al_images.get(0).getAl_imagepath().get(position));
                FragmentManager fm = getFragmentManager();
                //FragmentTransaction ft = fm.beginTransaction();
                ft = fm.beginTransaction();
                //getActivity().findViewById(R.id.fragment_research).setVisibility(View.GONE);

                fragment = new FragmentPhoto2();
                fragment.setArguments(args);
                ft.replace(R.id.content_frame, fragment).addToBackStack("recherche");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });





        Button selfie = (Button) view.findViewById(R.id.selfie);
        selfie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //NE MARCHE PAS
                //Intent intent = new Intent(getActivity(), AndroidCameraApi.class);
                //getActivity().startActivity(intent);


                //MARCHE A MOITIE
                //Intent intent = new Intent(getActivity(), AndroidCameraApi4.class);
                //getActivity().startActivity(intent);


                File file = null;


                boolean trouve = false;
                String trouve_Folder = "";
                String trouve_Column = "";
                int column_index_data, column_index_folder_name;
                Cursor cursor = null;
                String absolutePathOfImage = null;
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
                final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
                cursor = getActivity().getContentResolver().query(uri, projection, null, null, orderBy + " ASC");
                column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                while (cursor.moveToNext()) {
                    absolutePathOfImage = cursor.getString(column_index_data);
                    Log.e("Column", absolutePathOfImage);
                    Log.e("Folder", cursor.getString(column_index_folder_name));
                    if (absolutePathOfImage.contains(Environment.getExternalStorageDirectory() + "/GouiranLinkPhoto/images/")) {
                        System.out.println("TROUVER : " + cursor.getString(column_index_folder_name));


                        trouve = true;
                        trouve_Folder = cursor.getString(column_index_folder_name);
                        trouve_Column = absolutePathOfImage;
                    }
                }
                cursor.close();


                if (trouve) {
                    String str = trouve_Column.replace(Environment.getExternalStorageDirectory() + "/GouiranLinkPhoto/images/image", "");
                    String s = str.replace(".jpg", "");
                    //System.out.println("SSSSSSSSSSSSSSSSS"+ s);
                    int nombre = Integer.parseInt(s);


                    //final String appDirectoryName = "/GouiranLinkPhoto/images";
                    String imageRoot = Environment.getExternalStorageDirectory() + "/GouiranLinkPhoto/images/";
                    file = new File(imageRoot, "/image" + (nombre + 1) + ".jpg");
                    //System.out.println("Nom du fichier :" + file.getAbsolutePath());

                } else {
                    //si le dossier GouiranLinkPhoto/images exist
                    //si le fichier n'existe pas tu arrives ici.
                    System.out.println("je passe 1");

                    //System.out.println("PasTrouuuuuuuuuuuuuuve");
                    final String appDirectoryName = "GouiranLinkPhoto";
                    //final File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
                    //Environment.DIRECTORY_PICTURES), appDirectoryName);
                    //System.out.println("PasTrouuuuuuuuuuuuuuve" + imageRoot.getAbsolutePath());
                    File imageRoot = new File(Environment.getExternalStorageDirectory() + "/GouiranLinkPhoto/images/");
                    boolean success = false;
                    if (!imageRoot.exists()) {
                        Log.d("", "EXISTE PAS");
                        success = imageRoot.mkdirs();
                    }
                    if (!success) {
                        Log.d("", "Folder not created.");
                    } else {
                        Log.d("", "Folder created!");
                    }


                    //IMAGE avec date
                    //String fname = "img_"+ System.currentTimeMillis() + ".jpg";

                    //Image sans date
                    file = new File(imageRoot, "image1.jpg");

                }






                //Uri imageUri = Uri.fromFile(file);


                //String path = file.getAbsolutePath();
                //System.out.println("PATH photo:" + path);
                String path = Environment.getExternalStorageDirectory() + "/GouiranLinkPhoto/images/photo1.jpg";
                System.out.println("PATH photo:" + path);


                Uri uriSavedImage = Uri.fromFile(new File(path));
                System.out.println("omg1");
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                Intent cameraIntent2 =  new Intent(getActivity(), SecondActivity.class);
                System.out.println("omg2");
                cameraIntent.putExtra("return-data", true);
                System.out.println("omg3");
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                System.out.println("omg4");
                //startActivityForResult(cameraIntent,1);
                //getActivity().startActivityForResult(cameraIntent2,1);
                //cameraIntent2.putExtra("adapter", adapter_image);

                startActivity(cameraIntent2);


                System.out.println("omg5");
                /*getActivity().sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri
                        .parse(path)));*/
                System.out.println("omg6");
                fn_imagespath();
                adapter_image.notifyDataSetChanged();
            }
        });



        if ((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)){
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA))) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            fn_imagespath();
        }


        return view;
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



    public ArrayList<Model_images> fn_imagespath() {
        System.out.println("omg7");
        System.out.println("ildevrait passer par la2");
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

            if(absolutePathOfImage.contains("GouiranLinkPhoto/images/")) {

                for (int i = 0; i < al_images.size(); i++) {
                    System.out.println("al_images.get(i).getStr_folder():" + al_images.get(i).getStr_folder());
                    System.out.println("cursor.getString(column_index_folder_name):" + cursor.getString(column_index_folder_name));
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

                    System.out.println("Nom du folder + sa taille :" + al_images.get(int_position).getStr_folder() + ";    " + al_images.get(int_position).getAl_imagepath().size());
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

                    System.out.println("Nom du folder + sa taille :" + al_images.get(int_position).getStr_folder() + ";    " + al_images.get(int_position).getAl_imagepath().size());

                    Log.e("STR FOLDER + STRalimage", obj_model.getStr_folder() + ";" + obj_model.getAl_imagepath().get(obj_model.getAl_imagepath().size() - 1));


                }

            }

        }
        cursor.close();


        for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }
        //obj_adapter = new Adapter_PhotosFolder(getContext(),al_images);
        //gv_folder.setAdapter(obj_adapter);



        /*if(al_images.size()==0){
            tv_folder.setVisibility(true ? View.VISIBLE : View.GONE);
            gv_folder.setVisibility(true ? View.GONE : View.VISIBLE);
        }else{
            gv_folder.setVisibility(true ? View.VISIBLE : View.GONE);
            tv_folder.setVisibility(true ? View.GONE : View.VISIBLE);
            ImageAdapter adapter_image = new ImageAdapter(getContext(),al_images.get(0).getAl_imagepath());
            //adapter_image = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,al_images.get(0).getAl_imagepath());
            gv_folder.setAdapter(adapter_image);
        }*/

        if(al_images.size()==0){
            tv_folder.setVisibility(true ? View.VISIBLE : View.GONE);
            gv_folder.setVisibility(true ? View.GONE : View.VISIBLE);
        }else{
            gv_folder.setVisibility(true ? View.VISIBLE : View.GONE);
            tv_folder.setVisibility(true ? View.GONE : View.VISIBLE);
            //ImageAdapter adapter_image = new ImageAdapter(getContext(),al_images.get(0).getAl_imagepath());
            adapter_image = new ImageAdapter(getContext(),al_images.get(0).getAl_imagepath());
            //adapter_image = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,al_images.get(0).getAl_imagepath());
            gv_folder.setAdapter(adapter_image);
        }
        adapter_image.notifyDataSetChanged();
        return al_images;
    }








    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            //mimageView.setImageBitmap(mphoto);
        }
    }*/



    //ICIIIIIIIIIIIIIIIIIIII
    //A TESTER, ça devrait être bon
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("omg8");
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            System.out.println("omg9");
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            adapter_image.notifyDataSetChanged();

        }
        adapter_image.notifyDataSetChanged();
    }*/













    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Jenecomprendpas");
//user is returning from capturing an image using the camera
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                System.out.println("Jenecomprendpas2");
                Bundle extras = data.getExtras();
                //Bitmap thePic = extras.getParcelable("data");
                Bitmap thePic = (Bitmap) extras.get("data");
                System.out.println("oooooooooooh");
                String imgcurTime = dateFormat.format(new Date());
                File imageDirectory = new File(GridViewDemo_ImagePath);
                imageDirectory.mkdirs();
                String _path = GridViewDemo_ImagePath + imgcurTime+".jpg";
                try {
                    FileOutputStream out = new FileOutputStream(_path);
                    thePic.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.close();
                } catch (FileNotFoundException e) {
                    e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listOfImagesPath = null;
                listOfImagesPath = RetriveCapturedImagePath();
                if(listOfImagesPath!=null){
                    gv_folder.setAdapter(new ImageListAdapter(getActivity(),listOfImagesPath));
                }
            }
    }



    private List<String> RetriveCapturedImagePath() {
        List<String> tFileList = new ArrayList<String>();
        File f = new File(GridViewDemo_ImagePath);
        if (f.exists()) {
            File[] files=f.listFiles();
            Arrays.sort(files);

            for(int i=0; i<files.length; i++){
                File file = files[i];
                if(file.isDirectory())
                    continue;
                tFileList.add(file.getPath());
            }
        }
        return tFileList;
    }*/



    public class ImageListAdapter extends BaseAdapter
    {
        private Context context;
        private List<String> imgPic;
        public ImageListAdapter(Context c, List<String> thePic)
        {
            context = c;
            imgPic = thePic;
        }
        public int getCount() {
            if(imgPic != null)
                return imgPic.size();
            else
                return 0;
        }

        //---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            BitmapFactory.Options bfOptions=new BitmapFactory.Options();
            bfOptions.inDither=false;                     //Disable Dithering mode
            bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
            bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
            bfOptions.inTempStorage=new byte[32 * 1024];
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setPadding(0, 0, 0, 0);
            } else {
                imageView = (ImageView) convertView;
            }
            FileInputStream fs = null;
            Bitmap bm;
            try {
                fs = new FileInputStream(new File(imgPic.get(position).toString()));

                if(fs!=null) {
                    bm=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
                    imageView.setImageBitmap(bm);
                    imageView.setId(position);
                    imageView.setLayoutParams(new GridView.LayoutParams(200, 160));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(fs!=null) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return imageView;
        }
    }





}

