
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.ReatingDB;
import com.mycompany.javafx.electronicrecord.dao.impl.SubjectDB;
import com.mycompany.javafx.electronicrecord.dao.impl.SubjectTeacherGroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.TeacherDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Reating;
import com.mycompany.javafx.electronicrecord.utill.CSVUtils;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.model.SubjectTeacherGroup;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static void main(String[] args) throws FileNotFoundException, IOException {
       List<Reating> list = ReatingDB.getInstance().getReatingByStatement(2);
        System.out.println(list);
    }

    public static List<Student> parseCSVWithHeader(String URL) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(URL), ';');

        HeaderColumnNameMappingStrategy<Student> beanStrategy = new HeaderColumnNameMappingStrategy<Student>();

        beanStrategy.setType(Student.class);

        CsvToBean<Student> csvToBean = new CsvToBean<Student>();
        List<Student> students = csvToBean.parse(beanStrategy, reader);

        System.out.println(students);
        reader.close();

        return students;
    }

}
