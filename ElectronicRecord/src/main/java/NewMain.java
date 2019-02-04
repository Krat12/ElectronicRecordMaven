
import com.mycompany.javafx.electronicrecord.dao.impl.StudentDB;
import com.mycompany.javafx.electronicrecord.model.Student;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user07
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Student> student = StudentDB.getInstance().getStudentsByGroup("Пр15-04");
        System.out.println(student.get(0).getUser().getMidleName());
    }
    
}
