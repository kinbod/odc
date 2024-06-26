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

package com.oceanbase.odc.service.datatransfer;

import static com.oceanbase.odc.service.datatransfer.task.DataTransferTask.OUTPUT_FILTER_FILES;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.oceanbase.odc.plugin.task.api.datatransfer.dumper.ExportOutput;
import com.oceanbase.odc.plugin.task.api.datatransfer.model.DataTransferConfig;
import com.oceanbase.odc.plugin.task.api.datatransfer.model.DataTransferTaskResult;
import com.oceanbase.odc.service.objectstorage.cloud.CloudObjectStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultDataTransferAdapter implements DataTransferAdapter {

    @Autowired
    private CloudObjectStorageService cloudObjectStorageService;

    @Override
    public Long getMaxDumpSizeBytes() {
        return 2 * 1024 * 1024 * 1024L;
    }

    @Override
    public File preHandleWorkDir(DataTransferConfig config, String bucket, File workDir) throws IOException {
        return workDir;
    }

    @Override
    public void afterHandle(DataTransferConfig config, DataTransferTaskResult result, File exportFile)
            throws IOException {
        File dest = exportFile;
        if (exportFile.isDirectory()) {
            File workingDir = exportFile.getParentFile();
            dest = new File(workingDir.getPath() + File.separator + workingDir.getName() + "_export_file.zip");
            result.setExportZipFilePath(dest.getName());
            new ExportOutput(exportFile).toZip(dest, file -> !OUTPUT_FILTER_FILES.contains(file.getFileName()));
            FileUtils.deleteQuietly(exportFile);
        }
        result.setExportZipFilePath(dest.getName());

        if (cloudObjectStorageService.supported()) {
            String objectName = cloudObjectStorageService.uploadTemp(dest.getName(), dest);
            log.info("Upload the data file to the oss successfully, objectName={}", objectName);
            FileUtils.deleteQuietly(dest);
            result.setExportZipFilePath(objectName);
        }
    }

}

