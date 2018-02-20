package ua.kpi.ecampus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents teacher's Rating entity for voting.
 */
public class Rating implements Parcelable {

    private float ratingStar;
    private String criterion;

    public Rating(float ratingStar, String criterion) {
        this.ratingStar = ratingStar;
        this.criterion = criterion;
    }

    private Rating(Parcel in) {
        ratingStar = in.readFloat();
        criterion = in.readString();
    }

    public static final Parcelable.Creator<Rating> CREATOR
            = new Parcelable.Creator<Rating>() {
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(float ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getCriterion() {
        return criterion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(ratingStar);
        dest.writeString(criterion);
    }
}
