package com.example.franois.gouiranlinkproject.Account;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.Object.Customer;
import com.example.franois.gouiranlinkproject.R;
import com.example.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    private Customer customer;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditAccountFragment newInstance(String param1, String param2) {
        EditAccountFragment fragment = new EditAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_edit_account, container, false);

        ImageView imageView = (ImageView) root.findViewById(R.id.profile_picture);
        new DownloadImageTask(imageView).execute(customer.getImage().getThumbnails().get(0)[2]);
        TextView textView = (TextView) root.findViewById(R.id.name);
        String name = "";
        if (customer.getName().equals("null"))
            name += "Non renseigné";
        else
            name += customer.getName();
        name += " ";
        if (customer.getSurname().equals("null"))
            name += "Non renseigné";
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
                textView.setText("Non renseigné");
                break;
        }
        textView = (TextView) root.findViewById(R.id.value_birth_date);
        if (customer.getBirthday_date().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getBirthday_date());
        textView = (TextView) root.findViewById(R.id.value_email);
        if (customer.getEmail().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getEmail());

        textView = (TextView) root.findViewById(R.id.value_phone);
        if (customer.getPhone().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getPhone());
        textView = (TextView) root.findViewById(R.id.value_mobile_phone);
        if (customer.getMobilephone().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getMobilephone());
        textView = (TextView) root.findViewById(R.id.value_country);
        if (customer.getCountry().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getCountry());
        textView = (TextView) root.findViewById(R.id.value_city);
        if (customer.getCity().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getCity());
        textView = (TextView) root.findViewById(R.id.value_post_code);
        if (customer.getPost_code().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getPost_code());


        imageView = (ImageView) root.findViewById(R.id.edit_profile_picture);
        new DownloadImageTask(imageView).execute(customer.getImage().getThumbnails().get(0)[2]);
        textView = (TextView) root.findViewById(R.id.edit_name);
        name = "";
        if (customer.getName().equals("null"))
            name += "Non renseigné";
        else
            name += customer.getName();
        name += " ";
        if (customer.getSurname().equals("null"))
            name += "Non renseigné";
        else
            name += customer.getSurname();
        textView.setText(name);

        textView = (TextView) root.findViewById(R.id.edit_value_gender);
        switch (customer.getGender()) {
            case "M":
                textView.setText("Homme");
                break;
            case "F":
                textView.setText("Femme");
                break;
            default:
                textView.setText("Non renseigné");
                break;
        }
        textView = (TextView) root.findViewById(R.id.edit_value_birth_date);
        if (customer.getBirthday_date().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getBirthday_date());
        textView = (TextView) root.findViewById(R.id.edit_value_email);
        if (customer.getEmail().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getEmail());

        textView = (TextView) root.findViewById(R.id.edit_value_phone);
        if (customer.getPhone().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getPhone());
        textView = (TextView) root.findViewById(R.id.edit_value_mobile_phone);
        if (customer.getMobilephone().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getMobilephone());
        textView = (TextView) root.findViewById(R.id.edit_value_country);
        if (customer.getCountry().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getCountry());
        textView = (TextView) root.findViewById(R.id.edit_value_city);
        if (customer.getCity().equals("null"))
            textView.setText("Non renseigné");
        else
            textView.setText(customer.getCity());
        textView = (TextView) root.findViewById(R.id.edit_value_post_code);
        if (customer.getPost_code().equals("null"))
            textView.setText("Non renseigné");
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

        return (root);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    // TODO RuntimeException for every account fragment
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
