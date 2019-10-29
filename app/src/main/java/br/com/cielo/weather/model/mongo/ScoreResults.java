package br.com.cielo.weather.model.mongo;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.cielo.weather.util.Util;

public class ScoreResults implements Parcelable {

    private _id _id;
    private String applicationName;
    private float score;
    private int totalIssues;
    private int openIssues;
    private float duplicationDensity;
    private float coverage;
    private int openDefects;
    private int totalDefects;
    private float averageAge;
    private float averageTimeFix;
    private float effectiveness;
    private float scoreTotalIssues;
    private float scoreOpenIssues;
    private float scoreDuplicationDensity;
    private float scoreCoverage;
    private float scoreOpenDefects;
    private float scoreTotalDefects;
    private float scoreAverageAge;
    private float scoreAverageTimeFix;
    private float scoreSuccessRate;
    private float scoreEffectiveness;
    private TestsCase testsCase; // testCases
    private float defectsDensity;
    private int totalTestExecutions;
    private int totalProblems;
    private int ncloc;
    private int issuesBlocker;
    private int issuesCritical;
    private int issuesMajor;
    private int issuesBug;
    private int issuesVulnerabilities;
    private int issuesCodeSmell;
    private float successRate;
    private int executionsPassed;
    private int sysIcon;

    public br.com.cielo.weather.model.mongo._id get_id() {
        return _id;
    }

    public void set_id(br.com.cielo.weather.model.mongo._id _id) {
        this._id = _id;
    }

    public int getExecutionsPassed() {
        return executionsPassed;
    }

    public void setExecutionsPassed(int executionsPassed) {
        this.executionsPassed = executionsPassed;
    }

    public int getTotalTestExecutions() {
        return totalTestExecutions;
    }

    public void setTotalTestExecutions(int totalTestExecutions) {
        this.totalTestExecutions = totalTestExecutions;
    }

    public int getTotalProblems() {
        return totalProblems;
    }

    public void setTotalProblems(int totalProblems) {
        this.totalProblems = totalProblems;
    }

    public int getNcloc() {
        return ncloc;
    }

    public void setNcloc(int ncloc) {
        this.ncloc = ncloc;
    }

    public int getIssuesBlocker() {
        return issuesBlocker;
    }

    public void setIssuesBlocker(int issuesBlocker) {
        this.issuesBlocker = issuesBlocker;
    }

    public int getIssuesCritical() {
        return issuesCritical;
    }

    public void setIssuesCritical(int issuesCritical) {
        this.issuesCritical = issuesCritical;
    }

    public int getIssuesMajor() {
        return issuesMajor;
    }

    public void setIssuesMajor(int issuesMajor) {
        this.issuesMajor = issuesMajor;
    }

    public int getIssuesBug() {
        return issuesBug;
    }

    public void setIssuesBug(int issuesBug) {
        this.issuesBug = issuesBug;
    }

    public int getIssuesVulnerabilities() {
        return issuesVulnerabilities;
    }

    public void setIssuesVulnerabilities(int issuesVulnerabilities) {
        this.issuesVulnerabilities = issuesVulnerabilities;
    }

    public int getIssuesCodeSmell() {
        return issuesCodeSmell;
    }

    public void setIssuesCodeSmell(int issuesCodeSmell) {
        this.issuesCodeSmell = issuesCodeSmell;
    }

    public float getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(float successRate) {
        this.successRate = successRate;
    }

    public int getSysIcon() {
        return sysIcon;
    }

    public void setSysIcon(int sysIcon) {
        this.sysIcon = sysIcon;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(int totalIssues) {
        this.totalIssues = totalIssues;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    public float getDuplicationDensity() {
        return duplicationDensity;
    }

    public void setDuplicationDensity(float duplicationDensity) {
        this.duplicationDensity = duplicationDensity;
    }

    public float getCoverage() {
        return coverage;
    }

    public void setCoverage(float coverage) {
        this.coverage = coverage;
    }

    public int getOpenDefects() {
        return openDefects;
    }

    public void setOpenDefects(int openDefects) {
        this.openDefects = openDefects;
    }

    public int getTotalDefects() {
        return totalDefects;
    }

    public void setTotalDefects(int totalDefects) {
        this.totalDefects = totalDefects;
    }

    public float getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(float averageAge) {
        this.averageAge = averageAge;
    }

    public float getAverageTimeFix() {
        return averageTimeFix;
    }

    public void setAverageTimeFix(float averageTimeFix) {
        this.averageTimeFix = averageTimeFix;
    }

    public float getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(float effectiveness) {
        this.effectiveness = effectiveness;
    }

    public float getScoreTotalIssues() {
        return scoreTotalIssues;
    }

    public void setScoreTotalIssues(float scoreTotalIssues) {
        this.scoreTotalIssues = scoreTotalIssues;
    }

    public float getScoreOpenIssues() {
        return scoreOpenIssues;
    }

    public void setScoreOpenIssues(float scoreOpenIssues) {
        this.scoreOpenIssues = scoreOpenIssues;
    }

    public float getScoreDuplicationDensity() {
        return scoreDuplicationDensity;
    }

    public void setScoreDuplicationDensity(float scoreDuplicationDensity) {
        this.scoreDuplicationDensity = scoreDuplicationDensity;
    }

    public float getScoreCoverage() {
        return scoreCoverage;
    }

    public void setScoreCoverage(float scoreCoverage) {
        this.scoreCoverage = scoreCoverage;
    }

    public float getScoreOpenDefects() {
        return scoreOpenDefects;
    }

    public void setScoreOpenDefects(float scoreOpenDefects) {
        this.scoreOpenDefects = scoreOpenDefects;
    }

    public float getScoreTotalDefects() {
        return scoreTotalDefects;
    }

    public void setScoreTotalDefects(float scoreTotalDefects) {
        this.scoreTotalDefects = scoreTotalDefects;
    }

    public float getScoreAverageAge() {
        return scoreAverageAge;
    }

    public void setScoreAverageAge(float scoreAverageAge) {
        this.scoreAverageAge = scoreAverageAge;
    }

    public float getScoreAverageTimeFix() {
        return scoreAverageTimeFix;
    }

    public void setScoreAverageTimeFix(float scoreAverageTimeFix) {
        this.scoreAverageTimeFix = scoreAverageTimeFix;
    }

    public float getScoreSuccessRate() {
        return scoreSuccessRate;
    }

    public void setScoreSuccessRate(float scoreSuccessRate) {
        this.scoreSuccessRate = scoreSuccessRate;
    }

    public float getScoreEffectiveness() {
        return scoreEffectiveness;
    }

    public void setScoreEffectiveness(float scoreEffectiveness) {
        this.scoreEffectiveness = scoreEffectiveness;
    }

    public TestsCase getTestsCase() {
        return testsCase;
    }

    public void setTestsCase(TestsCase testsCase) {
        this.testsCase = testsCase;
    }

    public float getDefectsDensity() {
        return defectsDensity;
    }

    public void setDefectsDensity(float defectsDensity) {
        this.defectsDensity = defectsDensity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
