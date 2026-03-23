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
import org.apache.skywalking.oap.server.core.query.type.ProfileTask;
import org.apache.skywalking.oap.server.core.storage.profiling.trace.IProfileTaskQueryDAO;

public class KustoProfileTaskQueryDAO implements IProfileTaskQueryDAO {
    private final KustoClient kustoClient;

    public KustoProfileTaskQueryDAO(final KustoClient kustoClient) {
        this.kustoClient = kustoClient;
    }

    @Override
    public List<ProfileTask> getTaskList(final String serviceId,
                                         final String endpointName,
                                         final Long startTimeBucket,
                                         final Long endTimeBucket,
                                         final Integer limit) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto profile task getTaskList");
    }

    @Override
    public ProfileTask getById(final String id) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto profile task getById");
    }
}
