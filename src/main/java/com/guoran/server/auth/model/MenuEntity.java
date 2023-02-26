package com.guoran.server.auth.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuEntity extends BaseOfConcurrencySafeEntity {
    /**
     * 自增Id
     */
    private Long id;

    /**
     * 菜单名称menu_enname
     */
    private String menuName;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 菜单icon
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
     * 菜单顺序
     */
    private Long sortId;

    /**
     * 是否删除
     */
    private boolean isDelete;

    // 子菜单
    private List<MenuEntity> childMenus;

    /**
     * 接口路径
     */
    private String generalUrl;
}
