package com.stormfives.admin.thirdparty;

import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.common.util.PictureCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by zxb on 2016/12/6.
 */
@RestController
@RequestMapping("/api/coin/")
public class PicCodeController {

    @Autowired
    private PictureCodeService pictureCodeService;

    /**
     * @return
     * @throws InvalidArgumentException
     */
    @GetMapping("v1/pic/code")
    public Map getPicCode() throws InvalidArgumentException {
        return pictureCodeService.getPcrimg();
    }

}
