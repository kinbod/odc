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
package com.oceanbase.odc.service.iam.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BatchImportUser extends CreateUserReq {

    @JsonProperty(access = Access.READ_WRITE)
    private String password;

    private List<String> roleNames;

    private String errorMessage;

    public static BatchImportUser ofFail(String errorMessage) {
        BatchImportUser batchImportUser = new BatchImportUser();
        batchImportUser.setErrorMessage(errorMessage);
        return batchImportUser;
    }

}
