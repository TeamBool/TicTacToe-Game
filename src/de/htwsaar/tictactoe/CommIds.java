package de.htwsaar.tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommIds {
    public List<Integer> commLibIds = new ArrayList();

    public CommIds() {
    }

    public void addCommLibId(int commId) {
        this.commLibIds.add(commId);
    }

    public List<Integer> getCommLibIds() {
        return this.commLibIds;
    }
}
