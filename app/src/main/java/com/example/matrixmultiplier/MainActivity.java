package com.example.matrixmultiplier;

import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {

    TextView tvATimesBHeader;
    EditText[][] matrix;
    TextView[][] matrixDisplay;
    Button btnRestart;
    DialogFragment dialog;
    double[][] matrixA;
    double[][] matrixB;
    double[][] matrixAB;
    String matrixABStr;

    int numRows = 0;
    int numCols = 0;
    final int NUM_DIGITS = 7;
    int highestDigits = NUM_DIGITS;

    int enterValuesInc = 0;
    int enterDimensionsInc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvATimesBHeader = findViewById(R.id.tvATimesBHeader);
        btnRestart = findViewById(R.id.btnRestart);

//        DimensionsFragment dimensionsFragment = new DimensionsFragment("A");
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_layout_dimensions_fragment, dimensionsFragment);
//        fragmentTransaction.commit();
//
//        InputValuesFragment inputValuesFragment= new InputValuesFragment();
//        fragmentManager = this.getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_layout_input_values_fragment, inputValuesFragment);
//        fragmentTransaction.commit();

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrixA = null;
                matrixB = null;
                enterDimensionsInc = 0;
                enterValuesInc = 0;
                numRows = 0;
                numCols = 0;
                matrixABStr = "";
                if (matrix != null) {
                    for (EditText[] rows : matrix) {
                        for (EditText i : rows) {
                            i.setVisibility(View.GONE);
                        }
                    }
                }
                if (matrixDisplay != null) {
                    for (TextView[] rows : matrixDisplay) {
                        for (TextView tv : rows) {
                            tv.setVisibility(View.GONE);
                        }
                    }
                }
                matrix = null;
                findViewById(R.id.row_horizontal_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.col_horizontal_layout).setVisibility(View.VISIBLE);
                tvATimesBHeader.setVisibility(View.GONE);
                highestDigits = 7;
            }
        });
    }

    private void buildEditTextMatrix() {
        matrix = new EditText[numRows][numCols];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new EditText(this);
                matrix[i][j].setInputType(InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL |
                        InputType.TYPE_NUMBER_FLAG_SIGNED);
                matrix[i][j].setTextColor(getResources().getColor(R.color.colorPrimary));
                matrix[i][j].setWidth(350);
                matrix[i][j].setMinHeight(125);
                matrix[i][j].setMaxHeight(125);
                matrix[i][j].setBackgroundColor(getResources().getColor(R.color.white));
                matrix[i][j].setTextSize(20);
                matrix[i][j].setTypeface(Typeface.MONOSPACE);
                matrix[i][j].setGravity(Gravity.CENTER);
                matrix[i][j].setBackground(getResources().getDrawable(R.drawable.border));
            }
        }
    }

    private void buildTextViewDisplayMatrix() {
        matrixDisplay = new TextView[matrixAB.length][matrixAB[0].length];

        int width = 350;
        for (int i = 0; i < highestDigits - NUM_DIGITS; i++) {
                width += 45;
        }
        for (int i = 0; i < matrixDisplay.length; i++) {
            for (int j = 0; j < matrixDisplay[0].length; j++) {
                matrixDisplay[i][j] = new TextView(this);
                if ((matrixAB[i][j] == Math.floor(matrixAB[i][j])) &&
                        !Double.isInfinite(matrixAB[i][j])) {
                    matrixDisplay[i][j].setText(Integer.toString((int) matrixAB[i][j]));
                } else {
                    matrixDisplay[i][j].setText(Double.toString(matrixAB[i][j]));
                }
                matrixDisplay[i][j].setTextColor(getResources().getColor(R.color.colorPrimary));
                matrixDisplay[i][j].setMinWidth(width);
                matrixDisplay[i][j].setMinHeight(125);
                matrixDisplay[i][j].setMaxHeight(125);
                matrixDisplay[i][j].setBackgroundColor(getResources().getColor(R.color.white));
                matrixDisplay[i][j].setTextSize(20);
                matrixDisplay[i][j].setTypeface(Typeface.MONOSPACE);
                matrixDisplay[i][j].setGravity(Gravity.CENTER);
                matrixDisplay[i][j].setBackground(getResources().getDrawable(R.drawable.border));
            }
        }
    }

    private void addEditTextMatrix() {
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        LinearLayout vertiLayout = new LinearLayout(this);
        vertiLayout.setOrientation(LinearLayout.VERTICAL);
        vertiLayout.setGravity(Gravity.CENTER);
        LinearLayout horizLayout;
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_main);
        for (int i = 0; i < matrix.length; i++) {
            horizLayout = new LinearLayout(this);
            horizLayout.setGravity(Gravity.CENTER);
            horizLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < matrix[0].length; j++) {
                horizLayout.addView(matrix[i][j]);
            }
            vertiLayout.addView(horizLayout);
        }
        hsv.setFillViewport(true);
        hsv.addView(vertiLayout);
        layout.addView(hsv);
    }

    private void addTextViewMatrix() {
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        LinearLayout vertiLayout = new LinearLayout(this);
        vertiLayout.setOrientation(LinearLayout.VERTICAL);
        vertiLayout.setGravity(Gravity.CENTER);
        LinearLayout horizLayout;
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_main);
        for (TextView[] tvArray : matrixDisplay) {
            horizLayout = new LinearLayout(this);
            horizLayout.setGravity(Gravity.CENTER);
            horizLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (TextView tv : tvArray) {
                horizLayout.addView(tv);
            }
            vertiLayout.addView(horizLayout);
        }
        hsv.setFillViewport(true);
        hsv.addView(vertiLayout);
        layout.addView(hsv);
    }

    private boolean getInputValuesA() {
        matrixA = new double[numRows][numCols];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].getText().toString().equals("")) {
                    dialog = new MyDialogFragment("Please do not leave elements blank");
                    dialog.show(getSupportFragmentManager(), "MyDialogFragmentTag");
                    return false;
                } else {
                    try {
                        matrixA[i][j] = Double.parseDouble(matrix[i][j].getText().toString());
                    } catch (NumberFormatException ex) {
                        matrix[i][j].setText("");
                        dialog = new MyDialogFragment("Please enter decimal values only");
                        dialog.show(getSupportFragmentManager(), "MyDialogFragmentTag");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean getInputValuesB() {
        matrixB = new double[numRows][numCols];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].getText().toString().equals("")) {
                    dialog = new MyDialogFragment("Please do not leave elements blank");
                    dialog.show(getSupportFragmentManager(), "MyDialogFragmentTag");
                    return false;
                } else {
                    try {
                        matrixB[i][j] = Double.parseDouble(matrix[i][j].getText().toString());
                    } catch (NumberFormatException ex) {
                        matrix[i][j].setText("");
                        dialog = new MyDialogFragment("Please enter decimal values only");
                        dialog.show(getSupportFragmentManager(), "MyDialogFragmentTag");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void multiply() {
        matrixAB = new double[matrixA.length][matrixB[0].length];
        double data;
        for (int row = 0; row < matrixAB.length; row++) {
            for (int col = 0; col < matrixAB[0].length; col++) {
                data = 0;
                for (int i = 0; i < matrixA[0].length; i++) {
                    data += (matrixA[row][i] * matrixB[i][col]);
                }
                int length = Double.toString(data).length();
                if (length > highestDigits) {
                    highestDigits = length;
                }
                matrixAB[row][col] = data;
            }
        }
    }

    public void createStringFromMatrix() {
        int longestNum = 0;
        String str;
        matrixABStr = "";
        for (double[] cols : matrixAB) {
            for (double i : cols) {
                str = Double.toString(i);
                if (str.length() > longestNum) {
                    longestNum = str.length();
                }
            }
        }
        for (double[] cols : matrixAB) {
            for (double i : cols) {
                str = Double.toString(i);
                matrixABStr += str + " ";
            }
            matrixABStr += "\n";
        }
    }
}
