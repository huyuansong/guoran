package com.hisoft.pam.im.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangmengyu
 * @create 2020/9/11 9:53
 * @Modify By
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartAndPositonVO {

    private List<DepartmentVO> departmentVOList;

    private List<PositionVO> positionVOList;
}
