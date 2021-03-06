package com.misakamikoto.layout.contents.model;

import lombok.Data;

/**
 * Created by Misaka on 2016-03-16.
 */
@Data
public class ContentsVO {
    private int id;
    private int codeId;
    private String subject;
    private String text;
    private String viewUrl;
    private String uploadedId;
}
