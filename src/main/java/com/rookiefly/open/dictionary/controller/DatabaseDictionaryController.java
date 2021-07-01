package com.rookiefly.open.dictionary.controller;

import com.rookiefly.open.dictionary.common.CommonResponse;
import com.rookiefly.open.dictionary.database.DBMetadataHolder;
import com.rookiefly.open.dictionary.database.DatabaseConfig;
import com.rookiefly.open.dictionary.database.DefaultDataSource;
import com.rookiefly.open.dictionary.database.Dialect;
import com.rookiefly.open.dictionary.database.IntrospectedTable;
import com.rookiefly.open.dictionary.param.DataSourceParam;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rookiefly
 */
@Slf4j
@Controller
public class DatabaseDictionaryController {

    @Resource
    private Configuration cfg;

    /**
     * 下载md字典文件
     *
     * @param dataSourceParam
     */
    @GetMapping("/export/dictionary.md")
    public void exportDictionaryMarkdown(@Validated DataSourceParam dataSourceParam, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, TemplateException {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=dictionary.md");
        DefaultDataSource dataSource = new DefaultDataSource(
                Dialect.valueOf(dataSourceParam.getDialect().toUpperCase()),
                dataSourceParam.getHost(),
                dataSourceParam.getPort(),
                dataSourceParam.getSchema(),
                dataSourceParam.getUser(),
                dataSourceParam.getPassword()
        );

        DBMetadataHolder dbMetadataHolder = new DBMetadataHolder(dataSource);
        DatabaseConfig databaseConfig = dbMetadataHolder.getDefaultConfig();
        List<IntrospectedTable> introspectedTableList = dbMetadataHolder.introspectTables(databaseConfig);
        Map root = new HashMap();
        root.put("tables", introspectedTableList);
        root.put("schema", databaseConfig.getCatalog());
        Template temp = cfg.getTemplate("md.html");
        Writer out = new OutputStreamWriter(response.getOutputStream());
        temp.process(root, out);
        response.flushBuffer();
    }

    /**
     * html字典页面预览
     *
     * @param dataSourceParam
     * @return
     */
    @GetMapping("/view/dictionary.html")
    public String viewDictionaryHtml(@Validated DataSourceParam dataSourceParam, ModelMap modelMap) throws SQLException {
        DefaultDataSource dataSource = new DefaultDataSource(
                Dialect.valueOf(dataSourceParam.getDialect().toUpperCase()),
                dataSourceParam.getHost(),
                dataSourceParam.getPort(),
                dataSourceParam.getSchema(),
                dataSourceParam.getUser(),
                dataSourceParam.getPassword()
        );
        DBMetadataHolder dbMetadataHolder = new DBMetadataHolder(dataSource);
        DatabaseConfig databaseConfig = dbMetadataHolder.getDefaultConfig();
        List<IntrospectedTable> introspectedTableList = dbMetadataHolder.introspectTables(databaseConfig);
        modelMap.addAttribute("tables", introspectedTableList);
        modelMap.addAttribute("schema", databaseConfig.getCatalog());
        return "dictionary";
    }

    /**
     * html字典页面预览
     *
     * @param dataSourceParam
     * @return
     */
    @PostMapping("/data/dictionary.json")
    @ResponseBody
    public CommonResponse dictionaryJson(@RequestBody @Validated DataSourceParam dataSourceParam) throws SQLException {
        DefaultDataSource dataSource = new DefaultDataSource(
                Dialect.valueOf(dataSourceParam.getDialect()),
                dataSourceParam.getUrl(),
                dataSourceParam.getUser(),
                dataSourceParam.getPassword()
        );

        DBMetadataHolder dbMetadataHolder = new DBMetadataHolder(dataSource);
        List<IntrospectedTable> introspectedTableList = dbMetadataHolder.introspectTables(dbMetadataHolder.getDefaultConfig());
        return CommonResponse.newSuccessResponse(introspectedTableList);
    }
}
