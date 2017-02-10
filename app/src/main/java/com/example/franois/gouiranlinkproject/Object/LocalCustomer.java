package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by pyram_m on 31/01/17.
 */

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
            for (int i = 0; i < 9; i++) {
                history[i] = history[i + 1];
            }
            history[9] = research;
        }
    }
}
