package com.apptechbd.haatbazaar.views.fragments.checkout;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.adapters.CategoriesAdapter;
import com.apptechbd.haatbazaar.databinding.FragmentSetCategoryBinding;
import com.apptechbd.haatbazaar.interfaces.OnCategoryClickListener;
import com.apptechbd.haatbazaar.models.Category;
import com.apptechbd.haatbazaar.utils.HelperClass;
import com.apptechbd.haatbazaar.viewmodels.HomeViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SetCategoryFragment extends Fragment implements OnCategoryClickListener {
    private FragmentSetCategoryBinding binding;
    private HomeViewModel viewModel;
    private List<String> categories;
    private ArrayList<Category> categoriesList;
    private ArrayList<Category> categoriesSelected = new ArrayList<>();
    private CategoriesAdapter adapter;
    private MaterialAlertDialogBuilder builder;
    private AlertDialog progressDialog;

    public SetCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetCategoryBinding.inflate(inflater, container, false);

        initViewModel();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.setButtonEnabled(false);
        viewModel.buttonText.setValue(getString(R.string.set_the_type));
    }

    private void initViewModel() {
        String title = getString(R.string.retrieving_categories_title);
        String disclaimer = getString(R.string.retrieving_categories_disclaimer);
        progressDialog = new HelperClass().showProgressDialog(builder, title, disclaimer, progressDialog, requireContext());
        categoriesSelected = new ArrayList<>();
        Log.d("SetCategoryFragment", "categories purchased = " + categoriesSelected.size());
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.getCategories(viewModel.getAccount().getAdmin());
        viewModel.allCategories.observe(getViewLifecycleOwner(), allCategories -> {
            progressDialog.dismiss();
            // Update the UI with the retrieved categories
            if (allCategories != null) {
                // Display the categories in the UI
                categories = allCategories.getCategories();
                setCategories();
            }
        });
    }

    private void setCategories() {
        categoriesList = new ArrayList<>();
        for (String s : categories) {
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
        if (!categoriesSelected.isEmpty()) {
            ArrayList<String> categoriesPurchased = new ArrayList<>();
            for (Category categorySelected : categoriesSelected)
                if (categorySelected.isSelected())
                    categoriesPurchased.add(categorySelected.getCategoryName());
            viewModel.setCategoriesPurchased(categoriesPurchased);
        }
    }
}