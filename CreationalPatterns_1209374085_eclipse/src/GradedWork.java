import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {
        "name",
        "grade"
       })
public class GradedWork
{
    private String Name;

    private String Grade;

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getGrade ()
    {
        return Grade;
    }

    public void setGrade (String Grade)
    {
        this.Grade = Grade;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Name = "+Name+", Grade = "+Grade+"]";
    }
}
			