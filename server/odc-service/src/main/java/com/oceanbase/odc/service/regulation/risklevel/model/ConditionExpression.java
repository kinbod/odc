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
package com.oceanbase.odc.service.regulation.risklevel.model;

/**
 * @Author: Lebie
 * @Date: 2023/8/4 16:17
 * @Description: []
 */
public enum ConditionExpression {
    PROJECT_NAME,
    ENVIRONMENT_ID,
    ENVIRONMENT_NAME,
    TASK_TYPE,
    DATABASE_NAME,
    SQL_CHECK_RESULT
}
