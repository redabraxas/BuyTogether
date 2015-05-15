package com.chocoroll.buyto.DetailDeal;

/**
 * Created by RA on 2015-05-10.
 */
public class Qna {
    String num;
    String writer;
    String date;
    String content;
    String answerCount;

    Qna(String num, String writer, String date, String content, String answerCount){
        this.num = num;
        this.writer = writer;
        this.date = date;
        this.content = content;
        this.answerCount =answerCount;
    }

    public String getNum(){ return num; }
    public String getWriter(){return writer;}
    public String getDate(){return date;}
    public String getContent(){return content;}
    public String getAnswerCount() {return answerCount; }
}
