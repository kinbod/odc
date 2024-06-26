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
package com.oceanbase.odc.plugin.task.oboracle.partitionplan.mapper;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oceanbase.odc.core.sql.execute.mapper.CellData;
import com.oceanbase.odc.core.sql.execute.mapper.OracleNlsFormatDateMapper;
import com.oceanbase.odc.core.sql.execute.model.TimeFormatResult;
import com.oceanbase.odc.plugin.task.api.partitionplan.datatype.TimeDataType;
import com.oceanbase.odc.plugin.task.obmysql.partitionplan.mapper.CellDataProcessor;
import com.oceanbase.tools.dbbrowser.model.datatype.DataType;

import lombok.NonNull;

/**
 * {@link OBOracleDateTypeProcessor}
 *
 * @author yh263208
 * @date 2024-01-26 14:08
 * @since ODC_release_4.2.4
 * @see CellDataProcessor
 */
public class OBOracleDateTypeProcessor implements CellDataProcessor {

    @Override
    public Object mapCell(@NonNull CellData cellData) throws SQLException {
        return new OracleNlsFormatDateMapper("YYYY-MM-DD HH24:MI:SS").mapCell(cellData);
    }

    @Override
    public String convertToSqlLiteral(Object target, @NonNull DataType dataType) {
        if (target == null) {
            return "NULL";
        }
        if (!(target instanceof TimeFormatResult)) {
            throw new IllegalStateException("Target's type is illegal, actual: " + target.getClass());
        }
        TimeFormatResult result = (TimeFormatResult) target;
        if (result.getTimestamp() == null) {
            throw new IllegalStateException("Illegal mapping data, actual: " + result);
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "TO_DATE(' " + dateFormat.format(new Date(result.getTimestamp()))
                + "', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN')";
    }

    @Override
    public boolean supports(@NonNull DataType dataType) {
        if (!(dataType instanceof TimeDataType)) {
            return false;
        }
        return "date".equalsIgnoreCase(dataType.getDataTypeName());
    }

}
