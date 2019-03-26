
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
        
        String url = "jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_name+"?useUnicode=true&characterEncoding=UTF-8";
        
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
    public void bookAdd(String name, String writer, String type ,String publisher ){
        String sorgu;
        sorgu = "INSERT INTO `books_database`(`book_name`, `book_writer`, `book_type`, `book_publisher`) VALUES (?,?,?,?)";
        try {
            prepsta = con.prepareStatement(sorgu);
            prepsta.setString(1,name);
            prepsta.setString(2,writer);
            prepsta.setString(3,type);
            prepsta.setString(4,publisher);
            prepsta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void bookUpdate(int id,String name, String writer, String type ,String publisher ){
        String sorgu = "UPDATE books_database SET book_name=?,book_writer=?,book_type=?,book_publisher=? WHERE id=?";
        try {
            prepsta = con.prepareStatement(sorgu);
            prepsta.setString(1,name);
            prepsta.setString(2,writer);
            prepsta.setString(3,type);
            prepsta.setString(4,publisher);
            prepsta.setInt(5,id);
            prepsta.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void bookDelete(int id){
        String sorgu ="DELETE FROM books_database WHERE id = ?";
        try {
            prepsta = con.prepareStatement(sorgu);
            prepsta.setInt(1,id);
            prepsta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public int bookCount(){
        int label = 0;
        String sorgu = "Select count(*) from books_database"; 
       try{ 
        sta = con.createStatement();
        ResultSet rs = sta.executeQuery(sorgu);
        rs.next();
        label = rs.getInt(1);
        
       }
       catch(SQLException e){
           System.out.println("Book counter sql exception");
       return 0;
       }
        return label;
        
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
