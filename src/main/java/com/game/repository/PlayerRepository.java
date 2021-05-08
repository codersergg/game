package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>  {
    List<Player> findAll();
    List<Player> findAllById(Long id);



    @Query("SELECT p from Player p WHERE " +
            "(:name is not null and p.name LIKE CONCAT('%' , :name ,'%') or :name is null) " +
            "and (:title is not null and  p.title LIKE CONCAT('%', :title , '%') or :title is null) " +
            "and (:race is not null and p.race = :race or :race is null) " +
            "and (:profession is not null and p.profession = :profession or :profession is null) " +
            "and (:after is not null and p.birthday >= :after or :after is null) " +
            "and (:before is not null and p.birthday <= :before or :before is null) " +
            "and (:banned is not null and p.banned = :banned or :banned is null) " +
            "and (:minExperience is not null and p.experience >= :minExperience or :minExperience is null) " +
            "and (:maxExperience is not null and p.experience <= :maxExperience or :maxExperience is null) " +
            "and (:minLevel is not null and p.level >= :minLevel or :minLevel is null) " +
            "and (:maxLevel is not null and p.level <= :maxLevel or :maxLevel is null) " +
            "and (:untilNextLevel is not null and p.untilNextLevel = :untilNextLevel or :untilNextLevel is null)")
    Page<Player> findAllByParam(@Param("name") String name,
                                @Param("title") String title,
                                @Param("race") Race race,
                                @Param("profession") Profession profession,
                                @Param("after") Date after,
                                @Param("before") Date before,
                                @Param("banned") Boolean banned,
                                @Param("minExperience") Integer minExperience,
                                @Param("maxExperience")Integer maxExperience,
                                @Param("minLevel")Integer minLevel,
                                @Param("maxLevel")Integer maxLevel,
                                @Param("untilNextLevel")Integer untilNextLevel,
                                Pageable pageable
    );



    @Query("SELECT p from Player p WHERE " +
            "(:name is not null and p.name LIKE CONCAT('%' , :name ,'%') or :name is null) " +
            "and (:title is not null and  p.title LIKE CONCAT('%', :title , '%') or :title is null) " +
            "and (:race is not null and p.race = :race or :race is null) " +
            "and (:profession is not null and p.profession = :profession or :profession is null) " +
            "and (:after is not null and p.birthday >= :after or :after is null) " +
            "and (:before is not null and p.birthday <= :before or :before is null) " +
            "and (:banned is not null and p.banned = :banned or :banned is null) " +
            "and (:minExperience is not null and p.experience >= :minExperience or :minExperience is null) " +
            "and (:maxExperience is not null and p.experience <= :maxExperience or :maxExperience is null) " +
            "and (:minLevel is not null and p.level >= :minLevel or :minLevel is null) " +
            "and (:maxLevel is not null and p.level <= :maxLevel or :maxLevel is null) " +
            "and (:untilNextLevel is not null and p.untilNextLevel = :untilNextLevel or :untilNextLevel is null)")
    List<Player> countByParams(@Param("name") String name,
                               @Param("title") String title,
                               @Param("race") Race race,
                               @Param("profession") Profession profession,
                               @Param("after") Date after,
                               @Param("before") Date before,
                               @Param("banned") Boolean banned,
                               @Param("minExperience") Integer minExperience,
                               @Param("maxExperience")Integer maxExperience,
                               @Param("minLevel")Integer minLevel,
                               @Param("maxLevel")Integer maxLevel,
                               @Param("untilNextLevel")Integer untilNextLevel);


    @Override
    void deleteById(Long aLong);
}
