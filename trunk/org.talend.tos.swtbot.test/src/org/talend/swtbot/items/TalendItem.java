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
package org.talend.swtbot.items;

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Assert;
import org.talend.swtbot.Utilities;
import org.talend.swtbot.Utilities.TalendItemType;

/**
 * DOC fzhong class global comment. Detailled comment
 */
public class TalendItem {

    protected SWTBotTreeItem item;

    protected String itemFullName;

    protected String itemName;

    protected String itemVersion = "0.1"; //$NON-NLS-1$

    protected String folderPath;

    protected TalendItemType itemType;

    protected SWTBotTreeItem parentNode;

    protected SWTGefBot gefBot = new SWTGefBot();

    public TalendItem() {
    }

    public TalendItem(TalendItemType itemType) {
        initialise(itemType);
    }

    public TalendItem(String itemName, TalendItemType itemType) {
        this.itemName = itemName;
        this.itemFullName = this.itemName + " " + this.itemVersion;
        initialise(itemType);
    }

    public SWTBotTreeItem getItem() {
        return this.item;
    }

    public void setItem(SWTBotTreeItem item) {
        this.item = item;
        if (item == null)
            return;
        this.itemFullName = item.getText();
        if (item.getText().indexOf(" ") != -1) {
            String[] temp = itemFullName.split(" ");
            this.itemName = temp[0];
            this.itemVersion = temp[1];
        } else
            this.itemName = item.getText();
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public TalendItemType getItemType() {
        return this.itemType;
    }

    protected void setItemType(TalendItemType itemType) {
        this.itemType = itemType;
    }

    public SWTBotTreeItem getParentNode() {
        return parentNode;
    }

    protected void setParentNode(SWTBotTreeItem parentNode) {
        this.parentNode = parentNode;
    }

    public String getItemFullName() {
        return itemFullName;
    }

    protected void setItemFullName(String itemFullName) {
        this.itemFullName = itemFullName;
    }

    public String getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(String itemVersion) {
        this.itemVersion = itemVersion;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        if ("".equals(folderPath) || folderPath == null)
            return;
        this.folderPath = folderPath;
        String[] folders = folderPath.split("/");
        for (int i = 0; i < folders.length; i++) {
            setParentNode(parentNode.expandNode(folders[i]));
        }
    }

    protected void initialise(TalendItemType itemType) {
        setItemType(itemType);
        setParentNode(Utilities.getTalendItemNode(itemType));
    }

    public void create() {
    }

    public void copyAndPaste() {
        Utilities.copyAndPaste(parentNode, itemName, itemVersion);
    }

    public void delete() {
        parentNode.getNode(itemFullName).contextMenu("Delete").click();
        SWTBotTreeItem newItem = null;
        String path = "";
        try {
            if (folderPath != null)
                path = folderPath;
            if (!(getClass().newInstance() instanceof TalendSchemaItem))
                path = " (" + path + ")";
            newItem = Utilities.getRepositoryTree().expandNode("Recycle bin").select(itemFullName + path);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("item is not deleted to recycle bin", newItem);
        }
    }

    public void duplicate(String newItemName) {
        Utilities.duplicate(parentNode, itemName, itemVersion, newItemName);
    }

    public void rename(String newItemName) {
        SWTBotShell shell = beginEditWizard();
        gefBot.text(itemName).setText(newItemName);
        setItemName(newItemName);
        finishEditWizard(shell);
    }

    public SWTBotShell beginEditWizard() {
        return beginEditWizard("Edit properties", "Edit properties");
    }

    public SWTBotShell beginEditWizard(String contextMenu, String shellTitle) {
        parentNode.getNode(itemFullName).contextMenu(contextMenu).click();
        SWTBotShell shell = gefBot.shell(shellTitle).activate();
        return shell;
    }

    public void finishEditWizard(SWTBotShell shell) {
        finishCreationWizard(shell);
    }

    public SWTBotShell beginCreationWizard(String contextMenu, String shellTitle) {
        parentNode.contextMenu(contextMenu).click();

        SWTBotShell shell = gefBot.activeShell();
        if (!(itemType == Utilities.TalendItemType.JOBSCRIPTS)) {
            gefBot.waitUntil(Conditions.shellIsActive(shellTitle));
            shell = gefBot.shell(shellTitle);
            shell.activate();
        }

        gefBot.textWithLabel("Name").setText(itemName);
        return shell;
    }

    public void finishCreationWizard(final SWTBotShell shell) {
        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {
                return gefBot.button("Finish").isEnabled();
            }

            public String getFailureMessage() {
                shell.close();
                return "finish button is not enabled, item created fail. Maybe the item name already exist";
            }

        });
        gefBot.button("Finish").click();

        gefBot.waitUntil(new DefaultCondition() {

            public boolean test() throws Exception {
                return !shell.isOpen();
            }

            public String getFailureMessage() {
                shell.close();
                return "shell did not close automatically";
            }

        }, 2000);

        SWTBotTreeItem newTreeItem = null;
        try {
            if (gefBot.toolbarButtonWithTooltip("Save (Ctrl+S)").isEnabled()) {
                gefBot.toolbarButtonWithTooltip("Save (Ctrl+S)").click();
            }
            parentNode.setFocus();
            newTreeItem = parentNode.expand().select(itemName + " 0.1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertNotNull("item is not created", newTreeItem);
        }

        setItem(parentNode.getNode(itemName + " 0.1"));
    }
}