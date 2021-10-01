package com.softserveinc.app.repositories;

import com.softserveinc.app.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {

    @Query(nativeQuery = true,
            value = " SELECT * FROM APPLICATIONS")
    List<ApplicationEntity> getAllApps();

    @Query(nativeQuery = true,
            value = " INSERT INTO applications(name, version, content_rate) VALUES (:name, :version, :content_rate)")
    @Modifying
    void saveApplication(@Param("name") String name, @Param("version") String version, @Param("content_rate") Integer content_rate);

    @Query(nativeQuery = true,
            value = " SELECT A.* FROM APPLICATIONS A " +
                    " WHERE A.ID IN (:appIds) ")
    List<ApplicationEntity> findAllByIds(@Param("appIds") Set<Integer> appIds);

    @Query(nativeQuery = true,
            value = " SELECT COUNT(CONTENT_RATE) " +
                    "    FROM APPLICATIONS " +
                    "    WHERE CONTENT_RATE IN :contentRates ")
    Integer getCountOfAppsByContentRates(@Param("contentRates") Set<Integer> contentRates);
}
