package com.elison.platform.commons.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @Min(5)
    @Max(50)
    @Range(min = 5, max = 50, message = "分页大小请在5-50之间")
    private Long size = 10L;
    @NotNull
    @Range(min = 1, message = "请从第一页开始")
    private Long page = 1L;

    public <T> Page<T> convertPage() {
        if (page != null && page != 0L) {
            return new Page<T>(this.page, this.size);
        } else {
            return null;
        }
    }
}
