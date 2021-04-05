package com.cos.daangnapp.chat;

// 채팅 데이터
public class ChatData{
    private String msg;
    private String nickname;

    public ChatData() {
    }

    public ChatData(String msg, String nickname) {
        this.msg = msg;
        this.nickname = nickname;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
