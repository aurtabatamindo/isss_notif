package com.example.isss_usr.ui.laporan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isss_usr.AdapterRecyclerView;
import com.example.isss_usr.R;


public class LaporanFragment extends Fragment {
    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    String[] subjects = {
            "1", "2", "3", "4",
            "5"
    };
    private LaporanViewModel adminViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adminViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LaporanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);

        context = getActivity().getApplicationContext();
        recyclerView = root.findViewById(R.id.recycler_View);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new AdapterRecyclerView(context, subjects);
        recyclerView.setAdapter(recyclerViewAdapter);


        return root;
    }
}