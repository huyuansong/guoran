package com.guoran.server.common.utils;

import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.FilterRule;
import com.guoran.server.common.search.PageQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangmengyu
 * @create 2020/10/22 11:06
 * @Modify By
 */
public class CheckInputUtil {

    public boolean chikcInput(PageQuery pageQuery) {
        List<Boolean> booleans = new ArrayList<>();
        if (pageQuery.getWhere() != null) {
            FilterGroup filterGroup = pageQuery.getWhere();
            List<FilterRule> filterRuleList = filterGroup.getRules();
            for (FilterRule filterRule : filterRuleList) {
                String value = filterRule.getValue().toString();
                String regEx = "[_`~!@#$%^&*()+=|{}';'\\[\\]<>?~！@#￥%……&*（）——+|{}【】《》‘；：”“’。，\\\\、？]|\n|\r|\t";
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
}
