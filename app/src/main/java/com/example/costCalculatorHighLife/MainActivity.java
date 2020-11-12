package com.example.costCalculatorHighLife;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import static com.example.costCalculatorHighLife.R.id;
import static com.example.costCalculatorHighLife.R.layout;
import static com.example.costCalculatorHighLife.R.string;


public class MainActivity extends AppCompatActivity {
    TextView costOutput1,costOutput2;
    EditText qty,cost;
    Button etr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        linkView();
        cost.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                //do what you want on the press of 'done'
                etr.performClick();
            }
            return false;
        });
    }

    private void linkView() {
        costOutput1 = (TextView) findViewById(id.costLine);
        costOutput2 = (TextView) findViewById(id.costLine2);
        qty = (EditText) findViewById(id.qty);
        cost = (EditText) findViewById(id.cost);
        etr = (Button)findViewById(R.id.runCalculation);
    }
    public void doSomethingMathish(View v) {
        closeKeyboard();
        final int id = v.getId();
        if (id == R.id.reset) {
            qty.setText("");
            cost.setText("");
            costOutput1.setText("");
            costOutput2.setText("");
        } else if (id == R.id.runCalculation) {
            String totalQtyString = qty.getText().toString();
            String totalCostString = cost.getText().toString();
            if (totalQtyString.matches("") && totalQtyString.matches("")) {
                Toast.makeText(this, "You did not enter anything at all..", Toast.LENGTH_SHORT).show();
                return;
            } else if (totalQtyString.matches("")) {
                Toast.makeText(this, "You did not enter a quantity", Toast.LENGTH_SHORT).show();
                return;
            } else if (totalCostString.matches("")) {
                Toast.makeText(this, "You did not enter a cost", Toast.LENGTH_SHORT).show();
                return;
            }
            double totalQty = Double.parseDouble(totalQtyString);
            double totalCost = (int) Math.round(Double.parseDouble(totalCostString));
            double itemCost = totalCost / totalQty;
            double roundedCost = roundThreeDecimals(itemCost);
            double oneLess = roundNoDecimals(totalQty) - 1;
            int oneLessInt = (int) roundNoDecimals(oneLess);
            double allButOne = roundedCost * oneLess;
            double finalItem = totalCost - allButOne;
            String itemCostString = roundedCost + " X " + oneLessInt;
            String finalItemString = roundThreeDecimals(finalItem) + " X 1";
            if (finalItem == roundedCost) {
                String allCostString = roundedCost + " X " + totalQty;
                costOutput1.setText(allCostString);
                costOutput2.setText(getString(string.tooEasy));
            } else if (((roundedCost * oneLess) + roundThreeDecimals(finalItem)) == totalCost) {
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
    public void closeKeyboard(){
        try {
            InputMethodManager editTextInput = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            editTextInput.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            Log.e("AndroidView", "closeKeyboard: "+e);
        }
    }
}