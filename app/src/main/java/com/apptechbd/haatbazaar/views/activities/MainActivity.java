package com.apptechbd.haatbazaar.views.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.adapters.AccountsAdapter;
import com.apptechbd.haatbazaar.adapters.CategoriesAdapter;
import com.apptechbd.haatbazaar.databinding.ActivityAddAccountBinding;
import com.apptechbd.haatbazaar.databinding.ActivityMainBinding;
import com.apptechbd.haatbazaar.interfaces.OnCategoryClickListener;
import com.apptechbd.haatbazaar.models.Category;
import com.apptechbd.haatbazaar.utils.BaseActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends BaseActivity implements OnCategoryClickListener {
    private ActivityMainBinding binding;
    private ArrayList<Category> categories;
    private CategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater(), getWindow().getDecorView().findViewById(android.R.id.content), false);

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveLocale("bn");
        setLocale(new Locale("bn"));

        getDummyCategories();
    }

    private void getDummyCategories() {
        categories = new ArrayList<>();

        Category categoryCow = new Category("cow", false);
        Category categoryGoat = new Category("goat", false);
        Category categorySheep = new Category("sheep", false);
        Category categoryCamel = new Category("camel", false);

        categories.add(categoryCow);
        categories.add(categoryGoat);
        categories.add(categorySheep);
        categories.add(categoryCamel);

        setCategories(categories);
    }

    private void setCategories(ArrayList<Category> categories) {
        binding.recyclerviewCategories.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerviewCategories.setHasFixedSize(true);

        adapter = new CategoriesAdapter(categories, this);
        binding.recyclerviewCategories.setAdapter(adapter);
    }

    @Override
    public void onCategoryClick(Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryName().equals(category.getCategoryName())) {
                if (categories.get(i).isSelected())
                    categories.get(i).setSelected(true);
                else
                    categories.get(i).setSelected(false);
                adapter.notifyItemChanged(i);
            }
        }
    }
}