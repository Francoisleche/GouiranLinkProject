package com.example.franois.gouiranlinkproject;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

import static com.example.franois.gouiranlinkproject.R.id.center;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends android.support.v4.app.Fragment {

    Appointment[] appointments;
    View view_a;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        this.getUpcomingAppointments();
        view = this.setUpcomingAppointmentsLayout(view);

        return view;
    }

    public void getUpcomingAppointments() {
        Date date;
        date = new Date(2016, 12, 21, 14, 30);
        this.appointments = new Appointment[7];
        this.appointments[0] = new Appointment("Kawasaki", "NinjaH2R", date, "@drawable/h2r");
        date = new Date(2017, 3, 3, 11, 0);
        this.appointments[1] = new Appointment("Suzuki", "GSXR 1000", date, "@drawable/gsxr");
        date = new Date(2017, 5, 18, 8, 30);
        this.appointments[2] = new Appointment("Kawasaki", "Inconnue", date, "@drawable/kawa");
        date = new Date(2017, 6, 30, 16, 0);
        this.appointments[3] = new Appointment("Triumph", "Tiger", date, "@drawable/tiger");
        date = new Date(2017, 7, 21, 11, 50);
        this.appointments[4] = new Appointment("Kawasaki", "ER6N", date, "@drawable/er6n");
        date = new Date(2017, 10, 13, 20, 0);
        this.appointments[5] = new Appointment("Honda", "CB650F", date, "@drawable/cb650f");
        date = new Date(2017, 11, 30, 14, 0);
        this.appointments[6] = new Appointment("Kawasaki", "ZZR1400", date, "@drawable/zzr1400");
    }

    public LinearLayout setUpcomingAppointmentsLayout(View view) {
        //LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.upcomingFragment);
        LinearLayout linearLayout = new LinearLayout(getActivity());

        for (int i = 0; i < 7; i++) {
            TextView textViewProvider = new TextView(getActivity());
            TextView textViewService = new TextView(getActivity());

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            textViewProvider.setLayoutParams(param);
            textViewService.setLayoutParams(param);

            textViewProvider.setText(this.appointments[i].getProviderName());
            textViewProvider.setWidth(1);
            textViewService.setText(this.appointments[i].getServiceName());
            //textViewProvider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            textViewProvider.setTextSize(30);
            textViewService.setTextSize(30);
            //textViewService.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            linearLayout.addView(textViewProvider, param);
            linearLayout.addView(textViewService, param);
        }
        return (linearLayout);
    }

}
