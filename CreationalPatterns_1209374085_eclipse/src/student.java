import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {
        "name",
        "ID",
        "assignedWork",
        "finalGrade"
    })
abstract public class student {

	private String Name;
    private AssignedWork[] AssignedWork;
    
    private String ID;
    private String final_grade;
    
    public String getFinalGrade()
    {
    	return final_grade;
    }
    
    
    
    @XmlElement
    public void setFinalGrade(String str)
    {
    	final_grade = str;
    }
    public String getName ()
    {
        return Name;
    }

    @XmlElement
    public void setName (String Name)
    {
    	
        this.Name = Name;
    }

    
    public AssignedWork[] getAssignedWork ()
    {
        return AssignedWork;
    }

    @XmlElement
    public void setAssignedWork (AssignedWork[] AssignedWork)
    {
        this.AssignedWork = AssignedWork;
    }

    public String getID ()
    {
        return ID;
    }

    @XmlElement
    public void setID (String ID)
    {
        this.ID = ID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Name = "+Name+", AssignedWork = "+AssignedWork+", ID = "+ID+"]";
    }
	
	
}
@XmlRootElement
 class undergraduate extends student
{
	 
}
 
@XmlRootElement
class graduate extends student
{
	
}