/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.oap.server.storage.plugin.kusto;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.oap.server.core.analysis.Layer;
import org.apache.skywalking.oap.server.core.analysis.metrics.DataTable;
import org.apache.skywalking.oap.server.core.profiling.trace.ProfileLanguageType;
import org.apache.skywalking.oap.server.core.storage.StorageData;
import org.apache.skywalking.oap.server.core.storage.StorageException;
import org.apache.skywalking.oap.server.core.storage.model.Model;
import org.apache.skywalking.oap.server.core.storage.model.ModelColumn;
import org.apache.skywalking.oap.server.core.storage.model.ModelInstaller;
import org.apache.skywalking.oap.server.core.storage.type.StorageDataComplexObject;
import org.apache.skywalking.oap.server.library.module.ModuleManager;

/**
 * Creates and manages Kusto (Azure Data Explorer) tables based on SkyWalking {@link Model} definitions.
 * <p>
 * Uses {@code .create-merge table} KQL commands which are idempotent — they create the table
 * if it doesn't exist or add missing columns if it does.
 */
@Slf4j
public class KustoTableInstaller extends ModelInstaller {
    public static final String ID_COLUMN = StorageData.ID;

    private final KustoClient kustoClient;

    public KustoTableInstaller(final KustoClient kustoClient,
                               final ModuleManager moduleManager) {
        super(kustoClient, moduleManager);
        this.kustoClient = kustoClient;
    }

    @Override
    public InstallInfo isExists(final Model model) throws StorageException {
        final InstallInfoKusto info = new InstallInfoKusto(model);
        final String tableName = model.getName();
        info.setTableName(tableName);
        try {
            final String kql = tableName + " | count";
            kustoClient.query(kql);
            info.setTableExist(true);
            info.setAllExist(true);
        } catch (Exception e) {
            info.setTableExist(false);
            info.setAllExist(false);
        }
        return info;
    }

    @Override
    public void createTable(final Model model) throws StorageException {
        final String tableName = model.getName();
        try {
            final String command = buildCreateMergeTableCommand(tableName, model.getColumns());
            log.info("Creating Kusto table: {}", tableName);
            kustoClient.mgmt(command);
        } catch (Exception e) {
            throw new StorageException("Failed to create Kusto table: " + tableName, e);
        }
    }

    /**
     * Build a {@code .create-merge table} KQL command from the model column definitions.
     * This command is idempotent: it creates the table if absent, or adds new columns
     * to an existing table without dropping existing ones.
     */
    private String buildCreateMergeTableCommand(final String tableName,
                                                final List<ModelColumn> columns) {
        final List<String> colDefs = new ArrayList<>();
        colDefs.add(ID_COLUMN + ":string");
        for (final ModelColumn column : columns) {
            final String name = column.getColumnName().getStorageName();
            final String kustoType = mapToKustoType(column);
            colDefs.add(name + ":" + kustoType);
        }
        return ".create-merge table " + tableName + " (" + String.join(", ", colDefs) + ")";
    }

    /**
     * Map a SkyWalking {@link ModelColumn} Java type to a Kusto data type.
     */
    private String mapToKustoType(final ModelColumn column) {
        final Class<?> type = column.getType();
        if (Integer.class.equals(type) || int.class.equals(type)
            || Layer.class.equals(type) || ProfileLanguageType.class.equals(type)) {
            return "int";
        } else if (Long.class.equals(type) || long.class.equals(type)) {
            return "long";
        } else if (Double.class.equals(type) || double.class.equals(type)) {
            return "real";
        } else if (String.class.equals(type)) {
            return "string";
        } else if (StorageDataComplexObject.class.isAssignableFrom(type)) {
            return "dynamic";
        } else if (byte[].class.equals(type)) {
            return "dynamic";
        } else if (JsonObject.class.equals(type)) {
            return "dynamic";
        } else if (DataTable.class.isAssignableFrom(type)) {
            return "dynamic";
        } else if (List.class.isAssignableFrom(type)) {
            return "dynamic";
        } else {
            log.warn("Unknown column type {} for column {}, defaulting to string",
                     type.getName(), column.getColumnName().getStorageName());
            return "string";
        }
    }

    @Getter
    @Setter
    private static class InstallInfoKusto extends InstallInfo {
        private String tableName;
        private boolean tableExist;

        protected InstallInfoKusto(final Model model) {
            super(model);
        }

        @Override
        public String buildInstallInfoMsg() {
            return "InstallInfoKusto{"
                + "modelName=" + getModelName()
                + ", modelType=" + getModelType()
                + ", timeSeries=" + isTimeSeries()
                + ", superDataset=" + isSuperDataset()
                + ", tableName=" + tableName
                + ", allResourcesExist=" + isAllExist()
                + " [tableExist=" + tableExist
                + "]}";
        }
    }
}
