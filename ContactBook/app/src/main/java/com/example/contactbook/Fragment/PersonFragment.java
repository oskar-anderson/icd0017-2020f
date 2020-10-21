package com.example.contactbook.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactbook.Adapter.PersonAdapter;
import com.example.contactbook.MainActivity;
import com.example.contactbook.Model.Person;
import com.example.contactbook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PersonAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    public ArrayList<Person> mPersonList;

    private Button btn_add, btn_viewAll;
    private EditText et_firstName;
    private EditText et_lastName;

    public PersonFragment(Context context){
        this.context = context;
        mPersonList = (ArrayList<Person>) MainActivity.dbHelper.PersonGetAll();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new PersonAdapter(this);
        mAdapter.setOnItemClickListener(new PersonAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                Log.d("G", "onDeleteClick Person");
            }
        });


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        btn_add = view.findViewById(R.id.btn_add);
        btn_viewAll = view.findViewById(R.id.btn_view);
        et_firstName = view.findViewById(R.id.et_firstName);
        et_lastName = view.findViewById(R.id.et_lastName);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personAddBtn();
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personViewAll();
            }
        });

        return view;
    }

    public void removeItem(int position) {
        Person person = mPersonList.get(position);
        boolean success = MainActivity.dbHelper.PersonDelete(person.id);
        if (success) {
            mPersonList.remove(position);
            mAdapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Not happening!", Toast.LENGTH_LONG).show();
        }
    }

    public void personAddBtn() {
        if (!et_firstName.getText().toString().equals("") || !et_lastName.getText().toString().equals("")) {
            UUID id = java.util.UUID.randomUUID();
            Person person = new Person(id.toString(), et_firstName.getText().toString(), et_lastName.getText().toString());

            boolean success = MainActivity.dbHelper.PersonInsert(person);
            mPersonList.add(person);
            mAdapter.notifyItemInserted(mPersonList.size());
        }
    }

    public void personViewAll() {
        List<Person> personList = MainActivity.dbHelper.PersonGetAll();
        mPersonList = (ArrayList<Person>) personList;
    }

}
