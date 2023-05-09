

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Base64.*;


public class Authenticator {

    ArrayList<Account> accounts= new ArrayList<>();
    // SQLite connection string
    String url = "jdbc:sqlite:/Users/joaosantos/Documents/Project_Seg/src/main/resources/seguranca_software.db";
    Connection conn = null;
    Authenticator() {
        connect(url);
    }

    private Connection connect(String url) {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    void create_account(String name, String pwd1, String pwd2) {
        Account n_acc;
        if( pwd1 == pwd2){

            n_acc= new Account(name,pwd1);
            n_acc.logged_in=true;
            String sql = "INSERT INTO Account(username,password) VALUES(?,?)";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,n_acc.name);
                pstmt.setString(2, n_acc.pwd);
                pstmt.executeUpdate();
                }catch( SQLException e){
                System.out.println(e.getMessage());
            }
            System.out.println("Account Created:" +  n_acc.name + " with the password" + n_acc.pwd);
        }
    }
    void delete_account(String name){
        String sql = "DELETE FROM Account WHERE username = ?";
        try{
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,name);
            psmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void change_pwd(String name, String pwd2){
        String sql= "UPDATE Account SET password = ? WHERE username= ?";
        try {
            PreparedStatement psmt= conn.prepareStatement(sql);
            psmt.setString(1,pwd2);
            psmt.setString(2,name);
            psmt.executeUpdate();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void logout(Account acc){
            acc.logged_in = false ;
    }

    public static void main(String[] args) {
        Authenticator auth= new Authenticator();
        auth.create_account("Johnny","123","123");
    }
}

