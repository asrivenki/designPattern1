import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract public class calculateGrades
{
  abstract public void calculateGrade();
  abstract public void outputGrades(String type);
  String current=null;
  //public abstract void getGrade();
 // public grade g;
  public GradeBook gradeBook;
  //public GradingSchema gradeSchema = new GradingSchema();;
  student Student[];
  public GradeBook getgradeBook ()
  {
      return gradeBook;
  }

  public void setgradeBook (GradeBook gradeBook)
  {
      this.gradeBook = gradeBook;
  }

  @Override
  public String toString()
  {
      return "ClassPojo [GradeBook = "+gradeBook+"]";
  }
  
  public static float assignNumericGrade(String str)
  {
  	if(str.equals("A+"))
  		return (float) 99.5;
  	else if(str.equals("A"))
  		return (float) 96.5;
  	else if(str.equals("A-"))
  		return (float) 92.0;
  	else if(str.equals("B+"))
  		return (float) 88.0;
  	else if(str.equals("B"))
  		return (float) 85.0;
  	else if(str.equals("B-"))
  		return (float) 81.5;
  	else if(str.equals("C+"))
  		return (float) 78.0;
  	else if(str.equals("C"))
  		return (float) 72.0;
  	else if(str.equals("D"))
  		return (float) 65.5;
  	else return (float) 30.0;
  	
  }

  public static String assignLetterGrade(float s)
  {
  	if(s>=99)
  		return "A+";
  	else if(s>=95 && s<99)
  		return "A";
  	else if(s>=90 && s<95)
  		return "A-";
  	else if(s>=87 && s<90)
  		return "B+";
  	else if(s>=84 && s<87)
  		return "B";
  	else if(s>=80 && s<84)
  		return "B-";
  	else if(s>=75 && s<80)
  		return "C+";
  	else if(s>=70 && s<75)
  		return "C";
  	else if(s>=60 && s<70)
  		return "D";
  	else
  		return "E";
  }
  
  public static boolean isNumeric(String str)
  {
    return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
  }
  
}


