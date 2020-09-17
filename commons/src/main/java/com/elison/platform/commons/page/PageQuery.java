package com.elison.platform.commons.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.page
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/3 16:38
 * @UpdateDate: 2020/9/3 16:38
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery implements Serializable {
    private static final long serialVersionUID = 1207386372854887783L;
    private Long pageSize = 10L;
    private Long currentPage;

    public <T> Page<T> getPage() {
        if (currentPage != null && currentPage != 0L) {
            return new Page<T>(this.currentPage, this.pageSize);
        } else {
            return null;
        }
    }
}
