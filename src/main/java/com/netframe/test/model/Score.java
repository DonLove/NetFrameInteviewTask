package com.netframe.test.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import org.hibernate.annotations.ColumnDefault;

/**
 *
 * @author liubov
 */

@Entity
@JsonIgnoreProperties(value = "id")
public class Score {

    @Id
    private Long id;
    
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer team1Score;
    
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer team2Score;
    
    @OneToOne
    @MapsId
    @JsonBackReference
    private Event event;

    public Score() {
    }

    
    
    public Score(Integer team1Score, Integer team2Score) {
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(Integer team1Score) {
        this.team1Score = team1Score;
    }

    public Integer getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(Integer team2Score) {
        this.team2Score = team2Score;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
    @PrePersist
    public void prePersist() {
        if(this.team1Score == null) //We set default value in case if the value is not set yet.
            this.team1Score = 0;
        if(this.team2Score == null) //We set default value in case if the value is not set yet.
            this.team2Score = 0;
    }

    @Override
    public String toString() {
        return team1Score + " : " + team2Score;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Score other = (Score) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
