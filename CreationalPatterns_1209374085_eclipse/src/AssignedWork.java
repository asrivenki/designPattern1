
public class AssignedWork
{
    private String category;

    private GradedWork[] GradedWork;

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public GradedWork[] getGradedWork ()
    {
        return GradedWork;
    }

    public void setGradedWork (GradedWork[] GradedWork)
    {
        this.GradedWork = GradedWork;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [category = "+category+", GradedWork = "+GradedWork+"]";
    }
}