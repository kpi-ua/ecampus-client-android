package ua.kpi.ecampus.util;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;

/**
 * Created by Administrator on 29.03.2016.
 */
public class BulletinPredicates {

    public static Predicate<Bulletin> isNotExpired(String date) {
        Date currentDate = DateUtil.convert(date);
        return p -> (currentDate != null && !currentDate.before(DateUtil.convert
                (p.getDateStart()))) &&
                !currentDate.after(DateUtil.convert(p.getDateStop()));
    }

    public static Predicate<Bulletin> isMatchesProfile(List<Integer>
                                                               profileIds) {
        return p -> Stream.of(profileIds).anyMatch(i -> i.equals(p
                .getProfileId()));
    }

    public static Predicate<Bulletin> isMatchesSubdivision(List<Integer>
                                                                   subdiv) {
        return p -> Stream.of(subdiv).anyMatch(i -> i.equals(p
                .getSubdivisionId()));
    }

    public static Predicate<Bulletin> isDeleted() {
        return p -> (p.getActuality() == false);
    }

    public static Predicate<Bulletin> isMatchesQuerySubject(String query) {
        return p -> p.getSubject().toLowerCase().contains(query);
    }

    public static List<Integer> getIdsCollection(List<Item> items) {
        return Stream.of(items).map(Item::getId).collect
                (Collectors.toList());
    }

    public static List<Bulletin> filterBulletins(Collection<Bulletin> bulletins,
                                                 Predicate<Bulletin>
                                                         predicate) {
        return Stream.of(bulletins)
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
