package ua.kpi.ecampus.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoteTerm {

    @SerializedName("voteId") @Expose private Integer voteId;
    @SerializedName("voteName") @Expose private String voteName;
    @SerializedName("dateStart") @Expose private String dateStart;
    @SerializedName("dateStop") @Expose private String dateStop;

    public VoteTerm(Integer id, String name, String dateStart, String dateStop) {
        voteId = id;
        voteName = name;
        this.dateStart = dateStart;
        this.dateStop = dateStop;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateStop() {
        return dateStop;
    }

    public void setDateStop(String dateStop) {
        this.dateStop = dateStop;
    }
}
