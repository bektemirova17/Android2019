package com.example.gold;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mResult, mEdit;
    Button n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, plus, minus, div, mul, equ, ac, proc, c;
    int number1, number2;
    String sign = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResult = findViewById(R.id.tvResult);
        mEdit = findViewById(R.id.editText);
        n0 = findViewById(R.id.button0);
        n1 = findViewById(R.id.button1);
        n2 = findViewById(R.id.button2);
        n3 = findViewById(R.id.button3);
        n4 = findViewById(R.id.button4);
        n5 = findViewById(R.id.button5);
        n6 = findViewById(R.id.button6);
        n7 = findViewById(R.id.button7);
        n8 = findViewById(R.id.button8);
        n9 = findViewById(R.id.button9);
        plus = findViewById(R.id.buttonPlus);
        minus = findViewById(R.id.buttonMin);
        div = findViewById(R.id.buttonDiv);
        mul = findViewById(R.id.buttonMul);
        equ = findViewById(R.id.buttonEqu);
        proc = findViewById(R.id.buttonProc);
        ac = findViewById(R.id.buttonAC);
        c = findViewById(R.id.buttonC);

        n0.setOnClickListener(this);
        n1.setOnClickListener(this);
        n2.setOnClickListener(this);
        n3.setOnClickListener(this);
        n4.setOnClickListener(this);
        n5.setOnClickListener(this);
        n6.setOnClickListener(this);
        n7.setOnClickListener(this);
        n8.setOnClickListener(this);
        n9.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        mul.setOnClickListener(this);
        div.setOnClickListener(this);
        proc.setOnClickListener(this);
        equ.setOnClickListener(this);
        ac.setOnClickListener(this);
        c.setOnClickListener(this);
    }

    public void appendToEdit(String num){
        if (sign.equals("=")){
            clear();
        }
        if(mEdit.getText().toString().equals("0")) {
            if (!num.equals("0")){
                mEdit.setText("");
                mEdit.append(num);
            }
        }else{
            mEdit.append(num);
        }
    }
    public void clear(){
        mEdit.setText("");
        mResult.setText("");
        sign="";
    }
    public void signAdded(String signn){
        if (!mEdit.getText().toString().isEmpty()){
            number1 = Integer.parseInt(mEdit.getText().toString());
            mEdit.setText("");
            sign=signn;
            mResult.setText(number1+""+sign);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button0:
                appendToEdit("0");
                break;
            case R.id.button1:
                appendToEdit("1");
                break;
            case R.id.button2:
                appendToEdit("2");
                break;
            case R.id.button3:
                appendToEdit("3");
                break;
            case R.id.button4:
                appendToEdit("4");
                break;
            case R.id.button5:
                appendToEdit("5");
                break;
            case R.id.button6:
                appendToEdit("6");
                break;
            case R.id.button7:
                appendToEdit("7");
                break;
            case R.id.button8:
                appendToEdit("8");
                break;
            case R.id.button9:
                appendToEdit("9");
                break;
            case R.id.buttonAC:
                sign="AC";
                clear();
                break;
            case R.id.buttonC:
                if (!mEdit.getText().toString().isEmpty() && !sign.equals("="))
                        mEdit.setText(mEdit.getText().subSequence(0, mEdit.getText().length() - 1));
                break;
            case R.id.buttonPlus:
                signAdded("+");
                break;
            case R.id.buttonMin:
                signAdded("-");
                break;
            case R.id.buttonMul:
                signAdded("*");
                break;
            case R.id.buttonDiv:
                signAdded("/");
                break;
            case R.id.buttonProc:
                signAdded("%");
                break;
            case R.id.buttonEqu:
                if (!mEdit.getText().toString().isEmpty()) {
                    number2 = Integer.parseInt(mEdit.getText().toString());
                    int res;
                    switch (sign){
                        case "+":
                            res = number1+number2;
                            break;
                        case "-":
                            res = number1-number2;
                            break;
                        case "*":
                            res = number1*number2;
                            break;
                        case "/":
                            res = number1/number2;
                            break;
                        case "%":
                            res = number1*number2/100;
                            break;
                        default:
                            return;
                    }
                    mEdit.setText(res+"");
                    mResult.setText(number1+""+sign+""+number2);
                    sign="=";
                }
                break;
        }
    }
}
