package com.example.university;
import com.google.gson.annotations.SerializedName;
import java.util.*;

public class UniversityData {
    private Vector<String> domains;
    private String name;
    @SerializedName("state-province")
    private String state_province;
    private Vector<String> web_pages;
    private String country;
    private String alpha_two_code;

    public UniversityData(Vector<String> domains, String name, String state_province, Vector<String> web_pages, String country, String alpha_two_code) {
        this.name = name;
        this.state_province = state_province;
        this.country = country;
        this.alpha_two_code = alpha_two_code;

        this.domains = domains;
//        this.domains.addAll(domains);

        // Initialize and populate the web_pages vector
        this.web_pages = web_pages;
//        this.web_pages.addAll(web_pages);
    }
    public Vector<String> getDomains() {
        return domains;
    }
    public String getName() {
        return name;
    }
    public String getState_province() {
        return state_province;
    }

    public Vector<String> getWeb_pages() {
        return web_pages;
    }
    public String getCountry() {
        return country;
    }
    public String getAlpha_two_code() {
        return alpha_two_code;
    }
}
