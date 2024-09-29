package com.skiff.common.core.result;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.code.Code;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
public class ObjectResult<T> extends BaseResult implements Result<T>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 业务数据
     */
    private T data;

    public ObjectResult() {
    }

    public ObjectResult(boolean success) {
        super(success);
    }

    public ObjectResult(Code code) {
        super(code);
    }

    public ObjectResult(T t) {
        super(Optional.ofNullable(t).map(e -> BaseCodeEnum.SUCCESS).orElse(BaseCodeEnum.ERROR));
        this.data = t;
    }

    public ObjectResult(boolean success, String code, String message) {
        super(success, code, message);
    }
}