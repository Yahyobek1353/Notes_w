package com.geeks.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private static final List<Note> mainList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new MainFragment());
        transaction.addToBackStack("MainFragment");
        transaction.commit();
        NoteAdapter adapter = new NoteAdapter(this);
        getSupportFragmentManager().setFragmentResultListener("new_note", this, (requestKey, result) -> {
            Note note = (Note) result.getSerializable("note");
            mainList.add(note);
            adapter.addNote(mainList);
        });
    }

    public static List<Note> getList() {
        return mainList;
    }
}
