package com.example.lab9;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.Objects;

import math.Interpolation;

public class MainActivity extends AppCompatActivity {
    Graphic s;
    Integer currentXMin = null, currentXMax = null, currentPoints = 100;
    Runnable currentGraphic = this::drawCosGraph;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s = findViewById(R.id.graf);
        currentGraphic.run();

        findViewById(R.id.open).setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), SecondActivity.class);
            startActivityForResult(i, 10);
        });

        findViewById(R.id.choose).setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
            builderSingle.setTitle("Select One");

            String[] items = {"Cos", "Linear", "Parabola"};
            builderSingle.setItems(items, (dialog, which) -> {
                switch (items[which]) {
                    case "Cos":
                        currentGraphic = this::drawCosGraph;
                        break;
                    case "Linear":
                        currentGraphic = this::drawLinearGraph;
                        break;
                    case "Parabola":
                        currentGraphic = this::drawParabola;
                        break;
                }

                this.currentGraphic.run();
            });

            builderSingle.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            assert data != null;
            currentPoints = data.getIntExtra("n", 0);
            currentXMin = data.getIntExtra("xMin", 0);
            currentXMax = data.getIntExtra("xMax", 0);

            currentGraphic.run();
        }
    }

    public void drawCosGraph() {
        float[] x = new float[currentPoints];
        float[] y = new float[currentPoints];

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        for (int i = 0; i < currentPoints; i++) {
            x[i] = Interpolation.map(i, 0, currentPoints - 1, 0.0f, (float) Math.PI * 4);
            y[i] = (float) Math.cos(x[i]);
        }
        s.update(x, y, currentPoints, paint, currentXMin, currentXMax);
    }
    public void drawLinearGraph() {
        float[] x = new float[currentPoints];
        float[] y = new float[currentPoints];

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        for (int i = 0; i < currentPoints; i++) {
            x[i] = Interpolation.map(i, 0, currentPoints - 1, 0.0f, 100);
            y[i] = x[i];
        }
        s.update(x, y, currentPoints, paint, currentXMin, currentXMax);
    }
    public void drawParabola() {
        float[] x = new float[currentPoints];
        float[] y = new float[currentPoints];

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        for (int i = 0; i < currentPoints; i++) {
            x[i] = Interpolation.map(i, 0, currentPoints - 1, 0.0f, 100);
            y[i] = x[i] * x[i];
        }
        s.update(x, y, currentPoints, paint, currentXMin, currentXMax);
    }
}