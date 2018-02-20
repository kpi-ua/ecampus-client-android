package ua.kpi.ecampus.model.dao;

import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.util.DateUtil;

import static ua.kpi.ecampus.util.BulletinPredicates.filterBulletins;
import static ua.kpi.ecampus.util.BulletinPredicates.getIdsCollection;
import static ua.kpi.ecampus.util.BulletinPredicates.isDeleted;
import static ua.kpi.ecampus.util.BulletinPredicates.isMatchesProfile;
import static ua.kpi.ecampus.util.BulletinPredicates.isMatchesSubdivision;
import static ua.kpi.ecampus.util.BulletinPredicates.isNotExpired;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ua.kpi.ecampus.util.BulletinPredicates;

public class BulletinModeratorDao implements IDataAccessObject<Bulletin> {

    /**
     * All unique bulletins where user is creator
     */
    private Set<Bulletin> mAll = new LinkedHashSet<>();
    /**
     * Bulletins that are not expired
     */
    private Set<Bulletin> mNotExpired = new LinkedHashSet<>();
    /**
     * Bulletins filtered by user profile
     */
    private Set<Bulletin> mByProfile = new LinkedHashSet<>();
    /**
     * Bulletins filtered by user subdivision
     */
    private Set<Bulletin> mBySubdiv = new LinkedHashSet<>();
    /**
     * Bulletins that are deleted
     */
    private Set<Bulletin> mDeleted = new LinkedHashSet<>();

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
            mBySubdiv.addAll(filterBulletins(data, isMatchesSubdivision(ids)));
        }
        mNotExpired.addAll(filterBulletins(data, isNotExpired(DateUtil
                .getCurrentDate())));
        mDeleted.addAll(filterBulletins(data, isDeleted()));
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

    public Collection<Bulletin> getFilteredByProfile() {
        return mByProfile;
    }

    public Collection<Bulletin> getFilteredBySubdiv() {
        return mBySubdiv;
    }

    public Collection<Bulletin> getNotExpired() {
        return mNotExpired;
    }

    public Collection<Bulletin> getDeleted() {
        return mDeleted;
    }
}
