package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.entity.Expense;
import com.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class ExpenseDao {
	private EntityManagerFactory emf;

	public ExpenseDao() {

	}

	public ExpenseDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	public boolean saveExpense(Expense ex) {
		EntityManager em = null;
		EntityTransaction et = null;
		boolean f = false;

		try {
			em = emf.createEntityManager();
			et = em.getTransaction();
			et.begin();
			em.persist(ex);
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

	public List<Expense> getAllExpensesByUser(User user) {
	    EntityManager em = emf.createEntityManager();
	    List<Expense> list = new ArrayList<>();
	    try {
	        
	        Query q = em.createQuery("from Expense where user = :us");
	        q.setParameter("us", user);
	        list = q.getResultList();
	        System.out.println("Expenses found: " + list.size());
	    } catch (NoResultException e) {
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }

	    return list;
	}

	public Expense getExpenseById(int id) {
		Expense ex = null;
		EntityManager em = emf.createEntityManager();
		
		try {
			Query q = em.createQuery("from Expense where id = :id");
	        q.setParameter("id", id);
	        ex = (Expense) q.getSingleResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
	        em.close();
	    }
		
		return ex;
	}
	
	public boolean updateExpense(Expense ex) {
		EntityManager em = null;
		EntityTransaction et = null;
		boolean f = false;

		try {
			em = emf.createEntityManager();
			et = em.getTransaction();
			et.begin();
			em.merge(ex);
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
	
	public boolean deleteExpense(int id) {
		EntityManager em = null;
		EntityTransaction et = null;
		boolean f = false;

		try {
			em = emf.createEntityManager();
			et = em.getTransaction();
			
			Expense ex = em.find(Expense.class, id);
			
			et.begin();
			em.remove(ex);
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

}
