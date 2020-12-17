package com.elison.platform.commons.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.page
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/3 16:40
 * @UpdateDate: 2020/9/3 16:40
 **/
@Data
@Accessors(chain = true)
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -975487146783750574L;
    private Long pageSize;
    private Long currentPage;
    private Long totalPage;
    private Long totalCount;
    private List<T> records;

    public <T> PageResult (IPage iPage) {
        if (iPage != null) {
            this.pageSize = iPage.getSize();
            this.currentPage = iPage.getCurrent();
            this.totalCount = iPage.getTotal();
            this.totalPage = iPage.getTotal() / iPage.getSize() + (iPage.getTotal() % iPage.getSize() > 0 ? 1 : 0);
            this.records = new ArrayList<>();
        } else {
            this.records = new ArrayList<>();
        }
    }

}
