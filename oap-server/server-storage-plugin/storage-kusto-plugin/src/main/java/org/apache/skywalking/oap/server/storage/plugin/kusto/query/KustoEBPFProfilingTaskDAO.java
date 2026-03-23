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
import org.apache.skywalking.oap.server.core.profiling.ebpf.storage.EBPFProfilingTargetType;
import org.apache.skywalking.oap.server.core.profiling.ebpf.storage.EBPFProfilingTaskRecord;
import org.apache.skywalking.oap.server.core.profiling.ebpf.storage.EBPFProfilingTriggerType;
import org.apache.skywalking.oap.server.core.storage.profiling.ebpf.IEBPFProfilingTaskDAO;

public class KustoEBPFProfilingTaskDAO implements IEBPFProfilingTaskDAO {
    private final KustoClient kustoClient;

    public KustoEBPFProfilingTaskDAO(final KustoClient kustoClient) {
        this.kustoClient = kustoClient;
    }

    @Override
    public List<EBPFProfilingTaskRecord> queryTasksByServices(List<String> serviceIdList,
                                                              EBPFProfilingTriggerType triggerType,
                                                              long taskStartTime,
                                                              long latestUpdateTime) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto EBPF queryTasksByServices");
    }

    @Override
    public List<EBPFProfilingTaskRecord> queryTasksByTargets(String serviceId,
                                                             String serviceInstanceId,
                                                             List<EBPFProfilingTargetType> targetTypes,
                                                             EBPFProfilingTriggerType triggerType,
                                                             long taskStartTime,
                                                             long latestUpdateTime) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto EBPF queryTasksByTargets");
    }

    @Override
    public List<EBPFProfilingTaskRecord> getTaskRecord(String id) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto EBPF getTaskRecord");
    }
}
