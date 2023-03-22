package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.dto.ApiDTO;
import com.globits.da.service.impl.MyFirstApiServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFAutoFilter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/myfirstapi")
public class RestMyFirstAPIController {
    @Autowired
    MyFirstApiServiceImpl myFirstApiService;

    //    String myFirstApi = "Tran Tuan Trong";
    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @GetMapping("/todo")
    public String myFirstAPI() {
        return myFirstApiService.randomString();
    }

//    // request body
//    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
//    @PostMapping("/todo")
//    public ApiDTO postDTO(@RequestBody ApiDTO dto) {
//        return dto;
//    }

    // Request Param
    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping("/todo")
    public ApiDTO postDTO(@ModelAttribute("dto") ApiDTO dto) {
        return dto;
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile[] submissions) {
        for (MultipartFile multipartFile : submissions) {
            if (multipartFile.getOriginalFilename().endsWith(".txt")) {
                try {
                    String content = new String(multipartFile.getBytes(), StandardCharsets.UTF_8);
                    System.out.println(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (multipartFile.getOriginalFilename().endsWith(".xlsx")) {
                try {
                    XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
                    XSSFSheet sheet = workbook.getSheetAt(0);

                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            if (cell.getCellTypeEnum() != CellType.BLANK) {
                                System.out.print(cell.toString() + " ");
                            }
                        }
                        System.out.println();
                    }
//                    int rows = sheet.getPhysicalNumberOfRows();
//                    int cols = sheet.getRow(0).getPhysicalNumberOfCells();
//
//                    for (int r = 0; r < rows; r++) {
//                        Row row = sheet.getRow(r);
//                        for (int c = 0; c < cols; c++) {
//                            if (){
//                                Cell cell = row.getCell(c);
//                                System.out.print(cell.toString() + " ");
//                            }
//
//                        }
//                        System.out.println();
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("tep khong dung dinh dang!");
            }
        }

        return ResponseEntity.ok().build();
    }
}
