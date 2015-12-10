
import java.io.IOException;


import org.xml.sax.SAXException;

public class calculateGradeForStudents {
	
	
	
	public static void main(String[] args) throws SAXException, IOException {
		// TODO Auto-generated method stub
        createStudentGrader c1 = createStudentGrader.getInstance();
        calculateGrades g1= c1.getStudentType("undergraduate");
        g1.calculateGrade();
        g1.outputGrades("xml");
        g1.outputGrades("html");
        g1.outputGrades("csv");
        
        calculateGrades g2= c1.getStudentType("graduate");
        g2.calculateGrade();
        g2.outputGrades("xml");
        g2.outputGrades("html");
        g2.outputGrades("csv");
        
    			
    	
        
	}

}
