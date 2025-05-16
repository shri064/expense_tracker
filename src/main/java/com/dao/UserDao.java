package com.dao;

import javax.servlet.http.HttpSession;

import com.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class UserDao {
    private EntityManagerFactory emf;

    public UserDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public boolean saveUser(User user) {
        EntityManager em = null;
        EntityTransaction et = null;
        boolean f = false;

        try {
            em = emf.createEntityManager();
            et = em.getTransaction(); 
            et.begin();
            em.persist(user);
            et.commit();
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close(); 
            }
        }

        return f;
    }
    
    public User login(String email, String password) {
    	User u = null;
    	 EntityManager em = emf.createEntityManager();
    	 try {
    	        Query q = em.createQuery("from User where email = :em and password = :ps");
    	        q.setParameter("em", email);
    	        q.setParameter("ps", password);

    	        u = (User) q.getSingleResult();
    	    } catch (Exception e) {
    	        e.printStackTrace(); 
    	    } finally {
    	        em.close();
    	    }
    	 
    	return u;
    }
}
