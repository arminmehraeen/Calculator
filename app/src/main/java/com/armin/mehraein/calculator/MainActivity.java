package com.armin.mehraein.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_c, btn_ce;
    Button btn_del, btn_dat, btn_sign;
    ImageView btn_taghsim, btn_zarb, btn_menha, btn_jam, btn_mosavi;
    TextView txt_result, txt_history;
    boolean mustRest = false;
    float currentResult = 0;
    String oprand = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_c = findViewById(R.id.btn_c);
        btn_ce = findViewById(R.id.btn_ce);
        btn_del = findViewById(R.id.btn_del);
        btn_dat = findViewById(R.id.btn_dat);
        btn_sign = findViewById(R.id.btn_sign);
        btn_taghsim = findViewById(R.id.btn_taghsim);
        btn_zarb = findViewById(R.id.btn_zarb);
        btn_menha = findViewById(R.id.btn_menha);
        btn_jam = findViewById(R.id.btn_jam);
        btn_mosavi = findViewById(R.id.btn_mosavi);
        txt_result = findViewById(R.id.txt_1);
        txt_history = findViewById(R.id.txt_2);

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSign();
            }
        });
        btn_dat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint();
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backOnLetter();
            }
        });
        btn_mosavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processEqual();
            }
        });
        btn_ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_result.setText("0");
            }
        });
        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_result.setText("0");
                txt_history.setText("");
                currentResult = 0;
                mustRest = false;
            }
        });
        View.OnClickListener btnNumberic = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(view.getTag().toString());
                appendNumber(number);
            }
        };
        btn_0.setOnClickListener(btnNumberic);
        btn_1.setOnClickListener(btnNumberic);
        btn_2.setOnClickListener(btnNumberic);
        btn_3.setOnClickListener(btnNumberic);
        btn_4.setOnClickListener(btnNumberic);
        btn_5.setOnClickListener(btnNumberic);
        btn_6.setOnClickListener(btnNumberic);
        btn_7.setOnClickListener(btnNumberic);
        btn_8.setOnClickListener(btnNumberic);
        btn_9.setOnClickListener(btnNumberic);

        View.OnClickListener btnOperand = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn_oprand = view.getTag().toString();
                doOperand(btn_oprand);
            }
        };
        btn_jam.setOnClickListener(btnOperand);
        btn_zarb.setOnClickListener(btnOperand);
        btn_menha.setOnClickListener(btnOperand);
        btn_taghsim.setOnClickListener(btnOperand);
    }

    private void toggleSign() {
        String resultValue = txt_result.getText().toString();
        if (resultValue.contains("-")){
            resultValue = resultValue.replace("-","");
        }else {
            resultValue = "-" + resultValue ;
        }
        txt_result.setText(resultValue);
    }

    private void addPoint() {
        String oldValue = txt_result.getText().toString();
        if (mustRest){
            Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
            txt_result.setText("0.");
            mustRest = false ;
        }
        if (oldValue.contains(".")){
            return;
        }
        if (oldValue.length() >= 10) {
            return;
        }
        if (oldValue.length() == 0) {
            txt_result.setText("0.");
        } else {
            txt_result.setText(oldValue + ".");
        }
    }

    private void backOnLetter() {
        String resultValue = txt_result.getText().toString();
        String newValue = resultValue.substring(0, resultValue.length() - 1);
        txt_result.setText(newValue);
    }

    private void processEqual() {
        if (currentResult == 0) return;
        else {
            String resultValue = txt_result.getText().toString();
            float resultNumber = Float.parseFloat(resultValue);
            if (oprand.equals("+")) {
                currentResult += resultNumber;
            } else if (oprand.equals("-")) {
                currentResult -= resultNumber;
            } else if (oprand.equals("x")) {
                currentResult *= resultNumber;
            } else if (oprand.equals("/")) {
                currentResult /= resultNumber;
            } else if (oprand.equals("")) {
                currentResult = resultNumber;
            }
            txt_result.setText("" + currentResult);
            txt_history.setText("");
            oprand = "";
            currentResult = 0;
            mustRest = true ;
        }
    }

    private void doOperand(String nextOprand) {
        int count = 0;
        String resultValue = txt_result.getText().toString();
        float resultNumber = Float.parseFloat(resultValue);
        txt_history.setText(txt_history.getText().toString() + " " + resultValue + " " + nextOprand);
        String txtHistoryValue = txt_history.getText().toString();
        for (int i = 0; i < txtHistoryValue.length(); i++) {
            if (txtHistoryValue.charAt(i) == '+' ||
                    txtHistoryValue.charAt(i) == '/' ||
                    txtHistoryValue.charAt(i) == '-' ||
                    txtHistoryValue.charAt(i) == 'x') {
                count++;
            }
        }
        //Toast.makeText(this, "Count = " + count, Toast.LENGTH_SHORT).show();
        if (count == 1) {
            txt_result.setText(resultValue);
            currentResult = resultNumber;
            mustRest = true ;
        } else {
            if (oprand.equals("+")) {
                currentResult += resultNumber;
            } else if (oprand.equals("-")) {
                currentResult -= resultNumber;
            } else if (oprand.equals("x")) {
                currentResult *= resultNumber;
            } else if (oprand.equals("/")) {
                currentResult /= resultNumber;
            } else if (oprand.equals("")) {
                currentResult = resultNumber;
            }
            txt_result.setText("" + currentResult);
            mustRest = true ;
        }
        oprand = nextOprand;
        mustRest = true;
    }

    private void appendNumber(int number) {
        String oldValue = txt_result.getText().toString();
        if (mustRest) {
            oldValue = "";
            mustRest = false;
        }
        if (oldValue.length() >= 10) {
            return;
        }
        if (oldValue.equals("-0")){
            oldValue = "-";
        }
        if (oldValue.equals("0")) {
            if (number == 0) {
                return;
            } else {
                oldValue = "";
            }
        }
        txt_result.setText(oldValue + number);
    }
}
