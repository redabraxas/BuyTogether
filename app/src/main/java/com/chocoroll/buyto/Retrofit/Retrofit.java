package com.chocoroll.buyto.Retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface Retrofit {
    public static final String ROOT = "http://buytogether.dothome.co.kr";
    @POST("/login/login.php")
    public void login(@Body JsonObject info, Callback<String> callback);

    // 메인화면의 네가지 딜 리스트
    @POST("/listview/opendatelist.php")
    public void getOpenDateList(Callback<JsonArray> callback);
    @POST("/listview/limitdatelist.php")
    public void getLimitDateList(Callback<JsonArray> callback);
    @POST("/listview/bestdeal.php")
    public void getBestDealList(Callback<JsonArray> callback);
    @POST("/listview/bestwish.php")
    public void getBestWishList(Callback<JsonArray> callback);

    // 카테고리 별 딜 리스트
    @POST("/listview/categorylist.php")
    public void getDealList(@Body JsonObject info, Callback<JsonArray> callback);
    // 카테고리 별 딜 리스트
    @POST("/listview/wishDealCategoryList.php")
    public void getWishDealList(@Body JsonObject info, Callback<JsonArray> callback);

    // 신청(book), 찜(keep)
    @POST("/zzimwish/bookdeal.php")
    public void sendBookDeal(@Body JsonObject info, Callback<String> callback);
    @POST("/zzimwish/keepdeal.php")
    public void sendKeepDeal(@Body JsonObject info, Callback<String> callback);

    // 질문,답변
    @POST("/listview/detailquestion.php")
    public void getQnaList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/qna/enrollQuestion.php")
    public void sendQna(@Body JsonObject info, Callback<String> callback);
    @POST("/listview/detailanswer.php")
    public void getAnswerList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/qna/enrollAnswer.php")
    public void sendAnswer(@Body JsonObject info, Callback<String> callback);

    // Deal만들기
    @POST("/uploads/UploadDeal2.php")
    public void UploadDeal(@Body JsonObject info, Callback<String> callback);


}
