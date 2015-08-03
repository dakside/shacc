/*
 *  Copyright (C) 2010 Le Tuan Anh <tuananh.ke@gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dakside.hacc.modules.databridge.dataagent;

import org.dakside.duck.dakwizard.DefaultWizardModel;
import org.dakside.duck.dakwizard.WizardStep;
import dakside.hacc.modules.databridge.helpers.TypeDefinition;
import java.io.File;

/**
 * Import loader wizard
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ImportLoaderWizardModel extends DefaultWizardModel {

    private File dataSource = null;
    private boolean dataSourceValidated = false;
    private TypeDefinition dataDefinition = null;
    private final StringBuffer log = new StringBuffer();
    private Object[] modelset = null;

    /**
     * Wizard constructor
     */
    public ImportLoaderWizardModel() {
        WizardStep steps[] = new WizardStep[]{
            new DefineTypeStep(this),
            new TransactionListStep(this),
            new InsertTransactionStep(this)
        };
        initPath(steps);
    }

    /**
     * @return the dataSource
     */
    public File getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(File dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return the dataSourceValidated
     */
    public boolean isDataSourceValidated() {
        return dataSourceValidated;
    }

    /**
     * @param dataSourceValidated the dataSourceValidated to set
     */
    public void setDataSourceValidated(boolean dataSourceValidated) {
        this.dataSourceValidated = dataSourceValidated;
    }

    /**
     * @return the dataDefinition
     */
    public TypeDefinition getDataDefinition() {
        return dataDefinition;
    }

    /**
     * @param dataDefinition the dataDefinition to set
     */
    public void setDataDefinition(TypeDefinition dataDefinition) {
        this.dataDefinition = dataDefinition;
    }

    /**
     * @return the modelset
     */
    public Object[] getModelset() {
        return modelset;
    }

    /**
     * @param modelset the modelset to set
     */
    public void setModelset(Object[] modelset) {
        this.modelset = modelset;
    }

    /**
     * Log a line
     * @param message
     */
    public void log(String message){
        this.log.append(message).append("\r\n");
    }
}
