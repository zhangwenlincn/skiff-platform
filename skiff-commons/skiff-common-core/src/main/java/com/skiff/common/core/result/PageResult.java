package com.skiff.common.core.result;

import com.skiff.common.core.enums.BaseCodeEnum;
import com.skiff.common.core.enums.BaseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageResult<T> extends BaseResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分页信息
     */
    private Pager page;

    /**
     * 页码对应数据
     */
    private List<T> list;

    public PageResult() {
    }

    public PageResult(Boolean success) {
        super(success);
    }

    public PageResult(BaseEnum baseEnum) {
        super(baseEnum);
    }

    public PageResult(Boolean success, String code, String message) {
        super(success, code, message);
    }

    public PageResult(Pager pager, List<T> list) {
        super(Optional.ofNullable(list).map(e -> BaseCodeEnum.SUCCESS).orElse(BaseCodeEnum.ERROR));
        this.page = pager;
        this.list = list;
    }


}
