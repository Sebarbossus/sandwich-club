package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView tv_description;
    TextView tv_origin;
    TextView tv_ingredients;
    TextView tv_also_known;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String description = sandwich.getDescription();
        String origin = sandwich.getPlaceOfOrigin();
        List<String> ingredients = sandwich.getIngredients();
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        tv_description = (TextView) findViewById(R.id.description_tv);
        tv_origin = (TextView) findViewById(R.id.origin_tv);
        tv_ingredients = (TextView) findViewById(R.id.ingredients_tv);
        tv_also_known = (TextView) findViewById(R.id.also_known_tv);

        tv_description.setText(description);
        tv_origin.setText(origin);
        for (String ingredient : ingredients) {
            tv_ingredients.append("\u2022" + ingredient + '\n');
        }

        for (String dishName : alsoKnownAs) {
            tv_also_known.append("\u2022" + dishName + '\n');
        }
    }
}
