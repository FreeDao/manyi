package com.manyi.iw.agent.console.base.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.manyi.iw.agent.console.entity.Agent;

@Data
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private String rootUrl;

    @Autowired
    private RestTemplate restTemplate;

    private @Value("#{config_properties.AGENT_SOA_URL}")
    String AGENT_SOA_URL;

    public BaseController() {
        rootUrl = AGENT_SOA_URL;
    }

    /**
     * 功能描述:从session中获取Agent
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月21日      新建
     * </pre>
     *
     * @param session
     * @return
     */
    protected Agent getAgent(HttpSession session) {
        return (Agent) session.getAttribute("agent");
    }

    /**
     * 功能描述: restTemplate调用SOA
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     wangbiao:   2014年6月18日      新建
     * </pre>
     *
     * @param shortLink
     * @param clazz
     * @param req
     * @return
     */
    protected <T> T postForObject(String shortLink, Object req, Class<T> clazz) {
        return restTemplate.postForObject(rootUrl + shortLink, req, clazz);
    }

    protected <T> T getForObject(String shortLink, Class<T> clazz, HashMap<String, ?> map) {
        return restTemplate.getForObject(rootUrl + shortLink, clazz, map);
    }

}
