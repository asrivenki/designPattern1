public class GradeItem
{
    private int Percentage;

    private String Category;

    public int getPercentage ()
    {
        return Percentage;
    }

    public void setPercentage (int Percentage)
    {
        this.Percentage = Percentage;
    }

    public String getCategory ()
    {
        return Category;
    }

    public void setCategory (String Category)
    {
        this.Category = Category;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Percentage = "+Percentage+", Category = "+Category+"]";
    }
}