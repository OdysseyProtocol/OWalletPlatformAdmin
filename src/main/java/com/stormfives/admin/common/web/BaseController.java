package com.stormfives.admin.common.web;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
@Component
public class BaseController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                if (value != null && !value.isEmpty()) {

                    if (value.contains("-")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            setValue(dateFormat.parse(value));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        setValue(new Date(Long.valueOf(value))); //将时间戳格式封装为date
                    }
                }

            }
        });

    }

}
