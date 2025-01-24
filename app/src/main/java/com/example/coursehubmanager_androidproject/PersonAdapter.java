package com.example.coursehubmanager_androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubmanager_androidproject.databinding.ItemPersonBinding;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Person> personList;
    private ItemPersonBinding personBinding;
    private OnItemClick onItemClick;

    public PersonAdapter(Context context, List<Person> personList, OnItemClick onItemClick) {
        this.context = context;
        this.personList = personList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        personBinding = ItemPersonBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(personBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Person person = personList.get(position);

        myViewHolder.binding.namePTV.setText(person.getNamePerson());
        myViewHolder.binding.emailPerson.setText(person.getEmailPerson());
        myViewHolder.binding.passwordPTV.setText(person.getPasswordPerson());
        myViewHolder.binding.personEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onEdit(position);
            }
        });
        myViewHolder.binding.perosnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onDelete(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPersonBinding binding;

        public MyViewHolder(ItemPersonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClick {
        void onDelete(int position);
        void onEdit(int position);
    }
}

