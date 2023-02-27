package com.guoran.server.he.production.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @time 2023/2/2414:29
 * @outhor zhou
 */
public interface ManagementAnnualService {

    void explort(Long[] ids, HttpServletResponse response, HttpServletRequest request);
}
