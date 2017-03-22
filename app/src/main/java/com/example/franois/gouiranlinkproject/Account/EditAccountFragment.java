package com.example.franois.gouiranlinkproject.Account;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    String birth_date;
    String email;
    String phone;
    String mobile_phone;
    String country;
    String city;
    String post_code;
    String address;
    String profession;
    String[] gender;

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
        gender = new String[1];
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

        Spinner spinner = (Spinner) root.findViewById(R.id.gender_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (customer.getGender().equals("F")) {
            spinner.setSelection(1);
        }
        else if (customer.getGender().equals("M"))
            spinner.setSelection(0);
        else
            Toast.makeText(getActivity(), "GENDER=[" + customer.getGender() + "]", Toast.LENGTH_SHORT).show();
        /*EditText editText = (EditText) root.findViewById(R.id.edit_value_gender);
        editText.setHint(((TextView) root.findViewById(R.id.value_gender)).getText());

        final String gender = editText.getText().toString();*/
        EditText editText = (EditText) root.findViewById(R.id.edit_value_birth_date);
        editText.setHint(((TextView) root.findViewById(R.id.value_birth_date)).getText());
        birth_date = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_email);
        editText.setHint(((TextView) root.findViewById(R.id.value_email)).getText());
        email = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_phone);
        editText.setHint(((TextView) root.findViewById(R.id.value_phone)).getText());
        phone = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_mobile_phone);
        editText.setHint(((TextView) root.findViewById(R.id.value_mobile_phone)).getText());
        mobile_phone = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_country);
        editText.setHint(((TextView) root.findViewById(R.id.value_country)).getText());
        country = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_city);
        editText.setHint(((TextView) root.findViewById(R.id.value_city)).getText());
        city = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_post_code);
        editText.setHint(((TextView) root.findViewById(R.id.value_post_code)).getText());
        post_code = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_address);
        editText.setHint(((TextView) root.findViewById(R.id.value_address)).getText());
        address = editText.getText().toString();
        editText = (EditText) root.findViewById(R.id.edit_value_profession);
        editText.setHint(((TextView) root.findViewById(R.id.value_profession)).getText());
        profession = editText.getText().toString();

        if (birth_date.isEmpty() || birth_date.equals("") || birth_date == null)
            birth_date = customer.getBirthday_date();
        if (phone.isEmpty() || phone.equals("") || phone == null)
            phone = customer.getPhone();
        if (mobile_phone.isEmpty() || mobile_phone.equals("") || mobile_phone == null)
            mobile_phone = customer.getMobilephone();
        if (country.isEmpty() || country.equals("") || country == null)
            country = customer.getCountry();
        if (city.isEmpty() || city.equals("") || city == null)
            city = customer.getCity();
        if (address.isEmpty() || address.equals("") || address == null)
            address = customer.getAddress();
        if (profession.isEmpty() || profession.equals("") || profession == null)
            Toast.makeText(getActivity(), profession, Toast.LENGTH_SHORT).show();
            //profession = customer.getProfession().getName();
        if (post_code.isEmpty() || post_code.equals("") || post_code == null)
            post_code = customer.getPost_code();
        if (email.isEmpty() || email.equals("") || email == null)
            email = customer.getEmail();
        Toast.makeText(getActivity(), "1- gender = [" + gender[0] + "]", Toast.LENGTH_SHORT).show();
        if (gender[0] == null || gender[0].isEmpty() || gender[0].equals(""))
            gender[0] = customer.getGender();
        Toast.makeText(getActivity(), "2- gender = [" + gender[0] + "]", Toast.LENGTH_SHORT).show();
        Button confirm = (Button) root.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getActivity(), "3- gender = [" + gender[0] + "]", Toast.LENGTH_SHORT).show();
                    if (gender != null && gender[0].equals("Homme"))
                        gender[0] = "M";
                    else if (gender != null && gender[0].equals("Femme"))
                        gender[0] = "F";

                    String json = "{\n" +
                            "\"email\":\"" + email + "\",\n" +
                            "\"name\":\"" + customer.getName() + "\",\n" +
                            "\"surname\":\"" + customer.getSurname() + "\",\n" +
                            "\"birthday_date\":\"" + birth_date + "\",\n" +
                            "\"phone\":\"" + phone + "\",\n" +
                            "\"gender\":\"" + gender[0] + "\",\n" +
                            "\"mobilephone\":\"" + mobile_phone + "\",\n" +
                            "\"country\":\"" + country + "\",\n" +
                            "\"city\":\"" + city + "\",\n" +
                            "\"address\":\"" + address + "\",\n" +
                            //TODO "\"profession\":\"" + profession + "\",\n" +
                            "\"post_code\":\"" + post_code + "\"\n" +
                            "}\n";
                    String resp;
                    PutRequest putRequest = new PutRequest("https://www.gouiran-beaute.com/link/api/v1/customer/" + customer.getId() + "/", json, "Authorization", "Token " + customer.getToken());
                    resp = putRequest.execute().get();
                    Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();
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
