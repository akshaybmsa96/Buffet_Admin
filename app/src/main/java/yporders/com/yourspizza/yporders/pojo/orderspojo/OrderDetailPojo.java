package yporders.com.yourspizza.yporders.pojo.orderspojo;

/**
 * Created by akshaybmsa96 on 26/12/17.
 */

public class OrderDetailPojo {

    private String isCanceled;

    private String isConfirmed;

    private String user_address;

    private String delivery_centre;

    private String order_id;

    private String total_price;

    private String id;

    private String cooking_instructions;

    private String user_name;

    private String user_phn_number;

    private String user_alternate_phn_number;

    private String user_id;

    private String no_of_items;

    private String order_date;

    public String getIsCanceled ()
    {
        return isCanceled;
    }

    public void setIsCanceled (String isCanceled)
    {
        this.isCanceled = isCanceled;
    }

    public String getIsConfirmed ()
    {
        return isConfirmed;
    }

    public void setIsConfirmed (String isConfirmed)
    {
        this.isConfirmed = isConfirmed;
    }

    public String getUser_address ()
    {
        return user_address;
    }

    public void setUser_address (String user_address)
    {
        this.user_address = user_address;
    }

    public String getDelivery_centre ()
    {
        return delivery_centre;
    }

    public void setDelivery_centre (String delivery_centre)
    {
        this.delivery_centre = delivery_centre;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getTotal_price ()
    {
        return total_price;
    }

    public void setTotal_price (String total_price)
    {
        this.total_price = total_price;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCooking_instructions ()
    {
        return cooking_instructions;
    }

    public void setCooking_instructions (String cooking_instructions)
    {
        this.cooking_instructions = cooking_instructions;
    }

    public String getUser_name ()
    {
        return user_name;
    }

    public void setUser_name (String user_name)
    {
        this.user_name = user_name;
    }

    public String getUser_phn_number ()
    {
        return user_phn_number;
    }

    public void setUser_phn_number (String user_phn_number)
    {
        this.user_phn_number = user_phn_number;
    }

    public String getUser_alternate_phn_number ()
    {
        return user_alternate_phn_number;
    }

    public void setUser_alternate_phn_number (String user_alternate_phn_number)
    {
        this.user_alternate_phn_number = user_alternate_phn_number;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getNo_of_items ()
    {
        return no_of_items;
    }

    public void setNo_of_items (String no_of_items)
    {
        this.no_of_items = no_of_items;
    }

    public String getOrder_date ()
    {
        return order_date;
    }

    public void setOrder_date (String order_date)
    {
        this.order_date = order_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [isCanceled = "+isCanceled+", isConfirmed = "+isConfirmed+", user_address = "+user_address+", delivery_centre = "+delivery_centre+", order_id = "+order_id+", total_price = "+total_price+", id = "+id+", cooking_instructions = "+cooking_instructions+", user_name = "+user_name+", user_phn_number = "+user_phn_number+", user_alternate_phn_number = "+user_alternate_phn_number+", user_id = "+user_id+", no_of_items = "+no_of_items+", order_date = "+order_date+"]";
    }
}


