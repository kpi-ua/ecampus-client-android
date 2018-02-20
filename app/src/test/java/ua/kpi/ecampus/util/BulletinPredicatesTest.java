package ua.kpi.ecampus.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.kpi.ecampus.model.pojo.Bulletin;

import static org.junit.Assert.assertEquals;
import static ua.kpi.ecampus.util.BulletinPredicates.filterBulletins;
import static ua.kpi.ecampus.util.BulletinPredicates.isDeleted;
import static ua.kpi.ecampus.util.BulletinPredicates.isMatchesProfile;
import static ua.kpi.ecampus.util.BulletinPredicates.isMatchesQuerySubject;
import static ua.kpi.ecampus.util.BulletinPredicates.isMatchesSubdivision;
import static ua.kpi.ecampus.util.BulletinPredicates.isNotExpired;

public class BulletinPredicatesTest {

    private Collection<Bulletin> mData = new ArrayList<>();

    @Before
    public void setUp() {
        mData = bulletinList();
    }

    @Test
    public void matchProfile() {
        int actual = filterBulletins(mData, isMatchesProfile(stubIds())).size();
        assertEquals(2, actual);
    }

    @Test
    public void matchSubdivision() {
        int actual = filterBulletins(mData, isMatchesSubdivision(stubIds()))
                .size();
        assertEquals(2, actual);
    }

    @Test
    public void deleted() {
        int actual = filterBulletins(mData, isDeleted()).size();
        assertEquals(1, actual);
    }

    @Test
    public void matchQueryBySubject() {
        int actual = filterBulletins(mData, isMatchesQuerySubject("a")).size();
        assertEquals(1, actual);

        actual = filterBulletins(mData, isMatchesQuerySubject("dd")).size();
        assertEquals(0, actual);
    }

    @Test
    public void notExpired() {
        int actual = filterBulletins(mData, isNotExpired("2016-05-19")).size();
        assertEquals(3, actual);

        actual = filterBulletins(mData, isNotExpired("2017-05-25")).size();
        assertEquals(0, actual);
    }

    private List<Integer> stubIds() {
        return new ArrayList<Integer>() {{
            add(0);
            add(1);
        }};
    }

    private List<Bulletin> bulletinList() {
        List<Bulletin> list = new ArrayList<>();
        Bulletin b = new Bulletin("1", "a", "a", "2016", "2016-05-19",
                "2017-05-19", true, null);
        b.setProfileId(1);
        b.setSubdivisionId(1);
        list.add(b);
        b = new Bulletin("2", "b", "b", "2016", "2016-05-19",
                "2017-05-19", true, null);
        b.setProfileId(0);
        b.setSubdivisionId(0);
        list.add(b);
        b = new Bulletin("3", "c", "c", "2016", "2016-05-01",
                "2017-05-02", false, null);
        b.setProfileId(4);
        b.setSubdivisionId(4);
        list.add(b);
        return list;
    }
}
