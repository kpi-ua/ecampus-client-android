package ua.kpi.ecampus.util.pagination;

import rx.Observable;

public interface PagingListener<T> {
    Observable<T> onNextPage(int lastId);
}
