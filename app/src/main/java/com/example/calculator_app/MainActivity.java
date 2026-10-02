package com.example.calculator_app;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtDisplay, txtExpression;

    String currentNumber = "";
    double firstNumber = 0;
    String operator = "";
    boolean newNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button[] allButtons = {
                findViewById(R.id.btnClear),
                findViewById(R.id.btnDelete),
                findViewById(R.id.btnPercent),
                findViewById(R.id.btnDivide),
                findViewById(R.id.btn7),
                findViewById(R.id.btn8),
                findViewById(R.id.btn9),
                findViewById(R.id.btnMultiply),
                findViewById(R.id.btn4),
                findViewById(R.id.btn5),
                findViewById(R.id.btn6),
                findViewById(R.id.btnSubtract),
                findViewById(R.id.btn1),
                findViewById(R.id.btn2),
                findViewById(R.id.btn3),
                findViewById(R.id.btnAdd),
                findViewById(R.id.btnZero),
                findViewById(R.id.btnDecimal),
                findViewById(R.id.btnEquals)
        };

        for (Button button : allButtons) {
            button.setBackgroundTintList(null);
        }

        txtDisplay = findViewById(R.id.txtDisplay);
        txtExpression = findViewById(R.id.txtExpression);

        int[] numberButtons = {
                R.id.btnZero, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9
        };

        View.OnClickListener numberListener = view -> {
            Button button = (Button) view;

            if (newNumber) {
                currentNumber = "";
                newNumber = false;
            }

            currentNumber += button.getText().toString();
            txtDisplay.setText(currentNumber);
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(numberListener);
        }

        findViewById(R.id.btnDecimal).setOnClickListener(view -> {

            if (newNumber) {
                currentNumber = "0";
                newNumber = false;
            }

            if (!currentNumber.contains(".")) {
                currentNumber += ".";
                txtDisplay.setText(currentNumber);
            }
        });

        findViewById(R.id.btnAdd).setOnClickListener(view -> setOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(view -> setOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(view -> setOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(view -> setOperator("/"));

        findViewById(R.id.btnEquals).setOnClickListener(view -> calculate());

        findViewById(R.id.btnClear).setOnClickListener(view -> clear());

        findViewById(R.id.btnDelete).setOnClickListener(view -> {

            if (currentNumber.length() > 0) {
                currentNumber =
                        currentNumber.substring(0, currentNumber.length() - 1);

                if (currentNumber.isEmpty()) {
                    txtDisplay.setText("0");
                } else {
                    txtDisplay.setText(currentNumber);
                }
            }
        });

        findViewById(R.id.btnPercent).setOnClickListener(view -> {

            if (!currentNumber.isEmpty()) {
                double number = Double.parseDouble(currentNumber);
                number = number / 100;

                currentNumber = formatNumber(number);
                txtDisplay.setText(currentNumber);
            }
        });
    }

    private void setOperator(String selectedOperator) {

        if (currentNumber.isEmpty()) {
            return;
        }

        firstNumber = Double.parseDouble(currentNumber);
        operator = selectedOperator;

        txtExpression.setText(
                formatNumber(firstNumber) + " " + selectedOperator
        );

        newNumber = true;
    }

    private void calculate() {

        if (currentNumber.isEmpty() || operator.isEmpty()) {
            return;
        }

        double secondNumber = Double.parseDouble(currentNumber);
        double result = 0;

        switch (operator) {

            case "+":
                result = firstNumber + secondNumber;
                break;

            case "-":
                result = firstNumber - secondNumber;
                break;

            case "*":
                result = firstNumber * secondNumber;
                break;

            case "/":

                if (secondNumber == 0) {
                    txtDisplay.setText("Error");
                    return;
                }

                result = firstNumber / secondNumber;
                break;
        }

        txtExpression.setText(
                formatNumber(firstNumber) + " "
                        + operator + " "
                        + formatNumber(secondNumber)
                        + " ="
        );

        currentNumber = formatNumber(result);
        txtDisplay.setText(currentNumber);

        operator = "";
        newNumber = true;
    }

    private void clear() {

        currentNumber = "";
        firstNumber = 0;
        operator = "";

        txtDisplay.setText("0");
        txtExpression.setText("");

        newNumber = true;
    }

    private String formatNumber(double number) {

        if (number == (long) number) {
            return String.valueOf((long) number);
        }

        return String.valueOf(number);
    }
}