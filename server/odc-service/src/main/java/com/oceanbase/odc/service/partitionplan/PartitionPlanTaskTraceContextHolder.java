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
package com.oceanbase.odc.service.partitionplan;

import org.slf4j.MDC;

/**
 * @author yh263208
 * @author tianke
 * @date 2024-02-22 10:25
 * @since ODC_release_4.2.4
 */
public final class PartitionPlanTaskTraceContextHolder {

    public static final String TASK_ID = "taskId";

    private PartitionPlanTaskTraceContextHolder() {}

    public static void trace(long taskId) {
        MDC.put(TASK_ID, String.valueOf(taskId));
    }

    public static void clear() {
        MDC.remove(TASK_ID);
    }

}
