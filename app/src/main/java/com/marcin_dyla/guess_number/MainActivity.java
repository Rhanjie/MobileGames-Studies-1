package com.marcin_dyla.guess_number;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public enum Difficulty {
        EASY, MEDIUM, HARD, VERY_HARD
    }

    private final Map<Difficulty, Integer> difficulties = new HashMap<Difficulty, Integer>() {{
        put(Difficulty.EASY, 10);
        put(Difficulty.MEDIUM, 100);
        put(Difficulty.HARD, 250);
        put(Difficulty.VERY_HARD, 500);
    }};

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActivity();
        addEventToSpinner();

        game = new Game();
        createNewGame();
    }

    private void initActivity() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEventToSpinner() {
        Spinner spinner = findViewById(R.id.SpinnerLevels);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                createNewGame();
            }

            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    public void OnStartClick(View view) {
        createNewGame();
    }

    public void OnApplyClick(View view) {
        EditText editText = findViewById(R.id.editTextValue);
        TextView textView = findViewById(R.id.textViewInfo);

        try {
            int value = Integer.parseInt(editText.getText().toString() );
            int comparison = game.check(value);
            int amount = game.getAttemptsAmount();

            String information;
            if (comparison == 0) {
                information = getString(R.string.Level);

                OnStartClick(view);
            }
            else if (comparison < 0)
                information = getString(R.string.TooFew);

            else information = getString(R.string.TooMany);

            String info = String.format(getString(R.string.AttemptsAmount), information, amount);
            textView.setText(info);
        }
        catch (Exception exception){
            textView.setText(exception.getMessage());
        }
    }

    private void createNewGame() {
        EditText editText = findViewById(R.id.editTextValue);
        TextView textView = findViewById(R.id.textViewInfo);
        Spinner spinner = findViewById(R.id.SpinnerLevels);

        editText.setText("");

        Difficulty currentDifficulty = Difficulty.values()[spinner.getSelectedItemPosition()];
        int bounds = difficulties.get(currentDifficulty);

        String selectedLevelInfo = String.format(getString(R.string.SelectedLevel), currentDifficulty.toString());
        String tipInfo = String.format(getString(R.string.Tip), 0, bounds);

        textView.setText(selectedLevelInfo + "\n" + tipInfo);

        game.newGame(bounds);
    }
}