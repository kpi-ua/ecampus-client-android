package ua.kpi.ecampus.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ua.kpi.ecampus.model.Rating;

public class VoteTeacher implements Parcelable {

    @SerializedName("voteId") @Expose private Integer voteId;
    @SerializedName("teacherId") @Expose private Integer teacherId;
    @SerializedName("teacherName") @Expose private String teacherName;
    @SerializedName("isVoted") @Expose private boolean isVoted;
    @SerializedName("avgResult") @Expose private String avgResult;
    @SerializedName("criteria") @Expose private List<Rating> criteria = new ArrayList<>();

    public VoteTeacher(Integer voteId, Integer teacherId, String name,
                       boolean isVoted, String result) {
        this.voteId = voteId;
        this.teacherId = teacherId;
        this.teacherName = name;
        this.isVoted = isVoted;
        this.avgResult = result;
    }

    private VoteTeacher(Parcel in) {
        voteId = in.readInt();
        teacherId = in.readInt();
        teacherName = in.readString();
        isVoted = in.readByte() != 0;
        avgResult = in.readString();
        in.readTypedList(criteria, Rating.CREATOR);
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setIsVoted(boolean isVoted) {
        this.isVoted = isVoted;
    }

    public List<Rating> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Rating> criteria) {
        this.criteria = criteria;
    }

    public String getAvgResult() {
        return avgResult;
    }

    public void setAvgResult(String avgResult) {
        this.avgResult = avgResult;
    }

    public static final Parcelable.Creator<VoteTeacher> CREATOR
            = new Parcelable.Creator<VoteTeacher>() {
        public VoteTeacher createFromParcel(Parcel in) {
            return new VoteTeacher(in);
        }

        public VoteTeacher[] newArray(int size) {
            return new VoteTeacher[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(voteId);
        dest.writeInt(teacherId);
        dest.writeString(teacherName);
        dest.writeByte((byte) (isVoted ? 1 : 0));
        dest.writeString(avgResult);
        dest.writeTypedList(criteria);
    }
}
