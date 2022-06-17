package com.boot.jjrepository.hat.fast.parse.vo;

/**
 * @Description
 * @author AbrahamVong
 * @since 17/6/2022
 */
public class PreStructVo {

    private String template;

    private String[] filePath;

    public PreStructVo(String template, String[] filePath) {
        this.template = template;
        this.filePath = filePath;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String[] getFilePath() {
        return filePath;
    }

    public void setFilePath(String[] filePath) {
        this.filePath = filePath;
    }
}
