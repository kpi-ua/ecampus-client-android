package ua.kpi.ecampus.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.kpi.ecampus.model.pojo.VoteSet;

public class VoteDao implements IDataAccessObject<VoteSet> {

    private List<VoteSet> mVoting = new ArrayList<>();

    @Override
    public Collection<VoteSet> getData() {
        return mVoting;
    }

    @Override
    public void setData(Collection<VoteSet> data) {
        mVoting.addAll(data);
    }

    @Override
    public void update(VoteSet object) {

    }

    @Override
    public void delete(VoteSet object) {

    }
}
