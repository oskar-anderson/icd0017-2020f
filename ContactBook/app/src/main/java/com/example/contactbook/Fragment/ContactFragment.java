package com.example.contactbook.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactbook.Adapter.ContactAdapter;
import com.example.contactbook.MainActivity;
import com.example.contactbook.Model.Contact;
import com.example.contactbook.Model.ContactType;
import com.example.contactbook.Model.Person;
import com.example.contactbook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    public ArrayList<Contact> mContactList;

    private Button btn_add, btn_viewAll;
    private EditText et_contactText;
    private Spinner contactTypeSpinner, personSpinner;

    private ArrayList<Person> persons;
    private ArrayList<ContactType> contactTypes;

    public ContactFragment(Context context){
        this.context = context;
        ArrayList<Contact> unJoinedContactList = (ArrayList<Contact>) MainActivity.dbHelper.ContactGetAll();
        persons = (ArrayList<Person>) MainActivity.dbHelper.PersonGetAll();
        contactTypes = (ArrayList<ContactType>) MainActivity.dbHelper.ContactTypeGetAll();

        mContactList = new ArrayList<>();
        for (Contact contact : unJoinedContactList) {
            for (ContactType contactType : contactTypes) {
                if (contactType.id.equals(contact.contactTypeIdFK)) {
                    contact.contactTypeName = contactType.text;
                }
            }
            for (Person person : persons) {
                if (person.id.equals(contact.personIdFK)) {
                    contact.personFirstName = person.firstName;
                    contact.personLastName = person.lastName;
                }
            }
            mContactList.add(contact);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new ContactAdapter(this);
        mAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                Log.d("G", "onDeleteClick Contact");
            }
        });


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        btn_add = view.findViewById(R.id.btn_add);
        btn_viewAll = view.findViewById(R.id.btn_view);
        et_contactText = view.findViewById(R.id.et_contactText);
        contactTypeSpinner = view.findViewById(R.id.spinnerContact);
        personSpinner = view.findViewById(R.id.spinnerPerson);

        ArrayAdapter<ContactType> contactTypeArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        contactTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactTypeArrayAdapter.addAll(contactTypes);
        contactTypeSpinner.setAdapter(contactTypeArrayAdapter);
/*
        contactTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ContactType contactType = (ContactType) parent.getSelectedItem();
                Toast.makeText(context, contactType.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        ArrayAdapter<Person> personArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        personArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personArrayAdapter.addAll(persons);
        personSpinner.setAdapter(personArrayAdapter);

/*
        personSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Person person = (Person) parent.getSelectedItem();
                Toast.makeText(context, person.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactAddBtn();
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactViewAll();
            }
        });

        return view;
    }

    public void removeItem(int position) {
        Contact contact = mContactList.get(position);
        boolean success = MainActivity.dbHelper.ContactDelete(contact.id);
        if (success) {
            mContactList.remove(position);
            mAdapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Unexpected", Toast.LENGTH_LONG).show();
        }
    }

    public void contactAddBtn() {
        Person person = (Person) personSpinner.getSelectedItem();
        ContactType contactType = (ContactType) contactTypeSpinner.getSelectedItem();

        if (person != null || contactType != null) {
            UUID id = java.util.UUID.randomUUID();
            Contact contact = new Contact(id.toString(), contactType.id, person.id, et_contactText.getText().toString());

            boolean success = MainActivity.dbHelper.ContactInsert(contact);
            contact.personFirstName = person.firstName;
            contact.personLastName = person.lastName;
            contact.contactTypeName = contactType.text;
            mContactList.add(contact);
            mAdapter.notifyItemInserted(mContactList.size());
        }
    }

    public void contactViewAll() {
        List<Contact> contactList = MainActivity.dbHelper.ContactGetAll();
        mContactList = (ArrayList<Contact>) contactList;
    }
}
