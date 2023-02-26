package com.guoran.server.common.resource;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhangmengyu
 * @create 2020/12/3 9:35
 * @Modify By
 */
@RestController
public class AuditCheckController {

    public boolean checkAudit(HttpServletRequest request, Integer status, List<String> linkList) {
        if (status == 1 || status == 7 || status == 5 || status == 6 || status == 10) {

            String substring = null;
            try {
                String URI = request.getHeader("Referer");
                int i = URI.lastIndexOf("/");
                substring = URI.substring(i);
            } catch (Exception e) {
                return false;
            }
            return linkList.contains(substring);
        } else {
            return true;
        }
    }
}