package com.geeks.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SecondFragment extends Fragment {
    private EditText etTitle, etDesc, etDate;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTitle = view.findViewById(R.id.et_title);
        etDesc = view.findViewById(R.id.et_des);
        btnSave = view.findViewById(R.id.btn_save);
        etDate = view.findViewById(R.id.et_date);
        Bundle bundle = new Bundle();
        if (getArguments() != null) {
            etTitle.setText(getArguments().getString("changeTitle"));
            etDesc.setText(getArguments().getString("changeDesc"));
            etDate.setText(getArguments().getString("changeDate"));
            //Инициализация изменения заметок
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String changeTitle = etTitle.getText().toString();
                    String changeDesck = etDesc.getText().toString();
                    int position = getArguments().getInt("position");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault());
                    String changeDate = sdf.format(new Date());
                    Note note = new Note(changeTitle, changeDesck, changeDate, 1);
                    bundle.putSerializable("edit_note", note);
                    bundle.putInt("position", position);
                    requireActivity().getSupportFragmentManager().setFragmentResult("change_note", bundle);
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });

        } else {
            //Инициализация сохранения новых заметок
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = etTitle.getText().toString();
                    String desc = etDesc.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault());
                    String date = sdf.format(new Date());
                    Note note = new Note(title, desc, date, 1);
                    bundle.putSerializable("note", note);
                    requireActivity().getSupportFragmentManager().setFragmentResult("new_note", bundle);
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
    }
}