package yporders.com.yourspizza.yporders.pojo.orderitempojo;

import java.util.ArrayList;

/**
 * Created by akshaybmsa96 on 27/12/17.
 */

public class OrderItemPojo {

    private String error;

    private ArrayList<OrderItemDetailpojo> items;


    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }


    public ArrayList<OrderItemDetailpojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderItemDetailpojo> items) {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [error = "+error+", items = "+items+"]";
    }
}


