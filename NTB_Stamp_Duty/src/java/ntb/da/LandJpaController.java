/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ntb.da;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import ntb.da.exceptions.NonexistentEntityException;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.Building;
import ntb.entity.Land;

/**
 *
 * @author TUNG
 */
public class LandJpaController implements Serializable {

    public LandJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<Land> searchLandByStatus(String status){
         TypedQuery<Land> query = getEntityManager().createQuery("SELECT l FROM Land l WHERE l.lStatus LIKE :status",Land.class);
           query.setParameter("status", "%" + status + "%");
         return query.getResultList();
    }
    
   
    
    

    public void create(Land land) throws RollbackFailureException, Exception {
        if (land.getBuildingList() == null) {
            land.setBuildingList(new ArrayList<Building>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Building> attachedBuildingList = new ArrayList<Building>();
            for (Building buildingListBuildingToAttach : land.getBuildingList()) {
                buildingListBuildingToAttach = em.getReference(buildingListBuildingToAttach.getClass(), buildingListBuildingToAttach.getBId());
                attachedBuildingList.add(buildingListBuildingToAttach);
            }
            land.setBuildingList(attachedBuildingList);
            em.persist(land);
            for (Building buildingListBuilding : land.getBuildingList()) {
                Land oldLIdOfBuildingListBuilding = buildingListBuilding.getLId();
                buildingListBuilding.setLId(land);
                buildingListBuilding = em.merge(buildingListBuilding);
                if (oldLIdOfBuildingListBuilding != null) {
                    oldLIdOfBuildingListBuilding.getBuildingList().remove(buildingListBuilding);
                    oldLIdOfBuildingListBuilding = em.merge(oldLIdOfBuildingListBuilding);
                }
            }
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

    public void edit(Land land) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Land persistentLand = em.find(Land.class, land.getLId());
            List<Building> buildingListOld = persistentLand.getBuildingList();
            List<Building> buildingListNew = land.getBuildingList();
            List<Building> attachedBuildingListNew = new ArrayList<Building>();
            for (Building buildingListNewBuildingToAttach : buildingListNew) {
                buildingListNewBuildingToAttach = em.getReference(buildingListNewBuildingToAttach.getClass(), buildingListNewBuildingToAttach.getBId());
                attachedBuildingListNew.add(buildingListNewBuildingToAttach);
            }
            buildingListNew = attachedBuildingListNew;
            land.setBuildingList(buildingListNew);
            land = em.merge(land);
            for (Building buildingListOldBuilding : buildingListOld) {
                if (!buildingListNew.contains(buildingListOldBuilding)) {
                    buildingListOldBuilding.setLId(null);
                    buildingListOldBuilding = em.merge(buildingListOldBuilding);
                }
            }
            for (Building buildingListNewBuilding : buildingListNew) {
                if (!buildingListOld.contains(buildingListNewBuilding)) {
                    Land oldLIdOfBuildingListNewBuilding = buildingListNewBuilding.getLId();
                    buildingListNewBuilding.setLId(land);
                    buildingListNewBuilding = em.merge(buildingListNewBuilding);
                    if (oldLIdOfBuildingListNewBuilding != null && !oldLIdOfBuildingListNewBuilding.equals(land)) {
                        oldLIdOfBuildingListNewBuilding.getBuildingList().remove(buildingListNewBuilding);
                        oldLIdOfBuildingListNewBuilding = em.merge(oldLIdOfBuildingListNewBuilding);
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
                Integer id = land.getLId();
                if (findLand(id) == null) {
                    throw new NonexistentEntityException("The land with id " + id + " no longer exists.");
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
            Land land;
            try {
                land = em.getReference(Land.class, id);
                land.getLId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The land with id " + id + " no longer exists.", enfe);
            }
            List<Building> buildingList = land.getBuildingList();
            for (Building buildingListBuilding : buildingList) {
                buildingListBuilding.setLId(null);
                buildingListBuilding = em.merge(buildingListBuilding);
            }
            em.remove(land);
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

    public List<Land> findLandEntities() {
        return findLandEntities(true, -1, -1);
    }

    public List<Land> findLandEntities(int maxResults, int firstResult) {
        return findLandEntities(false, maxResults, firstResult);
    }

    private List<Land> findLandEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Land.class));
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

    public Land findLand(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Land.class, id);
        } finally {
            em.close();
        }
    }

    public int getLandCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Land> rt = cq.from(Land.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
