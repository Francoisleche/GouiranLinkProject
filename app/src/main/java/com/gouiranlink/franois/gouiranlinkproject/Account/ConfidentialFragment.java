package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetCustomerProfile;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PutRequest;

import java.util.concurrent.ExecutionException;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by François on 13/07/2017.
 */

public class ConfidentialFragment extends Fragment{

    private Customer customer;
    FragmentManager fragmentManager;

    public ConfidentialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer)getArguments().getSerializable("Customer");
        }
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_confidential, container, false);

        final LinearLayout layout_confidential_choix = (LinearLayout) root.findViewById(R.id.layout_confidential_choix);
        final LinearLayout layout_confidential_mdp = (LinearLayout) root.findViewById(R.id.layout_confidential_mdp);

        TextView text_modifier_mot_de_passe = (TextView) root.findViewById(R.id.text_modifier_mot_de_passe);
        text_modifier_mot_de_passe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_confidential_mdp.setVisibility(true ? View.VISIBLE : View.GONE);
                layout_confidential_choix.setVisibility(true ? View.GONE : View.VISIBLE);
            }
        });

        Button boutton_valider_nouveau_mdp = (Button) root.findViewById(R.id.boutton_valider_nouveau_mdp);
        final EditText mot_passe_actuel = (EditText) root.findViewById(R.id.mot_passe_actuel);
        final EditText nouveau_mot_passe = (EditText) root.findViewById(R.id.nouveau_mot_passe);
        final EditText nouveau_mot_passe_verif = (EditText) root.findViewById(R.id.nouveau_mot_passe_verif);

        boutton_valider_nouveau_mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mot_passe_actuel.getText().equals("")){
                    mot_passe_actuel.setError("Renseignez votre mot de passe");
                }if(nouveau_mot_passe.getText().equals("")){
                    nouveau_mot_passe.setError("Renseignez votre mot de passe");
                }if(nouveau_mot_passe_verif.getText().equals("")){
                    nouveau_mot_passe_verif.setError("Renseignez votre mot de passe");
                }

                if(!nouveau_mot_passe.getText().toString().equals(nouveau_mot_passe_verif.getText().toString())){
                    System.out.println("Mooooootdepasse :"+nouveau_mot_passe.getText()+"/"+nouveau_mot_passe_verif.getText());
                    nouveau_mot_passe_verif.setError("Erreur votre nouveau mot de passe ne correspond pas");
                }else {


                    try {

                        String json = "{\n" +
                                "\"old_public_key\":\"" + customer.getEmail() + "\",\n" +
                                "\"old_private_key\":\"" + mot_passe_actuel.getText() + "\",\n" +
                                "\"new_public_key\":\"" + customer.getEmail() + "\",\n" +
                                "\"new_private_key\":\"" + nouveau_mot_passe.getText() + "\"" +
                                "}\n";
                        String resp;
                        PutRequest putRequest = new PutRequest("https://www.gouiran-beaute.com/link/api/v1/customer/" + customer.getId() + "/", json, "Authorization", "Token " + customer.getToken());
                        resp = putRequest.execute().get();
                        //System.out.println("Puuuuuuuuut : "+city);
                        //System.out.println("Puuuuuuuuut : "+birth_date);
                        //System.out.println("Puuuuuuuuut : "+resp);
                        Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
                        Log.d("reponse", resp);
                        customer = new GetCustomerProfile().getCustomerProfile(customer.getToken(), customer);
                        //onCreateView(inflater, container, savedInstanceState);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    mot_passe_actuel.setText("");
                    nouveau_mot_passe.setText("");
                    nouveau_mot_passe_verif.setText("");

                    layout_confidential_choix.setVisibility(true ? View.VISIBLE : View.GONE);
                    layout_confidential_mdp.setVisibility(true ? View.GONE : View.VISIBLE);
                }


            }
        });





        TextView text_cgu = (TextView) root.findViewById(R.id.text_cgu);
        text_cgu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return root;

    }

}
