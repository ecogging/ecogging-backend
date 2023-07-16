package com.pickupluck.ecogging.domain.notification.entity;

public enum NotificationType {
    MESSAGE("쪽지"),
    COMMENT("댓글"),
    REPLYCOMMENT("대댓글"),
    ACCOMPANY("동행");

    private final String name;

    NotificationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
