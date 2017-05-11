package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<String> culture = new ArrayList<>();
        culture.add("Schloss Schönbrunn");
        culture.add("Belvedere");
        culture.add("Kunsthistorisches Museum");

        ArrayAdapter<String> cultureListAdapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.list_item,
                        R.id.list_item_textview,
                        culture
                );

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ListView cultureListListView = (ListView) rootView.findViewById(R.id.listview);
        cultureListListView.setAdapter(cultureListAdapter);

        return rootView;
    }
}
