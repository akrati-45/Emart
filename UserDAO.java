/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.UserPojo;
import emart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class UserDAO {
      public static boolean validateUser(UserPojo user)throws SQLException
      {
          Connection conn=DBConnection.getConnection();
          PreparedStatement ps=conn.prepareStatement("Select * from users where userid=? and password=? and usertype=?");
          ps.setString(1, user.getUserId());
          ps.setString(2, user.getPassword());
          ps.setString(3, user.getUserType());
          ResultSet rs=ps.executeQuery();
          if(rs.next())
          {
              String Username=rs.getString(5);
              UserProfile.setUserName(Username);
              return true;
          }
          return false;
      }
      public static boolean userIsPresent(String empid)throws SQLException
      {
          Connection conn=DBConnection.getConnection();
          PreparedStatement ps=conn.prepareStatement("Select 1 from users where empid=?");
          ps.setString(1, empid);
          int x=ps.executeUpdate();
          return x==1;
      }
      public static boolean addReceptionist(UserPojo u)throws SQLException
      {
         Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into users values(?,?,?,?,?)");
        ps.setString(1, u.getUserId());
        ps.setString(2, u.getEmpId());
        ps.setString(3, u.getPassword());
        ps.setString(4, u.getUserType());
        ps.setString(5, u.getUserName());
        int result=ps.executeUpdate();
        return result==1;
      }
     public static List<UserPojo> getAllReceptionist()throws SQLException
     {
         Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select * from users order by empid");
        ArrayList<UserPojo> empList=new ArrayList<>();
        while(rs.next())
        {
            UserPojo emp=new UserPojo();
            emp.setUserId(rs.getString(1));
            emp.setEmpId(rs.getString(2));
            emp.setPassword(rs.getString(3));
            emp.setUserName(rs.getString(5));
            empList.add(emp);           
        }
        return empList;
     }
     public static Map<String,String> getAllReceptionistDetails()throws SQLException
     {
         Connection conn=DBConnection.getConnection();
         Statement st=conn.createStatement();
         ResultSet rs=st.executeQuery("select userid,username from users where usertype='Receptionist' order by empid");
         HashMap<String,String> Receptionist=new HashMap<>();
        while(rs.next())
        {
            Receptionist.put(rs.getString(1), rs.getString(2));
        }
        return Receptionist;
     }
     public static boolean updateReceptionist(String userid,String pwd)throws SQLException
     {
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("Update users set password=? where userid=?");
         ps.setString(1, pwd);
         ps.setString(2, userid);
         return ps.executeUpdate()==1;
     }
     public static boolean removeReceptionist(String userid)throws SQLException
     {
          Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("Delete from users where userid=?");
         ps.setString(1, userid);
         return ps.executeUpdate()==1;
     }
}
