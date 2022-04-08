/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.ProductsPojo;
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
public class ProductsDAO {
     public static String getProductsNextId()throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(p_id) from products");
        rs.next();
        String id=rs.getString(1);
        if(id==null)
        {
            return "P101";
        }
        int productid=Integer.parseInt(id.substring(1));
        productid+=1;
        return "P"+productid;
    }
     public static boolean addProducts(ProductsPojo p)throws SQLException
     {
          Connection conn=DBConnection.getConnection();
          PreparedStatement ps=conn.prepareStatement("Insert into products values(?,?,?,?,?,?,?,'Y')");
          ps.setString(1, p.getProductId());
          ps.setString(2,p.getProductName());
          ps.setString(3, p.getProductCompany());
          ps.setDouble(4, p.getProductPrice());
          ps.setDouble(5, p.getOurPrice());
          ps.setInt(6, p.getTax());
          ps.setInt(7, p.getQuantity());
          return ps.executeUpdate()==1;
     }
     public static List<ProductsPojo> getAllProducts()throws SQLException
     {
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select * from products where status='Y' order by p_id");
        ArrayList<ProductsPojo> productsList = new ArrayList<>();
        while(rs.next())
        {
            ProductsPojo p=new ProductsPojo();
            p.setProductId(rs.getString(1));
            p.setProductName(rs.getString(2));
            p.setProductCompany(rs.getString(3));
            p.setProductPrice(Double.parseDouble(rs.getString(4)));
            p.setOurPrice(Double.parseDouble(rs.getString(5)));
            p.setTax(Integer.parseInt(rs.getString(6)));
            p.setQuantity(Integer.parseInt(rs.getString(7)));
            productsList.add(p);
        }
        return productsList;
     }
     public static boolean UpdateProducts(ProductsPojo p)throws SQLException
     {
         Connection conn=DBConnection.getConnection();
          PreparedStatement ps=conn.prepareStatement("Update products set p_name=?,p_companyname=?,p_price=?,our_price=?,p_tax=?,quantity=? where p_id=?");
          
          ps.setString(1,p.getProductName());
          ps.setString(2, p.getProductCompany());
          ps.setDouble(3, p.getProductPrice());
          ps.setDouble(4, p.getOurPrice());
          ps.setInt(5, p.getTax());
          ps.setInt(6, p.getQuantity());
          ps.setString(7, p.getProductId());
          return ps.executeUpdate()==1;
     }
     public static boolean DeleteProducts(String p_id)throws SQLException
     {
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("Update products set status='N' where p_id=?");
         ps.setString(1, p_id);
         return ps.executeUpdate()==1;
     }
     public static ProductsPojo getProductById(String pid)throws SQLException
     {
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("Select * from products where p_id=?");
         ps.setString(1, pid);
         ResultSet rs= ps.executeQuery();
         if(!rs.next())
         {
             return null;
         }
         ProductsPojo p=new ProductsPojo();
            p.setProductId(rs.getString(1));
            p.setProductName(rs.getString(2));
            p.setProductCompany(rs.getString(3));
            p.setProductPrice(Double.parseDouble(rs.getString(4)));
            p.setOurPrice(Double.parseDouble(rs.getString(5)));
            p.setTax(Integer.parseInt(rs.getString(6)));
            p.setQuantity(Integer.parseInt(rs.getString(7)));
         return p;   
     }
     public static boolean updateProducts(List<ProductsPojo>  pList)throws SQLException
     {
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("Update products set quantity=quantity-? where p_id=? and status='Y'");
         int x=0;
         for(ProductsPojo p: pList)
         {
             ps.setInt(1, p.getQuantity());
             ps.setString(2, p.getProductId());
             int y=ps.executeUpdate();
             if(y!=0)
             {
                 x++;
             }
         }
         return x==pList.size();
     }
}
