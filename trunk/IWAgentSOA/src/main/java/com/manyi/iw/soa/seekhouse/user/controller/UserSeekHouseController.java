package com.manyi.iw.soa.seekhouse.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.manyi.iw.soa.appointment.service.AppointmentServiceI;
import com.manyi.iw.soa.base.controller.BaseController;
import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.common.model.Response;
import com.manyi.iw.soa.seekhouse.model.SeeHouseVo;
import com.manyi.iw.soa.seekhouse.model.WillAppointMentVo;
import com.manyi.iw.soa.seekhouse.user.model.SeekhousePositionResponse;
import com.manyi.iw.soa.seekhouse.user.service.UserSeekHouseServiceI;
import com.manyi.iw.soa.user.model.UpdateDiscContentRequest;
import com.manyi.iw.soa.user.service.UserServiceImpl;

@Controller
@RequestMapping(value = "/userSeekHouse", produces = { "application/json;charset=UTF-8" })
public class UserSeekHouseController extends BaseController {

    @Autowired
    private UserSeekHouseServiceI seekHouseServiceI;

    @Autowired
    private AppointmentServiceI appointmentServiceI;

    @Autowired
    private UserServiceImpl userService;

    //    @Autowired
    //    private RestTemplate restTemplate;

    /**
     * 功能描述:TODO(描述这个方法的作用)
     *
     * <pre>
     *     Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     wangbiao:   2014年6月18日      新建
     * </pre>
     *
     * @param agentId
     * @return
     */
    @RequestMapping("/getWaitDealSeeHouseList")
    @ResponseBody
    public PageResponse<SeeHouseVo> getWaitDealList(@RequestBody String data) {

        logger.info("getWaitDealSeeHouseList");

        JSONObject jsons = JSONObject.parseObject(data);

        List<SeeHouseVo> list = seekHouseServiceI.getWaitDealSeeHouse(jsons.getLong("customerId"),
                jsons.getByte("type"));

        PageResponse<SeeHouseVo> pageResponse = new PageResponse<SeeHouseVo>();
        pageResponse.setData(list);

        return pageResponse;
    }

    @RequestMapping("/getCancelListSeeHouseList")
    @ResponseBody
    public PageResponse<SeeHouseVo> getCancelList(@RequestBody Long userId) {

        List<SeeHouseVo> list = seekHouseServiceI.getCancelSeeHouse(userId);

        PageResponse<SeeHouseVo> pageResponse = new PageResponse<SeeHouseVo>();
        pageResponse.setData(list);

        return pageResponse;
    }

    @RequestMapping("/getWillAppointmentList")
    @ResponseBody
    public PageResponse<WillAppointMentVo> getWillAppointmentList(@RequestBody Long houseId) {

        List<WillAppointMentVo> list = appointmentServiceI.getWillAppointment(houseId);
        PageResponse<WillAppointMentVo> pageResponse = new PageResponse<WillAppointMentVo>();
        pageResponse.setData(list);

        return pageResponse;
    }

