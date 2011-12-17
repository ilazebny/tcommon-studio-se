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
package tisstudio.filters;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withTooltip;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.talend.swtbot.SWTBotLabelExt;
import org.talend.swtbot.TalendSwtBotForTos;
import org.talend.swtbot.Utilities;
import org.talend.swtbot.items.TalendJobItem;

/**
 * DOC fzhong class global comment. Detailled comment
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class FilterItemsByUserTest extends TalendSwtBotForTos {

    private TalendJobItem jobItem;

    private SWTBotLabelExt filterLabel;

    private SWTBotShell tempShell;

    @Before
    public void initialisePrivateField() {
        jobItem = new TalendJobItem("job_a");
        jobItem.create();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void filterItemsByUser() {
        int rowCount = 0;

        Matcher matcher = withTooltip("Filters...\nRight click to set up");
        SWTBotLabel label = new SWTBotLabel((Label) gefBot.widget(matcher, Utilities.getRepositoryView().getWidget()));
        filterLabel = new SWTBotLabelExt(label);
        filterLabel.rightClick();

        tempShell = gefBot.shell("Repository Filter").activate();
        try {
            gefBot.checkBox("All Users").click();
            gefBot.table(0).getTableItem(0).uncheck();
            gefBot.button("OK").click();

            filterLabel.click();
            rowCount = jobItem.getParentNode().rowCount();
        } catch (WidgetNotFoundException wnfe) {
            tempShell.close();
            Assert.fail(wnfe.getCause().getMessage());
        } catch (Exception e) {
            tempShell.close();
            Assert.fail(e.getMessage());
        }

        Assert.assertEquals("items did not filter", 0, rowCount);
    }

    @After
    public void removePreviouslyCreateItems() {
        filterLabel.rightClick();
        try {
            gefBot.checkBox("All Users").click();
            gefBot.button("OK").click();
        } catch (WidgetNotFoundException wnfe) {
            tempShell.close();
            Assert.fail(wnfe.getCause().getMessage());
        } catch (Exception e) {
            tempShell.close();
            Assert.fail(e.getMessage());
        }
        filterLabel.click();

        for (SWTBotEditor editor : gefBot.editors())
            editor.saveAndClose();
        Utilities.cleanUpRepository(jobItem.getParentNode());
        Utilities.emptyRecycleBin();
    }
}