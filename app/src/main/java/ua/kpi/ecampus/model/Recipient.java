package ua.kpi.ecampus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represent Recipient of bulletin entity.
 */
public class Recipient {

    @SerializedName("subdivisionId") @Expose private String subdivisionId;
    @SerializedName("subdivisionName") @Expose private String subdivisionName;
    @SerializedName("profileId") @Expose private String profileId;
    @SerializedName("profileName") @Expose private String profileName;
    @SerializedName("studyGroupId") @Expose private String studyGroupId;
    @SerializedName("studyGroupName") @Expose private String studyGroupName;

    public Recipient() {
    }

    public Recipient(String subdivId, String profileId, String groupId) {
        this.subdivisionId = subdivId;
        this.profileId = profileId;
        this.studyGroupId = groupId;
    }

    public Recipient(String subdivId, String subdivName, String profileId,
                     String profileName, String groupId, String groupName) {
        this.subdivisionId = subdivId;
        this.subdivisionName = subdivName;
        this.profileId = profileId;
        this.profileName = profileName;
        this.studyGroupId = groupId;
        this.studyGroupName = groupName;
    }

    public String getSubdivisionName() {
        return subdivisionName;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getStudyGroupName() {
        return studyGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipient recipient = (Recipient) o;

        if (subdivisionId != null ? !subdivisionId.equals(recipient.subdivisionId) : recipient.subdivisionId != null)
            return false;
        if (subdivisionName != null ? !subdivisionName.equals(recipient.subdivisionName) : recipient.subdivisionName != null)
            return false;
        if (profileId != null ? !profileId.equals(recipient.profileId) : recipient.profileId != null)
            return false;
        if (profileName != null ? !profileName.equals(recipient.profileName) : recipient.profileName != null)
            return false;
        if (studyGroupId != null ? !studyGroupId.equals(recipient.studyGroupId) : recipient.studyGroupId != null)
            return false;
        return !(studyGroupName != null ? !studyGroupName.equals(recipient.studyGroupName) : recipient.studyGroupName != null);
        //return true;//test
    }

    @Override
    public int hashCode() {
        int result = subdivisionId != null ? subdivisionId.hashCode() : 0;
        result = 31 * result + (subdivisionName != null ? subdivisionName.hashCode() : 0);
        result = 31 * result + (profileId != null ? profileId.hashCode() : 0);
        result = 31 * result + (profileName != null ? profileName.hashCode() : 0);
        result = 31 * result + (studyGroupId != null ? studyGroupId.hashCode() : 0);
        result = 31 * result + (studyGroupName != null ? studyGroupName.hashCode() : 0);
        return result;
    }
}
