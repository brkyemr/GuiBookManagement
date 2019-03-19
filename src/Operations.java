
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Operations {
    
    Connection con = null;
    Statement sta =null;
    PreparedStatement prepsta = null;
    
    
    
    public Operations(){
        
        String url = "jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_name;
        
       try{
           
       
        Class.forName("com.mysql.jdbc.Driver");
        con =  DriverManager.getConnection(url,Database.id,Database.password);
           System.out.println("Veri Tabanı Bağlantısı Gerçekleşti");
        } 
       catch(ClassNotFoundException ex){
           System.out.println("driver çalışmadı");
       }
       catch(SQLException ex){
           System.out.println("connection çalışmadı");
       }
       
    }
    
    public boolean Login(String id, String password){
        String sorgu = "select * from admin where id= ? and password= ?";
        try {
         prepsta = con.prepareStatement(sorgu);
        prepsta.setString(1, id);
        prepsta.setString(2, password);
        ResultSet rs = prepsta.executeQuery();
        return rs.next();
        } catch (SQLException e) {
            return false;
        }
    
        
    }
    
    
    public static void main(String[] args) {
        
        Operations op = new Operations();
        
        
         
    }
    
    
    
    
    
    
}
