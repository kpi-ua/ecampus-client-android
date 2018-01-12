package ua.kpi.ecampus.api.service;

import ua.kpi.ecampus.model.Recipient;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.Item;

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

public interface BulletinService {

    @GET("/board/all")
    Observable<List<Bulletin>> getBulletins(
            @Header("Authorization") String authorization,
            @Query("limit") int limit,
            @Query("lastLoadedBulletinId") int lastId);

    @GET("/board/moderator/all")
    Observable<List<Bulletin>> getModeratorBulletins(
            @Header("Authorization") String authorization,
            @Query("limit") int limit,
            @Query("lastLoadedBulletinId") int lastId);

    @POST("/board")
    Observable<String> createBulletin(
            @Header("Authorization") String authorization,
            @Body Bulletin bulletin);

    @PUT("/board/{bulletinId}")
    Observable<String> updateBulletin(
            @Header("Authorization") String authorization,
            @Path("bulletinId") String bulletinId,
            @Body Bulletin bulletin);

    @DELETE("/board/{bulletinId}")
    Observable<String> deleteBulletin(
            @Header("Authorization") String authorization,
            @Path("bulletinId") String bulletinId);

    @GET("/board/{bulletinId}/recipient")
    Observable<List<Recipient>> getRecipientsBy(
            @Header("Authorization") String authorization,
            @Path("bulletinId") String bulletinId);

    @GET("/subdivision/{subdivisionId}/children")
    Observable<List<Item>> getDescendantSubdivisions(
            @Path("subdivisionId") String subdivisionId);

    @GET("/roles")
    Observable<List<Item>> getRoles();

    @GET("/subdivision/{subdivisionId}/group")
    Observable<List<Item>> getGroupsIn(@Path("subdivisionId") String
                                               subdivisionId);
}
