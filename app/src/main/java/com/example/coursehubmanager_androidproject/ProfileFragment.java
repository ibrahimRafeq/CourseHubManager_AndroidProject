package com.example.coursehubmanager_androidproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.coursehubmanager_androidproject.databinding.DialogDeleteUserBinding;
import com.example.coursehubmanager_androidproject.databinding.DialogUpdateProfileBinding;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private CourseDataBase personDB;
    private List<Person> personList;
    private PersonAdapter personAdapter;
    private RecyclerView profileRView;
    private Button goBookMark, updateProfile, deleteUser;
    private static final String ID_PERSON = "Id_Person";
    private long idPerson;
    private AlertDialog dialog;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(long personID) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putLong(ID_PERSON, personID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            idPerson = getArguments().getLong(ID_PERSON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        profileRView = view.findViewById(R.id.profileRV);
        goBookMark = view.findViewById(R.id.goBookMark);
        updateProfile = view.findViewById(R.id.updateProfile);
        deleteUser = view.findViewById(R.id.deleteUser);

        personDB = CourseDataBase.getDataBase(getContext());
        personList = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        idPerson = sharedPreferences.getLong("id_person", -1);

        personList.addAll(personDB.personDao().getAllPersonById(idPerson));
        personAdapter = new PersonAdapter(getActivity(), personList);
        profileRView.setAdapter(personAdapter);
        profileRView.setLayoutManager(new LinearLayoutManager(getActivity()));

        goBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Bookmark.class);
                startActivity(intent);
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfileInformation();
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(idPerson);
            }
        });

        return view;
    }

    public void updateProfileInformation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogUpdateProfileBinding bindingDialog = DialogUpdateProfileBinding.inflate(getLayoutInflater());
        builder.setView(bindingDialog.getRoot());

        bindingDialog.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = bindingDialog.etNamePersonUP.getText().toString();
                String email = bindingDialog.etEmailPersonUP.getText().toString();
                String password = bindingDialog.etPasswordPersonUP.getText().toString();
                Person person = new Person(name, email, password);
                person.setIdPerson(idPerson);
                int id = personDB.personDao().updatePerson(person);
                Toast.makeText(getActivity(), "The update was successfully", Toast.LENGTH_SHORT).show();
                refreshPersonList();
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public void deleteUser(long id_person){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogDeleteUserBinding bindingDialog = DialogDeleteUserBinding.inflate(getLayoutInflater());
        builder.setView(bindingDialog.getRoot());
        bindingDialog.deleteButDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personDB.personDao().deletePersonById(id_person);
                Toast.makeText(getActivity(), "The delete was successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        dialog = builder.create();
        dialog.show();

    }

    private void refreshPersonList() {
        personList.clear();
        personList.addAll(personDB.personDao().getAllPersonById(idPerson));
        personAdapter.notifyDataSetChanged();
    }
}