package br.com.cielo.weather.model.numbers;

public class FunctionalTestsNumbers {

    private long testCases;
    private int totalExecutions;
    private int executionsPassed;
    private float successRate;
    private float defectsDensity;

    public long getTestCases() {
        return testCases;
    }

    public void setTestCases(long testCases) {
        this.testCases = testCases;
    }

    public int getTotalExecutions() {
        return totalExecutions;
    }

    public void setTotalExecutions(int totalExecutions) {
        this.totalExecutions = totalExecutions;
    }

    public float getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(float successRate) {
        this.successRate = successRate;
    }

    public float getDefectsDensity() {
        return defectsDensity;
    }

    public void setDefectsDensity(float defectsDensity) {
        this.defectsDensity = defectsDensity;
    }

    public int getExecutionsPassed() {
        return executionsPassed;
    }

    public void setExecutionsPassed(int executionsPassed) {
        this.executionsPassed = executionsPassed;
    }
}
