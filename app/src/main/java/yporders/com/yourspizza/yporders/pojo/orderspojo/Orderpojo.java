package yporders.com.yourspizza.yporders.pojo.orderspojo;

import java.util.ArrayList;

/**
 * Created by akshaybmsa96 on 26/12/17.
 */

public class Orderpojo {

    private String error;

    private ArrayList<OrderDetailPojo> items;

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public ArrayList<OrderDetailPojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetailPojo> items) {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [error = "+error+", items = "+items+"]";
    }
}
