
import com.mycompany.javafx.electronicrecord.utill.CSVUtils;
import com.mycompany.javafx.electronicrecord.model.Student;
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
        List<Student> students = parseCSVWithHeader("C:\\Users\\user07\\Desktop\\exemple4.csv");
        String csvFile = "C:\\Users\\user07\\Desktop\\exemple5.csv";  
        FileWriter writer = new FileWriter(csvFile);
        CSVUtils.writeLine(writer, Arrays.asList("ФИО","Номер зачетки"),';');
        
        for (Student d : students) {

            List<String> list = new ArrayList<>();
            list.add(d.getFullName());
            list.add(String.valueOf(d.getNumberBook()));
            CSVUtils.writeLine(writer, list,';');
			//try custom separator and quote. 
			//CSVUtils.writeLine(writer, list, '|', '\"');
        }

        writer.flush();
        writer.close();

    

      
//     List<String[]> myEntries = reader.readAll();
//     int index = 0;
//        for (String[] myEntry : myEntries) {
//            if(index != 0){
//            System.out.println(myEntry[0]);
//            }
//            index++;
//            
//        }
//
//        List<StudentsListController.Student> emps = new ArrayList<>();
//        HeaderColumnNameMappingStrategy<StudentsListController.Student> beanStrategy = new HeaderColumnNameMappingStrategy<StudentsListController.Student>();
//        beanStrategy.setType(StudentsListController.Student.class);
//        // read line by line
//        String[] record = null;
//
//        while ((record = reader.readNext()) != null) {
//            StudentsListController.Student student = new StudentsListController.Student();
//            student.setNumberStudent(Integer.valueOf(record[0]));
//            student.setFullName(record[1]);
//            student.setNumberRecord(Integer.valueOf(record[2]));
//
//            emps.add(student);
//        }
//
//        System.out.println(emps);
//
        // reader.close();

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
