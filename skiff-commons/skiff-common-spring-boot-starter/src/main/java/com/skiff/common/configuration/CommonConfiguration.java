package com.skiff.common.configuration;

import com.skiff.common.exception.GlobalExceptionHandler;
import com.skiff.common.util.SpringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({GlobalExceptionHandler.class, SpringUtil.class})
public class CommonConfiguration {

}
