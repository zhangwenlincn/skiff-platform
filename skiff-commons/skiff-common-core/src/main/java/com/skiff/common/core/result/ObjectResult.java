package com.skiff.common.core.result;

import com.skiff.common.core.enums.BaseCodeEnum;
import com.skiff.common.core.enums.BaseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
public class ObjectResult<T> extends BaseResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private T data;

    public ObjectResult() {
    }

    public ObjectResult(boolean success) {
        super(success);
    }

    public ObjectResult(BaseEnum baseEnum) {
        super(baseEnum);
    }

    public ObjectResult(T t) {
        super(Optional.ofNullable(t).map(e -> BaseCodeEnum.SUCCESS).orElse(BaseCodeEnum.ERROR));
        this.data = t;
    }

    public ObjectResult(boolean success, String code, String message) {
        super(success, code, message);
    }
}