class undergraduateGradesCalculator extends calculateGrades
{
	 public void printError(String error)
	  {
		  System.out.println("Sorry please correct this error " + error);
	  }
	public void calculateGrade()
	{
		//Calculate three types of grades . csv, xml, JSON
		
		try {
			current = new java.io.File( "." ).getCanonicalPath();
			File fXmlFile = new File(current+ "\\undergraduate.json");
			
				
			ObjectMapper mapper = new ObjectMapper();
			
			
			gradeBook = new GradeBook();
			grade grade_value= new grade(); 
			 
			JsonNode rootNode = mapper.readTree(fXmlFile);
			JsonNode gradeBookNode = rootNode.get("GradeBook"); //Root node
			String gradeBookValue = gradeBookNode.get("-class").textValue();
			gradeBook.setClass_name(gradeBookValue);
			JsonNode gradeSchemaNode = gradeBookNode.get("GradingSchema");
			JsonNode gradeItemNode = gradeSchemaNode.get("GradeItem");
			GradeItem gradeItem[] = new GradeItem[gradeItemNode.size()];
			//System.out.println(gradeBookNode.size());
			int size=gradeItemNode.size();
			int sumPercentage=0;
			HashMap<String,Integer> gradingCategory = new HashMap<String,Integer>();
			for(int i=0;i<size;i++)
			{
				GradeItem g = new GradeItem();
				JsonNode categoryNode = gradeItemNode.get(i).path("Category");
				String categoryName = categoryNode.textValue();
				JsonNode percentageNode = gradeItemNode.get(i).path("Percentage");
				int percentage = Integer.parseInt(percentageNode.textValue());
				gradeItem[i] = g;
				sumPercentage += percentage;
				gradingCategory.put(categoryName, percentage);
			}
			//gradeSchema.setGradeItem(gradeItem);
			if(sumPercentage != 100)
				printError(" Total not equal to 100");  //Should throw an exception
			else
			{
				JsonNode gradeNode= gradeBookNode.get("Grades");
				//System.out.println(gradeNode.size() + "---");
				JsonNode studentNode = gradeNode.get("Student");
				//System.out.println(studentNode.size());
				Student =new  undergraduate[studentNode.size()];
				int student_finalGrade=0;
				for (int temp = 0; temp < studentNode.size(); temp++) { // For loop for students
				
					String studentName = studentNode.get(temp).get("Name").textValue();
					String ID = studentNode.get(temp).get("ID").textValue();
					student s= new undergraduate();
					s.setName(studentName);
					s.setID(ID);
					int assignedWorkLength = studentNode.get(temp).get("AssignedWork").size();
					AssignedWork[] assignedWork = new AssignedWork[assignedWorkLength];
					JsonNode assignedWorkNode = studentNode.get(temp).get("AssignedWork");
				//	System.out.println("Assigned Work " + assignedWorkLength);
					for (int temp_assigned = 0; temp_assigned < assignedWorkLength; temp_assigned++) //For loop for AssignedWork
					{
						AssignedWork tempassignedWork = new AssignedWork();
						JsonNode assignedWorkNode_temp = assignedWorkNode.get(temp_assigned);
						if(gradingCategory.containsKey(assignedWorkNode_temp.get("-category").textValue()) == false)
							printError("Category mismatch");  //Should throw an exception
						int gradedWorkLength = (assignedWorkNode_temp.path("GradedWork").size());
						JsonNode gradedWorkList = assignedWorkNode_temp.path("GradedWork");
						int numeric_grade_assignedwork = 0;
						boolean isArray = assignedWorkNode_temp.path("GradedWork").isArray();
					//	System.out.println("IS Array " + isArray);
						
						if(isArray==false)
						{
							//System.out.println(gradedWorkList.get("Name"));
							GradedWork GradedWork_temp = new GradedWork();
							GradedWork_temp.setName(gradedWorkList.get("Name").textValue());
							GradedWork_temp.setGrade(gradedWorkList.get("Grade").textValue());
							if(!isNumeric(gradedWorkList.get("Grade").textValue()))
							{
								numeric_grade_assignedwork += assignNumericGrade(gradedWorkList.get("Grade").textValue());
							}
							else
							{
								if(Float.parseFloat(gradedWorkList.get("Grade").textValue()) >100 || Float.parseFloat(gradedWorkList.get("Grade").textValue())<0)
									printError("Grade is not between 0 and 100");	
								else
									numeric_grade_assignedwork += Float.parseFloat(gradedWorkList.get("Grade").textValue());
							}
							gradedWorkLength =1;
							GradedWork gradedWork[] = new GradedWork[gradedWorkLength];
							gradedWork[0] = GradedWork_temp;
							tempassignedWork.setGradedWork(gradedWork);
							assignedWork[temp_assigned] = tempassignedWork;
						}
						else
						{
						GradedWork gradedWork[] = new GradedWork[gradedWorkLength];
						
						//System.out.println("GradedWorkLength " + gradedWorkLength);
						//System.out.println("In GradedWork " +gradedWorkList + "Length of categories " + gradedWorkList.size());
						for(int i=0;i<gradedWorkLength;i++)
						{
							JsonNode gradedWorkNode = gradedWorkList.get(i);
							
							GradedWork GradedWork_temp = new GradedWork();
							//System.out.println(gradedWorkNode);
							//System.out.println(gradedWorkNode.get("Name").textValue());
							GradedWork_temp.setName(gradedWorkNode.get("Name").textValue());
							GradedWork_temp.setGrade(gradedWorkNode.get("Grade").textValue());
							if(!isNumeric(gradedWorkNode.get("Grade").textValue()))
							{
								numeric_grade_assignedwork += assignNumericGrade(gradedWorkNode.get("Grade").textValue());
							}
							else
							{
								if(Float.parseFloat(gradedWorkNode.get("Grade").textValue()) >100 || Float.parseFloat(gradedWorkNode.get("Grade").textValue())<0)
									printError("Grade is not between 0 and 100");	
								else
									numeric_grade_assignedwork += Float.parseFloat(gradedWorkNode.get("Grade").textValue());
							}
							gradedWork[i] = GradedWork_temp;
						}
						tempassignedWork.setGradedWork(gradedWork);
						assignedWork[temp_assigned] = tempassignedWork;
						}
						numeric_grade_assignedwork = numeric_grade_assignedwork/gradedWorkLength;
						student_finalGrade += numeric_grade_assignedwork*(gradingCategory.get(assignedWorkNode_temp.get("-category").textValue()))/100 ;
						
					} // Loop for assignedWork
				s.setAssignedWork(assignedWork);
				//System.out.println("Numeric grade for student " + temp + " " + student_finalGrade);
				String final_grade = assignLetterGrade(student_finalGrade);
			    s.setFinalGrade(final_grade);
				Student[temp] = s;
				student_finalGrade = 0;
					
				}
			   }  //Close of else 
						
			grade_value.setStudent(Student);		
			gradeBook.setGrades(grade_value);	
				
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void outputGrades(String type)
	{
		if(type.equalsIgnoreCase("csv"))
		{
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("Student Name,Student ID");
			AssignedWork[] assignedWork_s1 = Student[0].getAssignedWork();
			for(int j=0; j<assignedWork_s1.length; j++ )
			{
				GradedWork gradedWork_s1[] = assignedWork_s1[j].getGradedWork();
			
				for(int k=0;k<gradedWork_s1.length;k++)
				{
					builder.append(","+gradedWork_s1[k].getName());
				}
			}
			builder.append(",Grade\n");
			for(int i=0; i<Student.length;i++)
			{
				String ID = Student[i].getID();
				String name = Student[i].getName();
				builder.append( name + "," + ID);
				String finalGrade = Student[i].getFinalGrade();
				AssignedWork[] assignedWork = Student[i].getAssignedWork();
				for(int j=0; j<assignedWork.length; j++ )
				{
					GradedWork gradedWork[] = assignedWork[j].getGradedWork();
				
					for(int k=0;k<gradedWork.length;k++)
					{
						builder.append("," + gradedWork[k].getGrade());
					}
				}
				builder.append("," + Student[i].getFinalGrade() + "\n");
			}
			FileWriter fstream = new FileWriter(current+ "\\undergraduategrade_output.csv");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(builder.toString());
			out.close();
		}
			catch(Exception e)
			{
			
			}
		}  //End of CSV
		
		else if(type.equalsIgnoreCase("html"))
		{
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<table border=2>");
		builder.append("<tr> <td>Student Name</td><td>Student ID</td>");
		AssignedWork[] assignedWork_s1 = Student[0].getAssignedWork();
		for(int j=0; j<assignedWork_s1.length; j++ )
		{
			GradedWork gradedWork_s1[] = assignedWork_s1[j].getGradedWork();
		
			for(int k=0;k<gradedWork_s1.length;k++)
			{
				builder.append("<td>"+gradedWork_s1[k].getName()+ "</td>");
			}
		}
		builder.append("<td>Grade</td></tr>");
		for(int i=0; i<Student.length;i++)
		{
			String ID = Student[i].getID();
			String name = Student[i].getName();
			builder.append("<tr>");
			builder.append( "<td>"+ name + "</td><td>" + ID + "</td>");
			String finalGrade = Student[i].getFinalGrade();
			AssignedWork[] assignedWork = Student[i].getAssignedWork();
			
			for(int j=0; j<assignedWork.length; j++ )
			{
				GradedWork gradedWork[] = assignedWork[j].getGradedWork();
			    
				for(int k=0;k<gradedWork.length;k++)
				{
					builder.append("<td>" + gradedWork[k].getGrade() + "</td>");
				}
			}
			builder.append("<td>" + Student[i].getFinalGrade() + "</td></tr>");
		}
		builder.append("</table>");
		builder.append("</html>");
		try
		{
			current = new java.io.File( "." ).getCanonicalPath();
			//System.out.println(builder.toString());
		FileWriter fstream = new FileWriter(current+ "\\undergraduateGrade_output.html");
	    BufferedWriter out = new BufferedWriter(fstream);
	    out.write(builder.toString());
	    out.close();
		}
		catch(Exception e)
		{
			System.out.println("Error in HTML");
		}
		} // End of html
		
		else if(type.equalsIgnoreCase("xml"))
		{
			try
			{
				JAXBContext jaxbContext = JAXBContext.newInstance(GradeBook.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				String current = new java.io.File( "." ).getCanonicalPath();
				File fXmlFile = new File(current+ "\\undergraduateGrade_output.xml");
				// output pretty printed
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

				jaxbMarshaller.marshal(gradeBook, fXmlFile);
				//jaxbMarshaller.marshal(gradeBook, System.out);
			
			}
			catch(Exception e)
			{
				
			}
			
		}
	}
	
	
	
}

class graduateGradesCalculator extends calculateGrades
{
	

	  
	  
	  public void printError(String error)
	  {
		  System.out.println("Sorry please correct this error " + error);
	  }
	public void calculateGrade()
	{
		
		try {
			current = new java.io.File( "." ).getCanonicalPath();
			File fXmlFile = new File(current+ "\\student.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
            
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			gradeBook = new GradeBook();
			grade grade_value= new grade(); 
			 
			
			String gradeBookValue = doc.getDocumentElement().getAttribute("class");
			gradeBook.setClass_name(gradeBookValue);
			
			NodeList nList_GradeItem = doc.getElementsByTagName("GradeItem");
			
			//System.out.println(nList_GradeItem.getLength());
			GradeItem gradeItem[] = new GradeItem[nList_GradeItem.getLength()];
			
			int sumPercentage=0;
			HashMap<String,Integer> gradingCategory = new HashMap<String,Integer>();
			for (int temp = 0; temp < nList_GradeItem.getLength(); temp++) {
				
				Node nNode = nList_GradeItem.item(temp);
				Element eElement = (Element) nNode;
				//Assigning each gradeItem
				GradeItem g = new GradeItem();
				
				String category = eElement.getElementsByTagName("Category").item(0).getTextContent();
				int percentage = Integer.parseInt(eElement.getElementsByTagName("Percentage").item(0).getTextContent());
				g.setCategory(category);
				g.setPercentage(percentage);
				gradeItem[temp] = g;
				sumPercentage += percentage;
				gradingCategory.put(category, percentage);
			}
			//System.out.println(gradingCategory.contains("exams"));
			//gradeSchema.setGradeItem(gradeItem);
			if(sumPercentage != 100)
				printError(" Total not equal to 100");  //Should throw an exception
			
			else
			{
			NodeList nList = doc.getElementsByTagName("Student");
			Student =new graduate[nList.getLength()];
			int student_finalGrade=0;
			for (int temp = 0; temp < nList.getLength(); temp++) { //For loop for student

				Node nNode = nList.item(temp);
				Element eElement_student = (Element) nNode;
				//System.out.println(eElement.getElementsByTagName("Name").item(0).getTextContent());
				String name = eElement_student.getElementsByTagName("Name").item(0).getTextContent();
				String ID = eElement_student.getElementsByTagName("ID").item(0).getTextContent();
				student s= new graduate();
				s.setName(name);
				s.setID(ID);
				
				int assignedWorkLength = (eElement_student.getElementsByTagName("AssignedWork").getLength());
				AssignedWork[] assignedWork = new AssignedWork[assignedWorkLength];
				NodeList assignedWorkList = eElement_student.getElementsByTagName("AssignedWork");
				
				for (int temp_assigned = 0; temp_assigned < assignedWorkLength; temp_assigned++) //For loop for AssignedWork
				{
					AssignedWork tempassignedWork = new AssignedWork();
					Node assignedwork = assignedWorkList.item(temp_assigned);
					Element element_assignwork = (Element) assignedwork;
					if(gradingCategory.containsKey(element_assignwork.getAttribute("category").toString()) == false)
						printError("Category mismatch");  //Should throw an exception
					int gradedWorkLength = (element_assignwork.getElementsByTagName("GradedWork").getLength());
					NodeList gradedWorkList = element_assignwork.getElementsByTagName("GradedWork");
					
					//System.out.println("Graded work Length "+ gradedWorkLength);
					GradedWork gradedWork[] = new GradedWork[gradedWorkLength];
					int numeric_grade_assignedwork = 0;
					for(int i=0;i<gradedWorkLength;i++)
					{
						Node nNode1 = gradedWorkList.item(i);
						Element element_gradedWork = (Element) nNode1;
						GradedWork GradedWork_temp = new GradedWork();
						GradedWork_temp.setName(element_gradedWork.getElementsByTagName("Name").item(0).getTextContent());
						GradedWork_temp.setGrade(element_gradedWork.getElementsByTagName("Grade").item(0).getTextContent());
						if(!isNumeric(element_gradedWork.getElementsByTagName("Grade").item(0).getTextContent()))
						{
							numeric_grade_assignedwork += assignNumericGrade(element_gradedWork.getElementsByTagName("Grade").item(0).getTextContent());
						}
						else
						{
							if(Float.parseFloat(element_gradedWork.getElementsByTagName("Grade").item(0).getTextContent()) >100 || Float.parseFloat(element_gradedWork.getElementsByTagName("Grade").item(0).getTextContent())<0)
								printError("Grade is not between 0 and 100");	
							else
								numeric_grade_assignedwork += Float.parseFloat(element_gradedWork.getElementsByTagName("Grade").item(0).getTextContent());
						}
						gradedWork[i] = GradedWork_temp;
					}
					tempassignedWork.setGradedWork(gradedWork);
					assignedWork[temp_assigned] = tempassignedWork;
					numeric_grade_assignedwork = numeric_grade_assignedwork/gradedWorkLength;
					
					student_finalGrade += numeric_grade_assignedwork*(gradingCategory.get(element_assignwork.getAttribute("category")))/100 ;
					
				}
			s.setAssignedWork(assignedWork);
			//System.out.println("Numeric grade for student " + temp + " " + student_finalGrade);
			String final_grade = assignLetterGrade(student_finalGrade);
		    s.setFinalGrade(final_grade);
			Student[temp] = s;
			student_finalGrade = 0;
				
			}
		   }  //Close of else 
			grade_value.setStudent(Student);		
			gradeBook.setGrades(grade_value);	
			
		} 
			catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void outputGrades(String type)
	{
		
		if(type.equalsIgnoreCase("html"))
		{
			StringBuilder builder = new StringBuilder();
			builder.append("<html>");
			builder.append("<table border=2>");
			builder.append("<tr> <td>Student Name</td><td>Student ID</td>");
			AssignedWork[] assignedWork_s1 = Student[0].getAssignedWork();
			for(int j=0; j<assignedWork_s1.length; j++ )
			{
				GradedWork gradedWork_s1[] = assignedWork_s1[j].getGradedWork();
			
				for(int k=0;k<gradedWork_s1.length;k++)
				{
					builder.append("<td>"+gradedWork_s1[k].getName()+ "</td>");
				}
			}
			builder.append("<td>Grade</td></tr>");
			for(int i=0; i<Student.length;i++)
			{
				String ID = Student[i].getID();
				String name = Student[i].getName();
				builder.append("<tr>");
				builder.append( "<td>"+ name + "</td><td>" + ID + "</td>");
				String finalGrade = Student[i].getFinalGrade();
				AssignedWork[] assignedWork = Student[i].getAssignedWork();
				
				for(int j=0; j<assignedWork.length; j++ )
				{
					GradedWork gradedWork[] = assignedWork[j].getGradedWork();
				    
					for(int k=0;k<gradedWork.length;k++)
					{
						builder.append("<td>" + gradedWork[k].getGrade() + "</td>");
					}
				}
				builder.append("<td>" + Student[i].getFinalGrade() + "</td></tr>");
			}
			builder.append("</table>");
			builder.append("</html>");
			try
			{
				current = new java.io.File( "." ).getCanonicalPath();
				//System.out.println(builder.toString());
			FileWriter fstream = new FileWriter(current+ "\\graduateGrade_output.html");
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(builder.toString());
		    out.close();
			}
			catch(Exception e)
			{
				System.out.println("Error in HTML");
			}
		}   // End of Html
		
		else if(type.equalsIgnoreCase("csv"))
		{
			try
			{

				StringBuilder builder = new StringBuilder();
				builder.append("Student Name,Student ID");
				AssignedWork[] assignedWork_s1 = Student[0].getAssignedWork();
				for(int j=0; j<assignedWork_s1.length; j++ )
				{
					GradedWork gradedWork_s1[] = assignedWork_s1[j].getGradedWork();
				
					for(int k=0;k<gradedWork_s1.length;k++)
					{
						builder.append(","+gradedWork_s1[k].getName());
					}
				}
				builder.append(",Grade\n");
				for(int i=0; i<Student.length;i++)
				{
					String ID = Student[i].getID();
					String name = Student[i].getName();
					builder.append( name + "," + ID);
					String finalGrade = Student[i].getFinalGrade();
					AssignedWork[] assignedWork = Student[i].getAssignedWork();
					for(int j=0; j<assignedWork.length; j++ )
					{
						GradedWork gradedWork[] = assignedWork[j].getGradedWork();
					
						for(int k=0;k<gradedWork.length;k++)
						{
							builder.append("," + gradedWork[k].getGrade());
						}
					}
					builder.append("," + Student[i].getFinalGrade() + "\n");
				}
				FileWriter fstream = new FileWriter(current+ "\\graduateGrade_output.csv");
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(builder.toString());
				out.close();
			}
			catch(Exception e)
			{
				
			}
		}   //End of CSV
		
		else if(type.equalsIgnoreCase("xml"))
		{
			
			try {
				JAXBContext jaxbContext1 = JAXBContext.newInstance(GradeBook.class);
				Marshaller jaxbMarshaller = jaxbContext1.createMarshaller();
				String current="";
				try {
					current = new java.io.File( "." ).getCanonicalPath();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				File fXmlFile = new File(current+ "\\graduateGrade_output.xml");
				// output pretty printed
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

				jaxbMarshaller.marshal(gradeBook, fXmlFile);
//				jaxbMarshaller.marshal(gradeBook, System.out);
			}
			catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	
}

