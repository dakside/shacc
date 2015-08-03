/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.TransactionController;
import controllers.TransactionTypeController;
import dao.TransactionTypeDAO;
import helpers.TransactionHelper;
import java.util.Date;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import models.Transaction;
import models.TransactionType;
import org.netbeans.microedition.lcdui.SimpleTableModel;
import org.netbeans.microedition.lcdui.TableItem;

/**
 * @author vanganh
 */
public class HaccMobile extends MIDlet implements CommandListener, ItemStateListener {

    private boolean midletPaused = false;
    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private Command cmdSaveTransaction;
    private Command cmdAllTrxBackToFrmNewTrx;
    private Command cmdAllTypes;
    private Command cmdFrmTypeBackToFrmNewTrx;
    private Command cmdAddTrxType;
    private Command cmdSaveTrxType;
    private Command cmdNewTypeBackToFrmAllTrx;
    private Command cmdDeleteTrxType;
    private Command cmdViewAllTrx;
    private Command cmdDeleteTrxType1;
    private Form frmNewTrx;
    private TextField txtAmount;
    private DateField dateTrxDate;
    private ChoiceGroup choiceType;
    private TextField txtDesc;
    private ChoiceGroup choiceCurrency;
    private Form frmAllTrx;
    private TableItem tableItemAllTrx;
    private Form frmAllType;
    private ChoiceGroup choicegroupTrxType;
    private StringItem stringItem;
    private Form frmNewTrxType;
    private TextField txtNewTypeName;
    //</editor-fold>//GEN-END:|fields|0|
    //declare other variables:
    TransactionController trxCtrl;
    TransactionTypeController trxTypeCtrl;
    Transaction trx;
    TransactionType trxType;

