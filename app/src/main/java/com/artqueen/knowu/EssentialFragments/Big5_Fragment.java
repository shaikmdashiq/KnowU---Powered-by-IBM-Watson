package com.artqueen.knowu.EssentialFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.artqueen.knowu.ListAdapters.Big5Adapter;
import com.artqueen.knowu.R;
import com.artqueen.knowu.Results_Activity;

public class Big5_Fragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "1";

    ListView lv;
    ArrayAdapter<String> arry;

    public Big5_Fragment() {
    }

    public static Big5_Fragment newInstance(int sectionNumber) {
        Big5_Fragment fragment = new Big5_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooty = inflater.inflate(R.layout.fragment_big5, container, false);

        Results_Activity.big5names.add("Values");
        Results_Activity.big5names.add("Needs");

        Big5Adapter arry = new Big5Adapter(getActivity(), R.layout.row_big5, Results_Activity.big5names);

        lv = (ListView) rooty.findViewById(R.id.listView001);
        lv.setAdapter(arry);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      public void onItemClick(AdapterView<?> parent, View view,
                                                              int position, long id) {

                                          if (position < 5) {
                                              Bundle args = new Bundle();
                                              args.putInt("index", position);
                                              args.putString("name", Results_Activity.big5names.get(position) + " Facets");
                                              SubChartFragment fragment = new SubChartFragment();
                                              fragment.setArguments(args);
                                              fragment.show(getFragmentManager(), "Big5_SubChart");
                                          } else if (position == 5) {
                                              Values_Fragment fragment = new Values_Fragment();
                                              fragment.show(getFragmentManager(), "Values");
                                          } else if (position == 6) {
                                              Needs_Fragment fragment = new Needs_Fragment();
                                              fragment.show(getFragmentManager(), "Needs");
                                          }
                                      }
                                  }

        );


        return rooty;
    }
}
