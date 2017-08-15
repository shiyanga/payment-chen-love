package com.payment.controller;
import com.payment.mapper.bnbordermapper.OrderTaskDao;
import com.payment.service.groupwormholeservice.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by shi_y on 2016/9/6.
 */
@Controller
public class MainController extends BasicController {
    @Autowired
    private OrderTaskDao orderTaskDao;

    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView pageForward() {
        String test = cacheService.guavaCache60seconds("test");
      //  List<FeeBase> listFee = cacheService.selectList(null);
        return modelAndView("welcome");
    }


    @RequestMapping(value = "index_v1", method = RequestMethod.GET)
    public String indexv1() {
        // orderTaskDao.selectByPrimaryKey(1l);
        return "include/index_v1";
    }
}
