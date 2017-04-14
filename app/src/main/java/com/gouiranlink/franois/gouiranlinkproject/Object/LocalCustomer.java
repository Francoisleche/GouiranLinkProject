package com.gouiranlink.franois.gouiranlinkproject.Object;

public class LocalCustomer {
    private String[] history = new String[10];

    public String[] getHistory() {
        return history;
    }

    public void setHistory(String[] history) {
        this.history = history;
    }

    public void updateHistory(String research) {
        if (history.length < 10)
            history[history.length] = research;
        else {
            System.arraycopy(history, 1, history, 0, 9);
            history[9] = research;
        }
    }
}
