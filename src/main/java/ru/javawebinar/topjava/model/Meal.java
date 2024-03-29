package ru.javawebinar.topjava.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity()
@NamedQueries({
        
        @NamedQuery(name = "Delete.meal", query = "DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = "Get.All", query = "SELECT m FROM Meal m WHERE m.user.id = :user_id ORDER BY   m.dateTime desc "),
        @NamedQuery(name = "Between", query = "SELECT m FROM Meal m WHERE m.dateTime>=:startTime and m.dateTime <:endTime" +
                " and m.user.id = :userId order by m.dateTime desc ")
})
@Table(name = "meal", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","date_time"})})
public class Meal extends AbstractBaseEntity {
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "calories", nullable = false)
    private int calories;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    public Meal() {
    }
    
    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }
    
    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getCalories() {
        return calories;
    }
    
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }
    
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setCalories(int calories) {
        this.calories = calories;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
