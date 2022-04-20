package com.polaris.exam.dto.message;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class MessagePageRequest {
    private String sendUserName;
    private Integer page;
    private Integer limit;
    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
