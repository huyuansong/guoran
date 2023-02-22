package com.hisoft.pam.im.common.search;

import com.hisoft.pam.im.common.utils.StringUtils;

import java.util.List;

/**
 * @description: 动态查询
 * @author: machao
 * @create: 2019-12-19 17:42
 * @Modify By
 **/
public class DynamicSearch {

    private static ThreadLocal<DynamicSearch> dynamicSearchThreadLocal = new ThreadLocal<DynamicSearch>(){
        public DynamicSearch initialValue(){
            return new DynamicSearch();
        }
    };
    public static DynamicSearch getInstance(){
        return dynamicSearchThreadLocal.get();
    }
    private DynamicSearch(){

    }
    /**
     *  filterGroup自动转查询字符串
     * @param filterGroup
     * @return
     */
    public String buildWhere(FilterGroup filterGroup){
        if(filterGroup==null || filterGroup.getRules() == null){
            return "";
        }
        StringBuilder whereBuilder = new StringBuilder();
        String op = filterGroup.getOp();
        if(StringUtils.isEmpty(op)){
            op = "and";
        }
        List<FilterRule> filterRuleList =  filterGroup.getRules();
        for (FilterRule filterRule : filterRuleList  ) {
            parseFilterRule(whereBuilder, op, filterRule);
        }

        return whereBuilder.toString();
    }

    /**
     *  filterRuleList自动转查询字符串
     * @param op
     * @param filterRuleList
     * @return
     */
    public String buildWhere( String op, List<FilterRule> filterRuleList){
        if(StringUtils.isEmpty(op)){
            op = "and";
        }
        StringBuilder whereBuilder = new StringBuilder();
        for (FilterRule filterRule : filterRuleList  ) {
            parseFilterRule(whereBuilder, op, filterRule);
        }
        return whereBuilder.toString();
    }
    private void parseFilterRule(StringBuilder whereBuilder, String op, FilterRule filterRule) {
        FileterOperatorEnum ruleOp = filterRule.getOp();
        if(ruleOp==null){
            ruleOp=FileterOperatorEnum.LIKE;
        }
        //驼峰转下划线
        String field = StringUtils.camelToUnderline(filterRule.getField());
        Object value = filterRule.getValue();
        String valueType = filterRule.getType();
        if(StringUtils.isEmpty(valueType)){
            valueType="string";
        }
        String ruleResult = null;
        switch (ruleOp) {
            case LIKE:
            case STARTWITH:
            case ENDWITH:
                ruleResult = StringUtils.format(ruleOp.getValue(),value);
                whereBuilder.append(" "+op + " "+ field + ruleResult);
                break;
            case EQUAL:
            case NOTEQUALEQUAL:
            case GREATER:
            case GREATEROREQUAL:
            case LESS:
            case LESSOREQUAL:
                if(!StringUtils.isEmpty(valueType)){
                    String lowerCaseType = StringUtils.lowerCase(valueType);
                    if(lowerCaseType.equals("string")||lowerCaseType.equals("date")){
                        value="'"+value+"'";
                    }
                }
                whereBuilder.append(" "+op + " "+ field +ruleOp.getValue()+ value);
                break;
            case BETWEEN:
                if(!StringUtils.isEmpty(valueType)){
                    String lowerCaseType = StringUtils.lowerCase(valueType);
                    if(lowerCaseType.equals("date")){
                        value="'"+StringUtils.replace(value.toString(),",","','")+"'";
                    }
                }
                ruleResult = StringUtils.format(ruleOp.getValue(),value.toString().split(","));
                whereBuilder.append(" "+op + " "+ field +" "+ruleResult);
                break;
            case IN:
            case NOTIN:
                if(!StringUtils.isEmpty(valueType)){
                    String lowerCaseType = StringUtils.lowerCase(valueType);
                    if(lowerCaseType.equals("string")||lowerCaseType.equals("date")){
                        value="'"+StringUtils.replace(value.toString(),",","','")+"'";
                    }
                }
                ruleResult = StringUtils.format(ruleOp.getValue(),value);
                whereBuilder.append(" "+op + " "+ field +ruleResult);
                break;
            default:
                break;
        }
    }
}
