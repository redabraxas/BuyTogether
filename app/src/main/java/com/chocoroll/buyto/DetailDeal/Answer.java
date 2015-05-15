package com.chocoroll.buyto.DetailDeal;

/**
 * Created by RA on 2015-05-10.
 */
public class Answer {
    String num;
    String content;
    String date;
    String writer;

    Answer(String num, String writer, String date, String content){
        this.num = num;
        this.date = date;
        this.content = content;
        this.writer =writer;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getNum() {
        return num;
    }

    public String getWriter() {
        return writer;
    }
}
