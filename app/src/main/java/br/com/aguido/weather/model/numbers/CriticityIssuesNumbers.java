package br.com.aguido.weather.model.numbers;

public class CriticityIssuesNumbers {

    private String blockers;
    private String criticals;
    private String majors;
    private int intBlockers;
    private int intCriticals;
    private int intMajors;

    public int getIntBlockers() {
        return intBlockers;
    }

    public void setIntBlockers(int intBlockers) {
        this.intBlockers = intBlockers;
    }

    public int getIntCriticals() {
        return intCriticals;
    }

    public void setIntCriticals(int intCriticals) {
        this.intCriticals = intCriticals;
    }

    public int getIntMajors() {
        return intMajors;
    }

    public void setIntMajors(int intMajors) {
        this.intMajors = intMajors;
    }

    public String getBlockers() {
        return blockers;
    }

    public void setBlockers(String blockers) {
        this.blockers = blockers;
    }

    public String getCriticals() {
        return criticals;
    }

    public void setCriticals(String criticals) {
        this.criticals = criticals;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }
}
