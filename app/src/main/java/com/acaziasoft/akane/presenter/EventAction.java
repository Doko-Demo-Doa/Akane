package com.acaziasoft.akane.presenter;

import com.acaziasoft.akane.model.Item;

import java.util.List;

/**
 * Created by duyth on 11/29/2017.
 */

public class EventAction {
    public static class ReloadData{
        public Item item;

        public ReloadData(Item item) {
            this.item = item;
        }
    }
}
