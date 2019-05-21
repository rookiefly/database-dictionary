package com.rookiefly.dict.mysqldict.controller;

import com.rookiefly.dict.mysqldict.model.ColumnDict;
import com.rookiefly.dict.mysqldict.model.TableDict;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MysqlDictController {

    @Autowired
    private Configuration cfg;

    @GetMapping("/dict/md")
    public void downloadMarkdown(HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=dict.md");
        FileInputStream fis = null;
        Map root = new HashMap();
        ArrayList<TableDict> tables = buildTestData();
        root.put("tables", tables);
        root.put("dbname", "dict");
        try {
            Template temp = cfg.getTemplate("md.html");
            Writer out = new OutputStreamWriter(response.getOutputStream());
            temp.process(root, out);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList<TableDict> buildTestData() {
        ArrayList<TableDict> tables = new ArrayList<>();
        TableDict table = new TableDict();
        table.setTableName("dict-type");
        table.setTableComment("字典类型");
        ArrayList<ColumnDict> columns = new ArrayList<>();
        ColumnDict column = new ColumnDict();
        column.setColumnName("id");
        column.setColumnType("int");
        column.setNullable("否");
        column.setAutoIncrement("是");
        columns.add(column);
        table.setColumns(columns);
        tables.add(table);
        return tables;
    }

    @GetMapping("/dict/html")
    public String liveHtml(ModelMap modelMap) {
        ArrayList<TableDict> tables = buildTestData();
        modelMap.addAttribute("tables", tables);
        modelMap.addAttribute("dbname", "dict");
        return "dict";
    }

}
