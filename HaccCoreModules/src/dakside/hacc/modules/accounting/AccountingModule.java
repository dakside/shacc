/*
 *  Copyright (C) 2009 Le Tuan Anh
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
package dakside.hacc.modules.accounting;

import dakside.hacc.modules.accounting.accountposting.AccountingView;
import dakside.hacc.modules.accounting.accounttree.AccountManagerView;
import dakside.hacc.modules.accounting.periodman.AccountPeriodView;
import dakside.hacc.modules.accounting.reports.BalanceSheetView;
import dakside.hacc.modules.accounting.reports.GeneralLedgerReportView;
import dakside.hacc.modules.accounting.reports.IncomeStatementView;
import dakside.hacc.modules.accounting.reports.JournalVoucherView;
import java.awt.Component;
import org.dakside.duck.plugins.Function;
import org.dakside.duck.plugins.Unloadable;

/**
 * Accounting  main module
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class AccountingModule implements Unloadable {

    private static Component viewAccountPosting = null;
    private static Component viewAccountTreeMgmt = null;
    private static Component viewPeriodMan = null;
    private static Component viewGeneralLedger = null;
    private static Component viewJournalVoucher = null;
    private static Component viewBalanceSheet = null;
    private static Component viewIncomeStatement = null;

    public synchronized void unload() {
        viewAccountPosting = null;
        viewAccountTreeMgmt = null;
        viewPeriodMan = null;
        viewGeneralLedger = null;
        viewJournalVoucher = null;
        viewBalanceSheet = null;
        viewIncomeStatement = null;
    }

    @Function(Text = "AccountPosting", Description = "AccountPostingDesc",
    IconPath = "icon_posting", Category = "Accounting", Location = Function.STARTPAGE)
    public synchronized Component showPosting() {
        if (viewAccountPosting == null) {
            viewAccountPosting = new AccountingView();
        }
        return viewAccountPosting;
    }

    @Function(Text = "AccountTreeManagement", Description = "AccountTreeManagementDesc",
    IconPath = "icon_acctree", Category = "Accounting", Location = Function.STARTPAGE)
    public Component showAccTreeMgmt() {
        if (viewAccountTreeMgmt == null) {
            viewAccountTreeMgmt = new AccountManagerView();
        }
        return viewAccountTreeMgmt;
    }

    @Function(Text = "AccountPeriodManagement", Description = "Create/ Edit Account Period",
    IconPath = "icon_periodman", Category = "Accounting", Location = Function.STARTPAGE)
    public Component showPeriodMan() {
        if (viewPeriodMan == null) {
            viewPeriodMan = new AccountPeriodView();
        }
        return viewPeriodMan;
    }

    @Function(Text = "GeneralLedgerReport", Description = "GeneralLedgerReportDesc",
    IconPath = "icon_generalledger", Category = "mnuReport", Location = Function.MENU)
    public Component showGeneralLedger() {
        if (viewGeneralLedger == null) {
            viewGeneralLedger = new GeneralLedgerReportView();
        }
        return viewGeneralLedger;
    }

    @Function(Text = "JournalVoucher", Description = "JournalVoucherDesc",
    IconPath = "icon_journalvoucher", Category = "mnuReport", Location = Function.MENU)
    public Component showJournalVoucher() {
        if (viewJournalVoucher == null) {
            viewJournalVoucher = new JournalVoucherView();
        }
        return viewJournalVoucher;
    }

    @Function(Text = "balance_sheet", Description = "balance_sheet_desc",
    IconPath = "icon_journalvoucher", Category = "mnuReport", Location = Function.MENU)
    public Component showBalanceSheet() {
        if (viewBalanceSheet == null) {
            viewBalanceSheet = new BalanceSheetView();
        }
        return viewBalanceSheet;
    }

    @Function(Text = "income_statement", Description = "income_statement_desc",
    IconPath = "icon_journalvoucher", Category = "mnuReport", Location = Function.MENU)
    public Component showIncomeStatement() {
        if (viewIncomeStatement == null) {
            viewIncomeStatement = new IncomeStatementView();
        }
        return viewIncomeStatement;
    }
}
