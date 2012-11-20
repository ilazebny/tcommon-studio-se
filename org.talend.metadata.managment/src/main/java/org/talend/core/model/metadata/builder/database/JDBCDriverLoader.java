// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.model.metadata.builder.database;

import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.map.MultiKeyMap;
import org.talend.commons.utils.encoding.CharsetToolkit;
import org.talend.core.database.EDatabase4DriverClassName;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.database.conn.version.EDatabaseVersion4Drivers;

/**
 * DOC YeXiaowei class global comment. Detailled comment <br/>
 * 
 */
public class JDBCDriverLoader {

    public static final String SHUTDOWN_PARAM = ";shutdown=true"; //$NON-NLS-1$

    private static MultiKeyMap classLoadersMap = new MultiKeyMap();

    private static final Map<String, DriverShim> DRIVER_CACHE = new HashMap<String, DriverShim>();

    /**
     * Loads the jars for hive embedded mode required, I do not think it is the better method to do this here. Due to
     * the limitation on code structure, I have to write this method to load the jar required by Hive. If metadata
     * connection part is refactored, developer could adjust this method. Added by Marvin Wang on Oct 24, 2012.
     * 
     * @param libraries
     * @param dbType
     * @param dbVersion
     */
    public void loadForHiveEmbedded(List<String> libraries, String dbType, String dbVersion) {
        boolean flog = EDatabaseVersion4Drivers.containTypeAndVersion(dbType, dbVersion);
        HotClassLoader loader = null;
        if (flog) {
            loader = (HotClassLoader) classLoadersMap.get(dbType, dbVersion);
            if (loader == null) {
                loader = new HotClassLoader();
                for (int i = 0; i < libraries.size(); i++) {
                    loader.addPath(libraries.get(i));
                }
                classLoadersMap.put(dbType, dbVersion, loader);
            } else {
                URL[] urls = loader.getURLs();
                if (urls != null && urls.length > 0) {
                    for (int i = 0; i < urls.length; i++) {
                        String urlPath = urls[i].getPath();
                        for (int j = 0; j < libraries.size(); j++) {
                            if (urlPath != null && !"".equals(urlPath) && urlPath.equals(libraries.get(j))) {
                                loader.addPath(libraries.get(j));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * DOC YeXiaowei Comment method "getConnection".
     * 
     * @param jarPath
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @return
     */
    public List getConnection(String[] jarPath, String driverClassName, String url, String username, String password,
            String dbType, String dbVersion, String additionalParams) throws Exception {
        // qli modified to fix the bug 7656.
        List list = new ArrayList();
        HotClassLoader loader;
        boolean flog = EDatabaseVersion4Drivers.containTypeAndVersion(dbType, dbVersion);
        if (flog) {
            loader = (HotClassLoader) classLoadersMap.get(dbType, dbVersion);
            if (loader == null) {
                loader = new HotClassLoader();
                for (int i = 0; i < jarPath.length; i++) {
                    loader.addPath(jarPath[i]);
                }
                classLoadersMap.put(dbType, dbVersion, loader);
            } else {
                for (int i = 0; i < jarPath.length; i++) {
                    loader.addPath(jarPath[i]);
                }
            }
        } else {
            loader = HotClassLoader.getInstance();
            for (int i = 0; i < jarPath.length; i++) {
                // bug 17800 fixed: fix a problem of jdbc drivers used in the wizard.
                if (jarPath[i].contains(";")) {
                    String[] splittedPath = jarPath[i].split(";");
                    for (int j = 0; j < splittedPath.length; j++) {
                        loader.addPath(splittedPath[j]);
                    }
                } else {
                    loader.addPath(jarPath[i]);
                }
            }
        }

        DriverShim wapperDriver = null;
        Connection connection = null;
        try {
            Class<?> driver = Class.forName(driverClassName, true, loader);
            // Object driver = loader.loadClass(driverClassName).newInstance();
            // the jtds mode to connect sqlserver database only Instance driver once
            if (EDatabaseTypeName.MSSQL.getDisplayName().equals(dbType)
                    && DRIVER_CACHE.containsKey(EDatabase4DriverClassName.MSSQL.getDriverClass()) && "".equals(username)) {
                wapperDriver = DRIVER_CACHE.get(EDatabase4DriverClassName.MSSQL.getDriverClass());
            } else {
                wapperDriver = new DriverShim((Driver) (driver.newInstance()));
            }
            Properties info = new Properties();

            // to avoid NPE
            username = username != null ? username : "";
            password = password != null ? password : "";
            info.put("user", username); //$NON-NLS-1$
            info.put("password", password); //$NON-NLS-1$
            if (dbType.equals(EDatabaseTypeName.ACCESS.getXmlName()) || dbType.equals(EDatabaseTypeName.GODBC.getXmlName())) {
                Charset systemCharset = CharsetToolkit.getInternalSystemCharset();
                if (systemCharset != null && systemCharset.displayName() != null) {
                    info.put("charSet", systemCharset.displayName()); //$NON-NLS-1$
                }
            }

            if (additionalParams != null && !"".equals(additionalParams) && dbType.toUpperCase().contains("ORACLE")) {
                additionalParams = additionalParams.replaceAll("&", "\n");//$NON-NLS-1$//$NON-NLS-2$
                info.load(new java.io.ByteArrayInputStream(additionalParams.getBytes()));
                connection = wapperDriver.connect(url, info);

            } else {
                if (dbType.equals(EDatabaseTypeName.HSQLDB_IN_PROGRESS.getDisplayName())
                        && additionalParams.indexOf(SHUTDOWN_PARAM) == -1) {
                    url = url + SHUTDOWN_PARAM;
                }
                // MOD klliu TDQ-4659 sso could not check passed.2012-02-10
                // if (dbType.equals(EDatabaseTypeName.MSSQL.getDisplayName())) {
                // connection = ConnectionUtils.createConnection(url, (Driver) (driver.newInstance()), info);
                // } else {
                // we need to change later when TDQ supports hive embedded mode.
                if (EDatabaseTypeName.HIVE.getDisplayName().equals(dbType) && "EMBEDDED".equalsIgnoreCase(dbVersion)) {
                    Thread.currentThread().setContextClassLoader(loader);
                }
                // ClassLoader currentContextCL = Thread.currentThread().getContextClassLoader();
                connection = wapperDriver.connect(url, info);
                // Thread.currentThread().setContextClassLoader(currentContextCL);
            }
            // }
            if (EDatabaseTypeName.MSSQL.getDisplayName().equals(dbType)
                    && !DRIVER_CACHE.containsKey(EDatabase4DriverClassName.MSSQL.getDriverClass()) && "".equals(username)) {
                DRIVER_CACHE.put(EDatabase4DriverClassName.MSSQL.getDriverClass(), wapperDriver);
            }
            // DriverManager.deregisterDriver(wapperDriver);
            // bug 9162
            list.add(connection);
            list.add(wapperDriver);
            return list;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
    // public static void main(String[] args) {
    // // test IBM DB2
    //        String jarPath = "D:\\YeXiaowei\\workrela\\eclipse_3_3\\lib\\java\\db2jcc.jar"; //$NON-NLS-1$
    //        String driverClassName = "com.ibm.db2.jcc.DB2Driver"; //$NON-NLS-1$
    //        String url = "jdbc:db2://192.168.0.108:50000/talend"; //$NON-NLS-1$
    //        String username = "root"; //$NON-NLS-1$
    //        String password = "toor"; //$NON-NLS-1$
    //
    // JDBCDriverLoader loader = new JDBCDriverLoader();
    // Connection conn;
    // try {
    // conn = loader.getConnection(String[] jarPath, driverClassName, url, username, password);
    // } catch (Throwable e) {
    // e.printStackTrace();
    // }
    // // System.out.println(conn.getClass());
    // }

}
