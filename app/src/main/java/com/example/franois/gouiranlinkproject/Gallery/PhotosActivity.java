package com.example.franois.gouiranlinkproject.Gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.franois.gouiranlinkproject.AndroidCameraApi2;
import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.Object.Professional;
import com.example.franois.gouiranlinkproject.Object.Professional_Product;
import com.example.franois.gouiranlinkproject.R;

import static com.example.franois.gouiranlinkproject.ToolsClasses.BaseFragment.ARGS_INSTANCE;

/**
 * Created by deepshikha on 20/3/17.
 */

public class PhotosActivity extends Fragment {
    int int_position;
    private GridView gridView;
    GridViewAdapter adapter;

    public PhotosActivity() {
        // Required empty public constructor
    }

    /*public static PhotosActivity newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        PhotosActivity firstFragment = new PhotosActivity();
        firstFragment.setArguments(args);
        return firstFragment;
    }*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int_position = (int) getArguments().getInt("value");

        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_gallery, container, false);

        //setContentView(R.layout.activity_main);
        gridView = (GridView)view.findViewById(R.id.gv_folder);
        //int_position = getIntent().getIntExtra("value", 0);
        adapter = new GridViewAdapter(getActivity(), ActivityGallery.al_images,int_position);
        gridView.setAdapter(adapter);




        Button selfie = (Button) view.findViewById(R.id.selfie);
        selfie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //CamTestActivity cam = new CamTestActivity();
                //Intent intent = new Intent(getActivity(), AndroidCameraApi.class);
                //AndroidCameraApi2 api = new AndroidCameraApi2();
                Intent intent = new Intent(getActivity(), AndroidCameraApi2.class);
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



        return view;
    }

}
