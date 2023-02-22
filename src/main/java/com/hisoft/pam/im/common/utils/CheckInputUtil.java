package com.hisoft.pam.im.common.utils;

import com.google.common.primitives.Booleans;
import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.common.search.FilterGroup;
import com.hisoft.pam.im.common.search.FilterRule;
import com.hisoft.pam.im.common.search.PageQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author zhangmengyu
 * @create 2020/10/22 11:06
 * @Modify By
 */
public class CheckInputUtil {

    public boolean chikcInput(PageQuery pageQuery) {
        List<Boolean> booleans = new ArrayList<>();
        if(pageQuery.getWhere()!=null) {
            FilterGroup filterGroup = pageQuery.getWhere();
            List<FilterRule> filterRuleList = filterGroup.getRules();
            List<FilterRule> collect = filterRuleList.stream().filter(x -> x.getValue() != null).collect(Collectors.toList());
            filterGroup.setRules(collect);
            pageQuery.setWhere(filterGroup);
            if (collect != null) {
                for (FilterRule filterRule : collect) {
                    String value = filterRule.getValue().toString();
                    String regEx = "[_`~!@#$%^&*+=|{}';'\\[\\]<>?~！@#￥%……&*——+|{}【】《》‘；：”“’。，\\\\、？]|\n|\r|\t";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(value);
                    if (m.find()) {
                        booleans.add(true);
                    } else {
                        booleans.add(false);
                    }
                }
                if (pageQuery.getOrderBy() != null) {
                    String regOrd = "[`~!@#$%^&*()+=|{}';'\\[\\]<>?~！@#￥%……&*（）——+|{}【】《》‘；：”“’。，\\\\、？]|\n|\r|\t";
                    Pattern p = Pattern.compile(regOrd);
                    Matcher m = p.matcher(pageQuery.getOrderBy());
                    if (m.find()) {
                        booleans.add(true);
                    } else {
                        booleans.add(false);
                    }
                }
            }
            if (booleans.contains(true)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
