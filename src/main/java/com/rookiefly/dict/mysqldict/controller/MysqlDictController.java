package com.rookiefly.dict.mysqldict.controller;

import com.rookiefly.dict.mysqldict.config.DynamicDataSourceContextHolder;
import com.rookiefly.dict.mysqldict.model.ColumnDict;
import com.rookiefly.dict.mysqldict.model.TableDict;
import com.rookiefly.dict.mysqldict.param.DynamicDataSourceParam;
import com.rookiefly.dict.mysqldict.service.MysqlDictService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MysqlDictController {

    @Autowired
    private Configuration cfg;

    @Autowired
    private MysqlDictService mysqlDictService;

    /**
     * 下载md字典文件
     *
     * @param request
     * @param response
     */
    @GetMapping("/dict.md")
    public void downloadMarkdown(@RequestParam(required = false) String schemaKey, HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=dict.md");
        FileInputStream fis = null;
        Map root = new HashMap();
        String schema = DynamicDataSourceContextHolder.getDataSourceKey();
        if (StringUtils.isNotBlank(schemaKey)) {
            schema = schemaKey;
            DynamicDataSourceContextHolder.setDataSourceKey(schemaKey);
        }
        List<TableDict> tables = mysqlDictService.queryMysqlDictBySchema(schema);
        root.put("tables", tables);
        root.put("schema", schema);
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

    /**
     * html字典页面预览
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/dict.html")
    public String liveHtml(@RequestParam(required = false) String schemaKey, ModelMap modelMap) {
        String schema = DynamicDataSourceContextHolder.getDataSourceKey();
        if (StringUtils.isNotBlank(schemaKey)) {
            schema = schemaKey;
            DynamicDataSourceContextHolder.setDataSourceKey(schemaKey);
        }
        List<TableDict> tables = mysqlDictService.queryMysqlDictBySchema(schema);
        modelMap.addAttribute("tables", tables);
        modelMap.addAttribute("schema", schema);
        return "dict";
    }

    /**
     * html字典页面预览
     *
     * @param modelMap
     * @return
     */
    @PostMapping("/dict.html")
    public String liveHtmlDynamic(@RequestBody DynamicDataSourceParam dynamicDataSourceParam, ModelMap modelMap) {
        String schema = dynamicDataSourceParam.getSchema();
        List<TableDict> tables = mysqlDictService.queryMysqlDictBySchema(schema);
        modelMap.addAttribute("tables", tables);
        modelMap.addAttribute("schema", schema);
        return "dict";
    }

    private List<TableDict> buildTestData() {
        List<TableDict> tables = new ArrayList<>();
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
}
