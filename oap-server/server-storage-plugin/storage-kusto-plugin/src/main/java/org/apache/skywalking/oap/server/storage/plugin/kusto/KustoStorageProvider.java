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

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.oap.server.core.CoreModule;
import org.apache.skywalking.oap.server.core.storage.IBatchDAO;
import org.apache.skywalking.oap.server.core.storage.IHistoryDeleteDAO;
import org.apache.skywalking.oap.server.core.storage.StorageBuilderFactory;
import org.apache.skywalking.oap.server.core.storage.StorageDAO;
import org.apache.skywalking.oap.server.core.storage.StorageModule;
import org.apache.skywalking.oap.server.core.storage.cache.INetworkAddressAliasDAO;
import org.apache.skywalking.oap.server.core.storage.management.UIMenuManagementDAO;
import org.apache.skywalking.oap.server.core.storage.management.UITemplateManagementDAO;
import org.apache.skywalking.oap.server.core.storage.model.ModelCreator;
import org.apache.skywalking.oap.server.core.storage.profiling.asyncprofiler.IAsyncProfilerTaskLogQueryDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.asyncprofiler.IAsyncProfilerTaskQueryDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.asyncprofiler.IJFRDataQueryDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.continuous.IContinuousProfilingPolicyDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.ebpf.IEBPFProfilingDataDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.ebpf.IEBPFProfilingScheduleDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.ebpf.IEBPFProfilingTaskDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.ebpf.IServiceLabelDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.pprof.IPprofDataQueryDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.pprof.IPprofTaskLogQueryDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.pprof.IPprofTaskQueryDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.trace.IProfileTaskLogQueryDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.trace.IProfileTaskQueryDAO;
import org.apache.skywalking.oap.server.core.storage.profiling.trace.IProfileThreadSnapshotQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.IAggregationQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.IAlarmQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.IBrowserLogQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.IEventQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.IHierarchyQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.ILogQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.IMetadataQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.IMetricsQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.IRecordsQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.ISpanAttachedEventQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.ITagAutoCompleteQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.ITopologyQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.ITraceQueryDAO;
import org.apache.skywalking.oap.server.core.storage.query.IZipkinQueryDAO;
import org.apache.skywalking.oap.server.core.storage.ttl.DefaultStorageTTLStatusQuery;
import org.apache.skywalking.oap.server.core.storage.ttl.StorageTTLStatusQuery;
import org.apache.skywalking.oap.server.library.module.ModuleConfig;
import org.apache.skywalking.oap.server.library.module.ModuleDefine;
import org.apache.skywalking.oap.server.library.module.ModuleProvider;
import org.apache.skywalking.oap.server.library.module.ModuleStartException;
import org.apache.skywalking.oap.server.library.module.ServiceNotProvidedException;
import org.apache.skywalking.oap.server.storage.plugin.kusto.base.KustoBatchDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.base.KustoHistoryDeleteDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.base.KustoNetworkAddressAliasDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.base.KustoStorageDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoAggregationQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoAlarmQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoAsyncProfilerTaskLogQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoAsyncProfilerTaskQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoBrowserLogQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoContinuousProfilingPolicyDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoEBPFProfilingDataDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoEBPFProfilingScheduleDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoEBPFProfilingTaskDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoEventQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoHierarchyQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoJFRDataQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoLogQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoMetadataQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoMetricsQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoPprofDataQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoPprofTaskLogQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoPprofTaskQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoProfileTaskLogQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoProfileTaskQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoProfileThreadSnapshotQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoRecordsQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoServiceLabelDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoSpanAttachedEventQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoTagAutoCompleteQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoTopologyQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoTraceQueryDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoUIMenuManagementDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoUITemplateManagementDAO;
import org.apache.skywalking.oap.server.storage.plugin.kusto.query.KustoZipkinQueryDAO;
import org.apache.skywalking.oap.server.telemetry.TelemetryModule;
import org.apache.skywalking.oap.server.telemetry.api.HealthCheckMetrics;
import org.apache.skywalking.oap.server.telemetry.api.MetricsCreator;
import org.apache.skywalking.oap.server.telemetry.api.MetricsTag;

@Slf4j
public class KustoStorageProvider extends ModuleProvider {
    private KustoStorageConfig config;
    private KustoClient kustoClient;
    private KustoTableInstaller modelInstaller;

    @Override
    public String name() {
        return "kusto";
    }

    @Override
    public Class<? extends ModuleDefine> module() {
        return StorageModule.class;
    }

    @Override
    public ConfigCreator<? extends ModuleConfig> newConfigCreator() {
        return new ConfigCreator<KustoStorageConfig>() {
            @Override
            public Class<KustoStorageConfig> type() {
                return KustoStorageConfig.class;
            }

            @Override
            public void onInitialized(final KustoStorageConfig initialized) {
                config = initialized;
            }
        };
    }

