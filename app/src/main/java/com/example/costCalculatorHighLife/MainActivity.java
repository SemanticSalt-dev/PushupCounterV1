package com.example.costCalculatorHighLife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.example.costCalculatorHighLife.R.id;
import static com.example.costCalculatorHighLife.R.layout;
import static com.example.costCalculatorHighLife.R.string;


public class MainActivity extends AppCompatActivity {
    TextView costOutput1,costOutput2;
    EditText qty,cost;
    ConstraintLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        linkView();
        root.setOnClickListener(view -> {
        });
    }

    private void linkView() {
        costOutput1 = (TextView) findViewById(id.costLine);
        costOutput2 = (TextView) findViewById(id.costLine2);
        qty = (EditText) findViewById(id.qty);
        cost = (EditText) findViewById(id.cost);
        cost.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
        root = (ConstraintLayout) findViewById(id.mainLayout);
    }
    public void doSomethingMathish(View v) {
        hideSoftKeyboard(cost);
        final int id = v.getId();
        if (id == R.id.reset) {
            qty.setText("");
            cost.setText("");
            costOutput1.setText("");
            costOutput2.setText("");
        } else if (id == R.id.runCalculation) {
            String totalQtyString = qty.getText().toString();
            String totalCostString = cost.getText().toString();
            double totalQty = Double.parseDouble(totalQtyString);
            double totalCost = Double.parseDouble(totalCostString);
            double itemCost = totalCost / totalQty;
            double roundedCost = roundThreeDecimals(itemCost);
            double oneLess = roundNoDecimals(totalQty) - 1;
            int oneLessInt = (int) roundNoDecimals(oneLess);
            double allButOne = roundedCost * oneLess;
            double finalItem = totalCost - allButOne;
            String itemCostString = roundedCost + " X " + oneLessInt;
            String finalItemString = roundThreeDecimals(finalItem) + " X 1";
            if (((roundedCost * oneLess) + roundThreeDecimals(finalItem)) == totalCost) {
                if (finalItem == roundedCost) {
                    String allCostString = roundedCost + " X " + totalQty;
                    costOutput1.setText(allCostString);
                    costOutput2.setText(getString(string.tooEasy));
                }

                Log.d("costCcostString", totalQtyString);
                Log.d("costCqtyString", totalCostString);
                Log.d("costCcostDoub", String.valueOf(totalQty));
                Log.d("costCqtyDoub", String.valueOf(totalCost));
                Log.d("costCindItem", String.valueOf(itemCost));
                costOutput1.setText(itemCostString);
                costOutput2.setText(finalItemString);
            } else {
                String errorString = (roundedCost * oneLess) + finalItem + " in not = to " + totalCost;
                costOutput1.setText(getString(string.errorWarning));
                costOutput2.setText(errorString);
            }
        }
    }
    double roundNoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return Double.parseDouble(twoDForm.format(d));
    }
    double roundThreeDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.###");
        return Double.parseDouble(twoDForm.format(d));
    }
    static class DecimalDigitsInputFilter implements InputFilter {
        private final Pattern mPattern;
        DecimalDigitsInputFilter() {
            mPattern = Pattern.compile("[0-9]*" + (6 - 1) + "}+((\\.[0-9]*" + (3 - 1) + "})?)|(\\.)?");
        }
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }
    }
    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}