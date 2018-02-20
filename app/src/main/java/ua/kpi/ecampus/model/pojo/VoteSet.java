package ua.kpi.ecampus.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VoteSet {

    @SerializedName("terms")
    @Expose
    private List<VoteTerm> mTerms = new ArrayList<>();
    @SerializedName("teachers")
    @Expose
    private List<VoteTeacher> mTeachers = new ArrayList<>();

    public List<VoteTerm> getTerms() {
        return mTerms;
    }

    public void setTerms(List<VoteTerm> terms) {
        this.mTerms = terms;
    }

    public List<VoteTeacher> getTeachers() {
        return mTeachers;
    }

    public void setTeachers(List<VoteTeacher> teachers) {
        this.mTeachers = teachers;
    }
}
