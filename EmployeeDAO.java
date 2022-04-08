/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.EmployeePojo;
import emart.pojo.UserPojo;
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
public class EmployeeDAO {
    public static String getEmpNextId()throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(empid) from employees");
        rs.next();
        String id=rs.getString(1);
        int empid=Integer.parseInt(id.substring(1));
        empid+=1;
        return "E"+empid;
    }
    public static boolean addEmployee(EmployeePojo e)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into employees values(?,?,?,?)");
        ps.setString(1, e.getEmpid());
        ps.setString(2, e.getEmpname());
        ps.setString(3, e.getJob());
        ps.setDouble(4, e.getSalary());
        int result=ps.executeUpdate();
        return result==1;
    }
    public static List<EmployeePojo> getAllEmployee()throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select * from employees order by empid");
        ArrayList<EmployeePojo> empList=new ArrayList<>();
        while(rs.next())
        {
            EmployeePojo emp=new EmployeePojo();
            emp.setEmpid(rs.getString(1));
            emp.setEmpname(rs.getString(2));
            emp.setJob(rs.getString(3));
            emp.setSalary(rs.getDouble(4));
            empList.add(emp);           
        }
        return empList;
    }
    public static List<String> getAllEmpId()throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select empid from employees order by empid");
        ArrayList<String> empList=new ArrayList<>();
        while(rs.next())
        {
            empList.add(rs.getString(1));
        }
        return empList;
    }
    public static EmployeePojo findEmpById(String empid)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from employees where empid=?");
        ps.setString(1, empid);
        ResultSet rs=ps.executeQuery();
        EmployeePojo e=new EmployeePojo();
        rs.next();
        e.setEmpid(empid);
        e.setEmpname(rs.getString(2));
        e.setJob(rs.getString(3));
        e.setSalary(rs.getDouble(4));
        return e;
    }
    public static boolean updateEmployee(EmployeePojo e)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update employees set empname=? , job=?, salary=? where empid=?");
        ps.setString(1, e.getEmpname());
        ps.setString(2, e.getJob());
        ps.setDouble(3, e.getSalary());
        ps.setString(4, e.getEmpid());
        int x=ps.executeUpdate();
        if(x==0)
           return false;
        else
        {
            if(!(UserDAO.userIsPresent(e.getEmpid())))
                    return true;
            ps=conn.prepareStatement("update users set username=? and usertype=? where empid=?");
            ps.setString(1, e.getEmpname());
            ps.setString(2, e.getJob());
            ps.setString(3, e.getEmpid());
            int y=ps.executeUpdate();
            return y==1;
        }
    }
    public static boolean deleteEmployee(String empid)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Delete from employees where empid=?");
        ps.setString(1,empid);
        int x=ps.executeUpdate();
        return x==1;
    }
    public static List<String> allReceptionist()throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select empid from Employees where job='Receptionist' AND empid not in(select empid from users where usertype='Receptionist') order by empid");
        ArrayList<String> empList=new ArrayList<>();
       
        while(rs.next())
        {  
          empList.add(rs.getString(1));
        } 
        return empList;
    }
    public static String getName(String empid)throws SQLException
    {
         Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Select empname from employees where empid=?");
        ps.setString(1,empid);
        ResultSet rs=ps.executeQuery();
        rs.next();
        return rs.getString(1);
    }
}
