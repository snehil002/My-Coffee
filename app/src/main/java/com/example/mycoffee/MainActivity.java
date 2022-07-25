package com.example.mycoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int min;
    int max;
    int quantity;
    int total_price;
    boolean is_whipped_checked = false;
    boolean is_chocolate_checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        min = Integer.parseInt(getString(R.string.init_quantity));
        max = Integer.parseInt(getString(R.string.max_quantity));
        quantity = min;
        displayQuantity();
        displayPrice();
    }

    public void increaseQuantity(View v) {
        if(quantity < max) {
            quantity++;
            displayQuantity();
            displayPrice();
        }else{
            Toast.makeText(this, "At most 20 coffees per order", Toast.LENGTH_SHORT).show();
        }
    }
    public void decreaseQuantity(View v) {
        if(quantity > min) {
            quantity--;
            displayQuantity();
            displayPrice();
        }else{
            Toast.makeText(this, "At least 1 coffee per order", Toast.LENGTH_SHORT).show();
        }
    }
    public void checkWhipped(View v) {
        CheckBox c = findViewById(R.id.whipped_checkbox);
        is_whipped_checked = c.isChecked();
        displayPrice();
    }
    public void checkChocolate(View v) {
        CheckBox c = findViewById(R.id.chocolate_checkbox);
        is_chocolate_checked = c.isChecked();
        displayPrice();
    }
    private void displayQuantity() {
        TextView t = findViewById(R.id.quantity);
        String r = "" + quantity;
        t.setText(r);
    }
    private void displayPrice() {
        TextView t = findViewById(R.id.price);
        calculate_price();
        String r = "" + total_price;
        t.setText(r);
    }
    private void calculate_price() {
        int base_price = Integer.parseInt(getString(R.string.base_price));
        int whipped_price = Integer.parseInt(getString(R.string.whipped_price));
        int chocolate_price = Integer.parseInt(getString(R.string.chocolate_price));

        int price_of_one = base_price;

        if(is_whipped_checked) {
            price_of_one += whipped_price;
        }
        if(is_chocolate_checked) {
            price_of_one += chocolate_price;
        }
        total_price = quantity * price_of_one;
    }
    public void buy_coffee(View v) {
        EditText e = findViewById(R.id.name);
        String name_of_customer = e.getText().toString();
        String[] to_address = {getString(R.string.to_address)};
        String subject = getString(R.string.subject, name_of_customer);
        String message_body = generateString(name_of_customer);

        Intent email_intent = new Intent(Intent.ACTION_SENDTO);
        email_intent.setData(Uri.parse("mailto:"));
        email_intent.putExtra(Intent.EXTRA_EMAIL, to_address);
        email_intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        email_intent.putExtra(Intent.EXTRA_TEXT, message_body);
        if (email_intent.resolveActivity(getPackageManager()) != null) {
            startActivity(email_intent);
        }
    }
    private String generateString(String name_of_customer) {
        String r = getString(R.string.mes1, name_of_customer, quantity);
        if(is_whipped_checked) {
            r += getString(R.string.whipped_cream_java);
        }
        if(is_chocolate_checked) {
            r += getString(R.string.chocolate_garnish_java);
        }
        r += getString(R.string.mes2, total_price);
        return r;
    }
}