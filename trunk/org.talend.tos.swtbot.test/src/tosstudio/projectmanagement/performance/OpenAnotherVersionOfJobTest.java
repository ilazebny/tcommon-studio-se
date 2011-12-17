// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package tosstudio.projectmanagement.performance;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.talend.swtbot.TalendSwtBotForTos;
import org.talend.swtbot.Utilities;
import org.talend.swtbot.items.TalendJobItem;

/**
 * DOC Administrator class global comment. Detailled comment
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class OpenAnotherVersionOfJobTest extends TalendSwtBotForTos {

    private TalendJobItem jobItem;

    private static final String JOBNAME = "test01"; //$NON-NLS-1$

    @Before
    public void createAJob() {
        jobItem = new TalendJobItem(JOBNAME);
        jobItem.create();
    }

    @Test
    public void openAnotherVersionOfJob() {
        jobItem.getEditor().saveAndClose();
        jobItem.getItem().contextMenu("Open an other version").click();

        gefBot.shell("New job").activate();
        gefBot.checkBox("Create new version and open it?").click();
        gefBot.button("M").click();
        gefBot.button("m").click();
        gefBot.button("Finish").click();

        SWTBotCTabItem newJobTabItem1 = gefBot.cTabItem("Job " + JOBNAME + " 1.1");
        Assert.assertNotNull("job tab is not opened", newJobTabItem1);

        SWTBotTreeItem newItem = jobItem.getParentNode().expand().getNode(JOBNAME + " 1.1");
        jobItem.setItem(newItem);
        jobItem.getItem().contextMenu("Open an other version").click();
        gefBot.shell("New job").activate();
        gefBot.table().select(0);
        gefBot.button("Finish").click();

        SWTBotCTabItem newJobTabItem2 = gefBot.cTabItem("Job " + JOBNAME + " 0.1");
        Assert.assertNotNull("job tab is not opened", newJobTabItem2);
    }

    @After
    public void removePreviouslyCreateItems() {
        gefBot.cTabItem("Job " + JOBNAME + " 0.1").close();
        gefBot.toolbarButtonWithTooltip("Save (Ctrl+S)").click();
        gefBot.cTabItem("Job " + JOBNAME + " 1.1").close();
        Utilities.cleanUpRepository(jobItem.getParentNode());
        Utilities.emptyRecycleBin();
    }
}