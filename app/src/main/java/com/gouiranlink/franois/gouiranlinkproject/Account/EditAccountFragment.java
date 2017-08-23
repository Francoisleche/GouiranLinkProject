package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetCustomerProfile;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PutRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

    String name2;
    String name;
    String surname;
    String birth_date;
    String email;
    String phone;
    String mobile_phone;
    String country;
    String city;
    String post_code;
    String address;
    String profession = "Profession";
    String[] gender;

    String json_profession;

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
        final View root = inflater.inflate(R.layout.fragment_edit_account2, container, false);


        final Spinner spinnerprofession = (Spinner) root.findViewById(R.id.spinnerprofession);
        ArrayList<String> liste_profession = new ArrayList<>();
        liste_profession.add("Chef d'entreprise");
        liste_profession.add("Profession libérale");
        liste_profession.add("Salarié(e) du privé ou du public");
        liste_profession.add("Etudiant");
        liste_profession.add("Retraité");
        liste_profession.add("Autre");
        ArrayAdapter<String> adapter_services = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,liste_profession);
        adapter_services.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerprofession.setAdapter(adapter_services);

        final EditText editText2 = (EditText) root.findViewById(R.id.edit_value_profession);
        final TextView editText3 = (TextView) root.findViewById(R.id.edit_value_profession2);
        //final String pro = spinnerprofession.getSelectedItem().toString();
        spinnerprofession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String msupplier=spinnerprofession.getSelectedItem().toString();
                //Log.e("Selected item : ",msupplier);
                //System.out.println("Ooooooooooooh"+spinnerprofession.getSelectedItem().toString());
                if(msupplier.equals("Autre")){
                    editText2.setVisibility(true ? View.VISIBLE : View.GONE);
                    editText3.setVisibility(true ? View.VISIBLE : View.GONE);
                }else{
                    editText2.setVisibility(true ? View.GONE : View.VISIBLE);
                    editText3.setVisibility(true ? View.GONE : View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });








        customer = new GetCustomerProfile().getCustomerProfile(customer.getToken());
        //ImageView imageView = (ImageView) root.findViewById(R.id.profile_picture);
        //new DownloadImageTask(imageView).execute(customer.getImage().getThumbnails().get(0)[2]);
        //Picasso.with(root.getContext()).load(customer.getImage().getThumbnails().get(0)[2]).into(imageView);



        TextView textView_name = (TextView) root.findViewById(R.id.name);
        String name2 = "";
        if (customer.getSurname().equals("null"))
            name2 += getString(R.string.not_known);
        else
            name2 += customer.getSurname();
        name2 += " ";
        if (customer.getName().equals("null"))
            name2 += getString(R.string.not_known);
        else
            name2 += customer.getName();

        textView_name.setText(name2);

        /*TextView textView = (TextView) root.findViewById(R.id.value_gender);
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


        TextView textView = (TextView) root.findViewById(R.id.value_birth_date);
        if (customer.getBirthday_date().equals("null")){

        }

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

        */

        ImageView imageView = (ImageView) root.findViewById(R.id.edit_profile_picture);
        //new DownloadImageTask(imageView).execute(customer.getImage().getThumbnails().get(0)[2]);
        Picasso.with(root.getContext()).load(customer.getImage().getThumbnails().get(0)[2]).into(imageView);
        /*TextView textView = (TextView) root.findViewById(R.id.edit_name);
        String name = "";
        if (customer.getSurname().equals("null"))
            name += getString(R.string.not_known);
        else
            name += customer.getSurname();
        name += " ";
        if (customer.getName().equals("null"))
            name += getString(R.string.not_known);
        else
            name += customer.getName();
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
    */


        /*Button modify = (Button) root.findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.findViewById(R.id.scrollViewEdit).setVisibility(View.GONE);
                root.findViewById(R.id.edit_profile_action).setVisibility(View.VISIBLE);
            }
        });*/


        //Bouton annuler les modification
       /*Button backEdit = (Button) root.findViewById(R.id.edit_modify);
       backEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.findViewById(R.id.scrollViewEdit).setVisibility(View.VISIBLE);
                root.findViewById(R.id.edit_profile_action).setVisibility(View.GONE);
            }
        });*/

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
        EditText editText_yyyy = (EditText) root.findViewById(R.id.edit_value_birth_date);
        EditText editText2_mm = (EditText) root.findViewById(R.id.edit_value_birth_date2);
        EditText editText3_dd = (EditText) root.findViewById(R.id.edit_value_birth_date3);
        //editText.setHint(((TextView) root.findViewById(R.id.value_birth_date)).getText());
        birth_date = editText_yyyy.getText().toString()+"/"+editText2_mm.getText().toString()+"/"+editText3_dd.getText().toString();

        System.out.println("Birthdayyyyyy : "+ customer.getBirthday_date());

        if(customer.getBirthday_date().equals("null")){

        }else{
            editText_yyyy.setHint(customer.getBirthday_date().substring(0,4));
            editText2_mm.setHint(customer.getBirthday_date().substring(5,7));
            editText3_dd.setHint(customer.getBirthday_date().substring(8,10));
            //editText_yyyy.setText(customer.getBirthday_date().substring(0,4));
            //editText2_mm.setText(customer.getBirthday_date().substring(5,7));
            //editText3_dd.setText(customer.getBirthday_date().substring(8,10));
        }


        EditText editText_name = (EditText) root.findViewById(R.id.edit_name2);
        if(customer.getName().equals("null"))
            editText_name.setHint("");
        else
            editText_name.setHint(customer.getName());
        name = editText_name.getText().toString();
        EditText editText_surname = (EditText) root.findViewById(R.id.edit_surname2);
        if(customer.getSurname().equals("null"))
            editText_surname.setHint("");
        else
            editText_surname.setHint(customer.getSurname());
        surname = editText_surname.getText().toString();


        EditText editText_email = (EditText) root.findViewById(R.id.edit_value_email);
        if(customer.getEmail().equals("null"))
            editText_email.setHint("");
        else
            editText_email.setHint(customer.getEmail());
        email = editText_email.getText().toString();

        EditText editText_phone = (EditText) root.findViewById(R.id.edit_value_phone);
        if(customer.getPhone().equals("null"))
            editText_phone.setHint("");
        else
            editText_phone.setHint(customer.getPhone());
        phone = editText_phone.getText().toString();

        EditText editText_mobilephone = (EditText) root.findViewById(R.id.edit_value_mobile_phone);
        if(customer.getMobilephone().equals("null"))
            editText_mobilephone.setHint("");
        else
            editText_mobilephone.setHint(customer.getMobilephone());
        mobile_phone = editText_mobilephone.getText().toString();

        EditText editText_country = (EditText) root.findViewById(R.id.edit_value_country);
        if(customer.getCountry().equals("null"))
            editText_country.setHint("");
        else
            editText_country.setHint(customer.getCountry());
        country = editText_country.getText().toString();

        EditText editText_city = (EditText) root.findViewById(R.id.edit_value_city);
        if(customer.getCity().equals("null"))
            editText_city.setHint("");
        else
            editText_city.setHint(customer.getCity());
        city = editText_city.getText().toString();

        EditText editText_postcode = (EditText) root.findViewById(R.id.edit_value_post_code);
        if(customer.getPost_code().equals("null"))
            editText_postcode.setHint("");
        else
            editText_postcode.setHint(customer.getPost_code());
        post_code = editText_postcode.getText().toString();

        EditText editText_address = (EditText) root.findViewById(R.id.edit_value_address);
        if(customer.getAddress().equals("null"))
            editText_address.setHint("");
        else
            editText_address.setHint(customer.getAddress());
        address = editText_address.getText().toString();



        json_profession = "{\n" +
                "\"id\":";
        System.out.println("Prooooooooofessionnnnnn : "+customer.getProfession());
        if(customer.getProfession() == null){
            spinnerprofession.setSelection(5);
            editText2.setVisibility(true ? View.VISIBLE : View.GONE);
            editText3.setVisibility(true ? View.VISIBLE : View.GONE);
            editText2.setText("");
            json_profession += "6," +  "\"name\":\"" + "Autre \" }";
        }else {
            if (customer.getProfession().getName().equals("Chef d'entreprise")) {
                json_profession += "1," + "\"name\":\"" + "Chef d'entreprise \" }";
                spinnerprofession.setSelection(0);
            }
            else if (customer.getProfession().getName().equals("Profession libérale")) {
                json_profession += "2," + "\"name\":\"" + "Profession libérale \" }";
                spinnerprofession.setSelection(1);
            }
            else if (customer.getProfession().getName().equals("Salarié(e) du privé ou du public")) {
                json_profession += "3," + "\"name\":\"" + "Salarié(e) du privé ou du public \" }";
                spinnerprofession.setSelection(2);
            }
            else if (customer.getProfession().getName().equals("Etudiant")) {
                json_profession += "4," + "\"name\":\"" + "Etudiant \" }";
                spinnerprofession.setSelection(3);
            }
            else if (customer.getProfession().getName().equals("Retraité")){
                json_profession += "5," + "\"name\":\"" + "Retraité \" }";
                spinnerprofession.setSelection(4);
            }
            else if (customer.getProfession().getName().equals("Autre")) {
                json_profession += "6," + "\"name\":\"" + "Autre\" }";
                spinnerprofession.setSelection(5);
                editText2.setVisibility(true ? View.VISIBLE : View.GONE);
                editText3.setVisibility(true ? View.VISIBLE : View.GONE);
                if(customer.getProfession_other().equals("null")){
                    editText2.setText("");
                }else
                    editText2.setText(customer.getProfession_other());
            } else
                Toast.makeText(getActivity(), "GENDER=[" + customer.getProfession().getName() + "]", Toast.LENGTH_SHORT).show();
        }


        //editText = (EditText) root.findViewById(R.id.edit_value_profession);
        //editText.setHint(((TextView) root.findViewById(R.id.value_profession)).getText());
        //profession = editText.getText().toString();

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

                    //Date d'anniversaire
                    EditText editText = (EditText) root.findViewById(R.id.edit_value_birth_date);
                    EditText editText2 = (EditText) root.findViewById(R.id.edit_value_birth_date2);
                    EditText editText3 = (EditText) root.findViewById(R.id.edit_value_birth_date3);
                    birth_date = editText.getText().toString()+"-"+editText2.getText().toString()+"-"+editText3.getText().toString();
                    if (birth_date.isEmpty() || birth_date.equals("--") || birth_date == null){
                        birth_date = customer.getBirthday_date();
                    }

                    //Ville
                    editText = (EditText) root.findViewById(R.id.edit_value_city);
                    city = editText.getText().toString();
                    if (city.isEmpty() || city.equals("") || city == null)
                        city = customer.getCity();


                    //PostCode
                    editText = (EditText) root.findViewById(R.id.edit_value_post_code);
                    post_code = editText.getText().toString();
                    if (post_code.isEmpty() || post_code.equals("") || post_code == null)
                        post_code = customer.getPost_code();





                    Toast.makeText(getActivity(), "3- gender = [" + gender[0] + "]", Toast.LENGTH_SHORT).show();
                    if (gender != null && gender[0].equals("Homme"))
                        gender[0] = "M";
                    else if (gender != null && gender[0].equals("Femme"))
                        gender[0] = "F";

                    editText2 = (EditText) root.findViewById(R.id.edit_value_profession);
                    System.out.println("profession other : " +editText2.getText());
                    if(!editText2.getText().equals("")){
                        json_profession = json_profession + ",\"profession_other\" : \""+editText2.getText()+"\"";
                    }

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
                            "\"profession\":" + json_profession + ",\n" +
                            "\"post_code\":\"" + post_code + "\"\n" +
                            "}\n";
                    System.out.println("Puuuuuuuuut : "+json);
                    String resp;
                    PutRequest putRequest = new PutRequest("https://www.gouiran-beaute.com/link/api/v1/customer/" + customer.getId() + "/", json, "Authorization", "Token " + customer.getToken());
                    resp = putRequest.execute().get();
                    System.out.println("Puuuuuuuuut : "+city);
                    System.out.println("Puuuuuuuuut : "+birth_date);
                    System.out.println("Puuuuuuuuut : "+resp);
                    Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
                    Log.d("reponse", resp);
                    customer = new GetCustomerProfile().getCustomerProfile(customer.getToken(), customer);
                    onCreateView(inflater, container, savedInstanceState);

                    //Fragment
                    Fragment fragment = null;
                    Bundle args = new Bundle();
                    args.putSerializable("Customer", customer);
                    fragment = new AccountFragment();
                    fragment.setArguments(args);
                    FragmentManager frgManager = getFragmentManager();
                    frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();


                    getActivity().getSupportFragmentManager().beginTransaction().remove(EditAccountFragment.this).commit();

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

            //Fonctionne mais je dois enlever
            /*PostFileRequest postFileRequest = new PostFileRequest("https://www.gouiran-beaute.com/link/api/v1/customer/upload/image/customer/" + customer.getId() + "/", String.valueOf(customer.getId()), getRealPathFromURI(uri), "Authorization", "Token " + customer.getToken());
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
            }*/
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
