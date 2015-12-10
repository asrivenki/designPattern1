
public class grade {
  
  public student[] Student;
  
  public student[] getStudent ()
  {
      return Student;
  }

  public void setStudent (student[] Student)
  {
      this.Student = Student;
  }

  @Override
  public String toString()
  {
      return "ClassPojo [Student = "+Student+"]";
  }
  
  
	
	
}

