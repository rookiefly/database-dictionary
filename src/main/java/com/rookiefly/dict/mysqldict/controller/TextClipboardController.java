package com.rookiefly.dict.mysqldict.controller;

import com.rookiefly.dict.mysqldict.config.DataSourceKey;
import com.rookiefly.dict.mysqldict.config.DynamicDataSourceContextHolder;
import com.rookiefly.dict.mysqldict.service.TextClipboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TextClipboardController {

    private static Logger logger = LoggerFactory.getLogger(TextClipboardController.class);

    @Autowired
    private TextClipboardService textClipboardService;

    @GetMapping("/clipboard.html")
    public String textClipboard() {
        return "clipboard";
    }

    @ResponseBody
    @PostMapping("/clipboard.html")
    public String saveText(@RequestParam String data) {
        logger.info("save input text: {}", data);
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.clipboard.name());
        String hash = textClipboardService.saveText(data);
        return hash;
    }

    @ResponseBody
    @GetMapping("/clipboard/{textId}")
    public String queryText(@PathVariable("textId") String textId) {
        logger.info("query text id: {}", textId);
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.clipboard.name());
        String content = textClipboardService.queryText(textId);
        return content;
    }
}
