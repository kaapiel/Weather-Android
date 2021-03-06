package br.com.aguido.weather.model.mongo;

public class ScoreHistoryUnit implements Comparable<ScoreHistoryUnit>{

    private float score;
    private Long date;

    public Float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public int compareTo(ScoreHistoryUnit o) {
        if(getScore() == null || o.getDate() == null) {
            return 0;
        }
        return getDate().compareTo(o.getDate());
    }
}
