package ua.kpi.ecampus.model.dao;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.User;

import static ua.kpi.ecampus.util.BulletinPredicates.filterBulletins;
import static ua.kpi.ecampus.util.BulletinPredicates.getIdsCollection;
import static ua.kpi.ecampus.util.BulletinPredicates.isMatchesProfile;
import static ua.kpi.ecampus.util.BulletinPredicates.isMatchesSubdivision;

/**
 * Implementation of IDataAccessObject for the Bulletin data model.
 */
public class BulletinDao implements IDataAccessObject<Bulletin> {
    /**
     * All unique bulletins available for current user
     */
    private Set<Bulletin> mAll = new LinkedHashSet<>();
    /**
     * Bulletins filtered by user profile
     */
    private Set<Bulletin> mByProfile = new LinkedHashSet<>();
    /**
     * Bulletins filtered by user subdivision
     */
    private Set<Bulletin> mBySubdivision = new LinkedHashSet<>();

    @Override
    public Collection<Bulletin> getData() {
        return mAll;
    }

    @Override
    public void setData(Collection<Bulletin> data) {
        if (data.isEmpty()) return;

        for (Bulletin b : data) {
            trimTime(b);
            mAll.add(b);
        }

        List<Item> userProfile = User.getInstance().position;
        List<Item> userSubdivision = User.getInstance().subdivision;

        if (userProfile != null && userSubdivision != null) {
            List<Integer> ids = getIdsCollection(userProfile);
            mByProfile.addAll(filterBulletins(data, isMatchesProfile(ids)));
            ids = getIdsCollection(userSubdivision);
            mBySubdivision.addAll(filterBulletins(data, isMatchesSubdivision(ids)));
        }
    }

    @Override
    public void update(Bulletin object) {
    }

    @Override
    public void delete(Bulletin object) {
    }

    /**
     * Trim time value in datetime strings in object
     * @param bulletin with datetime strings
     */
    private void trimTime(Bulletin bulletin) {
        bulletin.setDateStart(bulletin.getDateStart().split(" ")[0]);
        bulletin.setDateStop(bulletin.getDateStop().split(" ")[0]);
        bulletin.setDateCreate(bulletin.getDateCreate().split(" ")[0]);
    }

    /**
     * Get Bulletins filtered by user's profile.
     *
     * @return list of bulletins.
     */
    public Collection<Bulletin> getFilteredByProfile() {
        return mByProfile;
    }

    /**
     * Get Bulletins filtered by user's subdivision.
     *
     * @return list of bulletins.
     */
    public Collection<Bulletin> getFilteredBySubdiv() {
        return mBySubdivision;
    }
}
