package com.chocoroll.buyto.Retrofit;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface Retrofit {
	public static final String ROOT = "http://203.252.166.225/php/Hangang";
	public static final String GETIDROOT = "http://graph.facebook.com";
    @POST("/login.php")
    public void login(@Body JsonObject info, Callback<String> callback);
    @POST("/notice.php")
    public void getNotice(Callback<JsonArray> callback);
    @POST("/join.php")
    public void join(@Body JsonObject info, Callback<String> callback);
    @POST("/getcomment.php")
    public void getComment(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/sendcomment.php")
    public void sendComment(@Body JsonObject commentinfo, Callback<String> callback);
    @POST("/getteamlist.php")
    public void getTeamList(Callback<JsonArray> callback);
    @POST("/sendschedule.php")
    public void sendSchedule(@Body JsonObject info, Callback<String> callback);
    @POST("/getschedule.php")
    public void getSchedule(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/getteamofday.php")
    public void getTeamOfDay(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/cancelschedule.php")
    public void cancelSchedule(@Body JsonObject info, Callback<String> callback);
    @POST("/getquestion.php")
    public void getQna(@Body JsonObject info, Callback<JsonObject> callback);
    @POST("/sendquestion.php")
    public void sendQna(@Body JsonObject info, Callback<String> callback);
    @POST("/getanswer.php")
    public void getAnswer(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/sendanswer.php")
    public void sendAnswer(@Body JsonObject info, Callback<String> callback);
    @POST("/sendanswermodify.php")
    public void sendAnswerModify(@Body JsonObject info, Callback<String> callback);
    @POST("/sendanswerdelete.php")
    public void sendAnswerDelete(@Body JsonObject info, Callback<String> callback);
    @POST("/deletequestion.php")
    public void deleteQnA(@Body JsonObject info, Callback<String> callback);
    @POST("/changequestion.php")
    public void modifyQnA(@Body JsonObject info, Callback<String> callback);
    @POST("/sendreport.php")
    public void sendReport(@Body JsonObject info, Callback<String> callback);
    @POST("/getreport.php")
    public void getReport(Callback<JsonArray> callback);
    @POST("/deletereport.php")
    public void deleteReport(@Body JsonObject info, Callback<String> callback);
    @POST("/changereport.php")
    public void changeReport(@Body JsonObject info, Callback<String> callback);
    @POST("/getbloginfo.php")
    public void getBlogInfo(@Body JsonObject info, Callback<JsonArray> callback);
    
    @POST("/findid.php")
    public void findId(@Body JsonObject info, Callback<String> callback);
    @POST("/findpw.php")
    public void findPw(@Body JsonObject info, Callback<String> callback);
    @POST("/getteamname.php")
    public void getTeamname(@Body JsonObject info, Callback<String> callback);
    @POST("/getusermode.php")
    public void getUsermode(@Body JsonObject info, Callback<String> callback);
    @POST("/getteamblog.php")
    public void getTeamInfo(@Body JsonObject info, Callback<JsonObject> callback);
    @POST("/sendteamlogo.php")
    public void sendTeamlogo(@Body JsonObject info, Callback<JsonObject> callback);
    @POST("/sendnewteamname.php")
    public void sendNew_Teamname(@Body JsonObject info, Callback<String> callback);
    @POST("/cancelnewteam.php")
    public void cancelNew_Teamname(@Body JsonObject info, Callback<String> callback);
    @POST("/changeteaminfo.php")
    public void send_TeamInfo(@Body JsonObject info, Callback<String> callback);
    
    
    @POST("/changeemail.php")
    public void changeEmail(@Body JsonObject info, Callback<String> callback);
    @POST("/changepw.php")
    public void changePw(@Body JsonObject info, Callback<String> callback);
    @POST("/getpush.php")
    public void getPush(@Body JsonObject info, Callback<String> callback);
    @POST("/changepush.php")
    public void changePush(@Body JsonObject info, Callback<String> callback);
    @POST("/sendblogphoto.php")
    public void sendBlogPhoto(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/registpush.php")
    public void registPush(@Body JsonObject info, Callback<String> callback);
    @POST("/deleteblogphoto.php")
    public void deleteBlogPhoto(@Body JsonObject info, Callback<String> callback);
    @GET("/{teamurl}")
    public void getFaceBookId(@Path("teamurl") String teamurl, Callback<JsonObject> callback);
    @POST("/sendlink.php")
    public void sendLink(@Body JsonObject info, Callback<String> callback);
    @POST("/deletelink.php")
    public void deleteLink(@Body JsonObject info, Callback<String> callback);
    @POST("/addmember.php")
    public void addMember(@Body JsonObject info, Callback<JsonObject> callback);
    @POST("/deletemember.php")
    public void deleteMember(@Body JsonObject info, Callback<String> callback);
    
    @POST("/modifycomment.php")
    public void modifyComment(@Body JsonObject info, Callback<String> callback);
    @POST("/deletecomment.php")
    public void deleteComment(@Body JsonObject info, Callback<String> callback);
    @POST("/getperformofnow.php")
    public void getPerformOfNow(Callback<JsonObject> callback);
    @POST("/getteamofnow.php")
    public void getTeamOfNow(Callback<JsonArray> callback);
    
    @POST("/getpermapply.php")
    public void getPermApply(Callback<JsonArray> callback);
    @POST("/getpermchange.php")
    public void getPermChange(Callback<JsonArray> callback);
    
    @POST("/sendpermapply.php")
    public void sendPermApply(@Body JsonObject info, Callback<String> callback);
    @POST("/sendpermchange.php")
    public void sendPermChange(@Body JsonObject info, Callback<String> callback);
    @POST("/sendpermdelete.php")
    public void sendPermDelete(@Body JsonObject info, Callback<String> callback);
    @POST("/sendrealtime.php")
    public void sendRealtime(@Body JsonObject info, Callback<String> callback);
    @POST("/quitrealtime.php")
    public void quitRealtime(@Body JsonObject info, Callback<String> callback);
    @POST("/getbookmark.php")
    public void getBookmark(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/sendbookmark.php")
    public void addBookmark(@Body JsonObject info, Callback<String> callback);
    @POST("/deletebookmark.php")
    public void deleteBookmark(@Body JsonObject info, Callback<String> callback);
    @POST("/getmainnotice.php")
    public void getMainNotice(Callback<JsonArray> callback);
    @POST("/sendmainnotice.php")
    public void sendMainNotice(@Body JsonArray info, Callback<String> callback);
    @POST("/getmain.php")
    public void getMain(Callback<JsonArray> callback);
    
    
    @POST("/getcommentanswer.php")
    public void getCommentAnswer(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/sendcommentanswer.php")
    public void sendCommentAnswer(@Body JsonObject info, Callback<String> callback);
    @POST("/modifycommentanswer.php")
    public void ModifyCommentAnswer(@Body JsonObject info, Callback<String> callback);
    @POST("/deletecommentanswer.php")
    public void DeleteCommentAnswer(@Body JsonObject info, Callback<String> callback);
    
}
