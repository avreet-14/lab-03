package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    interface EditCityDialogListener {
        void editCity(City updatedCity);
    }

    private EditCityDialogListener listener;
    private City city;

    public EditCityFragment() {} 

    public static EditCityFragment newInstance(City city) {
        EditCityFragment f = new EditCityFragment();
        Bundle b = new Bundle();
        b.putSerializable("city", city);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText nameEt = view.findViewById(R.id.edit_text_city_text);
        EditText provEt = view.findViewById(R.id.edit_text_province_text);

        if (getArguments() != null) {
            city = (City) getArguments().getSerializable("city");
            if (city != null) {
                nameEt.setText(city.getName());
                provEt.setText(city.getProvince());
            }
        }

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (d, w) -> {
                    if (city != null) {
                        city.setName(nameEt.getText().toString());
                        city.setProvince(provEt.getText().toString());
                        listener.editCity(city);
                    }
                })
                .create();
    }
}