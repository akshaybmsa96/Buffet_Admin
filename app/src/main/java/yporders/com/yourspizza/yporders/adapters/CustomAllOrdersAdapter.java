package yporders.com.yourspizza.yporders.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;

import yporders.com.yourspizza.yporders.R;
import yporders.com.yourspizza.yporders.activity.FullDetailActivity;
import yporders.com.yourspizza.yporders.pojo.orderspojo.OrderDetailPojo;


/**
 * Created by Akki on 09-06-2017.
 */


public class CustomAllOrdersAdapter extends RecyclerView.Adapter<CustomAllOrdersAdapter.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context context;

    ArrayList<OrderDetailPojo> orderlist = new ArrayList<OrderDetailPojo>();
    Activity activity;

    public CustomAllOrdersAdapter(Context context, Activity activity, ArrayList<OrderDetailPojo> orderlist) {

        this.context = context;
        this.orderlist = orderlist;
        this.activity = activity;
    }


    @Override
    public CustomAllOrdersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.orderslistviewlayout, parent, false);
        CustomAllOrdersAdapter.ViewHolder holder = new CustomAllOrdersAdapter.ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAllOrdersAdapter.ViewHolder holder, int position) {

        holder.textViewCustomerName.setText(orderlist.get(position).getUser_name());
        holder.textViewAmount.setText("Amount : ₹ " + String.valueOf(orderlist.get(position).getTotal_price()));
        holder.textViewODate.setText(orderlist.get(position).getOrder_date().substring(0,16));
        holder.textViewAddress.setText(orderlist.get(position).getUser_address() + " " + orderlist.get(position).getDelivery_centre() );
        holder.textViewNumberOfItems.setText(orderlist.get(position).getNo_of_items()+ " " + "Items");

        //Toast.makeText(context,name.get(position),Toast.LENGTH_SHORT).show();

    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView textViewCustomerName;
        TextView textViewAmount;
        TextView textViewODate;
        ImageView imageViewCall;
        TextView textViewAddress;
        TextView textViewNumberOfItems;


        public ViewHolder(View view) {
            super(view);

            imageViewCall=(ImageView)view.findViewById(R.id.imageViewCall);
            textViewNumberOfItems=(TextView)view.findViewById(R.id.textViewNumberOfItems);
            textViewCustomerName=(TextView)view.findViewById(R.id.textViewCustomerName);
            textViewODate=(TextView)view.findViewById(R.id.textViewODate);
            textViewAmount=(TextView)view.findViewById(R.id.textViewAmount);
            textViewAddress=(TextView)view.findViewById(R.id.textViewAddress);


            imageViewCall.setVisibility(View.INVISIBLE);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View view) {

            Intent i = new Intent(context, FullDetailActivity.class);
            Gson gson = new Gson();
            i.putExtra("orderDetail",gson.toJson(orderlist.get(getPosition())));
            context.startActivity(i);

        }

        @Override
        public boolean onLongClick(View view) {
         //   Toast.makeText(context, "Item will be Delete", Toast.LENGTH_SHORT).show();
            return true;
        }

    }

    @Override
    public long getHeaderId(int position)
    {
        return getId(orderlist.get(position).getOrder_date());

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView.findViewById(R.id.textViewHeader);
        textView.setText(orderlist.get(position).getOrder_date().substring(0,10));

    }


    @Override
    public int getItemCount() {

        return orderlist.size();
    }

    Long getId(String input)
    {
        Long id=0l;
        input=input.replace("-","");
        input=input.replace(":","");
        input=input.replace(" ","");
        input=input.substring(0,8);
        //  System.out.println("Input is                                                     "+input);
        id=Long.parseLong(input);
        return id;
    }

    String getDate(String input)
    {
        String fDate="";
        fDate=input.substring(0,10);
        return fDate;
    }


}


