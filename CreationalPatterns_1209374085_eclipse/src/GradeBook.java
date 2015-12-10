import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GradeBook
{
    private String class_name;

    private grade Grades;

    public String getClass_name ()
    {
        return class_name;
    }

    @XmlAttribute
    public void setClass_name (String class_name)
    {
        this.class_name = class_name;
    }

   

    public grade getGrades ()
    {
        return Grades;
    }

    @XmlElement
    public void setGrades (grade Grades)
    {
        this.Grades = Grades;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [class = "+class_name+", Grades = "+Grades+"]";
    }
}