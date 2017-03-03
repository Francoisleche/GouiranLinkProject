package com.example.franois.gouiranlinkproject.Account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;
import com.example.franois.gouiranlinkproject.ToolsClasses.GetCustomerProfile;
import com.example.franois.gouiranlinkproject.ToolsClasses.PostFileRequest;
import com.example.franois.gouiranlinkproject.ToolsClasses.PostRequest;
import com.example.franois.gouiranlinkproject.ToolsClasses.PutRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static com.facebook.FacebookSdk.getApplicationContext;

/*
Fragment which contains the editable part of the account (through http request)
 */

public class EditAccountFragment extends Fragment {
    FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    static final int PICK_IMAGE_REQUEST = 1;  // The request code

    private Customer customer;


    private OnFragmentInteractionListener mListener;

    public EditAccountFragment() {
        // Required empty public constructor
    }

    public static EditAccountFragment newInstance() {
        EditAccountFragment fragment = new EditAccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
        }
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_edit_account, container, false);

        customer = new GetCustomerProfile().getCustomerProfile(customer.getToken());
        ImageView imageView = (ImageView) root.findViewById(R.id.profile_picture);
        new DownloadImageTask(imageView).execute(customer.getImage().getThumbnails().get(0)[2]);
        TextView textView = (TextView) root.findViewById(R.id.name);
        String name = "";
        if (customer.getName().equals("null"))
            name += getString(R.string.not_known);
        else
            name += customer.getName();
        name += " ";
        if (customer.getSurname().equals("null"))
            name += getString(R.string.not_known);
        else
            name += customer.getSurname();
        textView.setText(name);

        textView = (TextView) root.findViewById(R.id.value_gender);
        switch (customer.getGender()) {
            case "M":
                textView.setText("Homme");
                break;
            case "F":
                textView.setText("Femme");
                break;
            default:
                textView.setText(getString(R.string.not_known));
                break;
        }
        textView = (TextView) root.findViewById(R.id.value_birth_date);
        if (customer.getBirthday_date().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getBirthday_date());
        textView = (TextView) root.findViewById(R.id.value_email);
        if (customer.getEmail().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getEmail());

        textView = (TextView) root.findViewById(R.id.value_phone);
        if (customer.getPhone().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getPhone());
        textView = (TextView) root.findViewById(R.id.value_mobile_phone);
        if (customer.getMobilephone().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getMobilephone());
        textView = (TextView) root.findViewById(R.id.value_country);
        if (customer.getCountry().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getCountry());
        textView = (TextView) root.findViewById(R.id.value_city);
        if (customer.getCity().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getCity());
        textView = (TextView) root.findViewById(R.id.value_post_code);
        if (customer.getPost_code().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getPost_code());


        imageView = (ImageView) root.findViewById(R.id.edit_profile_picture);
        new DownloadImageTask(imageView).execute(customer.getImage().getThumbnails().get(0)[2]);
        textView = (TextView) root.findViewById(R.id.edit_name);
        name = "";
        if (customer.getName().equals("null"))
            name += getString(R.string.not_known);
        else
            name += customer.getName();
        name += " ";
        if (customer.getSurname().equals("null"))
            name += getString(R.string.not_known);
        else
            name += customer.getSurname();
        textView.setText(name);

        textView = (TextView) root.findViewById(R.id.value_gender);
        switch (customer.getGender()) {
            case "M":
                textView.setText(R.string.male);
                break;
            case "F":
                textView.setText(R.string.female);
                break;
            default:
                textView.setText(getString(R.string.not_known));
                break;
        }
        textView = (TextView) root.findViewById(R.id.value_birth_date);
        if (customer.getBirthday_date().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getBirthday_date());
        textView = (TextView) root.findViewById(R.id.value_email);
        if (customer.getEmail().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getEmail());

        textView = (TextView) root.findViewById(R.id.value_phone);
        if (customer.getPhone().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getPhone());
        textView = (TextView) root.findViewById(R.id.value_mobile_phone);
        if (customer.getMobilephone().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getMobilephone());
        textView = (TextView) root.findViewById(R.id.value_country);
        if (customer.getCountry().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getCountry());
        textView = (TextView) root.findViewById(R.id.value_city);
        if (customer.getCity().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getCity());
        textView = (TextView) root.findViewById(R.id.value_post_code);
        if (customer.getPost_code().equals("null"))
            textView.setText(getString(R.string.not_known));
        else
            textView.setText(customer.getPost_code());


        Button modify = (Button) root.findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.findViewById(R.id.scrollViewEdit).setVisibility(View.GONE);
                root.findViewById(R.id.edit_profile_action).setVisibility(View.VISIBLE);
            }
        });

        Button backEdit = (Button) root.findViewById(R.id.edit_modify);
        backEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.findViewById(R.id.scrollViewEdit).setVisibility(View.VISIBLE);
                root.findViewById(R.id.edit_profile_action).setVisibility(View.GONE);
            }
        });

        Button editImage = (Button) root.findViewById(R.id.edit_image);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        EditText editText = (EditText) root.findViewById(R.id.edit_value_gender);
        final String gender = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_birth_date);
        final String birth_date = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_email);
        String email = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_phone);
        final String phone = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_mobile_phone);
        final String mobile_phone = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_country);
        final String country = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_city);
        final String city = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_post_code);
        final String post_code = editText.getText().toString();

        Button confirm = (Button) root.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String json = "{\n" +
                            "\"email\":\"" + customer.getEmail() + "\",\n" +
                            "\"name\":\"" + customer.getName() + "\",\n" +
                            "\"surname\":\"" + customer.getSurname() + "\",\n" +
                            "\"gender\":\"" + gender + "\",\n" +
                            "\"birthday_date\":\"" + birth_date + "\",\n" +
                            "\"phone\":\"" + phone + "\",\n" +
                            "\"mobilephone\":\"" + mobile_phone + "\",\n" +
                            "\"country\":\"" + country + "\",\n" +
                            "\"city\":\"" + city + "\",\n" +
                            "\"post_code\":\"" + post_code + "\"\n" +
                            "}\n";
                    String resp;
                    PutRequest putRequest = new PutRequest("https://www.gouiran-beaute.com/link/api/v1/customer/" + customer.getId() + "/", json, "Authorization", "Token " + customer.getToken());
                    resp = putRequest.execute().get();
                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
                    Log.d("reponse", resp);
                    customer = new GetCustomerProfile().getCustomerProfile(customer.getToken(), customer);
                    onCreateView(inflater, container, savedInstanceState);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });


        return (root);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            uri = data.getData();
            PostFileRequest postFileRequest = new PostFileRequest("https://www.gouiran-beaute.com/link/api/v1/customer/upload/image/customer/" + customer.getId() + "/", String.valueOf(customer.getId()), getRealPathFromURI(uri), "Authorization", "Token " + customer.getToken());
            String resp = null;
            try {
                resp = postFileRequest.execute().get();
                Toast.makeText(getActivity(), resp, Toast.LENGTH_SHORT).show();
                Fragment newFragment = this;
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .detach(newFragment)
                        .attach(newFragment)
                        .commit();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
