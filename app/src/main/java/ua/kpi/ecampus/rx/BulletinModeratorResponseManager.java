package ua.kpi.ecampus.rx;

import ua.kpi.ecampus.api.service.BulletinService;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.User;

import java.util.List;

import rx.Observable;

/**
 * This class responsible for producing responses for the
 * BulletinBoardModerator.
 */
public class BulletinModeratorResponseManager extends BulletinResponseManager {

    @Override
    protected Observable<List<Bulletin>> getRequest(BulletinService service,
                                                    int limit, int lastId) {
        return service.getModeratorBulletins("bearer " + User.getInstance().token,
                                                limit, lastId);
    }
}
