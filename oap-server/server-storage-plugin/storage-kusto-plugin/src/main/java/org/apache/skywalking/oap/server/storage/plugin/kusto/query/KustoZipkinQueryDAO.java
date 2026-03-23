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
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.skywalking.oap.server.core.query.input.Duration;
import org.apache.skywalking.oap.server.core.storage.query.IZipkinQueryDAO;
import zipkin2.Span;
import zipkin2.storage.QueryRequest;

public class KustoZipkinQueryDAO implements IZipkinQueryDAO {
    private final KustoClient kustoClient;

    public KustoZipkinQueryDAO(final KustoClient kustoClient) {
        this.kustoClient = kustoClient;
    }

    @Override
    public List<String> getServiceNames() throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto Zipkin getServiceNames");
    }

    @Override
    public List<String> getRemoteServiceNames(final String serviceName) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto Zipkin getRemoteServiceNames");
    }

    @Override
    public List<String> getSpanNames(final String serviceName) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto Zipkin getSpanNames");
    }

    @Override
    public List<Span> getTrace(final String traceId,
                               @Nullable final Duration duration) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto Zipkin getTrace");
    }

    @Override
    public List<List<Span>> getTraces(final QueryRequest request,
                                      final Duration duration) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto Zipkin getTraces by request");
    }

    @Override
    public List<List<Span>> getTraces(final Set<String> traceIds,
                                      @Nullable final Duration duration) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto Zipkin getTraces by traceIds");
    }
}
