package br.com.aguido.weather.model.numbers;

public class CodeInspectionNumbers {

    private String lines;
    private String totalIssues;
    private String openIssues;
    private String coverage;
    private String duplication;
    private int intLines;
    private int intTotalIssues;
    private int intOpenIssues;
    private float floatCoverage;
    private float floatDuplication;

    public float getFloatCoverage() {
        return floatCoverage;
    }

    public void setFloatCoverage(float floatCoverage) {
        this.floatCoverage = floatCoverage;
    }

    public float getFloatDuplication() {
        return floatDuplication;
    }

    public void setFloatDuplication(float floatDuplication) {
        this.floatDuplication = floatDuplication;
    }

    public int getIntLines() {
        return intLines;
    }

    public void setIntLines(int intLines) {
        this.intLines = intLines;
    }

    public int getIntTotalIssues() {
        return intTotalIssues;
    }

    public void setIntTotalIssues(int intTotalIssues) {
        this.intTotalIssues = intTotalIssues;
    }

    public int getIntOpenIssues() {
        return intOpenIssues;
    }

    public void setIntOpenIssues(int intOpenIssues) {
        this.intOpenIssues = intOpenIssues;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public String getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(String totalIssues) {
        this.totalIssues = totalIssues;
    }

    public String getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(String openIssues) {
        this.openIssues = openIssues;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getDuplication() {
        return duplication;
    }

    public void setDuplication(String duplication) {
        this.duplication = duplication;
    }
}
