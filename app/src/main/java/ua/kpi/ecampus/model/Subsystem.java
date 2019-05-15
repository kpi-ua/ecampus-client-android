package ua.kpi.ecampus.model;

/**
 * Represents Subsystem entity.
 */
public class Subsystem {

    private String mName;
    private int mIconId;

    public Subsystem(String name, int iconId) {
        mName = name;
        mIconId = iconId;
    }

    public String getName() {
        return mName;
    }

    public int getIconId() {
        return mIconId;
    }
}
