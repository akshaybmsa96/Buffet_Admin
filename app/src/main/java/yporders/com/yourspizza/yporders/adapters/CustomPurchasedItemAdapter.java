package yporders.com.yourspizza.yporders.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import yporders.com.yourspizza.yporders.R;
import yporders.com.yourspizza.yporders.pojo.orderitempojo.OrderItemDetailpojo;

/**
 * Created by Akki on 03-06-2017.
 */


public class CustomPurchasedItemAdapter extends ArrayAdapter<OrderItemDetailpojo> {

    Context context;
    Activity activity;
    ArrayList<OrderItemDetailpojo> items;

    public CustomPurchasedItemAdapter(Context context,Activity activity,ArrayList<OrderItemDetailpojo> items) {
        super(context, R.layout.order_items_list_layout, items);
        this.context = context;
        this.items=items;
        this.activity=activity;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.order_items_list_layout, null, true);
        TextView textViewItemName=(TextView)rowView.findViewById(R.id.textViewItemName);
        TextView textViewToppings=(TextView)rowView.findViewById(R.id.textViewToppings);
        TextView textViewPrice=(TextView)rowView.findViewById(R.id.textViewPrice);
        TextView textViewOffer=(TextView)rowView.findViewById(R.id.textViewOffer);
        TextView textViewSize=(TextView)rowView.findViewById(R.id.textViewSize);

        textViewItemName.setText(items.get(position).getItem_name()+" X "+ items.get(position).getItem_quantity());
        textViewSize.setText(items.get(position).getItem_size());
        textViewPrice.setText("₹ " + items.get(position).getTotal_price());

        if (items.get(position).getOffer_detail()!=null && items.get(position).getOffer_detail().length() > 0) {
            textViewOffer.setText(items.get(position).getOffer_detail());
        }

        else
            {

            textViewOffer.setVisibility(View.GONE);
        }

        if(items.get(position).getItem_toppings().length()<1 && items.get(position).getExtra_add_on().length()<1)
        {
            textViewToppings.setVisibility(View.GONE);
        }

        else {

            if (items.get(position).getItem_toppings().length() > 0) {
                textViewToppings.setText("Toppings: " + items.get(position).getItem_toppings().replace("+", " "));
            }

            if (items.get(position).getItem_toppings().length() > 0 && items.get(position).getExtra_add_on().length() > 0) {
                textViewToppings.append("\nExtras : " + items.get(position).getExtra_add_on().replace("+", " "));
            }

            else if(items.get(position).getItem_toppings().length() < 1 && items.get(position).getExtra_add_on().length() > 0)
            {
                textViewToppings.setText("Extras : " + items.get(position).getExtra_add_on().replace("+", " "));
            }
        }

        return rowView;
    }

}

