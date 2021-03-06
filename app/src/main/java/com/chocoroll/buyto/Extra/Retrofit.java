package com.chocoroll.buyto.Extra;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface Retrofit {
    public static final String ROOT = "http://ourproject.dothome.co.kr/buytogether";
    @POST("/login/login.php")
    public void login(@Body JsonObject info, Callback<JsonElement> callback);
    @POST("/login/join.php")    //
    public void join(@Body JsonObject info, Callback<String> callback);
    @POST("/login/sellerJoin.php")
    public void seller_join(@Body JsonObject info, Callback<String> callback);
    @POST("/login/ch_myinfo.php")//key값 passwd
    public void ch_passwd(@Body JsonObject info, Callback<String> callback);
    @POST("/login/findPasswd.php")//key 값 "user_email" 받고, 없으면 failed 출력 + 현재 user의 비밀번호 발송한 임시 비밀번호로 변경
    public void find_passwd(@Body JsonObject info, Callback<String> callback);
    @POST("/login/ch_push.php")//push alarm 정보 변경 (key값 "pushalarm")
    public void pushalarm(@Body JsonObject info, Callback<String> callback);

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
    @POST("/seller/sellerDelivery.php") // 배송
    public void sellerDeliveryOK(@Body JsonObject info, Callback<String> callback);
    @POST("/seller/sellerDelete.php") // 삭제
    public void sellerDelete(@Body JsonObject info, Callback<String> callback);

    // 관리자메뉴
    @POST("/listview/dealState3List.php") // 관리자에게 딜 승인/거절 리스트
    public void getAdminDealList(Callback<JsonArray> callback);
    @POST("/listview/sellerRequestList.php") // 관리자에게 판매자 승인/거절 리스트
    public void getAdminSellerList(Callback<JsonArray> callback);
    @POST("/mydeal/dealState3to0.php") // 딜 승인/거절
    public void sendDealResult(@Body JsonObject info, Callback<String> callback);
    @POST("/seller/sellerRequestUp.php") // 판매자 승인/거절
    public void sendsSellerResult(@Body JsonObject info, Callback<String> callback);

    // 나의 딜/위시딜 상태
    @POST("/mydeal/showMyBookDeal.php") // 나의 신청딜 리스트를 가져옴
    public void getDealBookStateList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/mydeal/showMyKeepDeal.php") // 나의 찜딜 리스트를 가져옴
    public void getDealKeepStateList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/seller/sellerDepositDecide.php") // 구매확정
    public void sendBuySure(@Body JsonObject info, Callback<String> callback);

    // 즐겨찾기
    @POST("/mydeal/bookmarkList.php") // 북마크 리스트 가져옴
    public void getBookMarkList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/mydeal/bookmark.php") // 북마크 추가
    public void addBookMark(@Body JsonObject info, Callback<String> callback);
    @POST("/mydeal/bookmarkDelete.php") // 북마크 삭제
    public void deleteBookMark(@Body JsonObject info, Callback<String> callback);


    @POST("/seller/getSellerInfo.php") // 셀러정보 가져오기
    public void getSellerInfo(@Body JsonObject info, Callback<JsonElement> callback);
}
