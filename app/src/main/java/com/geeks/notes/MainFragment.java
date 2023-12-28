package com.geeks.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainFragment extends Fragment implements NoteAdapter.ListenersBtn {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private Button btnAdd;
    private FloatingActionButton btn_sort;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd = view.findViewById(R.id.btn_add);
        btn_sort = view.findViewById(R.id.btn_sort);
        //Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.rv_notes);
        adapter = new NoteAdapter(requireContext(), this);
        recyclerView.setAdapter(adapter);
        adapter.addNote(MainActivity.getList());


        btn_sort.setOnClickListener(view1 -> {
            adapter.sortAbc();
        });

        //Инициализация обработчика нажатия на кнопку и перехода на SecondFragment
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new SecondFragment());
                transaction.addToBackStack("SecondFragment");
                transaction.commit();
            }
        });


        //Инициализация добавления новых заметок
     /*   requireActivity().getSupportFragmentManager().setFragmentResultListener("new_note", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getSerializable("note");
                adapter.addNote(note);
            }
        });*/
        requireActivity().getSupportFragmentManager().setFragmentResultListener("change_note", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getSerializable("edit_note");
                adapter.changeNote(note, result.getInt("position"));
            }
        });
    }

    @Override
    public void change(int position) {
        Note note = adapter.getList().get(position);
        Bundle bundle = new Bundle();
        Log.e("ololo", note.getTitle());
        bundle.putString("changeTitle", note.getTitle());
        bundle.putString("changeDesc", note.getDescription());
        bundle.putString("changeDate", note.getDate());
        bundle.putInt("position", position);
        SecondFragment secondFragment = new SecondFragment();
        secondFragment.setArguments(bundle);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, secondFragment);
        transaction.addToBackStack("SecondFragment");
        transaction.commit();
    }
}