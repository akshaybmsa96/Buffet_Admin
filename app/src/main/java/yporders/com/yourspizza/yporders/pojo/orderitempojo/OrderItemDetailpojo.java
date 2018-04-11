package yporders.com.yourspizza.yporders.pojo.orderitempojo;

/**
 * Created by akshaybmsa96 on 27/12/17.
 */

public class OrderItemDetailpojo {

    private String isCanceled;

    private String isConfirmed;

    private String offer_detail;

    private String item_quantity;

    private String extra_price;

    private String order_id;

    private String total_price;

    private String item_base_price;

    private String total_base_price;

    private String item_size;

    private String id;

    private String added_at;

    private String user_phn_number;

    private String extra_add_on;

    private String item_name;

    private String user_id;

    private String item_toppings;


    public String getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(String isCanceled) {
        this.isCanceled = isCanceled;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(String isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getOffer_detail() {
        return offer_detail;
    }

    public void setOffer_detail(String offer_detail) {
        this.offer_detail = offer_detail;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(String item_quantity) {
        this.item_quantity = item_quantity;
    }

    public String getExtra_price() {
        return extra_price;
    }

    public void setExtra_price(String extra_price) {
        this.extra_price = extra_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getItem_base_price() {
        return item_base_price;
    }

    public void setItem_base_price(String item_base_price) {
        this.item_base_price = item_base_price;
    }

    public String getTotal_base_price() {
        return total_base_price;
    }

    public void setTotal_base_price(String total_base_price) {
        this.total_base_price = total_base_price;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdded_at() {
        return added_at;
    }

    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }

    public String getUser_phn_number() {
        return user_phn_number;
    }

    public void setUser_phn_number(String user_phn_number) {
        this.user_phn_number = user_phn_number;
    }

    public String getExtra_add_on() {
        return extra_add_on;
    }

    public void setExtra_add_on(String extra_add_on) {
        this.extra_add_on = extra_add_on;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem_toppings() {
        return item_toppings;
    }

    public void setItem_toppings(String item_toppings) {
        this.item_toppings = item_toppings;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [isCanceled = "+isCanceled+", isConfirmed = "+isConfirmed+", offer_detail = "+offer_detail+", item_quantity = "+item_quantity+", extra_price = "+extra_price+", order_id = "+order_id+", total_price = "+total_price+", item_base_price = "+item_base_price+", total_base_price = "+total_base_price+", item_size = "+item_size+", id = "+id+", added_at = "+added_at+", user_phn_number = "+user_phn_number+", extra_add_on = "+extra_add_on+", item_name = "+item_name+", user_id = "+user_id+", item_toppings = "+item_toppings+"]";
    }
}
