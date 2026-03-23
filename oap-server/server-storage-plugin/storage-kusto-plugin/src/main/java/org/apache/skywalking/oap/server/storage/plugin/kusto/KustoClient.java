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

import com.microsoft.azure.kusto.data.ClientFactory;
import com.microsoft.azure.kusto.data.ClientRequestProperties;
import com.microsoft.azure.kusto.data.KustoOperationResult;
import com.microsoft.azure.kusto.data.KustoResultSetTable;
import com.microsoft.azure.kusto.data.auth.ConnectionStringBuilder;
import com.microsoft.azure.kusto.ingest.IngestClientFactory;
import java.io.IOException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.oap.server.library.client.Client;
import org.apache.skywalking.oap.server.library.util.HealthChecker;

/**
 * Wraps the Azure Data Explorer (Kusto) Java SDK to implement SkyWalking's
 * {@link Client} lifecycle interface. Provides query and ingestion access to
 * a single ADX database.
 */
@Slf4j
public class KustoClient implements Client {
    private final KustoStorageConfig config;

    @Getter
    private com.microsoft.azure.kusto.data.Client queryClient;
    @Getter
    private com.microsoft.azure.kusto.ingest.IngestClient ingestClient;

    private HealthChecker healthChecker;

    public KustoClient(final KustoStorageConfig config) {
        this.config = config;
    }

    @Override
    public void connect() throws Exception {
        final ConnectionStringBuilder csb =
            ConnectionStringBuilder.createWithAadApplicationCredentials(
                config.getClusterUri(),
                config.getApplicationId(),
                config.getApplicationKey(),
                config.getTenantId()
            );

        this.queryClient = ClientFactory.createClient(csb);

        if ("streaming".equalsIgnoreCase(config.getIngestionType())) {
            final ConnectionStringBuilder ingestCsb =
                ConnectionStringBuilder.createWithAadApplicationCredentials(
                    config.getClusterUri(),
                    config.getApplicationId(),
                    config.getApplicationKey(),
                    config.getTenantId()
                );
            this.ingestClient = IngestClientFactory.createManagedStreamingIngestClient(csb, ingestCsb);
        } else {
            final ConnectionStringBuilder ingestCsb =
                ConnectionStringBuilder.createWithAadApplicationCredentials(
                    config.getClusterUri().replace("https://", "https://ingest-"),
                    config.getApplicationId(),
                    config.getApplicationKey(),
                    config.getTenantId()
                );
            this.ingestClient = IngestClientFactory.createClient(ingestCsb);
        }

        log.info("Kusto client connected to cluster: {}, database: {}",
                 config.getClusterUri(), config.getDbName());
        if (healthChecker != null) {
            healthChecker.health();
        }
    }

    @Override
    public void shutdown() throws IOException {
        if (queryClient != null) {
            queryClient.close();
        }
        if (ingestClient != null) {
            ingestClient.close();
        }
    }

    /**
     * Execute a KQL query and return the primary result set.
     */
    public KustoResultSetTable query(final String kql) throws Exception {
        final KustoOperationResult result =
            queryClient.execute(config.getDbName(), kql);
        return result.getPrimaryResults();
    }

    /**
     * Execute a KQL query with request properties.
     */
    public KustoResultSetTable query(final String kql,
                                     final ClientRequestProperties properties) throws Exception {
        final KustoOperationResult result =
            queryClient.execute(config.getDbName(), kql, properties);
        return result.getPrimaryResults();
    }

    /**
     * Execute a management (control) command, e.g. .create-merge table, .alter table policy.
     */
    public KustoResultSetTable mgmt(final String command) throws Exception {
        final KustoOperationResult result =
            queryClient.execute(config.getDbName(), command);
        return result.getPrimaryResults();
    }

    public String getDatabase() {
        return config.getDbName();
    }

    public void registerChecker(final HealthChecker checker) {
        this.healthChecker = checker;
    }
}
