/*
 * Copyright (c) 2023 OceanBase.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.plugin.schema.oboracle.browser;

import org.springframework.jdbc.core.JdbcOperations;

import com.oceanbase.odc.common.util.VersionUtils;
import com.oceanbase.tools.dbbrowser.schema.DBSchemaAccessor;
import com.oceanbase.tools.dbbrowser.schema.oracle.OBOracleLessThan2270SchemaAccessor;
import com.oceanbase.tools.dbbrowser.schema.oracle.OBOracleLessThan400SchemaAccessor;
import com.oceanbase.tools.dbbrowser.schema.oracle.OBOracleSchemaAccessor;
import com.oceanbase.tools.dbbrowser.util.ALLDataDictTableNames;

import lombok.NonNull;

/**
 * @author jingtian
 * @date 2023/6/29
 * @since 4.2.0
 */
public class DBSchemaAccessors {

    public static DBSchemaAccessor create(@NonNull JdbcOperations jdbcOperations, @NonNull String dbVersion) {
        if (VersionUtils.isGreaterThanOrEqualsTo(dbVersion, "4.0.0")) {
            // OB 版本 >= 4.0.0
            return new OBOracleSchemaAccessor(jdbcOperations, new ALLDataDictTableNames());
        } else if (VersionUtils.isGreaterThanOrEqualsTo(dbVersion, "2.2.70")) {
            // OB 版本为 [2.2.7, 4.0.0)
            return new OBOracleLessThan400SchemaAccessor(jdbcOperations, new ALLDataDictTableNames());
        } else {
            // OB 版本 < 2.2.70
            return new OBOracleLessThan2270SchemaAccessor(jdbcOperations, new ALLDataDictTableNames());
        }
    }
}
