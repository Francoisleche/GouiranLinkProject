package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    Boolean connected;
    TextView welcomeUser;
    String username = null;
    String text;
    String[] recentResearches;
    TextView[] recentResearchesText;
    String[] gouiranLinkSelection;
    TextView[] gouiranLinkSelectionText;
    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

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
            connected = getArguments().getBoolean("connected");
        }
        //Intent i = new Intent(getActivity(), MainHomePage.class);
        //startActivity(i);

    }

    private void generateRecentResearches(View root, Typeface font) {
        RelativeLayout myRecentResearchesProposal = (RelativeLayout)root.findViewById(R.id.my_recent_researches_proposal);

        TextView textView = (TextView)root.findViewById(R.id.my_recent_researches);
        textView.setTypeface(font);
        if (connected)
            textView.setText("Top Recherche");

        TextView textView1 = new TextView(getActivity());
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView1.setPadding(5, 0, 5, 0);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView1.setTextColor(getResources().getColor(R.color.black));
        textView1.setText("Curabitur suscipit quam");
        textView1.setLayoutParams(params1);
        textView1.setId(1);
        textView1.setTypeface(font);
        myRecentResearchesProposal.addView(textView1);

        TextView textView2 = new TextView(getActivity());
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView2.setPadding(5, 0, 5, 0);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView2.setTextColor(getResources().getColor(R.color.black));
        textView2.setText("Phasellus felis orci");
        params2.addRule(RelativeLayout.END_OF, 1);
        textView2.setLayoutParams(params2);
        textView2.setId(2);
        textView2.setTypeface(font);
        myRecentResearchesProposal.addView(textView2);

        TextView textView3 = new TextView(getActivity());
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView3.setPadding(5, 0, 5, 0);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView3.setTextColor(getResources().getColor(R.color.black));
        textView3.setText("Duis non ullamcorper");
        params3.addRule(RelativeLayout.END_OF, 1);
        params3.addRule(RelativeLayout.BELOW, 2);
        textView3.setLayoutParams(params3);
        textView3.setId(3);
        textView3.setTypeface(font);
        myRecentResearchesProposal.addView(textView3);

        TextView textView4 = new TextView(getActivity());
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView4.setPadding(5, 0, 5, 0);
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView4.setTextColor(getResources().getColor(R.color.black));
        textView4.setText("Quisque nisl ligula");
        params4.addRule(RelativeLayout.END_OF, 2);
        textView4.setLayoutParams(params4);
        textView4.setId(4);
        textView4.setTypeface(font);
        myRecentResearchesProposal.addView(textView4);

        TextView textView5 = new TextView(getActivity());
        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView5.setPadding(5, 0, 5, 0);
        textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView5.setTextColor(getResources().getColor(R.color.black));
        textView5.setText("Etiam nec quam");
        params5.addRule(RelativeLayout.END_OF, 4);
        textView5.setLayoutParams(params5);
        textView5.setId(5);
        textView5.setTypeface(font);
        myRecentResearchesProposal.addView(textView5);

        TextView textView6 = new TextView(getActivity());
        RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView6.setPadding(5, 0, 5, 0);
        textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView6.setTextColor(getResources().getColor(R.color.black));
        textView6.setText("Nam a sapien");
        params6.addRule(RelativeLayout.END_OF, 4);
        params6.addRule(RelativeLayout.BELOW, 5);
        textView6.setLayoutParams(params6);
        textView6.setId(6);
        textView6.setTypeface(font);
        myRecentResearchesProposal.addView(textView6);

        TextView textView7 = new TextView(getActivity());
        RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView7.setPadding(5, 0, 5, 0);
        textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView7.setTextColor(getResources().getColor(R.color.black));
        textView7.setText("Integer at nisi");
        params7.addRule(RelativeLayout.END_OF, 5);
        textView7.setLayoutParams(params7);
        textView7.setId(7);
        textView7.setTypeface(font);
        myRecentResearchesProposal.addView(textView7);

        TextView textView8 = new TextView(getActivity());
        RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView8.setPadding(5, 0, 5, 0);
        textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView8.setTextColor(getResources().getColor(R.color.black));
        textView8.setText("Aenean tristique nisi");
        params8.addRule(RelativeLayout.END_OF, 7);
        textView8.setLayoutParams(params8);
        textView8.setId(8);
        textView8.setTypeface(font);
        myRecentResearchesProposal.addView(textView8);

        TextView textView9 = new TextView(getActivity());
        RelativeLayout.LayoutParams params9 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView9.setPadding(5, 0, 5, 0);
        textView9.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView9.setTextColor(getResources().getColor(R.color.black));
        textView9.setText("Proin lobortis sollicitudin");
        params9.addRule(RelativeLayout.END_OF, 7);
        params9.addRule(RelativeLayout.BELOW, 8);
        textView9.setLayoutParams(params9);
        textView9.setId(9);
        textView9.setTypeface(font);
        myRecentResearchesProposal.addView(textView9);

    }

    private void generateAroundMe(View root, Typeface font) {

        TextView textView = (TextView)root.findViewById(R.id.around_me);
        textView.setTypeface(font);

        ImageView imageView = (ImageView) root.findViewById(R.id.around_1);
        textView = (TextView) root.findViewById(R.id.around_1_text);
        new DownloadImageTask(imageView).execute("http://polaris.hs.llnwd.net/o40/vic/2017/img/motorcycles/my17-motorcycles-page/cruisers-en-us.png");
        textView.setText("Vivamus id pretium");
        textView.setTypeface(font);
        imageView = (ImageView) root.findViewById(R.id.around_2);
        textView = (TextView) root.findViewById(R.id.around_2_text);
        new DownloadImageTask(imageView).execute("http://i2.cdn.cnn.com/cnnnext/dam/assets/160201113353-modern-motorcycle-style-13-super-169.jpg");
        textView.setText("Lorem ipsum dolor");
        textView.setTypeface(font);
        imageView = (ImageView) root.findViewById(R.id.around_3);
        textView = (TextView) root.findViewById(R.id.around_3_text);
        new DownloadImageTask(imageView).execute("http://t3.gstatic.com/images?q=tbn:ANd9GcRRq4kyBQRB-TiUZAW3oSBJ5Z6hRluE8qkGogx8VhrSEgUDB64tjXfnxHg");
        textView.setText("Cras placerat pulvinar");
        textView.setTypeface(font);
        imageView = (ImageView) root.findViewById(R.id.around_4);
        textView = (TextView) root.findViewById(R.id.around_4_text);
        new DownloadImageTask(imageView).execute("https://i.ytimg.com/vi/Fw8agSotU-M/maxresdefault.jpg");
        textView.setText("Curabitur a massa");
        textView.setTypeface(font);
        imageView = (ImageView) root.findViewById(R.id.around_5);
        textView = (TextView) root.findViewById(R.id.around_5_text);
        new DownloadImageTask(imageView).execute("https://i.ytimg.com/vi/VX3RXiEUuWw/hqdefault.jpg");
        textView.setText("Proin viverra bibendum");
        textView.setTypeface(font);
    }

    private void generateGouiranLinkSelection(View root, Typeface font) {
        RelativeLayout gouiranLinkSelectionProposals = (RelativeLayout)root.findViewById(R.id.gouiran_link_selection_proposals);

        TextView textView = (TextView)root.findViewById(R.id.gouiran_link_selection);
        textView.setTypeface(font);

        TextView textView1 = new TextView(getActivity());
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView1.setPadding(5, 0, 5, 0);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView1.setTextColor(getResources().getColor(R.color.black));
        textView1.setText("Suspendisse imperdiet elit");
        textView1.setLayoutParams(params1);
        textView1.setId(1);
        textView1.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView1);

        TextView textView2 = new TextView(getActivity());
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView2.setPadding(5, 0, 5, 0);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView2.setTextColor(getResources().getColor(R.color.black));
        textView2.setText("Curabitur non convallis");
        params2.addRule(RelativeLayout.END_OF, 1);
        textView2.setLayoutParams(params2);
        textView2.setId(2);
        textView2.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView2);

        TextView textView3 = new TextView(getActivity());
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView3.setPadding(5, 0, 5, 0);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView3.setTextColor(getResources().getColor(R.color.black));
        textView3.setText("Etiam sollicitudin quis");
        params3.addRule(RelativeLayout.END_OF, 1);
        params3.addRule(RelativeLayout.BELOW, 2);
        textView3.setLayoutParams(params3);
        textView3.setId(3);
        textView3.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView3);

        TextView textView4 = new TextView(getActivity());
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView4.setPadding(5, 0, 5, 0);
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView4.setTextColor(getResources().getColor(R.color.black));
        textView4.setText("Sed posuere nisl");
        params4.addRule(RelativeLayout.END_OF, 2);
        textView4.setLayoutParams(params4);
        textView4.setId(4);
        textView4.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView4);

        TextView textView5 = new TextView(getActivity());
        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView5.setPadding(5, 0, 5, 0);
        textView5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView5.setTextColor(getResources().getColor(R.color.black));
        textView5.setText("Nam nec erat");
        params5.addRule(RelativeLayout.END_OF, 4);
        textView5.setLayoutParams(params5);
        textView5.setId(5);
        textView5.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView5);

        TextView textView6 = new TextView(getActivity());
        RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView6.setPadding(5, 0, 5, 0);
        textView6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView6.setTextColor(getResources().getColor(R.color.black));
        textView6.setText("Vivamus vitae porttitor");
        params6.addRule(RelativeLayout.END_OF, 4);
        params6.addRule(RelativeLayout.BELOW, 5);
        textView6.setLayoutParams(params6);
        textView6.setId(6);
        textView6.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView6);

        TextView textView7 = new TextView(getActivity());
        RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView7.setPadding(5, 0, 5, 0);
        textView7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView7.setTextColor(getResources().getColor(R.color.black));
        textView7.setText("Quisque imperdiet, arcu");
        params7.addRule(RelativeLayout.END_OF, 5);
        textView7.setLayoutParams(params7);
        textView7.setId(7);
        textView7.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView7);

        TextView textView8 = new TextView(getActivity());
        RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView8.setPadding(5, 0, 5, 0);
        textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView8.setTextColor(getResources().getColor(R.color.black));
        textView8.setText("Integer posuere ligula");
        params8.addRule(RelativeLayout.END_OF, 7);
        textView8.setLayoutParams(params8);
        textView8.setId(8);
        textView8.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView8);

        TextView textView9 = new TextView(getActivity());
        RelativeLayout.LayoutParams params9 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView9.setPadding(5, 0, 5, 0);
        textView9.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView9.setTextColor(getResources().getColor(R.color.black));
        textView9.setText("Vestibulum lorem elit");
        params9.addRule(RelativeLayout.END_OF, 7);
        params9.addRule(RelativeLayout.BELOW, 8);
        textView9.setLayoutParams(params9);
        textView9.setId(9);
        textView9.setTypeface(font);
        gouiranLinkSelectionProposals.addView(textView9);
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
        final Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        generateRecentResearches(root, font);
        generateAroundMe(root, font);
        generateGouiranLinkSelection(root, font);
        return (root);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Resources res = getResources();
        getActivity().getAssets();
        TextView textView;
        textView = new TextView(getActivity());
        final Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");

        /* Editing username */
        //username = "XXXXXXXXXX";
        if (connected)
            text = String.format(res.getString(R.string.welcome_user), username);
        else
            text = "Bonjour,";
        welcomeUser = new TextView(getActivity());
        welcomeUser = (TextView)getActivity().findViewById(R.id.welcome_user);
        welcomeUser.setText(text);
        welcomeUser.setTypeface(font);
        welcomeUser.setTextColor(res.getColor(R.color.GouiranDarkBlue));

        /* Editing recent researches */
        /*textView = (TextView)getActivity().findViewById(R.id.my_recent_researches);
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
        }*/

        /* Around Me */
        /*textView = (TextView)getActivity().findViewById(R.id.around_me);
        textView.setTypeface(font);
        for (int i = 0; i < 5; i++) {
            String around = "around_" + (i + 1) + "_text";
            int resAround = res.getIdentifier(around, "id", getActivity().getPackageName());
            textView = (TextView)getActivity().findViewById(resAround);
            textView.setTypeface(font);
        }*/

        /* Editing Gouiran Link selections */
        /*textView = (TextView)getActivity().findViewById(R.id.gouiran_link_selection);
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
        }*/

        /* Invitez des amis */
        textView = (TextView)getActivity().findViewById(R.id.invite_your_friends);
        textView.setTypeface(font);

        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invitation Gouiran Link");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, je viens de découvrir l'application mobile Gouiran Link, elle est géniale!\nIl faudrait que tu l'essaye toi aussi!");

        Button button = (Button) view.findViewById(R.id.invite_your_friends_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

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
