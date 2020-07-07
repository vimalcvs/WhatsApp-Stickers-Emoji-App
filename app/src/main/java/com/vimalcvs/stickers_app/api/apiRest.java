package com.vimalcvs.stickers_app.api;



import com.google.gson.JsonObject;
import com.vimalcvs.stickers_app.config.Config;
import com.vimalcvs.stickers_app.entity.ApiResponse;
import com.vimalcvs.stickers_app.entity.CategoryApi;
import com.vimalcvs.stickers_app.entity.PackApi;
import com.vimalcvs.stickers_app.entity.SlideApi;
import com.vimalcvs.stickers_app.entity.TagApi;
import com.vimalcvs.stickers_app.entity.UserApi;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface apiRest {

    @GET("stickers/")
    Call<JsonObject> list();

    @GET("slide/all/"+ Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<List<SlideApi>> slideAll();

    @FormUrlEncoded
    @POST("pack/add/download/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> addDownload(@Field("id")  Integer id);

    @FormUrlEncoded
    @POST("pack/delete/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> deletePack(@Field("user")  Integer user,@Field("key")  String key,@Field("pack")  Integer pack);

    @GET("device/{tkn}/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> addDevice(@Path("tkn")  String tkn);

    @GET("install/add/{id}/"+ Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> addInstall(@Path("id") String id);


    @GET("pack/all/{page}/{order}/"+ Config.SECURE_KEY+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<PackApi>> packsAll(@Path("page") Integer page, @Path("order") String order);

    @GET("pack/by/id/{pack}/"+ Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<PackApi> packById(@Path("pack") Integer pack);

    @GET("pack/by/user/{page}/{order}/{user}/"+ Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<List<PackApi>> packsByUser(@Path("page") Integer page,@Path("order") String order, @Path("user") Integer user);

    @GET("pack/by/me/{page}/{user}/"+ Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<List<PackApi>> packsByMe(@Path("page") Integer page, @Path("user") Integer user);


    @GET("pack/by/category/{page}/{order}/{category}/"+ Config.SECURE_KEY+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<PackApi>> packsByCategory(@Path("page") Integer page, @Path("order") String order, @Path("category") Integer category);

    @GET("pack/by/follow/{page}/{user}/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<List<PackApi>> packsByFollwing(@Path("page") Integer page, @Path("user") Integer user);

    @GET("pack/by/query/{page}/{query}/"+ Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<List<PackApi>> packsByQuery(@Path("page") Integer page, @Path("query") String query);


    @GET("category/all/"+ Config.SECURE_KEY+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<CategoryApi>> AllCategories();

    @GET("category/popular/"+ Config.SECURE_KEY+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<CategoryApi>> PopularCategories();

    @GET("tags/all/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<List<TagApi>> TagList();

    @GET("user/followingstop/{user}/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<List<UserApi>> getFollowingTop(@Path("user") Integer user);

    @GET("user/followers/{user}/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<List<UserApi>> getFollowers(@Path("user") Integer user);

    @GET("user/followings/{user}/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<List<UserApi>> getFollowing(@Path("user") Integer user);

    @GET("user/follow/{user}/{follower}/{key}/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> follow(@Path("user") Integer user,@Path("follower") Integer follower,@Path("key") String key);


    @GET("rate/add/{user}/{pack}/{value}/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> addRate(@Path("user")  String user,@Path("pack") Integer pack,@Path("value") float value);

    @GET("rate/get/{user}/{pack}/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> getRate(@Path("user")  String user,@Path("pack") Integer pack);

    @Multipart
    @POST("pack/upload/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> uploadPack(@Part MultipartBody.Part file,@Part List<MultipartBody.Part> files, @Part("size") Integer size, @Part("id") String id, @Part("key") String key, @Part("name") String name, @Part("publisher") String publisher,@Part("email") String email,@Part("website") String website,@Part("privacy") String privacy,@Part("license") String license, @Part("categories") String categories);


    @FormUrlEncoded
    @POST("user/register/"+ Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> register(@Field("name") String name, @Field("username") String username, @Field("password") String password, @Field("type") String type, @Field("image") String image);

    @FormUrlEncoded
    @POST("user/token/"+ Config.SECURE_KEY+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> editToken(@Field("user") Integer user, @Field("key") String key, @Field("token_f") String token_f, @Field("name") String name);


    @GET("user/get/{user}/{me}/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> getUser(@Path("user") Integer user,@Path("me") Integer me);

    @FormUrlEncoded
    @POST("support/add/"+Config.SECURE_KEY+"/"+Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> addSupport(@Field("email") String email, @Field("name") String name , @Field("message") String message);

    @Multipart
    @POST("user/edit/"+ Config.SECURE_KEY+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> editUser(@Part MultipartBody.Part file,@Part("user") Integer user,@Part("key") String key,@Part("name") String name,@Part("email") String email,@Part("facebook") String facebook,@Part("twitter") String twitter,@Part("instagram") String instagram);


    @GET("version/check/{code}/"+ Config.SECURE_KEY+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> check(@Path("code") Integer code);

    @GET("user/check/{id}/{key}/"+ Config.SECURE_KEY+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> checkUser(@Path("id") Integer id,@Path("key") String key);
}
