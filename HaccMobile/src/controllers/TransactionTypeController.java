/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.TransactionTypeDAO;
import models.TransactionType;

/**
 *
 * @author vanganh
 */
public class TransactionTypeController {

    public TransactionTypeController() {
    }

    /**
     * get all types available inside record store
     * @return a string array containing all types
     */
    public TransactionType[] getTypes() {
        TransactionTypeDAO typeDAO = new TransactionTypeDAO();
        return typeDAO.getTypes();
    }

    /**
     * add new transaction type
     * @param type
     */
    public void addType(TransactionType type) {
        TransactionTypeDAO typeDAO = new TransactionTypeDAO();
        typeDAO.addType(type);
    }

    /**
     * insert all default types of transactions
     */
    public void addDefaultTypes() {
        TransactionTypeDAO typeDAO = new TransactionTypeDAO();
        typeDAO.addDefautlTypes();
    }

    public void deleteType(String typeName) {
        TransactionTypeDAO typeDAO = new TransactionTypeDAO();
        typeDAO.deleteType(typeName);
    }

    public boolean checkDuplicatedTrxType(String typeName) {
        TransactionTypeDAO typeDAO = new TransactionTypeDAO();
        return typeDAO.isDuplicatedTrxType(typeName);
    }
}
