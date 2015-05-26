package com.chocoroll.buyto.Extra;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface Retrofit {
    public static final String ROOT = "http://buytogether.dothome.co.kr";
    @POST("/login/login.php")
    public void login(@Body JsonObject info, Callback<String> callback);
    @POST("/login/join.php")
    public void join(@Body JsonObject info, Callback<String> callback);
    @POST("/login/ch_passwd.php")
    public void ch_passwd(@Body JsonObject info, Callback<String> callback);
    @POST("/login/find_passwd.php")
    public void find_passwd(@Body JsonObject info, Callback<String> callback);

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
    @POST("/zzimwish/wish.php")
    public void Wish(@Body JsonObject info, Callback<String> callback);


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
    @POST("/wish_uploads/UploadWishDeal.php")
    public void UploadWishDeal(@Body JsonObject info, Callback<String> callback);

    // 판메자 메뉴
    @POST("/seller/sellerDealList.php") // 판매자의 딜 리스트
    public void getSellerDealList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/seller/sellerDeposit12.php")    // 판매자의 딜 신청 유저들 리스트
    public void getDepositList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/seller/sellerDepositCheck.php") // 입금 확인
    public void sendDepositCheck(@Body JsonObject info, Callback<String> callback);

    // 관리자메뉴
    @POST("/seller/sellerDepositCheck.php") // 관리자에게 딜 승인/거절 리스트
    public void getAdminDealList(Callback<JsonArray> callback);
    @POST("/seller/sellerDepositCheck.php") // 관리자에게 판매자 승인/거절 리스트
    public void getAdminSellerList(Callback<JsonArray> callback);
    @POST("/seller/sellerDepositCheck.php") // 딜 승인
    public void sendDealOK(@Body JsonObject info, Callback<String> callback);
    @POST("/seller/sellerDepositCheck.php") // 딜 거절
    public void sendDealNO(@Body JsonObject info, Callback<String> callback);
    @POST("/seller/sellerDepositCheck.php") // 판매자 승인
    public void sendsSellerOK(@Body JsonObject info, Callback<String> callback);
    @POST("/seller/sellerDepositCheck.php") // 판매자 거절
    public void sendSellerNO(@Body JsonObject info, Callback<String> callback);

    // 나의 딜/위시딜 상태
    @POST("/seller/sellerDepositCheck.php") // 판매자 거절
    public void getDealStateList(@Body JsonObject info, Callback<JsonArray> callback);
}
