package com.geeks.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private LayoutInflater inflater;
    private List<Note> list = new ArrayList<>();
    private ListenersBtn listeners;

    public NoteAdapter(Context context, ListenersBtn listeners) {
        this.inflater = LayoutInflater.from(context);
        this.listeners = listeners;
    }

    public NoteAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void addNote(List<Note> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void changeNote(Note note, int position) {
        list.set(position, note);
        notifyItemChanged(position);
    }

    public List<Note> getList() {
        return list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sortAbc(){
        Collections.sort(list, (note, t1) -> note.getTitle().compareToIgnoreCase(note.getTitle()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
            holder.onBind(list.get(position));
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listeners.change(holder.getAdapterPosition());
                }
            });
        }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, desc, date;
        ImageView btnEdit;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_img);
            title = itemView.findViewById(R.id.item_tv_title);
            desc = itemView.findViewById(R.id.item_tv_des);
            date = itemView.findViewById(R.id.item_tv_date);
            btnEdit = itemView.findViewById(R.id.item_btn_edit);
        }

        public void onBind(Note note) {
                title.setText(note.getTitle());
                desc.setText(note.getDescription());
                date.setText(note.getDate());
            }
        }

        interface ListenersBtn {
    void change(int position);
    }
}

