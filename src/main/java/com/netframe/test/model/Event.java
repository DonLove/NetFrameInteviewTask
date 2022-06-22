package com.netframe.test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author liubov
 */
@Entity
@Table(
    uniqueConstraints=
        @UniqueConstraint(columnNames={"team1", "team2"})
)
@JsonIgnoreProperties(value = "id")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = false)
    private String team1;
    
    @Column(nullable = false)
    private String team2;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private Score score;

    public Event() {
    }

    public Event(String team1, String team2) {
        this.team1 = team1;
        this.team2 = team2;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
        score.setEvent(this);
    }
    
    @PrePersist
    public void prePersist() {
        if(this.score == null)  //We set default value in case if the value is not set yet.
            setScore(new Score(0,0));
        if(this.team1 == null) 
            this.team1 = "Undef";
        if(this.team2 == null) 
            this.team2 = "Undef";
        
    }

    @Override
    public String toString() {
        return "GameEvent{" + "id=" + id + ", " + team1 + " vs " + team2 + ", score=" + score + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.team1);
        hash = 53 * hash + Objects.hashCode(this.team2);
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
        final Event other = (Event) obj;
        if (!Objects.equals(this.team1, other.team1)) {
            return false;
        }
        if (!Objects.equals(this.team2, other.team2)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
    
    
}
