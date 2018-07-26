package com.example.android.inventory;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory.data.ProductContract;
import com.example.android.inventory.R;

import java.sql.RowId;


/**
        * {@link ProductCursorAdapter} is an adapter for a list or grid view
        * that uses a {@link Cursor} of product data as its data source. This adapter knows
        * how to create list items for each row of product data in the {@link Cursor}.
        */
public class ProductCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        final TextView priceTextView = (TextView) view.findViewById(R.id.price);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        // Find the columns of products attributes that we're interested in
        int productNameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the products attributes from the Cursor for the current product
        String productName = cursor.getString(productNameColumnIndex);
        String price = cursor.getString(priceColumnIndex);
        String quantity = cursor.getString(quantityColumnIndex);

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        priceTextView.setText(price);
        quantityTextView.setText(quantity);

        Button sale_Button = (Button) view.findViewById(R.id.sale_Button);
        sale_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //Get The Current Product Quantity From Text
                    String quantityStr = quantityTextView.getText().toString().trim();
                    //Convert Quantity To Number
                    int quantity = Integer.parseInt(quantityStr);
                    //Assert That Quantity Can't be negative
                    if (quantity > 0) {
                        //Update The Current Quantity and convert it to String
                        String newQuantity = String.valueOf(quantity - 1);
                        //Update The Text UI
                        quantityTextView.setText(newQuantity);
                    }
                    else
                        {
                            Toast.makeText(this, getString(R.string.negative_quantity), Toast.LENGTH_LONG).show();

                    }
                }

        });

    }
}

