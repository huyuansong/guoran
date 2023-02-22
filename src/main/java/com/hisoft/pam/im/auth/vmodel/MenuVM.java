package com.hisoft.pam.im.auth.vmodel;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MenuVM {

    /**
     * 自增Id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 菜单URL
     */
    private String icon;

    /**
     * 菜单URL
     */
    private String menuLink;

    /**
     * 上级菜单
     */
    private Long parentId;


    /**
     * 菜单排序
     */
    private Long sortId;

    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 修改人
     */
    private String modifier;

    /**
     * 是否删除
     */
    private boolean isDelete;


    // 子菜单
    private List<MenuVM> childMenus;

    /**
     * 接口路径
     */
    private String generalUrl;
}
