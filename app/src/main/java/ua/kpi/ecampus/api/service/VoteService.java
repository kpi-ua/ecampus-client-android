package ua.kpi.ecampus.api.service;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import ua.kpi.ecampus.model.Recipient;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.VoteTerm;

public interface VoteService {

    @GET("/vote/term/finished")
    Observable<List<VoteTerm>> getVoteTermFinish(
            @Header("Authorization") String authorization);

}
