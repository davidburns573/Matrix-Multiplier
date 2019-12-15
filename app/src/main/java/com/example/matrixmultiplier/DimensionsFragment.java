package com.example.matrixmultiplier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class DimensionsFragment extends Fragment {

    private String type;
    private int numRows;
    private int numCols;

    private Button btnEnterDimensions;
    private Button btnRowUpArrow;
    private Button btnRowDownArrow;
    private Button btnColUpArrow;
    private Button btnColDownArrow;
    private EditText etNumberRows;
    private EditText etNumberCols;
    private DialogFragment dialog;
    private LinearLayout layout;

    public DimensionsFragment(String type) {
        this.type = type;
        numRows = 1;
        numCols = 1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        layout = view.findViewById(R.id.dimensions_fragment);

        btnEnterDimensions = view.findViewById(R.id.btnEnterDimensions);
        btnRowUpArrow = view.findViewById(R.id.btnRowUpArrow);
        btnRowDownArrow = view.findViewById(R.id.btnRowDownArrow);
        btnColUpArrow = view.findViewById(R.id.btnColUpArrow);
        btnColDownArrow = view.findViewById(R.id.btnColDownArrow);
        etNumberRows = view.findViewById(R.id.etNumberRows);
        etNumberCols = view.findViewById(R.id.etNumberCols);

        btnRowDownArrow.setEnabled(false);
        btnColDownArrow.setEnabled(false);

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

                NavDirections action =
                        DimensionsFragmentDirections
                                .actionDimensionsFragmentToInputValuesFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    public String getType() {
        return type;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }
}
