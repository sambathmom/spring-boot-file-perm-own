package com.example.springbootfilepermown.service;

import com.example.springbootfilepermown.helper.FilerHelper;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilePermissionService {
    private Logger logger = LogManager.getLogger(FilePermissionService.class);

    private final FilerHelper filerHelper;

    public FilePermissionService(FilerHelper filerHelper) {
        this.filerHelper = filerHelper;
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void setFilePermission() {
        try {
            logger.info("----------------  Start getting and writing file ----------------");
            String filePath = "/opt/export_file/";
            //filePath = "E:/export_file/";
            String fileName = filePath + "text_file.csv";
            File file = new File(fileName);

            List<String[]> content = new ArrayList<>();
            String[] content1 = new String[]{"1", "THA1", "098766631"};
            String[] content2 = new String[]{"2", "THA2", "098766632"};
            String[] content3 = new String[]{"3", "THA3", "098766633"};
            content.add(content1);
            content.add(content2);
            content.add(content3);

            logger.info("============= Start writing csv file with file name : " + fileName);
            CSVWriter writer = new CSVWriter(new FileWriter(file), '|',
                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(content, false);
            writer.flush();
            logger.info("============= Ending writing csv file.");

            //======== Set  file permission
            logger.info("Set file permission");
            filerHelper.setPermission(file);

            //======== Set  file owner (user and group)
           /* logger.info("Change ownership");
            filerHelper.changeOwnership(file, "nobody","nogroup");*/

            logger.info("---------------- Ending getting and writing file -------------");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("============= IOException Writing file with error:  " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("============= Writing file with error:  " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
