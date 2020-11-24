package com.example.oops_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class frag_inventory extends Fragment {

    ArrayList<category> categories;
    FloatingActionButton add_category;
    private RecyclerView categoryRecView;
    private categoryRecViewAdapter adapter;
    Toolbar toolbar;

    public frag_inventory(ArrayList<category> categories, FloatingActionButton add, Toolbar toolbar) {
        this.categories = categories;
        this.add_category = add;
        this.toolbar = toolbar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        adapter = new categoryRecViewAdapter(getActivity(), getFragmentManager(), add_category,toolbar);
        categoryRecView = view.findViewById(R.id.categoryRecView);
        categoryRecView.setAdapter(adapter);
        categoryRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setCategories(categories);

        TextView inst = view.findViewById(R.id.category_instruction);
        if(categories.size() != 0)inst.setVisibility(View.GONE);else inst.setVisibility(View.VISIBLE);

        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addCategoryDialog dialog = new addCategoryDialog();
                dialog.setTransferCall(new addCategoryDialog.transferCall() {
                    @Override
                    public void onSaveCategory(String name, String des) {
                        String defimgCategoryUri = "https://media.gettyimages.com/photos/closeup-of-multi-colored-toys-over-white-background-picture-id1094028428?k=6&m=1094028428&s=612x612&w=0&h=YDfxr7175ae4yi07DtWOcqtAqi9GUIthBHNZzAF4dO8=";
                        categories.add(new category(categories.size(), name, des, defimgCategoryUri, new ArrayList<item>()));
                        adapter.notifyItemInserted(categories.size());
                        inst.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), name + " added!", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show(getFragmentManager(), "Adding category...");
            }
        });

        return view;
    }

}
