
public class GradingSchema
{
    private GradeItem[] GradeItem;

    public GradeItem[] getGradeItem ()
    {
        return GradeItem;
    }

    public void setGradeItem (GradeItem[] GradeItem)
    {
        this.GradeItem = GradeItem;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [GradeItem = "+GradeItem+"]";
    }
}