package springRibbon.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springRibbon.Service.ComputerService;

/**
 * Created by Administrator on 2017/5/15.
 */
@RestController
public class ConsumeController {
    @Autowired
    ComputerService computerService;
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(){
       return computerService.addService();
    }

}
