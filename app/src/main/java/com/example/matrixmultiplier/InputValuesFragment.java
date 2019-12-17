package com.example.matrixmultiplier;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.util.HashMap;

public class InputValuesFragment extends Fragment {

    private Button btnEnterValues;
    private Button btnCalculate;
    private DialogFragment dialog;
    private EditText[][] matrix;
    private LinearLayout mainHorizLayout;

    private InputValuesFragmentArgs args;
    private int numRows;
    private int numCols;
    private String type;
    private HashMap<Character, double[][]> matrices;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = InputValuesFragmentArgs.fromBundle(requireArguments());
        numRows = args.getNumRows();
        numCols = args.getNumCols();
        type = args.getType();
        Intent i = getActivity().getIntent();
        matrices = (HashMap<Character, double[][]>) i.getSerializableExtra("matrices");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.input_values_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEnterValues = view.findViewById(R.id.btnEnterValues);
        btnEnterValues.setText("Enter the values for matrix " + type);
        btnCalculate = view.findViewById(R.id.btnCalculate);
        mainHorizLayout = view.findViewById(R.id.display_matrices_list);

        buildEditTextMatrix(numRows, numCols);
        addEditTextMatrix(view);

        if (matrices.size() > 0) {
            btnCalculate.setVisibility(View.VISIBLE);
            btnEnterValues.setText(R.string.add_another_matrix);
        } else {
            btnCalculate.setVisibility(View.GONE);
            btnEnterValues.setText(R.string.enter_values);
        }

        btnEnterValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double[][] matrix = getInputValues();
                if (matrix == null) {
                    return;
                }
                if (matrices.size() > 6) {
                    dialog = new MyDialogFragment("You've reached the maximum amount of matrices!");
                    dialog.show(getActivity().getSupportFragmentManager(), "MyDialogFragmentTag");
                    return;
                }
                matrices.put(type.charAt(0), matrix);
                TextView tvName = new TextView(getActivity());
                tvName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvName.setText("[" + type.charAt(0) + "]");
                NavDirections action = InputValuesFragmentDirections.
                        actionInputValuesFragmentToDimensionsFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double[][] matrix = getInputValues();
                if (matrix == null) {
                    return;
                }
                matrices.put(type.charAt(0), matrix);
                if (matrices.size() <= 1) {
                    dialog = new MyDialogFragment("Must enter another matrix");
                    dialog.show(getActivity().getSupportFragmentManager(), "MyDialogFragmentTag");
                    return;
                }
                NavDirections action = InputValuesFragmentDirections.
                        actionInputValuesFragmentToDisplayFinalMatrixFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void buildEditTextMatrix(int numRows, int numCols) {
        matrix = new EditText[numRows][numCols];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new EditText(getActivity());
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

    private void addEditTextMatrix(View view) {
        HorizontalScrollView hsv = view.findViewById(R.id.horizontal_scroll_view_matrix);
        LinearLayout vertiLayout = new LinearLayout(getActivity());
        vertiLayout.setOrientation(LinearLayout.VERTICAL);
        vertiLayout.setGravity(Gravity.CENTER);
        LinearLayout horizLayout;
        for (int i = 0; i < matrix.length; i++) {
            horizLayout = new LinearLayout(getActivity());
            horizLayout.setGravity(Gravity.CENTER);
            horizLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < matrix[0].length; j++) {
                horizLayout.addView(matrix[i][j]);
            }
            vertiLayout.addView(horizLayout);
        }
        hsv.addView(vertiLayout);
    }

    private double[][] getInputValues() {
        double[][] matrixNum = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].getText().toString().equals("")) {
                    dialog = new MyDialogFragment("Please do not leave elements blank");
                    dialog.show(getActivity().getSupportFragmentManager(), "MyDialogFragmentTag");
                    return null;
                } else {
                    try {
                        matrixNum[i][j] = Double.parseDouble(matrix[i][j].getText().toString());
                    } catch (NumberFormatException ex) {
                        matrix[i][j].setText("");
                        dialog = new MyDialogFragment("Please enter decimal values only");
                        dialog.show(getActivity().getSupportFragmentManager(), "MyDialogFragmentTag");
                        return null;
                    }
                }
            }
        }
        return matrixNum;
    }
}
