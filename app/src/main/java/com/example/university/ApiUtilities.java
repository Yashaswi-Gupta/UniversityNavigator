package com.example.university;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiUtilities {
    private static Retrofit retrofit = null;
    public static UniversityApi getUniversityApi(){
        if(retrofit == null){
                retrofit = new Retrofit.Builder()
                    .baseUrl(UniversityApi.BASE_URL) // Replace with your API base URL
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(UniversityApi.class);
    }
}
