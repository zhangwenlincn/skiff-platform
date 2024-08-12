package com.skiff.common.core.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Getter;

@Getter
public enum JacksonModuleEnum {


    INSTANCE;

    private final SimpleModule simpleModule;

    JacksonModuleEnum() {
        this.simpleModule = new SimpleModule();

        //序列化设置

        this.simpleModule.addSerializer(java.lang.Long.class, SkiffJsonSerializer.LONG_SERIALIZER);
        this.simpleModule.addSerializer(java.util.Date.class, SkiffJsonSerializer.DATE_SERIALIZER);

        this.simpleModule.addSerializer(java.time.Year.class, SkiffJsonSerializer.YEAR_SERIALIZER);
        this.simpleModule.addSerializer(java.time.YearMonth.class, SkiffJsonSerializer.YEAR_MONTH_SERIALIZER);
        this.simpleModule.addSerializer(java.time.MonthDay.class, SkiffJsonSerializer.MONTH_DAY_SERIALIZER);

        this.simpleModule.addSerializer(java.time.LocalDate.class, SkiffJsonSerializer.LOCAL_DATE_SERIALIZER);
        this.simpleModule.addSerializer(java.time.LocalDateTime.class, SkiffJsonSerializer.LOCAL_DATE_TIME_SERIALIZER);
        this.simpleModule.addSerializer(java.time.LocalTime.class, SkiffJsonSerializer.LOCAL_TIME_SERIALIZER);


        //反序列化设置
        this.simpleModule.addDeserializer(java.util.Date.class, SkiffJsonDeserializer.DATE_DESERIALIZER);

        this.simpleModule.addDeserializer(java.time.Year.class, SkiffJsonDeserializer.YEAR_DESERIALIZER);
        this.simpleModule.addDeserializer(java.time.YearMonth.class, SkiffJsonDeserializer.YEAR_MONTH_DESERIALIZER);
        this.simpleModule.addDeserializer(java.time.MonthDay.class, SkiffJsonDeserializer.MONTH_DAY_DESERIALIZER);

        this.simpleModule.addDeserializer(java.time.LocalDate.class, SkiffJsonDeserializer.LOCAL_DATE_DESERIALIZER);
        this.simpleModule.addDeserializer(java.time.LocalDateTime.class, SkiffJsonDeserializer.LOCAL_DATE_TIME_DESERIALIZER);
        this.simpleModule.addDeserializer(java.time.LocalTime.class, SkiffJsonDeserializer.LOCAL_TIME_DESERIALIZER);


    }

    public JacksonModuleEnum getInstance() {
        return INSTANCE;
    }
}
