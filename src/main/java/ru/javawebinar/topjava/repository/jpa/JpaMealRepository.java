package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class JpaMealRepository implements MealRepository {
    @PersistenceContext
    EntityManager em;
    
    
    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User reference = em.getReference(User.class, userId);
        meal.setUser(reference);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        }
        if (get(meal.id(), userId) != null) {
            return em.merge(meal);
        }
        return null;
    }
    
    @Override
    @Transactional
    public boolean delete(int id, int userId) {
    return em.createNamedQuery("Delete.meal")
             .setParameter("id", id)
             .setParameter("userId",userId)
             .executeUpdate() !=0;
    }
    
    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (meal!=null &&  meal.getUser().getId() != userId) {
            return null;
        }
        return meal;
    }
    
    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery("Get.All", Meal.class)
                .setParameter("user_id", userId).getResultList();
    }
    
    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery("Between", Meal.class)
                .setParameter("startTime", startDateTime)
                .setParameter("endTime", endDateTime)
                .setParameter("userId", userId)
                .getResultList();
    }
}