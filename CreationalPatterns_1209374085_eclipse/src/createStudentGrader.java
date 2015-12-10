
public class createStudentGrader {
	private static createStudentGrader myObj = new createStudentGrader();
    /**
     * Create private constructor
     */
    private createStudentGrader(){
         
    }
    public static createStudentGrader getInstance(){
        return myObj;
    }

    
	public calculateGrades getStudentType(String type)
	{
		if(type.equalsIgnoreCase("undergraduate"))
		{
			return(new undergraduateGradesCalculator());
		}
		else if(type.equalsIgnoreCase("graduate"))
		{
			return(new graduateGradesCalculator());
		}
		else
			return null;
	}
	
	
}
