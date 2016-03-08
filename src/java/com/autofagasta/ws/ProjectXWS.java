/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autofagasta.ws;

import com.autofagasta.entities.Tag;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

/**
 *
 * @author zkropotkine
 */
@WebService(serviceName = "ProjectXWS")
public class ProjectXWS {
    @PersistenceContext(unitName = "ProjectXWebPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;


    /**
     * Web service operation
     */
    @WebMethod(operationName = "checkTag")
    public String checkTag(@WebParam(name = "tagInfo") String tagInfo) {
        /*final String success = "Sucess";
        String result = "Failure";
        
        if ("1001".equalsIgnoreCase(tagInfo)) {
            result = success;
        }
        
        return result;*/
        System.out.println(tagInfo);
        
        String a = tagInfo.trim();
        
        System.out.println(a);
        
        String result = "Failure";
        Tag tag = em.find(Tag.class, a);
        Short status = null;
        final Short newStatus = 1;
        
        System.out.println(tag);
        
        if (tag != null) {
            status = tag.getStatus();
            
            System.out.println(status);
            
            if (status == 0) {
                result = "Success";
            }
            
            tag.setStatus(newStatus);
            persist(tag);

        } 
        
       
        
        return result;
    }

    
    /*public void persist(Tag tag) {
        final Short newStatus = 1;
        try {
            utx.begin();
            tag.setStatus(newStatus);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }*/
    
    public void persist(Object object) {
        try {
            utx.begin();
            em.merge(object);
            //em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    
}
