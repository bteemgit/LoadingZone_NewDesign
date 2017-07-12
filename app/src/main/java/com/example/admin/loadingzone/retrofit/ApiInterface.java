package com.example.admin.loadingzone.retrofit;

import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.retrofit.model.ActiveTrucklistResponse;
import com.example.admin.loadingzone.retrofit.model.AdddriverResponnse;
import com.example.admin.loadingzone.retrofit.model.BlockTruckandDriverResponse;
import com.example.admin.loadingzone.retrofit.model.LoginResponse;
import com.example.admin.loadingzone.retrofit.model.MakerResponse;
import com.example.admin.loadingzone.retrofit.model.Meta;
import com.example.admin.loadingzone.retrofit.model.ModelResponse;
import com.example.admin.loadingzone.retrofit.model.PendingJobResponse;
import com.example.admin.loadingzone.retrofit.model.PendingQutationResponse;
import com.example.admin.loadingzone.retrofit.model.PostedJobResponse;
import com.example.admin.loadingzone.retrofit.model.QutationApplyResponse;
import com.example.admin.loadingzone.retrofit.model.TruckAddResponse;
import com.example.admin.loadingzone.retrofit.model.TruckDriverAddResponse;
import com.example.admin.loadingzone.retrofit.model.TruckDriverViewResponse;
import com.example.admin.loadingzone.retrofit.model.TruckResponse;
import com.example.admin.loadingzone.retrofit.model.TruckTypeResponse;
import com.example.admin.loadingzone.retrofit.model.TruckYearResponse;
import com.example.admin.loadingzone.retrofit.model.UserProfileResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleDetailsResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 5/15/2017.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("main/signup")
    Call<LoginResponse> SignUp(@Field("username") String username, @Field("password") String password, @Field("user_role_id") String type, @Field("user_type_id") String user_type_id);


    @FormUrlEncoded
    @POST("main/login")
    Call<LoginResponse> Singin(@Field("username") String username, @Field("password") String password, @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST("service-provider")
    Call<UserProfileResponse> CreateProfileDetails(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Field("provider_name") String provider_name, @Field("phone_1") String phone_1,
                                                   @Field("location_name") String location_name, @Field("latitude") String latitude, @Field("longitude") String longitude);

    @FormUrlEncoded
    @PUT("service-provider/{service_provider_id}")
    Call<UserProfileResponse> CreateProfileUpdate(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Path("service_provider_id") String service_provider_id, @Field("provider_name") String provider_name, @Field("phone_1") String phone_1,
                                                  @Field("location_name") String location_name, @Field("latitude") String latitude, @Field("longitude") String longitude);

    @Multipart
    @POST("service-provider/avatar")
    Call<UserProfileResponse> UploadprofilePicProvider(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Part MultipartBody.Part file);

    @GET("service-provider")
    Call<UserProfileResponse> UserProfile(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token);

    @FormUrlEncoded
    @POST("main/logout")
    Call<Meta> Logout(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Field("device_token") String device_token);

    @GET("provider-vehicles")
    Call<TruckResponse> TrckList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("page") int page);

    @GET("vehicle/maker")
    Call<MakerResponse> TrckMakerList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token);

    @GET("vehicle/model")
    Call<ModelResponse> TrckModelList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("maker_id") int maker_id);

    @GET("vehicle/truck-type")
    Call<TruckTypeResponse> TruckTypeList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token);

    @GET("vehicle/model-year")
    Call<TruckYearResponse> TruckYearList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token);

    @FormUrlEncoded
    @POST("provider-vehicles")
    Call<TruckAddResponse> TruckAdd(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Field("avg_running_speed") String avg_running_speed, @Field("custom_name") String custom_name,
                                    @Field("insurance_exp_date") String insurance_exp_date, @Field("weight") String weight, @Field("container_length") String container_length, @Field("container_width") String container_width
            , @Field("container_height") String container_height, @Field("model_id") String model_id, @Field("truck_type_id") String truck_type_id, @Field("model_year") String model_year);
    @FormUrlEncoded
    @PUT("provider-vehicles/{provider_vehicle_id}")
    Call<TruckAddResponse> TruckUpdate(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Path("provider_vehicle_id") String provider_vehicle_id,@Field("avg_running_speed") String avg_running_speed, @Field("custom_name") String custom_name,
                                    @Field("insurance_exp_date") String insurance_exp_date, @Field("weight") String weight, @Field("container_length") String container_length, @Field("container_width") String container_width
            ,@Field("container_height") String container_height, @Field("model_id") String model_id, @Field("truck_type_id") String truck_type_id, @Field("model_year") String model_year);
    @DELETE("provider-vehicles/{provider_vehicle_id}")
    Call<TruckAddResponse>DeleteTruck(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Path("provider_vehicle_id") String provider_vehicle_id);
    @GET("provider-vehicles/{provider_vehicle_id}")
    Call<VehicleDetailsResponse>GetTruckDetails(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Path("provider_vehicle_id") String provider_vehicle_id);
    @FormUrlEncoded
    @POST("driver")
    Call<TruckDriverAddResponse> AddDriver(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Field("driver_name") String driver_name, @Field("driver_phone") String driver_phone, @Field("driver_email") String driver_email, @Field("driver_address") String driver_address);


    @FormUrlEncoded
    @PUT("driver/{driver_id}")
    Call<TruckDriverAddResponse>UpdateDriver(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Path("driver_id") String driver_id,@Field("driver_name") String driver_name, @Field("driver_phone") String driver_phone, @Field("driver_email") String driver_email, @Field("driver_address") String driver_address);
    @DELETE("driver/{driver_id}")
    Call<TruckDriverAddResponse>DeleteDriver(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Path("driver_id") String driver_id);

    @Multipart
    @POST("driver/avatar")
    Call<UserProfileResponse> UploadDriverprofilePic(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Part MultipartBody.Part file);

    @GET("driver")
    Call<TruckDriverViewResponse> driverList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token);


    @GET("job")
    Call<PostedJobResponse> PostedJobList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("page") int page);

    @FormUrlEncoded
    @POST("quotation")
    Call<QutationApplyResponse>ApplyQutation(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Field("job_id") String job_id, @Field("quotation_amount") String quotation_amount, @Field("quotation_description") String quotation_description);

    @FormUrlEncoded
    @PUT("quotation/{quotation_id}")
    Call<QutationApplyResponse> UpdateQutation(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Path("quotation_id") String quotation_id, @Field("quotation_amount") String quotation_amount, @Field("quotation_description") String quotation_description );

    @DELETE("quotation/{quotation_id}")
    Call<QutationApplyResponse> DeleteQutation(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Path("quotation_id") String quotation_id);

    @GET("quotation/pending-quotations")
    Call<PendingQutationResponse> PendingQutationList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("page") int page);
    @GET("quotation/accepted-quotations")
    Call<PendingQutationResponse> AcceptedQutationList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("page") int page);
    @GET("quotation/rejected-quotations")
    Call<PendingQutationResponse> RejectedQutationList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("page") int page);
    @GET("job/completed-list")
    Call<PendingJobResponse> CompletedJobList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("service_provider_id") String service_provider_id,@Query("page") int page);
    @GET("job/pending-list")
    Call<PendingJobResponse> PendingJobList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("service_provider_id") String service_provider_id,@Query("page") int page);
    @FormUrlEncoded
    @POST("vehicle-driver")
    Call<AdddriverResponnse>AssignDrivertoTruck(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Field("driver_id") String driver_id, @Field("vehicle_id") String vehicle_id);
    @GET("service-provider/available-trucks")
    Call<ActiveTrucklistResponse> ActiveTruckandDriverList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("expected_start_date") String expected_start_date, @Query("expected_end_date") String expected_end_date, @Query("page") int page);

    @FormUrlEncoded
    @POST("job/block-vehicle")
    Call<BlockTruckandDriverResponse> BlockTruckandDriverResponse(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Field("job_id") String job_id, @Field("provider_vehicle_id") String provider_vehicle_id, @Field("job_driver_id") String job_driver_id, @Field("expected_start_date") String expected_start_date, @Field("expected_end_date") String expected_end_date);

    @FormUrlEncoded
    @POST("job/block-vehicle")
    Call<Meta> CreateJob(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Field("job_id") String job_id);
}
