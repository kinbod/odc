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
package com.oceanbase.odc.service.flow.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;

/**
 *
 * @author yaobin
 * @date 2024-02-29
 * @since 4.2.4
 */
public class ExecutionEntityFacade extends ExecutionEntityImpl {

    public ExecutionEntityFacade(DelegateExecution execution) {
        execution.getVariables().forEach(this::setVariable);
        execution.getVariablesLocal().forEach(this::setVariableLocal);
        execution.getTransientVariables().forEach(this::setTransientVariable);
        execution.getTransientVariablesLocal().forEach(this::setTransientVariableLocal);

        this.id = execution.getId();
        this.activityId = execution.getCurrentActivityId();
        this.rootProcessInstanceId = execution.getRootProcessInstanceId();
        this.processDefinitionId = execution.getProcessDefinitionId();
        this.processInstanceId = execution.getProcessInstanceId();
        this.eventName = execution.getEventName();
    }
}
