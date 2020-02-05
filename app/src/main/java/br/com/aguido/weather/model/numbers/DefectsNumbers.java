package br.com.aguido.weather.model.numbers;

public class DefectsNumbers {

    private int openDefects;
    private float averageAging;
    private int totalDefects;
    private float averageFixTime;

    public int getOpenDefects() {
        return openDefects;
    }

    public void setOpenDefects(int openDefects) {
        this.openDefects = openDefects;
    }

    public float getAverageAging() {
        return averageAging;
    }

    public void setAverageAging(float averageAging) {
        this.averageAging = averageAging;
    }

    public int getTotalDefects() {
        return totalDefects;
    }

    public void setTotalDefects(int totalDefects) {
        this.totalDefects = totalDefects;
    }

    public float getAverageFixTime() {
        return averageFixTime;
    }

    public void setAverageFixTime(float averageFixTime) {
        this.averageFixTime = averageFixTime;
    }
}
