package Model;

public class ReportThree {

    String officeName;
    int count;

    public ReportThree(String officeName, int count) {
        this.officeName = officeName;
        this.count = count;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ReportThree{" +
                "officeName='" + officeName + '\'' +
                ", count=" + count +
                '}';
    }
}
