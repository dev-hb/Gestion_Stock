/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author pcmad
 */
public class DbConnection {
    
    private Connection  cn;
    private static DbConnection instance;
    
    
    private final String hostName="localhost";
    private final String dbName="gestion_stock";
    private final String userName="root";
    private final String password="";
    
    
    private DbConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn=DriverManager.getConnection("jdbc:mysql://"+hostName+"/"+dbName,userName,password);
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static DbConnection getInstance(){
		if(instance==null) instance=new DbConnection();
		return instance;
    }
    
    
    public boolean logIn(String email,String password){
        try{
            getInstance();
            PreparedStatement ps=cn.prepareCall("select * from users where email_user=? and pwd_user=?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs=ps.executeQuery();
            return rs.first();
        }catch(Exception ex){
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean regester(String nom,String prenom,String email,String password) throws SQLException{
        getInstance();
        PreparedStatement ps=cn.prepareCall("insert into users(nom_user,prenom_user,email_user,pwd_user) valuse (?,?,?,?)");
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setString(3, email);
        ps.setString(4, password);
        return ps.execute();
    }
    
    
}
