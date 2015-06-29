package com.artqueen.knowu.Analysis;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.artqueen.knowu.MainActivity;
import com.artqueen.knowu.PersonalityInsights;
import com.artqueen.knowu.R;
import com.artqueen.knowu.Results_Activity;
import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Trait;

import java.util.ArrayList;
import java.util.List;


public class Analysis_startpage extends Fragment {

    private static final String ARG_SECTION_NUMBER = "0";
    private Gson gson = new Gson();
    View rootView;
    Button btn1;
    EditText ed1;
    ProgressDialog loadingDiag;

    public Analysis_startpage() {
    }

    public static Analysis_startpage newInstance(int sectionNumber) {
        Analysis_startpage fragment = new Analysis_startpage();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analysis_startpage, container, false);
        System.out.println("started");

        btn1 = (Button) rootView.findViewById(R.id.button01);
        ed1 = (EditText) rootView.findViewById(R.id.editText01);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trimmed = ed1.getText().toString().trim();
                int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
                if (words < 100) {
                    Toast.makeText(getActivity(), "Not enough words, you only have " + String.valueOf(words), Toast.LENGTH_SHORT).show();
                } else {
                    LongOp task = new LongOp();
                    task.execute(trimmed);
                }
            }
        });

        return rootView;
    }


    public static void wipeStored() {
        MainActivity.big5names.clear();
        MainActivity.big5percentages.clear();
        MainActivity.big5subcats.clear();
        MainActivity.big5subPcnt.clear();
        MainActivity.needsnames.clear();
        MainActivity.needspercentages.clear();
        MainActivity.valuesnames.clear();
        MainActivity.valuespercentages.clear();
    }

    protected Gson getGson() {
        return gson;
    }

    private void lockScreenOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void unlockScreenOrientation() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public class LongOp extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lockScreenOrientation();
            loadingDiag = new ProgressDialog(getActivity());
            loadingDiag.setMessage("Gathering collected data...");
            loadingDiag.setIndeterminate(true);
            //loadingDiag.setMax(allthedatainList.size() + 5);
            loadingDiag.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            loadingDiag.setCancelable(false);
            loadingDiag.show();
        }

        @Override
        protected void onPostExecute(String unused) {
            super.onPostExecute(unused);
            Intent intent = new Intent(getActivity(), Results_Activity.class);
            Bundle b = new Bundle();
            b.putInt("src", 0);
            intent.putExtras(b);
            startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Integer... progUpdate) {
            if (progUpdate[0] == 1) {
                loadingDiag.setMessage("Sending to IBM...");
            }

            if (progUpdate[0] == 2) {
                if (loadingDiag != null) {
                    loadingDiag.dismiss();
                }
                Toast.makeText(getActivity(), "You may now check your result.", Toast.LENGTH_LONG).show();
                unlockScreenOrientation();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            //TODO Replace API key
            final PersonalityInsights service = new PersonalityInsights();
            service.setUsernameAndPassword("YOUR-KEY", "YOUR-PASSWORD");

            wipeStored();

            publishProgress(1);

            try {
                Profile profile = getGson().fromJson(service.getProfile(params[0]), Profile.class);

                Trait main = profile.getTree();
                List<Trait> mainList = main.getChildren();
                Trait big5 = mainList.get(0);
                List<Trait> big5Child = big5.getChildren();
                Trait big5list = big5Child.get(0);

                List<Trait> big5Categories = big5list.getChildren();

                for (int r = 0; r < big5Categories.size(); r++) {
                    Trait trait = big5Categories.get(r);
                    MainActivity.big5names.add(trait.getName());
                    MainActivity.big5percentages.add(trait.getPercentage());

                    //getting final children
                    List<Trait> finalChildren = trait.getChildren();

                    MainActivity.big5subcats.add(new ArrayList<String>());
                    MainActivity.big5subPcnt.add(new ArrayList<Double>());

                    for (int j = 0; j < finalChildren.size(); j++) {
                        Trait openness = finalChildren.get(j);
                        MainActivity.big5subcats.get(r).add(openness.getName());
                        MainActivity.big5subPcnt.get(r).add(openness.getPercentage());
                    }
                }

                //adding needs
                Trait need = mainList.get(1);
                List<Trait> needChild = need.getChildren();
                System.out.println("needchild = " + needChild.size());

                Trait needFirst = needChild.get(0);
                List<Trait> needElements = needFirst.getChildren();

                for (int j = 0; j < needElements.size(); j++) {
                    Trait needelementss = needElements.get(j);
                    MainActivity.needsnames.add(needelementss.getName());
                    MainActivity.needspercentages.add(needelementss.getPercentage());
                }


                //adding values
                Trait values = mainList.get(2);
                List<Trait> mainValues = values.getChildren();
                Trait valueFirst = mainValues.get(0);
                List<Trait> valueElements = valueFirst.getChildren();

                for (int j = 0; j < valueElements.size(); j++) {
                    Trait valuesElementss = valueElements.get(j);
                    MainActivity.valuesnames.add(valuesElementss.getName());
                    MainActivity.valuespercentages.add(valuesElementss.getPercentage());
                }
                publishProgress(2);
            } catch (Exception e) {
                Log.e("KnowU Submission", e.getMessage());
                Toast.makeText(getActivity(), "Something went wrong: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return "done";
        }
    }
}



