package com.example.calculatorr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mResult, mEdit;
    Button n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, plus, minus, div, mul, equ, ac;
    int number1, number2, cnt = 0;
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
        ac = findViewById(R.id.buttonAC);

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
        equ.setOnClickListener(this);
        ac.setOnClickListener(this);
    }

    public void appendToEdit(String num){
        if (sign.equals("=")){
            clear();
        }
        mEdit.append(num);
        writeWithSpace();
    }

    public void writeWithSpace(){
        //
        String res = "";
        String text = removeSpaces();
        StringBuffer buffer = new StringBuffer(text);
        text = buffer.reverse().toString();
        int cnt = 0;
        for (int i = 0; i < text.length(); i++){
            if (cnt % 3 == 0) {
                res+= " ";
            }
            res+=text.charAt(i);
            cnt++;
        }
        StringBuffer buf = new StringBuffer(res);
        res = buf.reverse().toString();
        mEdit.setText(res);
    }

    public void clear(){
        mEdit.setText("");
        mResult.setText("");
        sign="";
    }
    public void signAdded(String signn){
        if (!mEdit.getText().toString().isEmpty()){
            number1 = Integer.parseInt(removeSpaces());
            mEdit.setText("");
            sign=signn;
            mResult.setText(number1+""+sign);
        }
    }

    public String removeSpaces(){
        StringBuilder res = new StringBuilder();
        StringTokenizer st = new StringTokenizer(mEdit.getText().toString(), " ");
        while (st.countTokens() != 0){
            res.append(st.nextToken());
        }
        return res.toString();
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
            case R.id.buttonEqu:
                if (!mEdit.getText().toString().isEmpty()) {
                    number2 = Integer.parseInt(removeSpaces());
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
                        default:
                            return;
                    }
                    mEdit.setText(res+"");
                    writeWithSpace();
                    mResult.setText(number1+""+sign+""+number2);
                    sign="=";
                }
                break;
        }
    }
}
