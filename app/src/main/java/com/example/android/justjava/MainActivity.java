/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.jar.Attributes;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        Toast.makeText(this, "Enjoy Your Coffee ! :) ", Toast.LENGTH_SHORT).show();
        CheckBox wCheckBox = (CheckBox) findViewById(R.id.W_checkbox);
        boolean hasCream = wCheckBox.isChecked();
        CheckBox cCheckBox = (CheckBox) findViewById(R.id.C_checkbox);
        boolean hasChocalate = cCheckBox.isChecked();
        EditText name = (EditText) findViewById(R.id.textfield);
        Editable Name = name.getText();
        String priceMessage = createOrderSummary(Name,quantity, hasCream, hasChocalate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "sent from just java app :)");
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        displayMessage(priceMessage);

    }

    /**
     *
     * Final price is calculated in this method.
     */
    public int calculatePrice (int quantity){
        CheckBox wCheckBox = (CheckBox) findViewById(R.id.W_checkbox);
        boolean hasCream = wCheckBox.isChecked();
        CheckBox cCheckBox = (CheckBox) findViewById(R.id.C_checkbox);
        boolean hasChocalate = cCheckBox.isChecked();
        if((hasChocalate==true)&&(hasCream==true)){
            int price = quantity * 8;
            return price;
        }
        else if(hasCream==true){
            int price = quantity * 6;
            return price;
        }
        else if(hasChocalate==true){
            int price = quantity * 7;
            return price;
        }
        else {
            int price = quantity * 5;
            return price;
        }

    }
    /**
     *
     * Summary is generated in this method.
     * Also note that += operator can be used in the priceMessage instead of priceMessage = priceMessage + "" ;
     */
    private String createOrderSummary(Editable Name,int quantity, boolean hasCream, boolean hasChocalate){
        String priceMessage = "\n"+ getString(R.string.name)+ Name ;
        priceMessage = priceMessage + "\n" + getString(R.string.quantity)+ ": " + quantity ;
        priceMessage += "\n"+ getString(R.string.addcream)+hasCream;
        priceMessage += "\n " + getString(R.string.addchoc) +hasChocalate;
        priceMessage = priceMessage + "\n" + getString(R.string.price)+ calculatePrice(quantity);
        priceMessage = priceMessage + "\n" + getString((R.string.thankyou)) ;
     return priceMessage;
    }

    public void increment(View view){
        quantity = quantity + 1;
        display(quantity);

    }

    public void decrement(View view){
        quantity = quantity - 1 ;
        if( quantity < 0 ) {
            quantity=0;
            Toast.makeText(this, " Negative amount of coffee? ", Toast.LENGTH_SHORT).show();
        }else {
            display(quantity);

        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}