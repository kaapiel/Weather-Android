package br.com.aguido.weather.model.numbers;

import java.io.Serializable;

public class OverallNumbers implements Serializable {

    private String overallLines;
    private String overallIssues;
    private String overallBugs;

    private int overallLinesNumber;
    private int overallIssuesNumber;
    private int overallBugsNumber;

    public int getOverallLinesNumber() {
        return overallLinesNumber;
    }

    public void setOverallLinesNumber(int overallLinesNumber) {
        this.overallLinesNumber = overallLinesNumber;
    }

    public int getOverallIssuesNumber() {
        return overallIssuesNumber;
    }

    public void setOverallIssuesNumber(int overallIssuesNumber) {
        this.overallIssuesNumber = overallIssuesNumber;
    }

    public int getOverallBugsNumber() {
        return overallBugsNumber;
    }

    public void setOverallBugsNumber(int overallBugsNumber) {
        this.overallBugsNumber = overallBugsNumber;
    }

    public String getOverallLines() {
        return overallLines;
    }

    public void setOverallLines(String overallLines) {
        this.overallLines = overallLines;
    }

    public String getOverallIssues() {
        return overallIssues;
    }

    public void setOverallIssues(String overallIssues) {
        this.overallIssues = overallIssues;
    }

    public String getOverallBugs() {
        return overallBugs;
    }

    public void setOverallBugs(String overallBugs) {
        this.overallBugs = overallBugs;
    }
}
