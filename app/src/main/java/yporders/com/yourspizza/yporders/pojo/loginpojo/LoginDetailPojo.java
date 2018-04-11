package yporders.com.yourspizza.yporders.pojo.loginpojo;

/**
 * Created by akshaybmsa96 on 31/12/17.
 */

public class LoginDetailPojo {

    private String centre_name;

    public String getCentre_name ()
    {
        return centre_name;
    }

    public void setCentre_name (String centre_name)
    {
        this.centre_name = centre_name;
    }


    @Override
    public String toString()
    {
        return "centre_name = "+centre_name+"";
    }
}
