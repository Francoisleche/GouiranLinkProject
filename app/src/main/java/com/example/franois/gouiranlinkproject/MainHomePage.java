package com.example.franois.gouiranlinkproject;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainHomePage extends AppCompatActivity {

    TextView welcomeUser;
    String username;
    String text;
    String[] recentResearches;
    TextView[] recentResearchesText;
    String[] gouiranLinkSelection;
    TextView[] gouiranLinkSelectionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_page);
        Resources res = getResources();

        /* Editing username */
        username = "XXXXXXXXXX";
        text = String.format(res.getString(R.string.welcome_user), username);
        welcomeUser = new TextView(this);
        welcomeUser = (TextView)findViewById(R.id.welcome_user);
        welcomeUser.setText(text);

        /* Editing recent researches */
        getRecentResearches();
        recentResearchesText = new TextView[9];
        for (int i = 0; i < 9; i++)
            recentResearchesText[i] = new TextView(this);

        for (int i = 0; i < 9; i++) {
            String researchId = "previous_" + (i + 1);
            String researchString = "research_" + (i + 1);
            int resId = res.getIdentifier(researchId, "id", getPackageName());
            int resString = res.getIdentifier(researchString, "string", getPackageName());
            recentResearchesText[i] = (TextView)findViewById(resId);
            recentResearchesText[i].setText(String.format(res.getString(resString), recentResearches[i]));
        }

        /* Editing Gouiran Link selections */
        getGouiranLinkSelection();
        gouiranLinkSelectionText = new TextView[9];
        for (int i = 0; i < 9; i++)
            gouiranLinkSelectionText[i] = new TextView(this);

        for (int i = 0; i < 9; i++) {
            String researchId = "selection_" + (i + 1);
            String researchString = "research_" + (i + 1);
            int resId = res.getIdentifier(researchId, "id", getPackageName());
            int resString = res.getIdentifier(researchString, "string", getPackageName());
            gouiranLinkSelectionText[i] = (TextView)findViewById(resId);
            gouiranLinkSelectionText[i].setText(String.format(res.getString(resString), gouiranLinkSelection[i]));
        }
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

    public void onClick(View v) {
            Intent intent = new Intent(this, AppointmentPage.class);
            startActivity(intent);

        /*final Dialog dialog = new Dialog(this);

        if (v instanceof ImageView) {
            dialog.setContentView(R.layout.textpopupview);
            dialog.setTitle("Title...");
            Button button = (Button) dialog.findViewById(R.id.clickButton);
            button.setBackground(((ImageView) v).getDrawable());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        else if (v instanceof TextView) {
            dialog.setContentView(R.layout.textpopupview);
            dialog.setTitle("Text popup");
            TextView txt = (TextView)dialog.findViewById(R.id.textbox);
            txt.setText(getString(R.string.text_textpopup));
            Button button = (Button) dialog.findViewById(R.id.clickButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();*/
    }
}
