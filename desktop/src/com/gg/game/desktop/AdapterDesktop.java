package com.gg.game.desktop;

import com.gg.game.utils.NotificationHandler;

public class AdapterDesktop implements NotificationHandler {

    @Override
    public void showNotification(String title, String text) {
        System.out.println(title + " " + text);
    }
}