package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.franois.gouiranlinkproject.BaseFragment.ARGS_INSTANCE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView welcomeUser;
    String username = "XXXXXXXXXX";
    String text;
    String[] recentResearches;
    TextView[] recentResearchesText;
    String[] gouiranLinkSelection;
    TextView[] gouiranLinkSelectionText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int instance, String username) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString("username", username);
        HomeFragment firstFragment = new HomeFragment();
        firstFragment.setArguments(args);
        return firstFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            username = getArguments().getString("username");
        }
        //Intent i = new Intent(getActivity(), MainHomePage.class);
        //startActivity(i);

    }

    private void getGouiranLinkSelection() {
        gouiranLinkSelection = new String[9];

        gouiranLinkSelection[0] = "Kawasaki Ninja H2R";
        gouiranLinkSelection[1] = "Suzuki GSXR 1000";
        gouiranLinkSelection[2] = "Yamaha R6";
        gouiranLinkSelection[3] = "Yamaha R1";
        gouiranLinkSelection[4] = "Yamaha MT-10";
        gouiranLinkSelection[5] = "Ducati 996";
        gouiranLinkSelection[6] = "KTM Super Duke 1290";
        gouiranLinkSelection[7] = "BMW S1000 RR";
        gouiranLinkSelection[8] = "Ducati Multistrada";
    }

    private void getRecentResearches() {
        recentResearches = new String[9];

        recentResearches[0] = "Massage suédois";
        recentResearches[1] = "Massage pierres chaudes";
        recentResearches[2] = "Massage relaxant";
        recentResearches[3] = "Massage ayurvédique";
        recentResearches[4] = "Massage du corps aux huiles essentielles";
        recentResearches[5] = "Massage aux choix en duo";
        recentResearches[6] = "Massage californien";
        recentResearches[7] = "Séance de Reiki";
        recentResearches[8] = "Massage ayurvédique aux huiles chaudes";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Resources res = getResources();
        getActivity().getAssets();
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");
        TextView textView;
        textView = new TextView(getActivity());

        /* Editing username */
        //username = "XXXXXXXXXX";
        text = String.format(res.getString(R.string.welcome_user), username);
        welcomeUser = new TextView(getActivity());
        welcomeUser = (TextView)getActivity().findViewById(R.id.welcome_user);
        welcomeUser.setText(text);
        welcomeUser.setTypeface(font);
        welcomeUser.setTextColor(res.getColor(R.color.GouiranDarkBlue));

        /* Editing recent researches */
        textView = (TextView)getActivity().findViewById(R.id.my_recent_researches);
        textView.setTypeface(font);
        getRecentResearches();
        recentResearchesText = new TextView[9];
        for (int i = 0; i < 9; i++)
            recentResearchesText[i] = new TextView(getActivity());

        for (int i = 0; i < 9; i++) {
            String researchId = "previous_" + (i + 1);
            String researchString = "research_" + (i + 1);
            int resId = res.getIdentifier(researchId, "id", getActivity().getPackageName());
            int resString = res.getIdentifier(researchString, "string", getActivity().getPackageName());
            recentResearchesText[i] = (TextView)getActivity().findViewById(resId);
            recentResearchesText[i].setText(String.format(res.getString(resString), recentResearches[i]));
            recentResearchesText[i].setTypeface(font);
            recentResearchesText[i].setTextColor(res.getColor(R.color.GouiranDarkBlue));
        }

        /* Around Me */
        textView = (TextView)getActivity().findViewById(R.id.around_me);
        textView.setTypeface(font);
        for (int i = 0; i < 5; i++) {
            String around = "around_" + (i + 1) + "_text";
            int resAround = res.getIdentifier(around, "id", getActivity().getPackageName());
            textView = (TextView)getActivity().findViewById(resAround);
            textView.setTypeface(font);
        }

        /* Editing Gouiran Link selections */
        textView = (TextView)getActivity().findViewById(R.id.gouiran_link_selection);
        textView.setTypeface(font);
        getGouiranLinkSelection();
        gouiranLinkSelectionText = new TextView[9];
        for (int i = 0; i < 9; i++)
            gouiranLinkSelectionText[i] = new TextView(getActivity());

        for (int i = 0; i < 9; i++) {
            String researchId = "selection_" + (i + 1);
            String researchString = "research_" + (i + 1);
            int resId = res.getIdentifier(researchId, "id", getActivity().getPackageName());
            int resString = res.getIdentifier(researchString, "string", getActivity().getPackageName());
            gouiranLinkSelectionText[i] = (TextView)getActivity().findViewById(resId);
            gouiranLinkSelectionText[i].setText(String.format(res.getString(resString), gouiranLinkSelection[i]));
            gouiranLinkSelectionText[i].setTypeface(font);
            gouiranLinkSelectionText[i].setTextColor(res.getColor(R.color.GouiranDarkBlue));
        }

        /* Invitez des amis */
        textView = (TextView)getActivity().findViewById(R.id.invite_your_friends);
        textView.setTypeface(font);

    }

    // TODO: Rename method, update argument and hook method into UI event
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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
