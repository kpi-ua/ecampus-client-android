package ua.kpi.ecampus.util;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.Collection;
import java.util.List;

import ua.kpi.ecampus.model.pojo.VoteTeacher;

public class TermPredicates {

    public static Predicate<VoteTeacher> isMatchesId(Integer id) {
        return p -> p.getVoteId().equals(id);
    }

    public static List<VoteTeacher> filter(Collection<VoteTeacher> bulletins,
                                           Predicate<VoteTeacher>
                                                   predicate) {
        return Stream.of(bulletins)
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
