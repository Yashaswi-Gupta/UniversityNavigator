package com.example.university;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface UniversityApi {
    static final String BASE_URL = "http://universities.hipolabs.com/";
    @GET("search")
    Call<List<UniversityData>> getUniversityData();
}
