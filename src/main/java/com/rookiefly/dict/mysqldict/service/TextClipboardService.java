package com.rookiefly.dict.mysqldict.service;

public interface TextClipboardService {

    /**
     * 保存文本到数据库
     * @param text
     * @return
     */
    String saveText(String text);

    /**
     * 根据文本ID（文本hash）查询文本内容
     * @param textId
     * @return
     */
    String queryText(String textId);
}
