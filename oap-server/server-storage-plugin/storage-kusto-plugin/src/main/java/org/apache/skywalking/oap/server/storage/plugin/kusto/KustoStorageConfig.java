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

import lombok.Getter;
import lombok.Setter;
import org.apache.skywalking.oap.server.library.module.ModuleConfig;

@Getter
@Setter
public class KustoStorageConfig extends ModuleConfig {
    /**
     * Azure Data Explorer cluster URI, e.g. https://mycluster.region.kusto.windows.net
     */
    private String clusterUri;
    /**
     * Service principal application (client) ID for authentication.
     */
    private String applicationId;
    /**
     * Service principal application secret.
     */
    private String applicationKey;
    /**
     * Azure AD tenant ID.
     */
    private String tenantId;
    /**
     * Kusto database name.
     */
    private String dbName = "skywalking";
    /**
     * Ingestion mode: "queued" (default, batched) or "streaming" (low-latency).
     * Streaming ingestion must be enabled on the ADX cluster.
     */
    private String ingestionType = "queued";
    /**
     * Number of async threads for batch flush operations.
     */
    private int flushThreadPoolSize = 4;
}
