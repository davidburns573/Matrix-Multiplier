package com.example.matrixmultiplier;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button btnRestart;
    HashMap<Character, double[][]> matrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRestart = findViewById(R.id.btnRestart);

        matrices = new HashMap<>();
        getIntent().putExtra("matrices", matrices);

        Navigation.findNavController(findViewById(R.id.nav_host_fragment)).
                setGraph(R.navigation.nav_graph);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrices = new HashMap<>();
                getIntent().putExtra("matrices", matrices);
                Navigation.findNavController(findViewById(R.id.nav_host_fragment)).
                        setGraph(R.navigation.nav_graph);
            }
        });
    }
}
