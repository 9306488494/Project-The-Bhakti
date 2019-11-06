package com.thebhakti;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PayuMoney extends AppCompatActivity {
    EditText fname, pnumber, emailAddress, rechargeAmt;
    Button Paynow;
    String newAmount,IMEI;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payu_main);

        fname        = (EditText)findViewById(R.id.fname);
        pnumber      = (EditText)findViewById(R.id.pnumber);
        emailAddress = (EditText)findViewById(R.id.emailAddress);
        rechargeAmt  = (EditText)findViewById(R.id.rechargeAmt);
        Paynow       = (Button)findViewById(R.id.Paynow);

        //--------------------------------------------
        // Receive Intent for amount
        {
            Intent intent1;
            intent1=getIntent();
            newAmount=intent1.getStringExtra("getNewamount");
            IMEI=intent1.getStringExtra("imei");

        }
        rechargeAmt.setText("Rs."+newAmount+" /-");
        Paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getFname = fname.getText().toString().trim();
                String getPhone = pnumber.getText().toString().trim();
                String getEmail = emailAddress.getText().toString().trim();
                String getAmt   = newAmount;//rechargeAmt.getText().toString().trim();
// Validation of text boxes
                if(fname.getText().toString().equals("") || fname.getText().toString().length()<=2)
                {
                    Toast.makeText(PayuMoney.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                else if(pnumber.getText().toString().equals("") || pnumber.getText().toString().length()<10)
                {
                    Toast.makeText(PayuMoney.this, "Enter 10 Digit Mobile No", Toast.LENGTH_SHORT).show();
                }
                else if(!emailAddress.getText().toString().contains("@"))
                {
                    Toast.makeText(PayuMoney.this, "Write complete emial address with @", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), PayMentGateWay.class);
                intent.putExtra("FIRST_NAME",getFname);
                intent.putExtra("PHONE_NUMBER",getPhone);
                intent.putExtra("EMAIL_ADDRESS",getEmail);
                intent.putExtra("RECHARGE_AMT",getAmt);
                intent.putExtra("imei",IMEI);
                startActivity(intent);
                finish();

            }
        });

    }// closer of onCreate method

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        Intent intent1=new Intent(PayuMoney.this,MyWallet2.class);
        startActivity(intent1);
        finish();
        }
        return super.onKeyDown(keyCode, event);

    }

}