/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 2;


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean isWhippedCreamChecked = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean isChocolateChecked = chocolateCheckBox.isChecked();
        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        int price = calculatePrice(isWhippedCreamChecked, isChocolateChecked);
        String priceMessage = createOrderSummary(name, price, isWhippedCreamChecked, isChocolateChecked);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Yo Coffee Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

 //       displayMessage(priceMessage);
    }



    private String createOrderSummary(String name, int price, boolean isWhippedCreamChecked, boolean isChocolateChecked) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nAdd Whipped Cream? " + isWhippedCreamChecked;
        priceMessage += "\nAdd Chocolate? " + isChocolateChecked;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $: " + price;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }


    /**
     * Calculates the price of the order.
     **/
    private int calculatePrice(boolean whipped, boolean chocolate) {
        int cost = 5;
        if (whipped){
            cost += 1;
        }
        if (chocolate){
            cost += 2;
        }
        return quantity * cost;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Context context = getApplicationContext();
            CharSequence text = "Maximum of 100 cups of coffee!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity <= 1){
            Context context = getApplicationContext();
            CharSequence text = "Minimum of 1 cup of coffee!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int amount) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + amount);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


}