    @Override
    public void prepare() throws ServiceNotProvidedException, ModuleStartException {
        kustoClient = new KustoClient(config);
        modelInstaller = new KustoTableInstaller(kustoClient, getManager());

        this.registerServiceImplementation(StorageBuilderFactory.class, new StorageBuilderFactory.Default());
        this.registerServiceImplementation(IBatchDAO.class, new KustoBatchDAO(kustoClient));
        this.registerServiceImplementation(StorageDAO.class, new KustoStorageDAO(kustoClient));
        this.registerServiceImplementation(IHistoryDeleteDAO.class, new KustoHistoryDeleteDAO(kustoClient));
        this.registerServiceImplementation(INetworkAddressAliasDAO.class, new KustoNetworkAddressAliasDAO(kustoClient));
        this.registerServiceImplementation(ITopologyQueryDAO.class, new KustoTopologyQueryDAO(kustoClient));
        this.registerServiceImplementation(IMetricsQueryDAO.class, new KustoMetricsQueryDAO(kustoClient));
        this.registerServiceImplementation(ITraceQueryDAO.class, new KustoTraceQueryDAO(kustoClient));
        this.registerServiceImplementation(IMetadataQueryDAO.class, new KustoMetadataQueryDAO(kustoClient));
        this.registerServiceImplementation(IAggregationQueryDAO.class, new KustoAggregationQueryDAO(kustoClient));
        this.registerServiceImplementation(IAlarmQueryDAO.class, new KustoAlarmQueryDAO(kustoClient));
        this.registerServiceImplementation(IRecordsQueryDAO.class, new KustoRecordsQueryDAO(kustoClient));
        this.registerServiceImplementation(ILogQueryDAO.class, new KustoLogQueryDAO(kustoClient));
        this.registerServiceImplementation(IProfileTaskQueryDAO.class, new KustoProfileTaskQueryDAO(kustoClient));
        this.registerServiceImplementation(IProfileTaskLogQueryDAO.class, new KustoProfileTaskLogQueryDAO(kustoClient));
        this.registerServiceImplementation(
            IProfileThreadSnapshotQueryDAO.class, new KustoProfileThreadSnapshotQueryDAO(kustoClient));
        this.registerServiceImplementation(UITemplateManagementDAO.class, new KustoUITemplateManagementDAO(kustoClient));
        this.registerServiceImplementation(UIMenuManagementDAO.class, new KustoUIMenuManagementDAO(kustoClient));
        this.registerServiceImplementation(IBrowserLogQueryDAO.class, new KustoBrowserLogQueryDAO(kustoClient));
        this.registerServiceImplementation(IEventQueryDAO.class, new KustoEventQueryDAO(kustoClient));
        this.registerServiceImplementation(IEBPFProfilingTaskDAO.class, new KustoEBPFProfilingTaskDAO(kustoClient));
        this.registerServiceImplementation(IEBPFProfilingScheduleDAO.class, new KustoEBPFProfilingScheduleDAO(kustoClient));
        this.registerServiceImplementation(IEBPFProfilingDataDAO.class, new KustoEBPFProfilingDataDAO(kustoClient));
        this.registerServiceImplementation(
            IContinuousProfilingPolicyDAO.class, new KustoContinuousProfilingPolicyDAO(kustoClient));
        this.registerServiceImplementation(IServiceLabelDAO.class, new KustoServiceLabelDAO(kustoClient));
        this.registerServiceImplementation(ITagAutoCompleteQueryDAO.class, new KustoTagAutoCompleteQueryDAO(kustoClient));
        this.registerServiceImplementation(IZipkinQueryDAO.class, new KustoZipkinQueryDAO(kustoClient));
        this.registerServiceImplementation(ISpanAttachedEventQueryDAO.class, new KustoSpanAttachedEventQueryDAO(kustoClient));
        this.registerServiceImplementation(IHierarchyQueryDAO.class, new KustoHierarchyQueryDAO(kustoClient));
        this.registerServiceImplementation(IAsyncProfilerTaskQueryDAO.class, new KustoAsyncProfilerTaskQueryDAO(kustoClient));
        this.registerServiceImplementation(
            IAsyncProfilerTaskLogQueryDAO.class, new KustoAsyncProfilerTaskLogQueryDAO(kustoClient));
        this.registerServiceImplementation(IJFRDataQueryDAO.class, new KustoJFRDataQueryDAO(kustoClient));
        this.registerServiceImplementation(IPprofTaskQueryDAO.class, new KustoPprofTaskQueryDAO(kustoClient));
        this.registerServiceImplementation(IPprofTaskLogQueryDAO.class, new KustoPprofTaskLogQueryDAO(kustoClient));
        this.registerServiceImplementation(IPprofDataQueryDAO.class, new KustoPprofDataQueryDAO(kustoClient));
        this.registerServiceImplementation(StorageTTLStatusQuery.class, new DefaultStorageTTLStatusQuery());
    }

    @Override
    public void start() throws ServiceNotProvidedException, ModuleStartException {
        final MetricsCreator metricsCreator = getManager()
            .find(TelemetryModule.NAME)
            .provider()
            .getService(MetricsCreator.class);
        final HealthCheckMetrics healthChecker = metricsCreator.createHealthCheckerGauge(
            "storage_kusto", MetricsTag.EMPTY_KEY, MetricsTag.EMPTY_VALUE);
        kustoClient.registerChecker(healthChecker);

        try {
            kustoClient.connect();
            modelInstaller.start();

            getManager()
                .find(CoreModule.NAME)
                .provider()
                .getService(ModelCreator.class)
                .addModelListener(modelInstaller);
        } catch (Exception e) {
            throw new ModuleStartException("Failed to connect to Kusto cluster: " + config.getClusterUri(), e);
        }
    }

    @Override
    public void notifyAfterCompleted() throws ServiceNotProvidedException, ModuleStartException {
    }

    @Override
    public String[] requiredModules() {
        return new String[] {CoreModule.NAME, TelemetryModule.NAME};
    }
}