    /**
     * 功能描述:记录联系房东信息
     * 如果 type 是房子已出租（1）  或者 房子停止出租（2）  取消所有，待预约，约看待处理房源。 
     * TODO 同时调  callcenter 接口  
     * 如果type 是 3 或者 4 记录文本
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param houseId
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping("/recordContact")
    @ResponseBody
    public PageResponse recordContact(@RequestBody String data) {

        PageResponse pageResponse = new PageResponse();
        JSONObject datajson = JSONObject.parseObject(data);
        JSONObject jsons = JSONObject.parseObject(datajson.getString("params"));
        if (jsons.getString("type").equals("1") || jsons.getString("type").equals("2")) {

            if (!seekHouseServiceI.offSaleSeekHouseByhoustId(jsons.getLong("id"))) {
                pageResponse.setErrorCode(1);
            }//本地房源下架操作 后调接口
            logger.info("经纪人Id：{}调用房源接口下架出错，房源id:{},出错码:{}", datajson.getInteger("agentId"),
                    jsons.getInteger("houseId"), pageResponse.getErrorCode());

            UpdateDiscContentRequest updateDiscContenRequest = new UpdateDiscContentRequest();
            updateDiscContenRequest.setHostMobile(jsons.getString("lianxifs"));//
            updateDiscContenRequest.setHostName("");//
            updateDiscContenRequest.setHouseId(jsons.getInteger("houseId"));//
            updateDiscContenRequest.setRemark("联系房东后房源已下架");
            updateDiscContenRequest.setRentType(jsons.getInteger("type"));//(1.已出租;2,不出租)
            updateDiscContenRequest.setUserId(datajson.getInteger("agentId"));

            //MsResponse reponse = restTemplate.postForObject("http://192.168.1.149:80/rest/sourceLog/updateDisc.rest",updateDiscContenRequest, MsResponse.class
            //            if (reponse.getErrorCode() > 0) {
            //                logger.info("经纪人Id：{}调用房源接口下架出错，房源id:{},出错码:{}", datajson.getInteger("agentId"),
            //                        jsons.getInteger("houseId"), pageResponse.getErrorCode());
            //            }
            //            else {
            //                logger.info("经纪人Id：{}调用房源接口下架成功，房源id:{}", datajson.getInteger("agentId"), jsons.getInteger("houseId"));
            //            }
        }
        else {//修改备注
            seekHouseServiceI.updateSeekHouseMemo(jsons.getLong("id"), jsons.getString("memo"));
        }
        return pageResponse;
    }

    /****
     * 
     * 功能描述:彻底取消某个约看，根据约看id 删除记录
     * 删除某个房源 如果这个房源是来自看经纪人推荐看房单，则入看房单表@TODO
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param id
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/cancelSeekHouse")
    @ResponseBody
    public PageResponse cancelSeekHouse(@RequestBody String id) {

        PageResponse pageResponse = new PageResponse();
        seekHouseServiceI.deleteSeekHouseById(Long.valueOf(id));
        return pageResponse;
    }

    /****
     * 
     * 经纪人发起约会，推送消息
     * TODO 调接口 发送 消息
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param id
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/addApponitment")
    @ResponseBody
    public Response addApponitmentSeekHouse(@RequestBody String params) {

        Response response = new Response();

        JSONObject jsons = JSONObject.parseObject(params);

        String paramStr = jsons.getString("params");

        if (seekHouseServiceI.addAppointment(paramStr, jsons.getLong("agentId"))) {//本地事物成功再发短信
            response.setMessage("约会添加成功");
            logger.info("经纪人id{}推送短信开始，约会id{}.客户id{}", jsons.getLong("agentId"), jsons.getLong("appointmentId"),
                    jsons.getLong("customerId"));
            //            Map map = new HashMap<String, String>();
            //            map.put("mobile", userService.getUserDetail(jsons.getLong("customerId")).getMobile());
            //            map.put("content", "textccc");
            //            MsResponse resposeMsg = restTemplate.postForObject("http://192.168.1.150:8081/rest/common/sendSMS.rest", map,
            //                    MsResponse.class);
            //            respose.getMessage();//不等于0失败
            //            if (resposeMsg.getErrorCode() == 0) {//短信发送成功
            //                response.setErrorCode(0);
            //                response.setMessage("添加失败");
            //            }
            //            else {
            //                response.setErrorCode(1);
            //            }
        }
        else {
            response.setErrorCode(1);
        }
        return response;

    }

    /**
     * 
     * 功能描述: 保存推荐的房源
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/addRecommand", method = RequestMethod.POST)
    @ResponseBody
    public PageResponse addRecommand(@RequestBody String params) {

        PageResponse pageResponse = new PageResponse();
        seekHouseServiceI.addRecommand(params);
        return pageResponse;
    }

    /**
     * 功能描述:获取看房单中状态为预约中、该其中、取消中状态的数据的经纬度集合
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月24日      新建
     * </pre>
     *
     * @param map{userId,agentId}
     * @return
     */
    @RequestMapping(value = "/position/list")
    @ResponseBody
    public List<SeekhousePositionResponse> getSeekhousePosition(@RequestBody Map<String, Object> map) {
        return seekHouseServiceI.getSeekhousePositionList(map);
    }

}
