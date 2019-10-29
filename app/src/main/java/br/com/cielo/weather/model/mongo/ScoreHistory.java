package br.com.cielo.weather.model.mongo;

import java.util.List;

public class ScoreHistory {

    private String sysName;
    private List<ScoreHistoryUnit> scoreHistoryList;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public List<ScoreHistoryUnit> getScoreHistoryList() {
        return scoreHistoryList;
    }

    public void setScoreHistoryList(List<ScoreHistoryUnit> scoreHistoryList) {
        this.scoreHistoryList = scoreHistoryList;
    }
}
