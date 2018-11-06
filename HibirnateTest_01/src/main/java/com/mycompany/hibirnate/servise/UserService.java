package com.mycompany.hibirnate.servise;


import com.mycompany.hibirnate.dao.DAO;
import com.mycompany.hibirnate.dao.MySQLDatabaseUser;
import com.mycompany.hibirnate.model.User;
import java.util.List;

public class UserService {
    
  private DAO<User,String> userDAO;
  

    public UserService() {
        
    }
    
    public String getUserRole(User user){
        String role = user.getRole().getRoleId();
       return role;
    }

    public void saveUser(User user) {
        userDAO = new MySQLDatabaseUser();
        System.out.println(user);
        userDAO.insert(user);    
    }
    
    public User getUserByEmail(String email){    
        userDAO = new MySQLDatabaseUser();
        return userDAO.getObjectByID(email);
    }
    
    public List<User> getAllUsers(){
        userDAO = new MySQLDatabaseUser();
        return userDAO.getAllObject();
    }

}
