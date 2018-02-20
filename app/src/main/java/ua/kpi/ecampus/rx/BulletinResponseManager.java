package ua.kpi.ecampus.rx;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.kpi.ecampus.api.service.BulletinService;
import ua.kpi.ecampus.api.service.ServiceCreator;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.User;

/**
 * This class responsible for producing responses for the BulletinBoard.
 */
public class BulletinResponseManager {

    /**
     * Create observable which implements service request.
     *
     * @param lastId ID of the last existing bulletin.
     * @param limit  limit number of bulletins.
     * @return observable.
     */
    public Observable<List<Bulletin>> getResponse(int lastId, int limit) {
        BulletinService service = ServiceCreator.createService
                (BulletinService.class);
        Observable<List<Bulletin>> observable = getRequest(service, limit,
                lastId);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bulletin>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //if (e != null)
                            //Log.e(Config.LOG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<Bulletin> bulletins) {
//                        Log.d(Config.LOG, bulletins.size() + " loaded " +
//                                "successful");
                    }
                });
        return observable;
    }

    /**
     * Returns service request.
     *
     * @param service
     * @param limit
     * @param lastId
     * @return service request
     */
    protected Observable<List<Bulletin>> getRequest(BulletinService service,
                                                    int limit, int lastId) {
        return service.getBulletins("bearer " + User.getInstance().token,
                limit, lastId);
    }
}