    /**
     * The HaccMobile constructor.
     */
    public HaccMobile() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|
    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getFrmNewTrx());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == frmAllTrx) {//GEN-BEGIN:|7-commandAction|1|42-preAction
            if (command == cmdAllTrxBackToFrmNewTrx) {//GEN-END:|7-commandAction|1|42-preAction
                // write pre-action user code here
                itemStateChanged(txtAmount);
                itemStateChanged(txtDesc);
                itemStateChanged(dateTrxDate);
                switchDisplayable(null, getFrmNewTrx());//GEN-LINE:|7-commandAction|2|42-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|3|59-preAction
        } else if (displayable == frmAllType) {
            if (command == cmdAddTrxType) {//GEN-END:|7-commandAction|3|59-preAction
                // write pre-action user code here
                switchDisplayable(null, getFrmNewTrxType());//GEN-LINE:|7-commandAction|4|59-postAction
                // write post-action user code here
            } else if (command == cmdDeleteTrxType1) {//GEN-LINE:|7-commandAction|5|82-preAction
                // write pre-action user code here
                deleteTrxTypePoint();//GEN-LINE:|7-commandAction|6|82-postAction
                // write post-action user code here
            } else if (command == cmdFrmTypeBackToFrmNewTrx) {//GEN-LINE:|7-commandAction|7|55-preAction
                // write pre-action user code here
                itemStateChanged(txtAmount);
                itemStateChanged(txtDesc);
                switchDisplayable(null, getFrmNewTrx());//GEN-LINE:|7-commandAction|8|55-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|9|50-preAction
        } else if (displayable == frmNewTrx) {
            if (command == cmdAllTypes) {//GEN-END:|7-commandAction|9|50-preAction
                // write pre-action user code here
                beforeAllTypes();//GEN-LINE:|7-commandAction|10|50-postAction
                // write post-action user code here
            } else if (command == cmdSaveTransaction) {//GEN-LINE:|7-commandAction|11|33-preAction
                // write pre-action user code here
                saveTrxPoint();//GEN-LINE:|7-commandAction|12|33-postAction
                // write post-action user code here
            } else if (command == cmdViewAllTrx) {//GEN-LINE:|7-commandAction|13|85-preAction
                // write pre-action user code here
                beforeAllTrx();//GEN-LINE:|7-commandAction|14|85-postAction
                // write post-action user code here
            } else if (command == exitCommand) {//GEN-LINE:|7-commandAction|15|19-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|16|19-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|17|68-preAction
        } else if (displayable == frmNewTrxType) {
            if (command == cmdNewTypeBackToFrmAllTrx) {//GEN-END:|7-commandAction|17|68-preAction
                // write pre-action user code here
                switchDisplayable(null, getFrmAllType());//GEN-LINE:|7-commandAction|18|68-postAction
                // write post-action user code here
            } else if (command == cmdSaveTrxType) {//GEN-LINE:|7-commandAction|19|70-preAction
                // write pre-action user code here
                saveTrxTypePoint();//GEN-LINE:|7-commandAction|20|70-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|21|7-postCommandAction
        }//GEN-END:|7-commandAction|21|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|22|
    //</editor-fold>//GEN-END:|7-commandAction|22|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
        }//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|18-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmNewTrx ">//GEN-BEGIN:|14-getter|0|14-preInit
    /**
     * Returns an initiliazed instance of frmNewTrx component.
     * @return the initialized component instance
     */
    public Form getFrmNewTrx() {
        if (frmNewTrx == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
            frmNewTrx = new Form("New transaction", new Item[] { getDateTrxDate(), getTxtAmount(), getTxtDesc(), getChoiceType(), getChoiceCurrency() });//GEN-BEGIN:|14-getter|1|14-postInit
            frmNewTrx.addCommand(getCmdSaveTransaction());
            frmNewTrx.addCommand(getCmdViewAllTrx());
            frmNewTrx.addCommand(getCmdAllTypes());
            frmNewTrx.addCommand(getExitCommand());
            frmNewTrx.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
        }//GEN-BEGIN:|14-getter|2|
        return frmNewTrx;
    }
    //</editor-fold>//GEN-END:|14-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: dateTrxDate ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initiliazed instance of dateTrxDate component.
     * @return the initialized component instance
     */
    public DateField getDateTrxDate() {
        if (dateTrxDate == null) {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
            dateTrxDate = new DateField("Date", DateField.DATE_TIME);//GEN-BEGIN:|22-getter|1|22-postInit
            dateTrxDate.setDate(new java.util.Date(System.currentTimeMillis()));//GEN-END:|22-getter|1|22-postInit
            // write post-init user code here
            dateTrxDate.setDate(new Date());
        }//GEN-BEGIN:|22-getter|2|
        return dateTrxDate;
    }
    //</editor-fold>//GEN-END:|22-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtAmount ">//GEN-BEGIN:|23-getter|0|23-preInit
    /**
     * Returns an initiliazed instance of txtAmount component.
     * @return the initialized component instance
     */
    public TextField getTxtAmount() {
        if (txtAmount == null) {//GEN-END:|23-getter|0|23-preInit
            // write pre-init user code here
            txtAmount = new TextField("Amount", "", 32, TextField.DECIMAL);//GEN-LINE:|23-getter|1|23-postInit
            // write post-init user code here
        }//GEN-BEGIN:|23-getter|2|
        return txtAmount;
    }
    //</editor-fold>//GEN-END:|23-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtDesc ">//GEN-BEGIN:|24-getter|0|24-preInit
    /**
     * Returns an initiliazed instance of txtDesc component.
     * @return the initialized component instance
     */
    public TextField getTxtDesc() {
        if (txtDesc == null) {//GEN-END:|24-getter|0|24-preInit
            // write pre-init user code here
            txtDesc = new TextField("Description", "", 32, TextField.ANY);//GEN-LINE:|24-getter|1|24-postInit
            // write post-init user code here
        }//GEN-BEGIN:|24-getter|2|
        return txtDesc;
    }
    //</editor-fold>//GEN-END:|24-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceType ">//GEN-BEGIN:|25-getter|0|25-preInit
    /**
     * Returns an initiliazed instance of choiceType component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceType() {
        if (choiceType == null) {//GEN-END:|25-getter|0|25-preInit
            // write pre-init user code here
            choiceType = new ChoiceGroup("Type", Choice.EXCLUSIVE);//GEN-LINE:|25-getter|1|25-postInit
            // write post-init user code here
            //TODO: seperate into func later
            TransactionTypeDAO typeDAO = new TransactionTypeDAO();
            //TODO: seperate into check empty type
            if (typeDAO.getTypes().length == 0) {
                typeDAO.addDefautlTypes();
            }
            TransactionType[] types = typeDAO.getTypes();
            if (types.length > 0) {
                for (int i = 0; i < types.length; i++) {
                    TransactionType type = types[i];
                    String typeName = type.toString();
                    choiceType.append(typeName, null);
                }
            }
        }//GEN-BEGIN:|25-getter|2|
        return choiceType;
    }
    //</editor-fold>//GEN-END:|25-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceCurrency ">//GEN-BEGIN:|26-getter|0|26-preInit
    /**
     * Returns an initiliazed instance of choiceCurrency component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceCurrency() {
        if (choiceCurrency == null) {//GEN-END:|26-getter|0|26-preInit
            // write pre-init user code here
            choiceCurrency = new ChoiceGroup("Currency", Choice.EXCLUSIVE);//GEN-BEGIN:|26-getter|1|26-postInit
            choiceCurrency.append("SGD", null);
            choiceCurrency.append("VND", null);
            choiceCurrency.append("USD", null);
            choiceCurrency.setSelectedFlags(new boolean[] { false, false, false });//GEN-END:|26-getter|1|26-postInit
            // write post-init user code here
        }//GEN-BEGIN:|26-getter|2|
        return choiceCurrency;
    }
    //</editor-fold>//GEN-END:|26-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdSaveTransaction ">//GEN-BEGIN:|32-getter|0|32-preInit
    /**
     * Returns an initiliazed instance of cmdSaveTransaction component.
     * @return the initialized component instance
     */
    public Command getCmdSaveTransaction() {
        if (cmdSaveTransaction == null) {//GEN-END:|32-getter|0|32-preInit
            // write pre-init user code here
            cmdSaveTransaction = new Command("Save Transaction", "Save Transaction", Command.ITEM, 0);//GEN-LINE:|32-getter|1|32-postInit
            // write post-init user code here
        }//GEN-BEGIN:|32-getter|2|
        return cmdSaveTransaction;
    }
    //</editor-fold>//GEN-END:|32-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: saveTrxPoint ">//GEN-BEGIN:|34-entry|0|35-preAction
    /**
     * Performs an action assigned to the saveTrxPoint entry-point.
     */
    public void saveTrxPoint() {//GEN-END:|34-entry|0|35-preAction
        // write pre-action user code here
        //TODO: remove debugging
        trx = getInputTrx();
        if (saveTransaction(trx) == false) {
            String alertErr = "Invalid transaction data!";
            if (txtAmount.getString().equals("")) {
                alertErr.concat("\r\nAmount could not be empty");
            }
            Alert alert = new Alert("Error", alertErr, null, AlertType.ERROR);
            getDisplay().setCurrent(alert, getDisplay().getCurrent());
        } else {
            beforeAllTrx();//GEN-LINE:|34-entry|1|35-postAction
            // write post-action user code here
        }
    }//GEN-BEGIN:|34-entry|2|
    //</editor-fold>//GEN-END:|34-entry|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: beforeAllTrx ">//GEN-BEGIN:|37-entry|0|38-preAction
    /**
     * Performs an action assigned to the beforeAllTrx entry-point.
     */
    public void beforeAllTrx() {//GEN-END:|37-entry|0|38-preAction
        // write pre-action user code here
        //reset the form
        frmAllTrx = null;
        tableItemAllTrx = null;
        //get data to put into the form
        displayAllTrx();
        switchDisplayable(null, getFrmAllTrx());//GEN-LINE:|37-entry|1|38-postAction
        // write post-action user code here
    }//GEN-BEGIN:|37-entry|2|
    //</editor-fold>//GEN-END:|37-entry|2|
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdAllTrxBackToFrmNewTrx ">//GEN-BEGIN:|41-getter|0|41-preInit
    /**
     * Returns an initiliazed instance of cmdAllTrxBackToFrmNewTrx component.
     * @return the initialized component instance
     */
    public Command getCmdAllTrxBackToFrmNewTrx() {
        if (cmdAllTrxBackToFrmNewTrx == null) {//GEN-END:|41-getter|0|41-preInit
            // write pre-init user code here
            cmdAllTrxBackToFrmNewTrx = new Command("Back", Command.BACK, 0);//GEN-LINE:|41-getter|1|41-postInit
            // write post-init user code here
        }//GEN-BEGIN:|41-getter|2|
        return cmdAllTrxBackToFrmNewTrx;
    }
    //</editor-fold>//GEN-END:|41-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmAllTrx ">//GEN-BEGIN:|40-getter|0|40-preInit
    /**
     * Returns an initiliazed instance of frmAllTrx component.
     * @return the initialized component instance
     */
    public Form getFrmAllTrx() {
        if (frmAllTrx == null) {//GEN-END:|40-getter|0|40-preInit
            // write pre-init user code here
            frmAllTrx = new Form("Transactions list", new Item[] { getTableItemAllTrx() });//GEN-BEGIN:|40-getter|1|40-postInit
            frmAllTrx.addCommand(getCmdAllTrxBackToFrmNewTrx());
            frmAllTrx.setCommandListener(this);//GEN-END:|40-getter|1|40-postInit
            // write post-init user code here
        }//GEN-BEGIN:|40-getter|2|
        return frmAllTrx;
    }
    //</editor-fold>//GEN-END:|40-getter|2|
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tableItemAllTrx ">//GEN-BEGIN:|46-getter|0|46-preInit
    /**
     * Returns an initiliazed instance of tableItemAllTrx component.
     * @return the initialized component instance
     */
    public TableItem getTableItemAllTrx() {
        if (tableItemAllTrx == null) {//GEN-END:|46-getter|0|46-preInit
            // write pre-init user code here
            tableItemAllTrx = new TableItem(getDisplay(), "");//GEN-LINE:|46-getter|1|46-postInit
            // write post-init user code here
        }//GEN-BEGIN:|46-getter|2|
        return tableItemAllTrx;
    }
    //</editor-fold>//GEN-END:|46-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: beforeAllTypes ">//GEN-BEGIN:|47-entry|0|48-preAction
    /**
     * Performs an action assigned to the beforeAllTypes entry-point.
     */
    public void beforeAllTypes() {//GEN-END:|47-entry|0|48-preAction
        // write pre-action user code here
        //frmAllType = null;
        //choicegroupTrxType = null;
        displayAllTrxType();
        switchDisplayable(null, getFrmAllType());//GEN-LINE:|47-entry|1|48-postAction
        // write post-action user code here
    }//GEN-BEGIN:|47-entry|2|
    //</editor-fold>//GEN-END:|47-entry|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdAllTypes ">//GEN-BEGIN:|49-getter|0|49-preInit
    /**
     * Returns an initiliazed instance of cmdAllTypes component.
     * @return the initialized component instance
     */
    public Command getCmdAllTypes() {
        if (cmdAllTypes == null) {//GEN-END:|49-getter|0|49-preInit
            // write pre-init user code here
            cmdAllTypes = new Command("View Types", Command.ITEM, 0);//GEN-LINE:|49-getter|1|49-postInit
            // write post-init user code here
        }//GEN-BEGIN:|49-getter|2|
        return cmdAllTypes;
    }
    //</editor-fold>//GEN-END:|49-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmAllType ">//GEN-BEGIN:|52-getter|0|52-preInit
    /**
     * Returns an initiliazed instance of frmAllType component.
     * @return the initialized component instance
     */
    public Form getFrmAllType() {
        if (frmAllType == null) {//GEN-END:|52-getter|0|52-preInit
            // write pre-init user code here
            frmAllType = new Form("Transaction Types", new Item[] { getStringItem(), getChoicegroupTrxType() });//GEN-BEGIN:|52-getter|1|52-postInit
            frmAllType.addCommand(getCmdAddTrxType());
            frmAllType.addCommand(getCmdDeleteTrxType1());
            frmAllType.addCommand(getCmdFrmTypeBackToFrmNewTrx());
            frmAllType.setCommandListener(this);//GEN-END:|52-getter|1|52-postInit
            // write post-init user code here
        }//GEN-BEGIN:|52-getter|2|
        return frmAllType;
    }
    //</editor-fold>//GEN-END:|52-getter|2|
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdFrmTypeBackToFrmNewTrx ">//GEN-BEGIN:|54-getter|0|54-preInit
    /**
     * Returns an initiliazed instance of cmdFrmTypeBackToFrmNewTrx component.
     * @return the initialized component instance
     */
    public Command getCmdFrmTypeBackToFrmNewTrx() {
        if (cmdFrmTypeBackToFrmNewTrx == null) {//GEN-END:|54-getter|0|54-preInit
            // write pre-init user code here
            cmdFrmTypeBackToFrmNewTrx = new Command("Back", Command.BACK, 0);//GEN-LINE:|54-getter|1|54-postInit
            // write post-init user code here
        }//GEN-BEGIN:|54-getter|2|
        return cmdFrmTypeBackToFrmNewTrx;
    }
    //</editor-fold>//GEN-END:|54-getter|2|
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choicegroupTrxType ">//GEN-BEGIN:|57-getter|0|57-preInit
    /**
     * Returns an initiliazed instance of choicegroupTrxType component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoicegroupTrxType() {
        if (choicegroupTrxType == null) {//GEN-END:|57-getter|0|57-preInit
            // write pre-init user code here
            choicegroupTrxType = new ChoiceGroup("", Choice.MULTIPLE);//GEN-BEGIN:|57-getter|1|57-postInit
            choicegroupTrxType.setFitPolicy(Choice.TEXT_WRAP_DEFAULT);//GEN-END:|57-getter|1|57-postInit
            // write post-init user code here
        }//GEN-BEGIN:|57-getter|2|
        return choicegroupTrxType;
    }
    //</editor-fold>//GEN-END:|57-getter|2|
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdAddTrxType ">//GEN-BEGIN:|58-getter|0|58-preInit
    /**
     * Returns an initiliazed instance of cmdAddTrxType component.
     * @return the initialized component instance
     */
    public Command getCmdAddTrxType() {
        if (cmdAddTrxType == null) {//GEN-END:|58-getter|0|58-preInit
            // write pre-init user code here
            cmdAddTrxType = new Command("Add Type", Command.ITEM, 0);//GEN-LINE:|58-getter|1|58-postInit
            // write post-init user code here
        }//GEN-BEGIN:|58-getter|2|
        return cmdAddTrxType;
    }
    //</editor-fold>//GEN-END:|58-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: saveTrxTypePoint ">//GEN-BEGIN:|60-entry|0|61-preAction
    /**
     * Performs an action assigned to the saveTrxTypePoint entry-point.
     */
    public void saveTrxTypePoint() {//GEN-END:|60-entry|0|61-preAction
        // write pre-action user code here
        trxType = getInputTrxType();
        if (saveTrxType(trxType) == false) {
            String alertErr = "";
            if (txtAmount.getString().equals("")) {
                alertErr = "Invalid transaction type data!\nNew type could not be duplicated and type name could not be empty";
            }
            Alert alert = new Alert("Error", alertErr, null, AlertType.ERROR);
            getDisplay().setCurrent(alert, getDisplay().getCurrent());
        } else {
        beforeAllTypes();//GEN-LINE:|60-entry|1|61-postAction
            // write post-action user code here
        }
    }//GEN-BEGIN:|60-entry|2|
    //</editor-fold>//GEN-END:|60-entry|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmNewTrxType ">//GEN-BEGIN:|63-getter|0|63-preInit
    /**
     * Returns an initiliazed instance of frmNewTrxType component.
     * @return the initialized component instance
     */
    public Form getFrmNewTrxType() {
        if (frmNewTrxType == null) {//GEN-END:|63-getter|0|63-preInit
            // write pre-init user code here
            frmNewTrxType = new Form("New Transaction Type", new Item[] { getTxtNewTypeName() });//GEN-BEGIN:|63-getter|1|63-postInit
            frmNewTrxType.addCommand(getCmdSaveTrxType());
            frmNewTrxType.addCommand(getCmdNewTypeBackToFrmAllTrx());
            frmNewTrxType.setCommandListener(this);//GEN-END:|63-getter|1|63-postInit
            // write post-init user code here
        }//GEN-BEGIN:|63-getter|2|
        return frmNewTrxType;
    }
    //</editor-fold>//GEN-END:|63-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtNewTypeName ">//GEN-BEGIN:|66-getter|0|66-preInit
    /**
     * Returns an initiliazed instance of txtNewTypeName component.
     * @return the initialized component instance
     */
    public TextField getTxtNewTypeName() {
        if (txtNewTypeName == null) {//GEN-END:|66-getter|0|66-preInit
            // write pre-init user code here
            txtNewTypeName = new TextField("Enter Type name;", null, 32, TextField.ANY);//GEN-LINE:|66-getter|1|66-postInit
            // write post-init user code here
        }//GEN-BEGIN:|66-getter|2|
        return txtNewTypeName;
    }
    //</editor-fold>//GEN-END:|66-getter|2|
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdNewTypeBackToFrmAllTrx ">//GEN-BEGIN:|67-getter|0|67-preInit
    /**
     * Returns an initiliazed instance of cmdNewTypeBackToFrmAllTrx component.
     * @return the initialized component instance
     */
    public Command getCmdNewTypeBackToFrmAllTrx() {
        if (cmdNewTypeBackToFrmAllTrx == null) {//GEN-END:|67-getter|0|67-preInit
            // write pre-init user code here
            cmdNewTypeBackToFrmAllTrx = new Command("Back", Command.BACK, 0);//GEN-LINE:|67-getter|1|67-postInit
            // write post-init user code here
        }//GEN-BEGIN:|67-getter|2|
        return cmdNewTypeBackToFrmAllTrx;
    }
    //</editor-fold>//GEN-END:|67-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdSaveTrxType ">//GEN-BEGIN:|69-getter|0|69-preInit
    /**
     * Returns an initiliazed instance of cmdSaveTrxType component.
     * @return the initialized component instance
     */
    public Command getCmdSaveTrxType() {
        if (cmdSaveTrxType == null) {//GEN-END:|69-getter|0|69-preInit
            // write pre-init user code here
            cmdSaveTrxType = new Command("Save", Command.ITEM, 0);//GEN-LINE:|69-getter|1|69-postInit
            // write post-init user code here
        }//GEN-BEGIN:|69-getter|2|
        return cmdSaveTrxType;
    }
    //</editor-fold>//GEN-END:|69-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdDeleteTrxType ">//GEN-BEGIN:|74-getter|0|74-preInit
    /**
     * Returns an initiliazed instance of cmdDeleteTrxType component.
     * @return the initialized component instance
     */
    public Command getCmdDeleteTrxType() {
        if (cmdDeleteTrxType == null) {//GEN-END:|74-getter|0|74-preInit
            // write pre-init user code here
            cmdDeleteTrxType = new Command("Delete Type(s)", Command.ITEM, 0);//GEN-LINE:|74-getter|1|74-postInit
            // write post-init user code here
        }//GEN-BEGIN:|74-getter|2|
        return cmdDeleteTrxType;
    }
    //</editor-fold>//GEN-END:|74-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: deleteTrxTypePoint ">//GEN-BEGIN:|77-entry|0|78-preAction
    /**
     * Performs an action assigned to the deleteTrxTypePoint entry-point.
     */
    public void deleteTrxTypePoint() {//GEN-END:|77-entry|0|78-preAction
        // write pre-action user code here
        boolean[] arr = new boolean[getChoicegroupTrxType().size()];
        getChoicegroupTrxType().getSelectedFlags(arr);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]) {
                String selectedType = getChoicegroupTrxType().getString(i);
                deleteTrxType(selectedType);
            }
        }
        beforeAllTypes();//GEN-LINE:|77-entry|1|78-postAction
        // write post-action user code here
    }//GEN-BEGIN:|77-entry|2|
    //</editor-fold>//GEN-END:|77-entry|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdDeleteTrxType1 ">//GEN-BEGIN:|81-getter|0|81-preInit
    /**
     * Returns an initiliazed instance of cmdDeleteTrxType1 component.
     * @return the initialized component instance
     */
    public Command getCmdDeleteTrxType1() {
        if (cmdDeleteTrxType1 == null) {//GEN-END:|81-getter|0|81-preInit
            // write pre-init user code here
            cmdDeleteTrxType1 = new Command("Delete Type(s)", Command.ITEM, 0);//GEN-LINE:|81-getter|1|81-postInit
            // write post-init user code here
        }//GEN-BEGIN:|81-getter|2|
        return cmdDeleteTrxType1;
    }
    //</editor-fold>//GEN-END:|81-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdViewAllTrx ">//GEN-BEGIN:|84-getter|0|84-preInit
    /**
     * Returns an initiliazed instance of cmdViewAllTrx component.
     * @return the initialized component instance
     */
    public Command getCmdViewAllTrx() {
        if (cmdViewAllTrx == null) {//GEN-END:|84-getter|0|84-preInit
            // write pre-init user code here
            cmdViewAllTrx = new Command("View Transactions", Command.ITEM, 0);//GEN-LINE:|84-getter|1|84-postInit
            // write post-init user code here
        }//GEN-BEGIN:|84-getter|2|
        return cmdViewAllTrx;
    }
    //</editor-fold>//GEN-END:|84-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem ">//GEN-BEGIN:|87-getter|0|87-preInit
    /**
     * Returns an initiliazed instance of stringItem component.
     * @return the initialized component instance
     */
    public StringItem getStringItem() {
        if (stringItem == null) {//GEN-END:|87-getter|0|87-preInit
            // write pre-init user code here
            stringItem = new StringItem("", "all default types could not be deleted");//GEN-LINE:|87-getter|1|87-postInit
            // write post-init user code here
        }//GEN-BEGIN:|87-getter|2|
        return stringItem;
    }
    //</editor-fold>//GEN-END:|87-getter|2|



    /* ------------------------------------ FUNCTIONS zone ------------------------------------ */

    /**
     * FUNC: getInputTrx() declare
     */
    public Transaction getInputTrx() {
        String desc = "";
        if (txtDesc.getString().equals("")) {
            desc = " ";
        } else {
            desc = txtDesc.getString();
        }
        double amount = 0.00;
        if (TransactionHelper.isDouble(txtAmount.getString())) {
            amount = Double.parseDouble(txtAmount.getString());
        } else {
            return null;
        }
        String type = choiceType.getString(choiceType.getSelectedIndex());
        TransactionType trxType = new TransactionType(type);
        String currency = choiceCurrency.getString(choiceCurrency.getSelectedIndex());
        Date trxDate = dateTrxDate.getDate();
        return new Transaction(trxType, amount, desc, currency, trxDate);
    }

    /**
     * insert new Transaction
     * @param trx
     * @return true if insert successfully or false if not
     */
    public boolean saveTransaction(Transaction trx) {
        boolean isSave = false;
        if (trx != null && trx.validate()) {
            trxCtrl = new TransactionController();
            trxCtrl.addTransaction(trx);
            isSave = true;
        }
        return isSave;
    }

    /**
     * display all transaction using TableItem
     */
    public void displayAllTrx() {
        //get all trx availabled in record store
        trxCtrl = new TransactionController();
        Transaction[] transactions = trxCtrl.getAllTransactions();

        //create simple table model
        String[] columnNames = new String[]{"Transactions"};
        String[][] defaultValues = new String[][]{
            new String[]{"No transaction found"}
        };
        SimpleTableModel simpleTableModel = new SimpleTableModel(defaultValues, columnNames);
        if (transactions.length > 0) {
            String[][] rows = new String[transactions.length][];
            for (int i = 0; i < transactions.length; i++) {
                Transaction transaction = transactions[i];
                String strTransaction = transaction.toString();
                trx = TransactionHelper.toTransaction(strTransaction);
                rows[i] = new String[]{trx.getType().toString(),
                            TransactionHelper.doubleToString(trx.getAmount()),
                            trx.getDescription(),
                            trx.getCurrency(),
                            TransactionHelper.convertInputDateToString(trx.getDate())};
            }
            columnNames = new String[]{"Type", "Amount", "Desc", "Curr", "Date"};
            simpleTableModel.setColumnNames(columnNames);
            simpleTableModel.setValues(rows);
        } else {
            simpleTableModel.setValues(defaultValues);
            simpleTableModel.setColumnNames(columnNames);
        }
        getTableItemAllTrx().setModel(simpleTableModel);
    }

    /**
     * display all transaction types in multi-selectable list
     */
    public void displayAllTrxType() {
        trxTypeCtrl = new TransactionTypeController();
        //check if no types: auto get the default types
        if (trxTypeCtrl.getTypes().length == 0) {
            trxTypeCtrl.addDefaultTypes();
        }

        TransactionType[] types = trxTypeCtrl.getTypes(); System.out.println("total types:" + types.length);
        choicegroupTrxType = getChoicegroupTrxType();
        choicegroupTrxType.deleteAll(); //clear types

        boolean[] selectedFlag = new boolean[types.length];
        for (int i = 0; i < types.length; i++) {
            TransactionType type = types[i];
            choicegroupTrxType.append(type.getTypeName(), null);
            selectedFlag[i] = false;
        }
        choicegroupTrxType.setSelectedFlags(selectedFlag);
    }

    /**
     * get user input new trx type
     * @return a transaction type
     */
    public TransactionType getInputTrxType() {
        String typeName = txtNewTypeName.getString();
        if (!typeName.equals(null) || !typeName.equals("")) {
            trxTypeCtrl = new TransactionTypeController();
            if (!trxTypeCtrl.checkDuplicatedTrxType(typeName)) {
                trxType = new TransactionType(typeName);
            }
        } else {
            trxType = null;
        }
        return trxType;
    }

    /**
     * insert new transaction type
     * @param trxType
     * @return true if new transaction type is inserted successfully or false if not
     */
    public boolean saveTrxType(TransactionType trxType) {
        boolean isSave = false;
        if (trxType != null) {
            trxTypeCtrl = new TransactionTypeController();
            //TODO: saveTrxType() >> need to check Transaction Type again if got other info
            trxTypeCtrl.addType(trxType);
            isSave = true;
        }
        return isSave;
    }

    /**
     * delete 1 or more transaction type
     * @param typeName
     */
    public void deleteTrxType(String typeName) {
        trxTypeCtrl = new TransactionTypeController();
        if (!typeName.equals(null) && !typeName.equals("")) {
            trxTypeCtrl.deleteType(typeName);
        }
    }
    /* ------------------------------------ end FUNCTIONS zone ------------------------------------ */

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay() {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet();
        } else {
            initialize();
            startMIDlet();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

    public void itemStateChanged(Item item) {
        if(item == txtAmount){ 
            txtAmount.setString("");
        }
        if(item == txtDesc){
            txtDesc.setString("");
        }
        if(item == dateTrxDate){
            dateTrxDate.setDate(new Date());
        }
    }

}
