
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    public ArrayList<Book> bookGet(){
        ArrayList<Book> list = new ArrayList<Book>();
        String sorgu ="select * from books_database";
        try {
            sta = con.createStatement();
            ResultSet rs = sta.executeQuery(sorgu);
            while(rs.next()){
                int id = rs.getInt("id");
                String book_writer = rs.getString("book_writer");
                String book_name = rs.getString("book_name");
                String book_type =  rs.getString("book_type");
                String book_publisher = rs.getString("book_publisher");
                list.add(new Book(id, book_name, book_writer, book_type, book_publisher));  
            }
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        return null;
        }
        
    }
    
    
    public static void main(String[] args) {
        
        Operations op = new Operations();
        AdminPanel ap = new AdminPanel();
        
         
    }
    
    
    
    
    
    
}
