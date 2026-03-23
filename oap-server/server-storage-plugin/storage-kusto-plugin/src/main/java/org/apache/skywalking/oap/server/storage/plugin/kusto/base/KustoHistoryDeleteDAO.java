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

package org.apache.skywalking.oap.server.storage.plugin.kusto.base;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.oap.server.core.storage.IHistoryDeleteDAO;
import org.apache.skywalking.oap.server.core.storage.model.Model;
import org.apache.skywalking.oap.server.storage.plugin.kusto.KustoClient;

@Slf4j
@RequiredArgsConstructor
public class KustoHistoryDeleteDAO implements IHistoryDeleteDAO {
    private final KustoClient kustoClient;

    @Override
    public void deleteHistory(final Model model,
                              final String timeBucketColumnName,
                              final int ttl) throws IOException {
        // TODO: Implement data retention via Kusto's native retention policy:
        //   .alter table <tableName> policy retention '{"SoftDeletePeriod":"<ttl>d:00:00:00"}'
        // Alternatively, use .drop extents to purge old data:
        //   .drop extents <| .show table <tableName> extents where MaxCreatedOn < ago(<ttl>d)
        log.info("History delete requested for table {} with TTL {} days", model.getName(), ttl);
    }
}
