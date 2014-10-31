/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.da;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ntb.entity.Land;
import ntb.entity.Apartment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import ntb.da.exceptions.NonexistentEntityException;
import ntb.da.exceptions.PreexistingEntityException;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.Building;

/**
 *
 * @author TUNG
 */
public class BuildingJpaController implements Serializable {

    public BuildingJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Building> getAllBuilding() {
        TypedQuery<Building> query = getEntityManager().createQuery("SELECT b FROM Building b", Building.class);
        return query.getResultList();
    }

    public void create(Building building) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (building.getApartmentList() == null) {
            building.setApartmentList(new ArrayList<Apartment>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Land LId = building.getLId();
            if (LId != null) {
                LId = em.getReference(LId.getClass(), LId.getLId());
                building.setLId(LId);
            }
            List<Apartment> attachedApartmentList = new ArrayList<Apartment>();
            for (Apartment apartmentListApartmentToAttach : building.getApartmentList()) {
                apartmentListApartmentToAttach = em.getReference(apartmentListApartmentToAttach.getClass(), apartmentListApartmentToAttach.getAPId());
                attachedApartmentList.add(apartmentListApartmentToAttach);
            }
            building.setApartmentList(attachedApartmentList);
            em.persist(building);
            if (LId != null) {
                LId.getBuildingList().add(building);
                LId = em.merge(LId);
            }
            for (Apartment apartmentListApartment : building.getApartmentList()) {
                Building oldBIdOfApartmentListApartment = apartmentListApartment.getBId();
                apartmentListApartment.setBId(building);
                apartmentListApartment = em.merge(apartmentListApartment);
                if (oldBIdOfApartmentListApartment != null) {
                    oldBIdOfApartmentListApartment.getApartmentList().remove(apartmentListApartment);
                    oldBIdOfApartmentListApartment = em.merge(oldBIdOfApartmentListApartment);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBuilding(building.getBId()) != null) {
                throw new PreexistingEntityException("Building " + building + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Building building) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Building persistentBuilding = em.find(Building.class, building.getBId());
            Land LIdOld = persistentBuilding.getLId();
            Land LIdNew = building.getLId();
            List<Apartment> apartmentListOld = persistentBuilding.getApartmentList();
            List<Apartment> apartmentListNew = building.getApartmentList();
            if (LIdNew != null) {
                LIdNew = em.getReference(LIdNew.getClass(), LIdNew.getLId());
                building.setLId(LIdNew);
            }
            List<Apartment> attachedApartmentListNew = new ArrayList<Apartment>();
            for (Apartment apartmentListNewApartmentToAttach : apartmentListNew) {
                apartmentListNewApartmentToAttach = em.getReference(apartmentListNewApartmentToAttach.getClass(), apartmentListNewApartmentToAttach.getAPId());
                attachedApartmentListNew.add(apartmentListNewApartmentToAttach);
            }
            apartmentListNew = attachedApartmentListNew;
            building.setApartmentList(apartmentListNew);
            building = em.merge(building);
            if (LIdOld != null && !LIdOld.equals(LIdNew)) {
                LIdOld.getBuildingList().remove(building);
                LIdOld = em.merge(LIdOld);
            }
            if (LIdNew != null && !LIdNew.equals(LIdOld)) {
                LIdNew.getBuildingList().add(building);
                LIdNew = em.merge(LIdNew);
            }
            for (Apartment apartmentListOldApartment : apartmentListOld) {
                if (!apartmentListNew.contains(apartmentListOldApartment)) {
                    apartmentListOldApartment.setBId(null);
                    apartmentListOldApartment = em.merge(apartmentListOldApartment);
                }
            }
            for (Apartment apartmentListNewApartment : apartmentListNew) {
                if (!apartmentListOld.contains(apartmentListNewApartment)) {
                    Building oldBIdOfApartmentListNewApartment = apartmentListNewApartment.getBId();
                    apartmentListNewApartment.setBId(building);
                    apartmentListNewApartment = em.merge(apartmentListNewApartment);
                    if (oldBIdOfApartmentListNewApartment != null && !oldBIdOfApartmentListNewApartment.equals(building)) {
                        oldBIdOfApartmentListNewApartment.getApartmentList().remove(apartmentListNewApartment);
                        oldBIdOfApartmentListNewApartment = em.merge(oldBIdOfApartmentListNewApartment);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = building.getBId();
                if (findBuilding(id) == null) {
                    throw new NonexistentEntityException("The building with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Building building;
            try {
                building = em.getReference(Building.class, id);
                building.getBId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The building with id " + id + " no longer exists.", enfe);
            }
            Land LId = building.getLId();
            if (LId != null) {
                LId.getBuildingList().remove(building);
                LId = em.merge(LId);
            }
            List<Apartment> apartmentList = building.getApartmentList();
            for (Apartment apartmentListApartment : apartmentList) {
                apartmentListApartment.setBId(null);
                apartmentListApartment = em.merge(apartmentListApartment);
            }
            em.remove(building);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Building> findBuildingEntities() {
        return findBuildingEntities(true, -1, -1);
    }

    public List<Building> findBuildingEntities(int maxResults, int firstResult) {
        return findBuildingEntities(false, maxResults, firstResult);
    }

    private List<Building> findBuildingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Building.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Building findBuilding(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Building.class, id);
        } finally {
            em.close();
        }
    }

    public int getBuildingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Building> rt = cq.from(Building.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
