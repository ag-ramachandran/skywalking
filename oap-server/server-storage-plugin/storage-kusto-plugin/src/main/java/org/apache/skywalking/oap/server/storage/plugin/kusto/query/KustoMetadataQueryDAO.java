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

package org.apache.skywalking.oap.server.storage.plugin.kusto.query;

import org.apache.skywalking.oap.server.storage.plugin.kusto.KustoClient;

import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.skywalking.oap.server.core.query.enumeration.ProfilingSupportStatus;
import org.apache.skywalking.oap.server.core.query.input.Duration;
import org.apache.skywalking.oap.server.core.query.type.Endpoint;
import org.apache.skywalking.oap.server.core.query.type.Process;
import org.apache.skywalking.oap.server.core.query.type.Service;
import org.apache.skywalking.oap.server.core.query.type.ServiceInstance;
import org.apache.skywalking.oap.server.core.storage.query.IMetadataQueryDAO;

public class KustoMetadataQueryDAO implements IMetadataQueryDAO {
    private final KustoClient kustoClient;

    public KustoMetadataQueryDAO(final KustoClient kustoClient) {
        this.kustoClient = kustoClient;
    }

    @Override
    public List<Service> listServices() throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto listServices");
    }

    @Override
    public List<ServiceInstance> listInstances(@Nullable final Duration duration,
                                               final String serviceId) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto listInstances");
    }

    @Override
    public ServiceInstance getInstance(final String instanceId) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto getInstance");
    }

    @Override
    public List<ServiceInstance> getInstances(final List<String> instanceIds) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto getInstances");
    }

    @Override
    public List<Endpoint> findEndpoint(final String keyword,
                                       final String serviceId,
                                       final int limit,
                                       final Duration duration) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto findEndpoint");
    }

    @Override
    public List<Process> listProcesses(final String serviceId,
                                       final ProfilingSupportStatus supportStatus,
                                       final long lastPingStartTimeBucket,
                                       final long lastPingEndTimeBucket) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto listProcesses by service");
    }

    @Override
    public List<Process> listProcesses(final String serviceInstanceId,
                                       final Duration duration,
                                       boolean includeVirtual) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto listProcesses by instance");
    }

    @Override
    public List<Process> listProcesses(final String agentId,
                                       long startPingTimeBucket,
                                       long endPingTimeBucket) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto listProcesses by agent");
    }

    @Override
    public long getProcessCount(final String serviceId,
                                final ProfilingSupportStatus profilingSupportStatus,
                                final long lastPingStartTimeBucket,
                                final long lastPingEndTimeBucket) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto getProcessCount by service");
    }

    @Override
    public long getProcessCount(final String instanceId) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto getProcessCount by instance");
    }

    @Override
    public Process getProcess(final String processId) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto getProcess");
    }
}
