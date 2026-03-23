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
import org.apache.skywalking.oap.server.core.query.input.DashboardSetting;
import org.apache.skywalking.oap.server.core.query.type.DashboardConfiguration;
import org.apache.skywalking.oap.server.core.query.type.TemplateChangeStatus;
import org.apache.skywalking.oap.server.core.storage.management.UITemplateManagementDAO;

public class KustoUITemplateManagementDAO implements UITemplateManagementDAO {
    private final KustoClient kustoClient;

    public KustoUITemplateManagementDAO(final KustoClient kustoClient) {
        this.kustoClient = kustoClient;
    }

    @Override
    public DashboardConfiguration getTemplate(String id) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto UI template getTemplate");
    }

    @Override
    public List<DashboardConfiguration> getAllTemplates(Boolean includingDisabled) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto UI template getAllTemplates");
    }

    @Override
    public TemplateChangeStatus addTemplate(DashboardSetting setting) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto UI template addTemplate");
    }

    @Override
    public TemplateChangeStatus changeTemplate(DashboardSetting setting) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto UI template changeTemplate");
    }

    @Override
    public TemplateChangeStatus disableTemplate(String id) throws IOException {
        throw new UnsupportedOperationException("TODO: Implement Kusto UI template disableTemplate");
    }
}
