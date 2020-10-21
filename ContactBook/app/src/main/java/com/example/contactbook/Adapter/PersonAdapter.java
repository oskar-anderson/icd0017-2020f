package com.example.contactbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactbook.Fragment.PersonFragment;
import com.example.contactbook.Model.Person;
import com.example.contactbook.R;

import java.util.ArrayList;

@SuppressWarnings("UnnecessaryLocalVariable")
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    private OnItemClickListener mListener;
    private PersonFragment fragment;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public PersonAdapter(PersonFragment fragment) {
        this.fragment = fragment;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView mFirstName;
        public TextView mLastName;
        public ImageView mDeleteImage;

        public PersonViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mFirstName = itemView.findViewById(R.id.rv_person_firstName_text);
            mLastName = itemView.findViewById(R.id.rv_person_lastName_text);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_person_item, parent, false);
        PersonViewHolder evh = new PersonViewHolder(v, mListener);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person currentItem = fragment.mPersonList.get(position);

        holder.mFirstName.setText(currentItem.firstName);
        holder.mLastName.setText(currentItem.lastName);
    }

    @Override
    public int getItemCount() {
        return fragment.mPersonList.size();
    }
}
