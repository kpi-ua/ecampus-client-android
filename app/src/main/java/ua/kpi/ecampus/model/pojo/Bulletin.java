package ua.kpi.ecampus.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ua.kpi.ecampus.model.Recipient;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Bulletin entity.
 */
public class Bulletin implements Parcelable {

    @SerializedName("id") @Expose private String id;
    @SerializedName("subject") @Expose private String subject;
    @SerializedName("text") @Expose private String text;
    @SerializedName("creatorId") @Expose private String creatorId;
    @SerializedName("creatorName") @Expose private String creatorName;
    @SerializedName("profileId") @Expose private int profileId;
    @SerializedName("profileName") @Expose private String profileName;
    @SerializedName("subdivisionId") @Expose private int subdivisionId;
    @SerializedName("subdivisionName") @Expose private String subdivisionName;
    @SerializedName("studyGroupId") @Expose private String studyGroupId;
    @SerializedName("studyGroupName") @Expose private String studyGroupName;
    @SerializedName("dateCreate") @Expose private String dateCreate;
    @SerializedName("dateStart") @Expose private String dateStart;
    @SerializedName("dateStop") @Expose private String dateStop;
    @SerializedName("actuality") @Expose private boolean actuality;
    @SerializedName("recipient")
    @Expose private List<Recipient> recipientList = new ArrayList<>();

    public Bulletin(String userId, String subject, String text, String
            dateCreate, String dateStart, String dateStop, boolean actuality,
                    List<Recipient> recipients) {
        this.creatorId = userId;
        this.subject = subject;
        this.text = text;
        this.dateCreate = dateCreate;
        this.dateStart = dateStart;
        this.dateStop = dateStop;
        this.actuality = actuality;
        this.recipientList = recipients;
    }

    public Bulletin(String id, String theme, String author, String dateCreate) {
        this.id = id;
        this.subject = theme;
        this.creatorName = author;
        this.dateCreate = dateCreate;
    }

    private Bulletin(Parcel in) {
        id = in.readString();
        subject = in.readString();
        text = in.readString();
        creatorName = in.readString();
        subdivisionName = in.readString();
        dateCreate = in.readString();
        dateStart = in.readString();
        dateStop = in.readString();
        actuality = in.readByte() != 0;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName The creatorName
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return The profileName
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * @param profileName The profileName
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * @return The subdivisionName
     */
    public String getSubdivisionName() {
        return subdivisionName;
    }

    /**
     * @param subdivisionName The subdivisionName
     */
    public void setSubdivisionName(String subdivisionName) {
        this.subdivisionName = subdivisionName;
    }

    /**
     * @return The studyGroupName
     */
    public String getstudyGroupName() {
        return studyGroupName;
    }

    /**
     * @param studyGroupName The studyGroupName
     */
    public void setstudyGroupName(String studyGroupName) {
        this.studyGroupName = studyGroupName;
    }

    /**
     * @return The dateCreate
     */
    public String getDateCreate() {
        return dateCreate;
    }

    /**
     * @param dateCreate The dateCreate
     */
    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    /**
     * @return The dateStart
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     * @param dateStart The dateStart
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * @return The dateStop
     */
    public String getDateStop() {
        return dateStop;
    }

    /**
     * @param dateStop The dateStop
     */
    public void setDateStop(String dateStop) {
        this.dateStop = dateStop;
    }

    /**
     * @return The actuality
     */
    public boolean getActuality() {
        return actuality;
    }

    /**
     * @param actuality The actuality
     */
    public void setActuality(boolean actuality) {
        this.actuality = actuality;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(subject);
        dest.writeString(text);
        dest.writeString(creatorName);
        dest.writeString(subdivisionName);
        dest.writeString(dateCreate);
        dest.writeString(dateStart);
        dest.writeString(dateStop);
        dest.writeByte((byte) (actuality ? 1 : 0));
    }

    /**
     * Must have a non-null static field called CREATOR of a type that
     * implements the Parcelable.Creator interface
     */
    public static final Parcelable.Creator<Bulletin> CREATOR
            = new Parcelable.Creator<Bulletin>() {
        public Bulletin createFromParcel(Parcel in) {
            return new Bulletin(in);
        }

        public Bulletin[] newArray(int size) {
            return new Bulletin[size];
        }
    };

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getSubdivisionId() {
        return subdivisionId;
    }

    public void setSubdivisionId(int subdivisionId) {
        this.subdivisionId = subdivisionId;
    }

    public String getstudyGroupId() {
        return studyGroupId;
    }

    public void setstudyGroupId(String studyGroupId) {
        this.studyGroupId = studyGroupId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public List<Recipient> getRecipientList() {
        return recipientList;
    }

    public void setRecipientList(List<Recipient> recipientList) {
        this.recipientList = recipientList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bulletin bulletin = (Bulletin) o;

        return !(id != null ? !id.equals(bulletin.id) : bulletin.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
