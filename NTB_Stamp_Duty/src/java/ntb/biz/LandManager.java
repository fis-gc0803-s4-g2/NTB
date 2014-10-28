/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntb.biz;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import ntb.da.LandJpaController;
import ntb.da.exceptions.RollbackFailureException;
import ntb.entity.Land;

/**
 *
 * @author TUNG
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LandManager {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceUnit(unitName = "NTB_Stamp_DutyPU")

    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;
    
    private LandJpaController daController;
    
    public LandJpaController getDaController() {
        if (daController == null) {
            daController = new LandJpaController(utx, emf); 

        }
        return daController;
    }
    
    /**
     * 
     * @return list
     */
    public List<Land> getAllLand(){
        return getDaController().getAllLand();
    }
    
    /**
     *
     * @param land
     * @throws RollbackFailureException
     * @throws Exception
     */
    public void createLand(Land land) throws RollbackFailureException, Exception{
        getDaController().create(land);
    }
    
    public Land getLandById(int lId){
        return getDaController().getLandById(lId);
    }
}
