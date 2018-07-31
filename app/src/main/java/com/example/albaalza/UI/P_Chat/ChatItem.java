package com.example.albaalza.UI.P_Chat;

/**
 * Created by jinyoungkim on 2018. 5. 17..
 */

public class ChatItem {
    String groupname;
    int bookmark=0;

    public String getGroupname() {
        return groupname;
    }

    public ChatItem(String groupname,int bookmark) {
        this.groupname = groupname;
        this.bookmark=bookmark;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }
}
