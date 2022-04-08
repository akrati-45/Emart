/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.ProductsPojo;
import emart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class OrdersDAO {
    
    public static String getNextId()throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(order_id) from orders");
        rs.next();
        String id=rs.getString(1);
        if(id==null)
        {
            return "O101";
        }
        int orderid=Integer.parseInt(id.substring(1));
        orderid+=1;
        return "O"+orderid;
    }
    public static boolean addOrders(ArrayList<ProductsPojo> al,String ordId)throws SQLException
    {
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("Insert into orders values(?,?,?,?)");
         int count=0;
         for(ProductsPojo p:al)
         {
             ps.setString(1,ordId);
             ps.setString(2, p.getProductId());
             ps.setInt(3, p.getQuantity());
             ps.setString(4, UserProfile.getUserId());
             int x=ps.executeUpdate();
             count=count+x;
         }
         
         return count==al.size();
    }
    public static List<String> viewOrderDetails(String oid)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Select orders.p_id,Products.p_name,Products.p_companyname,Products.p_price,Products.our_price,Products.p_tax,Orders.quantity from orders,products where orders.p_id=products.p_id and  orders.order_id=?");
        ArrayList<String> al=new ArrayList<String>();
        ps.setString(1, oid);
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            al.add(rs.getString(1));
        }
        return al;
    }
    public static List<String> allOrdersId()throws SQLException
    {
         Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select distinct(order_id) from orders ");
        ArrayList<String> al=new ArrayList<String>();
        while(rs.next())
        {
            al.add(rs.getString(1));
        }
        return al;
    }
}
