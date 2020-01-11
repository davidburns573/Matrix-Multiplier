package com.example.matrixmultiplier;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

public class DisplayFinalMatrixFragment extends Fragment {

    TextView tvHeader;
    TextView[][] matrixDisplay;
    HashMap<Character,double[][]> matrices;
    HashMap<Integer, Character> characterHashMap;

    private final int NUM_DIGITS = 7;
    private int highestDigits = 7;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getActivity().getIntent();
        matrices = (HashMap<Character, double[][]>) i.getSerializableExtra("matrices");
        characterHashMap = new HashMap<>();
        initializeCharacterHashMap();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.display_final_matrix_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.tvHeader);
        String str = createDisplayHeader();
        tvHeader.setText(str);

        double[][] matrixAB = multiply(matrices.get('A'), matrices.get('B'));

        if (matrices.size() > 2) {
            for (int i = 2; i < matrices.size(); i++) {
                matrixAB = multiply(matrixAB, matrices.get(characterHashMap.get(i)));
            }
        }

        buildTextViewDisplayMatrix(matrixAB);
        addTextViewMatrix(view);
    }

    private void buildTextViewDisplayMatrix(double[][] matrix) {
        matrixDisplay = new TextView[matrix.length][matrix[0].length];

        int width = 350;
        for (int i = 0; i < highestDigits - NUM_DIGITS; i++) {
            width += 40;
        }
        for (int i = 0; i < matrixDisplay.length; i++) {
            for (int j = 0; j < matrixDisplay[0].length; j++) {
                matrixDisplay[i][j] = new TextView(getActivity());
                if ((matrix[i][j] == Math.floor(matrix[i][j])) &&
                        !Double.isInfinite(matrix[i][j])) {
                    matrixDisplay[i][j].setText(Integer.toString((int) matrix[i][j]));
                } else {
                    matrixDisplay[i][j].setText(Double.toString(matrix[i][j]));
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

    private void addTextViewMatrix(View view) {
        HorizontalScrollView hsv = view.findViewById(R.id.horizontal_scroll_view_matrix_display);
        LinearLayout vertiLayout = new LinearLayout(getActivity());
        vertiLayout.setOrientation(LinearLayout.VERTICAL);
        vertiLayout.setGravity(Gravity.CENTER);
        LinearLayout horizLayout;
        for (TextView[] tvArray : matrixDisplay) {
            horizLayout = new LinearLayout(getActivity());
            horizLayout.setGravity(Gravity.CENTER);
            horizLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (TextView tv : tvArray) {
                horizLayout.addView(tv);
            }
            vertiLayout.addView(horizLayout);
        }
        hsv.addView(vertiLayout);
    }

    private double[][] multiply(double[][] matrixA, double matrixB[][]) {
        double[][] matrixAB = new double[matrixA.length][matrixB[0].length];
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
        return matrixAB;
    }

    private void initializeCharacterHashMap() {
        characterHashMap.put(0,'A');
        characterHashMap.put(1,'B');
        characterHashMap.put(2,'C');
        characterHashMap.put(3,'D');
        characterHashMap.put(4,'E');
        characterHashMap.put(5,'F');
        characterHashMap.put(6,'G');
        characterHashMap.put(7,'H');
        characterHashMap.put(8,'I');
    }

    private String createDisplayHeader() {
        String str = "";

        int itr = 0;
        for (Character c : matrices.keySet()) {
            str += "["+ c + "]";
            if (itr < matrices.size() - 1) {
                str += " * ";
            }
            itr++;
        }

        str += " =";

        return str;
    }
}
