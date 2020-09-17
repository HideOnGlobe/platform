package com.elison.platform.commons.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -975487146783750574L;
    private Long pageSize;
    private Long currentPage;
    private Long totalPage;
    private List<T> records;

    public static <T> PageResult<T> getPageResult(IPage<T> iPage) {
        return iPage != null
                ? new PageResult<T>(iPage.getSize(), iPage.getCurrent(), iPage.getTotal(), iPage.getRecords())
                : null;
    }


}
