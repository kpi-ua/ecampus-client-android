package ua.kpi.ecampus.ui.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ua.kpi.ecampus.model.Rating;
import ua.kpi.ecampus.model.dao.IDataAccessObject;
import ua.kpi.ecampus.model.dao.VotingDao;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.VoteSet;
import ua.kpi.ecampus.model.pojo.VoteTeacher;
import ua.kpi.ecampus.model.pojo.VoteTerm;
import ua.kpi.ecampus.ui.Navigator;
import ua.kpi.ecampus.util.DateUtil;

/**
 * Created by Administrator on 01.06.2016.
 */
public class VotingStudentPresenter extends BasePresenter {

    private IView mView;
    private IDataAccessObject<VoteSet> mDataAccess;
    private Navigator mNavigator;

    @Inject
    public VotingStudentPresenter(Navigator navigator) {
        mDataAccess = new VotingDao();
        mNavigator = navigator;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public void loadVoting() {
        // load
        makeStubData();
        setResult();
    }

    private void makeStubData() {
        List<VoteSet> set = new ArrayList<>();
        VoteSet vs = new VoteSet();

        List<VoteTerm> terms = new ArrayList<>();
        terms.add(new VoteTerm(1, "2015-2016", "2015-09-01", "2019-09-01"));
        terms.add(new VoteTerm(2, "2014-2015", "2014-09-01", "2018-09-01"));

        List<Rating> criteria = new ArrayList<>();
        criteria.add(new Rating(3F, "1"));
        criteria.add(new Rating(4F, "2"));
        criteria.add(new Rating(3F, "3"));
        criteria.add(new Rating(4F, "4"));
        criteria.add(new Rating(3F, "5"));
        criteria.add(new Rating(4F, "6"));

        List<VoteTeacher> teachers = new ArrayList<>();
        VoteTeacher t = new VoteTeacher(1, 1, "Крилов Євген " +
                "Володимирович", false, "4.0");
        t.setCriteria(criteria);
        teachers.add(t);
        t = new VoteTeacher(1, 2, "Лісовиченко Олег Іванович", false, "4.3");
        t.setCriteria(criteria);
        teachers.add(t);
        t = new VoteTeacher(1, 3, "Мелкумян Катерина Юріївна", false, "4.3");
        t.setCriteria(criteria);
        teachers.add(t);
        t = new VoteTeacher(2, 4, "Олійник Волдимир Валентинович", false, "4" +
                ".3");
        t.setCriteria(criteria);
        teachers.add(t);

        vs.setTeachers(teachers);
        vs.setTerms(terms);

        mDataAccess.setData(new ArrayList<VoteSet>() {{
            add(vs);
        }});

    }

    public void setResult() {
        List<VoteTerm> terms = getVoting().getTerms();

        List<Item> termNames = new ArrayList<>();
        for (VoteTerm t : terms) {
            Item i = new Item(t.getVoteId(), t.getVoteName());
            termNames.add(i);
        }
        mView.setTermsSpinner(termNames);
    }

    public void setSpecificAdapter() {
        VoteSet voting = getVoting();
        VoteTerm latestTerm = voting.getTerms().get(0);
        if (isVotePeriod(latestTerm.getDateStop())) {
            // voting adapter
            mView.setVoteInProgressAdapter(voting.getTeachers());
        } else {
            // resulting adapter
            mView.setVoteEndedAdapter(voting.getTeachers());
        }
    }

    public VoteSet getVoting() {
        return mDataAccess.getData().iterator().next();
    }

    public void onItemClick(Object item) {
        mNavigator.startRatingActivity((VoteTeacher)item);
    }

    private boolean isVotePeriod(String endDate) {
        Date currentDate = DateUtil.convert(DateUtil.getCurrentDate());
        return !currentDate.after(DateUtil.convert(endDate));
    }

    public interface IView {
        void setViewComponent();

        void setTermsSpinner(List<Item> terms);

        void setVoteInProgressAdapter(List<VoteTeacher> teachers);

        void setVoteEndedAdapter(List<VoteTeacher> teachers);
    }
}
