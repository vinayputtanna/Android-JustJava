package com.example.android.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.contextClickable;
import static android.R.attr.y;
import static android.widget.Toast.makeText;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*TextView textView=new TextView(this);
        textView.setText("Customized UI");
        textView.setTextColor(Color.RED);
        textView.setTextSize(36);
        textView.setAllCaps(true);
        setContentView(textView);
        */
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view){
        EditText nameEditText=(EditText)findViewById(R.id.name_edittext);
        String name=nameEditText.getText().toString();
        String emailSubject="Just Java order from " + name;

        CheckBox whippedCreamCheckBox=(CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCreamTopping= whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateTopping=chocolateCheckBox.isChecked();
        double price= calculatePrice(hasWhippedCreamTopping, hasChocolateTopping, quantity);

        String orderSummaryMessage=createOrderSummary(price, hasWhippedCreamTopping, hasChocolateTopping, name);
        composeEmail(emailSubject, orderSummaryMessage);
       // displayMessage(orderSummaryMessage);
    }

    /**
     * Calculates order price
     * @param hasWhippedCream
     * @param hasChocolate
     * @param quantity
     * @return
     */
    private double calculatePrice(boolean hasWhippedCream, boolean hasChocolate, int quantity){
        double basePrice=5.18;
        if(hasWhippedCream){
            basePrice+=1;
        }

        if(hasChocolate){
            basePrice+=2;
        }
        return (basePrice * quantity);
    }

    /**
     * Creates order summary
     * @param price
     * @param hasWhippedCreamTopping
     * @param hasChocolateTopping
     * @param customerName
     * @return
     */
    private String createOrderSummary(double price, boolean hasWhippedCreamTopping, boolean hasChocolateTopping, String customerName){
        String orderSummary="Name: "+customerName;
        orderSummary+="\nAdd whipped cream? "+ hasWhippedCreamTopping;
        orderSummary+="\nAdd chocolate? "+ hasChocolateTopping;
        orderSummary+="\nQuantity: "+ quantity;
        orderSummary+="\nTotal: $"+ String.format("%.2f",price);
        orderSummary+="\n"+getString(R.string.thank_you);

        return orderSummary;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Increments the order quantity by 1
     * @param view
     */
    public void increment(View view){
        if(quantity<100){
            quantity++;
            displayQuantity(quantity);
        }
        else{
            Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Decrements the order quantity by 1
     * @param view
     */
    public void decrement(View view){
        if(quantity>1){
            quantity--;
            displayQuantity(quantity);
        }
        else{
            Toast.makeText(this, "You cannot order less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Composes email message and starts the email activity
     * @param view
     */
    public void composeEmail(String subject, String emailBody) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vinayp@outlook.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6,-122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }
}