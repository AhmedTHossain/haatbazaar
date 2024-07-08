package com.apptechbd.haatbazaar.views.fragments.staff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.adapters.CategoriesAdapter;
import com.apptechbd.haatbazaar.databinding.FragmentCategorySelectBinding;
import com.apptechbd.haatbazaar.interfaces.OnCategoryClickListener;
import com.apptechbd.haatbazaar.models.Category;
import com.apptechbd.haatbazaar.viewmodels.HomeViewModel;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class CategorySelectFragment extends Fragment implements OnCategoryClickListener {
    private FragmentCategorySelectBinding binding;
    private HomeViewModel viewModel;
    private List<String> categories;
    private ArrayList<Category> categoriesList;
    private ArrayList<Category> categoriesSelected = new ArrayList<>();
    private CategoriesAdapter adapter;

    public CategorySelectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategorySelectBinding.inflate(inflater, container, false);

        initViewModel();

        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.getCategories(viewModel.getAccount().getAdmin());
        viewModel.allCategories.observe(getViewLifecycleOwner(), allCategories -> {
            // Update the UI with the retrieved categories
            if (allCategories != null) {
                // Display the categories in the UI
                categories = allCategories.getCategories();
                setCategories();
            }
        });
    }

    private void setCategories(){
        categoriesList = new ArrayList<>();
        for (String s: categories){
            Category category = new Category(s, false);
            categoriesList.add(category);
        }
        binding.recyclerviewCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerviewCategories.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesList, this);
        binding.recyclerviewCategories.setAdapter(adapter);
    }

    @Override
    public void onCategoryClick(Category category) {
        // Handle category click event here
        for (int i = 0; i < categoriesList.size(); i++) {
            if (categoriesList.get(i).getCategoryName().equals(category.getCategoryName())) {
                if (categoriesList.get(i).isSelected()) {
                    categoriesList.get(i).setSelected(true);
                    categoriesSelected.add(categoriesList.get(i));
                } else {
                    categoriesList.get(i).setSelected(false);
                    categoriesSelected.remove(categoriesList.get(i));
                }
                adapter.notifyItemChanged(i);
            }
        }
        viewModel.setButtonEnabled(!categoriesSelected.isEmpty());
    }
}