package com.example.matrixmultiplier;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.HashMap;

public class DimensionsFragment extends Fragment {

    private int numRows;
    private int numCols;
    private HashMap<Character, double[][]> matrices;
    HashMap<Integer, String> characterHashMap;
    private String type;

    private Button btnEnterDimensions;
    private Button btnRowUpArrow;
    private Button btnRowDownArrow;
    private Button btnColUpArrow;
    private Button btnColDownArrow;
    private EditText etNumberRows;
    private EditText etNumberCols;
    private DialogFragment dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getActivity().getIntent();
        matrices = (HashMap<Character, double[][]>) i.getSerializableExtra("matrices");
        numCols = 1;
        numRows = 1;
        characterHashMap = new HashMap<>();
        initializeCharacterHashMap();
        type = characterHashMap.get(matrices.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dimensions_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEnterDimensions = view.findViewById(R.id.btnEnterDimensions);
        btnRowUpArrow = view.findViewById(R.id.btnRowUpArrow);
        btnRowDownArrow = view.findViewById(R.id.btnRowDownArrow);
        btnColUpArrow = view.findViewById(R.id.btnColUpArrow);
        btnColDownArrow = view.findViewById(R.id.btnColDownArrow);
        etNumberRows = view.findViewById(R.id.etNumberRows);
        etNumberCols = view.findViewById(R.id.etNumberCols);

        btnEnterDimensions.setText("Enter Dimensions for Matrix " + type);

        btnRowDownArrow.setEnabled(false);
        btnColDownArrow.setEnabled(false);

        int size = matrices.size();
        if (size > 0) {
            btnRowUpArrow.setEnabled(false);
            char c = characterHashMap.get(size - 1).charAt(0);
            double[][] lastMatrix = matrices.get(c);
            etNumberRows.setText(Integer.toString(lastMatrix[0].length));
            etNumberRows.setEnabled(false);
        }

        btnRowUpArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(etNumberRows.getText().toString()) + 1;
                if (num > 1) {
                    btnRowDownArrow.setEnabled(true);
                } else {
                    btnRowDownArrow.setEnabled(false);
                }
                etNumberRows.setText(Integer.toString(num));
            }
        });

        btnRowDownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(etNumberRows.getText().toString()) - 1;
                if (num <= 1) {
                    btnRowDownArrow.setEnabled(false);
                }
                etNumberRows.setText(Integer.toString(num));
            }
        });

        btnColUpArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(etNumberCols.getText().toString()) + 1;
                if (num > 1) {
                    btnColDownArrow.setEnabled(true);
                } else {
                    btnColDownArrow.setEnabled(false);
                }
                etNumberCols.setText(Integer.toString(num));
            }
        });

        btnColDownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(etNumberCols.getText().toString()) - 1;
                if (num <= 1) {
                    btnColDownArrow.setEnabled(false);
                }
                etNumberCols.setText(Integer.toString(num));            }
        });

        btnEnterDimensions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    numRows = Integer.parseInt(etNumberRows.getText().toString());
                    numCols = Integer.parseInt(etNumberCols.getText().toString());
                    if (numRows == 0 || numCols == 0) {
                        dialog = new MyDialogFragment("Cannot have zero rows or columns");
                        dialog.show(getActivity().getSupportFragmentManager(), "MyDialogFragmentTag");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    dialog = new MyDialogFragment("Must enter a positive integer only");
                    dialog.show(getActivity().getSupportFragmentManager(), "MyDialogFragmentTag");
                    etNumberRows.setText(R.string._1);
                    etNumberCols.setText(R.string._1);
                    return;
                }

                String type = characterHashMap.get(matrices.size());
                Bundle bundle = new Bundle();
                bundle.putInt("numRows", numRows);
                bundle.putInt("numCols", numCols);
                bundle.putString("type", type);

//                NavDirections action =
//                        DimensionsFragmentDirections
//                                .actionDimensionsFragmentToInputValuesFragment();
                Navigation.findNavController(view).
                        navigate(R.id.action_dimensionsFragment_to_inputValuesFragment, bundle);
            }
        });
    }

    private void initializeCharacterHashMap() {
        characterHashMap.put(0,"A");
        characterHashMap.put(1,"B");
        characterHashMap.put(2,"C");
        characterHashMap.put(3,"D");
        characterHashMap.put(4,"E");
        characterHashMap.put(5,"F");
        characterHashMap.put(6,"G");
        characterHashMap.put(7,"H");
        characterHashMap.put(8,"I");
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }
}
