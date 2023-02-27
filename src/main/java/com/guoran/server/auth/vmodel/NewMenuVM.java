package com.guoran.server.auth.vmodel;

import lombok.Data;

import java.util.List;

@Data
public class NewMenuVM {

    /**
     * 自增Id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 上级菜单
     */
    private Long parentId;

    /**
     * 菜单排序
     */
    private Long sortId;
    /**
     * 接口url
     */
    private String generalUrl;
    // 子菜单
    private List<NewMenuVM> children;
}
