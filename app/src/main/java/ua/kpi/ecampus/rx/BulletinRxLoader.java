package ua.kpi.ecampus.rx;

import android.util.Log;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.kpi.ecampus.Config;
import ua.kpi.ecampus.api.service.BulletinService;
import ua.kpi.ecampus.api.service.ServiceCreator;
import ua.kpi.ecampus.model.Recipient;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.ui.presenter.SaveBulletinPresenter;

/**
 * Created by Administrator on 13.04.2016.
 */
public class BulletinRxLoader {

    private SaveBulletinPresenter mPresenter;

    public BulletinRxLoader(SaveBulletinPresenter presenter) {
        mPresenter = presenter;
    }

    public void addBulletin(Bulletin bulletin) {
        BulletinService service = ServiceCreator.createService
                (BulletinService.class);
        Observable<String> observable = service.createBulletin("bearer " +
                User.getInstance().token, bulletin);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseMsg -> mPresenter.onFinishRequest(200,
                                responseMsg),
                        e -> {
                            Log.e(Config.LOG, e.getMessage());
                            if (e instanceof HttpException)
                                mPresenter.onFinishRequest(((HttpException)
                                        e).code(), e.getMessage());
                            else
                                mPresenter.onFinishRequest(0, e.getMessage());
                        }
                );
    }

    public void editBulletin(Bulletin bulletin) {
        BulletinService service = ServiceCreator.createService
                (BulletinService.class);
        Observable<String> observable = service.updateBulletin("bearer " +
                User.getInstance().token, bulletin.getId(), bulletin);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseMsg -> mPresenter.onFinishRequest(200,
                                responseMsg),
                        e -> {
                            //Log.e(Config.LOG, e.getMessage());
                            if (e instanceof HttpException)
                                mPresenter.onFinishRequest(((HttpException)
                                        e).code(), e.getMessage());
                            else
                                mPresenter.onFinishRequest(0, e.getMessage());
                        }
                );
    }

    public void deleteBulletin(String bulletinId) {
        BulletinService service = ServiceCreator.createService
                (BulletinService.class);
        Observable<String> observable = service.deleteBulletin("bearer " +
                User.getInstance().token, bulletinId);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseMsg -> mPresenter.onFinishRequest(200,
                                responseMsg),
                        e -> {
                            //Log.e(Config.LOG, e.getMessage());
                            if (e instanceof HttpException)
                                mPresenter.onFinishRequest(((HttpException)
                                        e).code(), e.getMessage());
                            else
                                mPresenter.onFinishRequest(0, e.getMessage());
                        }
                );
    }
/**
    public void loadDescSubdivisions(String subdivisionId) {
        BulletinService service = ServiceCreator.createService
                (BulletinService.class);
        Observable<List<Item>> observable = service.getDescendantSubdivisions
                (subdivisionId);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> mPresenter.setDescSubdivisions(list),
                        e -> {
                            //Log.e(Config.LOG, e.getMessage());
                        });
    }

    public void loadProfiles() {
        BulletinService service = ServiceCreator.createService
                (BulletinService.class);
        Observable<List<Item>> observable = service.getRoles();
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> mPresenter.setProfiles(list),
                        e -> {
                            //Log.e(Config.LOG, e.getMessage());
                        });
    }

    public void loadGroupsOf(String subdivisionId) {
        BulletinService service = ServiceCreator.createService
                (BulletinService.class);
        Observable<List<Item>> observable = service.getGroupsIn
                (subdivisionId);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> mPresenter.setGroups(list),
                        e -> {
                            //Log.e(Config.LOG, e.getMessage());
                        });
    }
**/
    public void loadRecipients(String bulletinId) {
        BulletinService service = ServiceCreator.createService
                (BulletinService.class);
        Observable<List<Recipient>> observable = service.getRecipientsBy
                ("bearer " + User.getInstance().token, bulletinId);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> mPresenter.setRecipients(list),
                        e -> {
                            //Log.e(Config.LOG, e.getMessage());
                        });
    }
}
